package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestHttpClients {
      @Test
      public void test01() throws ClientProtocolException, IOException {
    	  //创建HttpClients的实例
    	  CloseableHttpClient httpClient = HttpClients.createDefault();
           //定义访问的ip地址
    	  String url = "https://www.baidu.com/";
    	  //设定请求
    	  HttpPost httpPost = new HttpPost(url);
    	  //获取response对象
    	  CloseableHttpResponse response = httpClient.execute(httpPost);
    	  String html = EntityUtils.toString(response.getEntity());  //获取页面信息
  		System.out.println(html);
      }
}
