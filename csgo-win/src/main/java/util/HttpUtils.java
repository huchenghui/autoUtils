package util;

import com.alibaba.fastjson.JSONObject;
import config.EnumHttp;
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

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
    private static CloseableHttpClient client = null;
    private static CloseableHttpResponse response = null;



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
        LOG.info(builder.toString());
        return builder.toString();
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
    public static CloseableHttpResponse doUpload(String url, JSONObject json,String fieldName,File filePath,String author) throws IOException {
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(fieldName,new FileInputStream(filePath),ContentType.DEFAULT_BINARY,filePath.getName());

        if (json != null){
            for (Map.Entry<String,Object> entry : json.entrySet()){
                builder.addTextBody(entry.getKey(),entry.getValue().toString());
            }
        }
        HttpPost post = new HttpPost(url);
        post.setEntity(builder.build());
        if (author != null){
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
    public static CloseableHttpResponse doGet(String url,JSONObject json,String author) throws URISyntaxException, IOException {
        URIBuilder builder = new URIBuilder(url);
        if (json != null){
            for (Map.Entry<String,Object> entry : json.entrySet()){
                builder.addParameter(entry.getKey(),entry.getValue().toString());
            }
        }

        HttpGet get = new HttpGet(builder.build());
        if (author != null){
            get.setHeader("Authorization","Bearer "+ author);
        }

        LOG.info("Get对象 >> {}",get);

        response = (CloseableHttpResponse) createClient().execute(get);


        return response;
    }

    /**
     * 发送post请求，请求体为json格式
     * @param url 请求地址
     * @param jsonBody 请求参数
     * **/
    public static CloseableHttpResponse doPost(String url,JSONObject jsonBody,String author) throws Exception {
        return doRequest("post",url,jsonBody,author);
    }
    
    public static CloseableHttpResponse doPut(String url,JSONObject jsonBody,String author) throws Exception {
        return doRequest("put",url,jsonBody,author);
    }
    
    public static CloseableHttpResponse doDelete(String url,JSONObject jsonBody,String author) throws Exception {
        return doRequest("delete",url,jsonBody,author);
    }
    

    private static CloseableHttpResponse doRequest(String method,String url,JSONObject jsonBody,String author) throws IOException {
        StringEntity entity;
        RequestBuilder request = null;
        
        if ("post".equalsIgnoreCase(method)){
            request = RequestBuilder.post(url);
        }else if ("put".equalsIgnoreCase(method)){
            request = RequestBuilder.put(url);

        }else if ("delete".equalsIgnoreCase(method)){
            request = RequestBuilder.delete(url);
        }
        
        if (jsonBody != null){
            entity = new StringEntity(jsonBody.toJSONString(), ContentType.APPLICATION_JSON);
            if (request != null) {
                request.setEntity(entity);
            }
        }
        
        if (author != null){
            if (request != null) {
                request.setHeader("Authorization","Bearer "+ author);
            }
        }


        if (request != null) {
            response = (CloseableHttpResponse) createClient().execute(request.build());
        }

        return response;
    }

    /**
     发送post请求，请求体为表单格式
     * @param url 请求地址
     * @param json 请求参数
     * **/
    public static CloseableHttpResponse doPostByFormData(String url,JSONObject json,String author) throws IOException {
        if (json.isEmpty()){
            return null;
        }
        HttpPost post = new HttpPost(url);
        List<NameValuePair> list = new ArrayList<>();
        for (Map.Entry<String,Object> map : json.entrySet()){
            list.add(new BasicNameValuePair(map.getKey(),map.getValue().toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(list));
        if (author != null){
            post.setHeader("Authorization","Bearer "+ author);
        }

        response = (CloseableHttpResponse) createClient().execute(post);

        return response;
    }

    /**
     * 创建HttpClient客户端
     * **/
    private static HttpClient createClient(){
        if (client == null){
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
