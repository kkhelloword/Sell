package com.example.sell.repository.sms;


import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


/**
 * 创建外围接口XML 
 * @author Administrator
 *
 */
public class MakeCustXml {
	/**
	 * 统一接口模拟报文
	 * @return
	 */
	public static String getCustXmlstr(String MSG_NUM, String sys,String telephone,String muban,String batch,String custMsgNo,
			String acttimeFlag) {
	     StringWriter buffer = new StringWriter();
	     // 生成头
	     XMLStreamWriter writer =MakeXmlUtil.createStartDocument(buffer);

	     try {
	    	 MakeXmlUtil.startElement("PACKET",writer);
	    	 MakeXmlUtil.startElement("HEAD",writer);
	    	 Map<String,String> headMap = new HashMap<String,String>();  
	    	 headMap.put("REQUEST_TYPE", "C01");
	    	 MakeXmlUtil.addMapToElement(headMap,writer);
	    	 MakeXmlUtil.endElement("HEAD",writer);
	    	 MakeXmlUtil.startElement("BODY",writer);
	    	 MakeXmlUtil.startElement("BATCH_INFO",writer);
	    	 Map<String,String> batchInfoMap = new HashMap<String,String>();

	    	 batchInfoMap.put("CUST_SYS", sys);
	    	 batchInfoMap.put("CUST_BATCH_NO", batch);//"1000000208"
	    	 batchInfoMap.put("MSG_NUM", MSG_NUM); //消息条数
	    	 
	    	 //实时标示  1 实时; 2 延时
	    	 batchInfoMap.put("ACTTIME_FLAG", acttimeFlag);
	    	
	    	 MakeXmlUtil.addMapToElement(batchInfoMap,writer);    	
	    	 MakeXmlUtil.endElement("BATCH_INFO",writer);
	    	 MakeXmlUtil.startElement("MSG_LIST",writer);
	    	 //测试根据批次对应数量循环的 实际开发应该为对象集合循环赋值
	    	 int msgCount = Integer.valueOf(batchInfoMap.get("MSG_NUM"));
	    	 for (int i = 0; i < msgCount; i++) {
	    		 //long s= Long.valueOf(custMsgNo)+i;
	    		 MakeXmlUtil.startElement("MSG_INFO",writer);
	    		 Map<String,String> msgInfoMap = new HashMap<String,String>();  
	    		 msgInfoMap.put("TPL_ID", muban);//"900002"
	    		 
	    		 //手机号码
//	    		 msgInfoMap.put("CELLER", telephone+new Random(4).nextInt(10000));
	    		 msgInfoMap.put("CELLER", telephone);
	    		 msgInfoMap.put("WECHAT", "");
//	    		 消息编号
	    		 msgInfoMap.put("CUST_MSG_NO",String.valueOf(123456789));

	    		 msgInfoMap.put("CHANNEL_TYPE", "1");
//	    		 msgInfoMap.put("ORGAN_ID", "109");
				 //TODO 机构编码
//	    		 msgInfoMap.put("ORGAN_ID", "101");
	    		 msgInfoMap.put("ORGAN_ID", "");
//	    		 msgInfoMap.put("DEPT_ID", "109-1004");
				 //TODO 渠道编码
	    		 msgInfoMap.put("CHANNEL_ID", "CHANNEL_ZHYYT");
	    		 
	    		 MakeXmlUtil.addMapToElement(msgInfoMap,writer);
	    		 //FIELD_LIST表示的是一个msg用到多个字段
	    		 MakeXmlUtil.startElement("FIELD_LIST",writer);
	    		 Map<String,String> fieldMap = new HashMap<String,String>(); 
	    		 //900269
				 fieldMap.put("NAME", "白玉杰");
				 fieldMap.put("GENDER", "先生");
				 // 备注
				 fieldMap.put("TO_STORE_COUNT", "66");

	    		 
	    		 MakeXmlUtil.addMapToElement(fieldMap,writer);    	
	    		 MakeXmlUtil.endElement("FIELD_LIST",writer);
	    		 MakeXmlUtil.endElement("MSG_INFO",writer);
	    	 }
	    	 MakeXmlUtil.endElement("MSG_LIST",writer);
	    	 MakeXmlUtil.endElement("BODY",writer);
	    	//加入校验密钥 KEY_CODE ：TP_AUTO_MSS_CUST （18位）
	    	 String xmlstr=buffer.toString();
	    	 String bodystr="";	    	
	    	 if(xmlstr.indexOf("<BODY>")!=-1){
	    		 bodystr = xmlstr.substring(xmlstr.indexOf("<BODY>"));
	    	 }	    	
	    	 
	    	 //System.out.println("------------------------");
	    	 //System.out.println(bodystr);
	    	 
	    	 String verifyCode ="";
			try {
				verifyCode = OneWayHash.encrypt(URLEncoder.encode(SysCommIdentify.KEY_CODE+"||"+bodystr.trim(), "UTF-8"));
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	    	//System.out.println(verifyCode);
	    	 
	    	Map<String,String> verifyCodeMap = new HashMap<String,String>(); 
	    	verifyCodeMap.put("VERIFY_CODE", verifyCode);
	    	MakeXmlUtil.addMapToElement(verifyCodeMap,writer);   
	    	//加入校验密钥 end
	    	MakeXmlUtil.endElement("PACKET",writer);
	     } catch (XMLStreamException e) {
	         e.printStackTrace();
	     }
	     
	     return buffer.toString();
	 }
	

	/**
	 * 统一接口查询模拟报文
	 * @return
	 */
	public static String getCustQueryXmlstr(String sys, String msgSysBatch) {
	     StringWriter buffer = new StringWriter();
	     XMLStreamWriter writer =MakeXmlUtil.createStartDocument(buffer);
	     try {
	    	 MakeXmlUtil.startElement("PACKET",writer);
	    	 MakeXmlUtil.startElement("HEAD",writer);
	    	 Map<String,String> headMap = new HashMap<String,String>();  
	    	 headMap.put("REQUEST_TYPE", "C02");
	    	 MakeXmlUtil.addMapToElement(headMap,writer);
	    	 MakeXmlUtil.endElement("HEAD",writer);
	    	 MakeXmlUtil.startElement("BODY",writer);
	    	 MakeXmlUtil.startElement("QUERY_INFO",writer);
	    	 Map<String,String> batchInfoMap = new HashMap<String,String>();  
	    	 batchInfoMap.put("MSG_QUE_CODE", msgSysBatch);
	    	 //batchInfoMap.put("SEND_DATE", "2016-12-16");
	    	 batchInfoMap.put("CUST_SYS", sys);
	    	 batchInfoMap.put("TPL_ID", "");
	    	 MakeXmlUtil.addMapToElement(batchInfoMap,writer);    	
	    	 MakeXmlUtil.endElement("QUERY_INFO",writer);
	    	 MakeXmlUtil.endElement("BODY",writer);
	    	//加入校验密钥 KEY_CODE ：TP_AUTO_MSS_CUST （18位）
	    	 String xmlstr=buffer.toString();
	    	 String bodystr="";	    	
	    	 if(xmlstr.indexOf("<BODY>")!=-1){
	    		 bodystr = xmlstr.substring(xmlstr.indexOf("<BODY>"));
	    	 }	    	
	    	 String verifyCode ="";
			try {
				verifyCode = OneWayHash.encrypt(URLEncoder.encode(SysCommIdentify.KEY_CODE+"||"+bodystr.trim(), "UTF-8"));
			}  catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	 Map<String,String> verifyCodeMap = new HashMap<String,String>(); 
	    	 verifyCodeMap.put("VERIFY_CODE", verifyCode);
	    	 MakeXmlUtil.addMapToElement(verifyCodeMap,writer);   
	    	 //加入校验密钥 end
	    	 MakeXmlUtil.endElement("PACKET",writer);
	     } catch (XMLStreamException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	     return buffer.toString();
	 }
	public static void main(String[] args) {
		System.out.println(new Random().nextInt(10000));
	}
}
