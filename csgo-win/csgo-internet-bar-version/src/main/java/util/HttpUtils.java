package util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
import io.qameta.allure.Allure;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.util.Strings;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    private static CloseableHttpClient client = null;
    private static CloseableHttpResponse response = null;


    public static void addAllureResp(String resp){
        Allure.description("response data >>>\n\n" + resp);
    }

    /**
     * 解析响应
     * @param response 响应体
     * **/
    public static String analysis(HttpResponse response) throws IOException {
        StringBuilder builder = new StringBuilder();
        InputStream stream = response.getEntity().getContent();
        InputStreamReader inputStreamReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
        BufferedReader reader = new BufferedReader(inputStreamReader);

        String len;
        while ((len = reader.readLine()) != null){
            builder.append(len);
        }
        LOG.info("RESPONSE IS >> {}",builder.toString());
        return builder.toString();
    }

    public static String runCaseByExcel(String testCase,String token) throws Exception {
        String resp;
        JSONObject ddt = JSON.parseObject(testCase);
        LOG.info("test case id is >> {}",ddt.getString("id"));
        String url = EnumHttp.API_DOMAIN.getVal() + ddt.getString("api_path");
//        LOG.info("REQUEST URL IS >> {}",url);
        String reqData = ddt.getString("parameter_data");
//        LOG.info("REQUEST PARAM IS >> {}",reqData);
        String transferType = ddt.getString("transfer_type");
        String method = ddt.getString("method");
//        LOG.info("REQUEST METHOD IS >> {}",method);

        if ("get".equalsIgnoreCase(method))
        {
            resp = analysis(doGet(url,reqData,token));
        }
        else if ("post".equalsIgnoreCase(method))
        {
            if (Strings.isNullOrEmpty(transferType)) {
                resp = analysis(doPost(url, reqData, token,ddt.getString("title")));
            }
            else {
                resp = analysis(Objects.requireNonNull(doPostByFormData(url, reqData, token)));
            }
        }
        else if ("put".equalsIgnoreCase(method))
        {
            if (Strings.isNullOrEmpty(transferType)) {
                resp = analysis(doPut(url, reqData, token,ddt.getString("title")));
            }
            else {
                resp = analysis(Objects.requireNonNull(doPutByFormData(url, reqData, token)));
            }
        }
        else if ("delete".equalsIgnoreCase(method))
        {
            resp = analysis(doDelete(url,reqData,token,ddt.getString("title")));
        }
        else
        {
            throw new Exception("Unsupported request method >>> " + method);
        }
        return resp;
    }

    /**
     * 释放资源
     * **/
    public static void consumerResource() throws IOException {
        if (response != null){
            response.close();
        }
        if (client != null){
            client.close();
        }
    }

    /**
     * 上传文件
     * @param url 请求地址
     * @param json 请求参数
     * @param fieldName 接口字段
     * @param filePath 文件路径
     * **/
    public static CloseableHttpResponse doUpload(String url, String json,String fieldName,File filePath,String author) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(fieldName,new FileInputStream(filePath),ContentType.DEFAULT_BINARY,filePath.getName());

        if (Strings.isNotNullAndNotEmpty(json))
        {
            for (Map.Entry<String,Object> entry : JSON.parseObject(json).entrySet()){
                builder.addTextBody(entry.getKey(),entry.getValue().toString());
            }
        }
        HttpPost post = new HttpPost(url);
        post.setEntity(builder.build());
        Allure.parameter("request data >>>",json);
        if (Strings.isNotNullAndNotEmpty(author))
        {
            post.setHeader("Authorization","Bearer "+ author);
        }

        response = (CloseableHttpResponse) createClient().execute(post);

        return response;
    }

    /**
     * 发送get请求
     * @param url 请求地址
     * @param json 请求参数
     **/
    public static CloseableHttpResponse doGet(String url,String json,String author) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url);
        if (Strings.isNotNullAndNotEmpty(json))
        {
            for (Map.Entry<String,Object> entry : JSON.parseObject(json).entrySet())
            {
                builder.addParameter(entry.getKey(),entry.getValue().toString());
            }
        }

        HttpGet get = new HttpGet(builder.build());
        if (Strings.isNotNullAndNotEmpty(author))
        {
            get.setHeader("Authorization","Bearer "+ author);
        }

        LOG.info("Get对象 >> {}",get);

        response = (CloseableHttpResponse) createClient().execute(get);


        return response;
    }

    /**
     * 发送post请求，请求体为json格式
     * @param url 请求地址
     * @param requestData 请求参数
     * **/
    public static CloseableHttpResponse doPost(String url,String requestData,String author,String testDesc) throws Exception {
        return doRequest("post",url,requestData,author,testDesc);
    }
    
    public static CloseableHttpResponse doPut(String url,String requestData,String author,String testDesc) throws Exception {
        return doRequest("put",url,requestData,author,testDesc);
    }
    
    public static CloseableHttpResponse doDelete(String url,String requestData,String author,String testDesc) throws Exception {
        return doRequest("delete",url,requestData,author,testDesc);
    }
    

    private static CloseableHttpResponse doRequest(String method,String url,String requestData,String author,String testDesc) throws IOException {
        LOG.info("REQUEST URL IS >> {}",url);
        LOG.info("REQUEST METHOD IS >> {}",method);
        LOG.info("REQUEST PARAM IS >> {}",requestData);
        LOG.info("REQUEST AUTHOR IS >> {}",author);

        StringEntity entity;
        RequestBuilder request = null;

        if ("post".equalsIgnoreCase(method))
        {
            request = RequestBuilder.post(url);
        }
        else if ("put".equalsIgnoreCase(method))
        {
            request = RequestBuilder.put(url);

        }
        else if ("delete".equalsIgnoreCase(method))
        {
            request = RequestBuilder.delete(url);
        }
        
        if (Strings.isNotNullAndNotEmpty(requestData))
        {
            entity = new StringEntity(requestData, ContentType.APPLICATION_JSON);
            if (request != null)
            {
                request.setEntity(entity);
            }
        }
        Allure.parameter("request data >>>",requestData);
        Allure.description("test case description >>>\n\t" + testDesc);
        
        if (Strings.isNotNullAndNotEmpty(author))
        {
            if (request != null)
            {
                request.setHeader("Authorization","Bearer "+ author);
            }
        }

        if (request != null)
        {
            response = (CloseableHttpResponse) createClient().execute(request.build());
        }

        return response;
    }

    /**
     发送post请求，请求体为表单格式
     * @param url 请求地址
     * @param json 请求参数
     * **/
    public static CloseableHttpResponse doPostByFormData(String url,String json,String author) throws IOException {
        HttpPost post = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<>();
        if (Strings.isNotNullAndNotEmpty(json))
        {
            for (Map.Entry<String, Object> map : JSON.parseObject(json).entrySet())
            {
                list.add(new BasicNameValuePair(map.getKey(), map.getValue().toString()));
            }
            post.setEntity(new UrlEncodedFormEntity(list));
        }
        Allure.parameter("request data >>>",json);
        if (Strings.isNotNullAndNotEmpty(author))
        {
            post.setHeader("Authorization","Bearer "+ author);
        }

        response = (CloseableHttpResponse) createClient().execute(post);

        return response;
    }

    public static CloseableHttpResponse doPutByFormData(String url,String json,String author) throws IOException {
        HttpPut put = new HttpPut(url);
        List<NameValuePair> list = new ArrayList<>();
        if (Strings.isNotNullAndNotEmpty(json))
        {
            for (Map.Entry<String, Object> map : JSON.parseObject(json).entrySet())
            {
                list.add(new BasicNameValuePair(map.getKey(), map.getValue().toString()));
            }
            put.setEntity(new UrlEncodedFormEntity(list));
        }
        Allure.parameter("request data >>>",json);
        if (Strings.isNotNullAndNotEmpty(author))
        {
            put.setHeader("Authorization","Bearer "+ author);
        }

        response = (CloseableHttpResponse) createClient().execute(put);

        return response;
    }

    public static String few(int num){
        StringBuilder str = new StringBuilder();
        for (int i = 0;i < num;i++){
            str.append(Integer.toString(random()));
        }
        return str.toString();
    }

    public static int random(){
        Random r = new Random();
        return r.nextInt(10);
    }

    public static Object[][] dataProvider(String filePath,int sheetNo,String funcName) throws Exception {
        FileUtils ddt = new FileUtils(filePath,sheetNo);
        List<Map<String,Object>> list = ddt.read(funcName);

        List<String> keys = new LinkedList<>();
        Object[][] objs = new Object[list.size()][2];

        JSONObject testCase;
        JSONObject jsonObject;

        for (int i = 0; i < list.size(); i++)
        {
            jsonObject = new JSONObject();
            testCase = new JSONObject(list.get(i));
            LOG.info("from {} ddt is {}",filePath,testCase);
            for (Map.Entry<String, Object> entry : testCase.entrySet())
            {
                keys.add(entry.getKey());
            }

            for (int j = 0; j < testCase.size(); j++)
            {
                jsonObject.put(keys.get(j),testCase.getString(keys.get(j)));
            }
            objs[i][0] = jsonObject.toJSONString();
            objs[i][1] = jsonObject.getString("expected_result");
            keys.clear();
        }
        return objs;
    }


    /**
     * 创建HttpClient客户端
     * **/
    private static HttpClient createClient(){
        if (client == null)
        {
            client = HttpClients.custom()
                    .setConnectionManager(poolingManager())
                    .setDefaultRequestConfig(requestConfig())
                    .setConnectionManagerShared(true)
                    .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                    .build();
        }

        return client;
    }

    /**
     * 连接配置
     * **/
    private static RequestConfig requestConfig(){
        int timeOut = Integer.parseInt(EnumHttp.TIME_OUT.getVal());
        return RequestConfig.custom()
                    .setConnectionRequestTimeout(timeOut)
                    .setSocketTimeout(timeOut)
                    .setConnectTimeout(timeOut)
                    .build();

    }

    /**
     * 创建连接池
     * **/
    private static PoolingHttpClientConnectionManager poolingManager(){
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(Integer.parseInt(EnumHttp.MAX_TOTAL.getVal()));
        connectionManager.setDefaultMaxPerRoute(Integer.parseInt(EnumHttp.MAX_PRE_ROUTER.getVal()));

        return connectionManager;

    }

}
