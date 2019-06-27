package com.jt;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.validator.constraints.Range;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jt.util.HttpClientService;

@SpringBootTest
@RunWith(SpringRunner.class)

public class TestHttpClients {
	
	@Autowired
	private CloseableHttpClient httpClient;
	
      @Test
      public void test01() throws ClientProtocolException, IOException {
//    	  //创建HttpClients的实例
//    	  CloseableHttpClient httpClient = HttpClients.createDefault();
           //定义访问的ip地址
    	  String url = "https://www.baidu.com/";
    	  //设定请求
    	  HttpPost httpPost = new HttpPost(url);
    	  //获取response对象
    	  CloseableHttpResponse response = httpClient.execute(httpPost);
    	  String html = EntityUtils.toString(response.getEntity());  //获取页面信息
  		System.out.println(html);
      }
      
      
      //测试工具api
      @Autowired
      private HttpClientService httpClientService;
      @Test
      public void test02() {
    	  String url = "https://www.baidu.com/";
    	  String result = httpClientService.doGet(url);
    	  System.out.println(result);
      }
      
      
      
      
}
