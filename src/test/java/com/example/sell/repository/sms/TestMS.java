package com.example.sell.repository.sms;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.InputStreamReader;

/**
 * 测试线程类
 * @author Administrator
 *
 */
public class TestMS{
	
	static String uri = "http://10.28.128.33:8001/tpl_msg_serv/cust/cust.do"; //UAT消息集群地址
	// 生产环境
	static String produce = "http://10.1.151.38/tpl_msg_serv/cust/cust.do";

	
	public static void main(String[] args) throws Exception {
		String s = "<ERROR_CODE>", em = "</ERROR_MESSAGE>";
		String ma = "<MSG_QUE_CODE>", mb = "</MSG_QUE_CODE>";
		String msg_num = "1";//消息条数
		String sys = "10";        //系统来源  Y
		String telephone = "15751160802"; //手机
		//String tplId = "831005"; //900251,841140,900258,900003
		String tplId = "8101001";  // 模板ID
		//String tplId = "811140"; 
		int batchId = 123123;  //批次号  Y
		String custMsgNo = "1"; 
		
		//实时标示  2 延时; 1 实时
		String acttimeFlag = "2";

		String content=MakeCustXml.getCustXmlstr(msg_num, sys ,telephone+"",tplId,""+batchId,custMsgNo,acttimeFlag);
		
		
		
		HttpClientBuilder httpClientBuilder  = HttpClientBuilder.create();
		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
		HttpPost httppost = new HttpPost(uri);
		StringEntity entity = new StringEntity(content,"UTF-8");
		httppost.addHeader("Content-Type","text/xml");
		httppost.addHeader("Connection", "close");
		httppost.setEntity(entity);
//		响应模型
		CloseableHttpResponse closeableHttpResponse;
		try {
//		发送post请求 得到响应
			closeableHttpResponse = closeableHttpClient.execute(httppost);
//		从响应中获取响应实体
			HttpEntity httpEntity = closeableHttpResponse.getEntity();
//      响应内容的实体流，如果失败就抛出io异常  创建字符输入流
			InputStreamReader reader = new InputStreamReader(httpEntity.getContent(),"utf8");
			char[]buff = new char[1024];
			int length=0;
			String returnXmlStr ="";
			while ((length =reader.read(buff))!=-1) {
				returnXmlStr=new String(buff,0,length);
				System.out.println("响应报文: "+new String(buff,0,length));
			}
			
			if(returnXmlStr.indexOf("MSG_QUE_CODE")!=-1){
				System.out.println("---结果：" + returnXmlStr.substring(returnXmlStr.indexOf(ma), returnXmlStr.indexOf(mb)+mb.length()));
			}
			
			if(returnXmlStr.indexOf(s) != -1){
				System.out.println("---结果：" +returnXmlStr.substring(returnXmlStr.indexOf(s), returnXmlStr.indexOf(em)+em.length()));
			}

			closeableHttpClient.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}