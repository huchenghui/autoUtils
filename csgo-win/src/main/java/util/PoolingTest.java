package util;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class PoolingTest {
    private HttpUtils httpUtils;

    @BeforeClass
    public void before(){
        httpUtils = new HttpUtils();
    }

    @AfterClass
    public void after(){
        try {
            httpUtils.consumerResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


   @Test
    public void baidu(){
       try {
           httpUtils.analysis(httpUtils.doGet("http://www.baidu.com",null));
       } catch (IOException | URISyntaxException e) {
           e.printStackTrace();
       }
   }

    @Test
    public void taobao(){
        try {
            httpUtils.analysis(httpUtils.doGet("http://www.taobao.com",null));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void sougou(){
        try {
            httpUtils.analysis(httpUtils.doGet("https://www.sohu.com",null));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
