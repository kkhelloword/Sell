package com.example.sell.repository.sms;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class MakeXmlUtil {
	 /**
     * 生成xml第一行：<?xml version="1.0" encoding="UTF-8"?>
     * @param buffer
     * @return
     */
    public static XMLStreamWriter createStartDocument(StringWriter buffer) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        outputFactory.setProperty("javax.xml.stream.isRepairingNamespaces",
                new Boolean(true));
        XMLStreamWriter writer = null;
        try {
            writer = outputFactory.createXMLStreamWriter(buffer);
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeCharacters("\r\n");
        } catch (XMLStreamException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return writer;
    }
	 /**
	  * 将Map转为元素 key为标签 value为值  
	  * @param map
	  * @param writer
	  */
	 @SuppressWarnings("rawtypes")
	public static void addMapToElement(Map<String,String> map, XMLStreamWriter writer) {	     
         Iterator keyValuePairs = map.entrySet().iterator();
         for (int i = 0; i < map.size(); i++) {
             Map.Entry entry = (Map.Entry) keyValuePairs.next();
             String key = (String) entry.getKey();
             String value = entry.getValue()==null?"":(String)entry.getValue();
             addStringToElement( key, value,  writer);
         }
	 } 
	 /**
	  * 创建单个标签和内容  
	  * @param key 标签
	  * @param value 内容
	  * @param writer
	  */
	 public static void addStringToElement(String key,String value, XMLStreamWriter writer) {
	     try {
	    	 	writer.writeStartElement(key);     
	            writer.writeCharacters(value);             
	            writer.writeEndElement();	        
	     } catch (XMLStreamException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	     }
	 } 
	 
	 /**
	  * 开始标签
	  * @param name
	  * @param writer
	  * @throws XMLStreamException
	  */
	 public static void startElement( String name,XMLStreamWriter writer) throws XMLStreamException{
		 writer.writeStartElement(name);
		 if("PACKET".equals(name)){
			 writer.writeAttribute("type", "REQUEST");
			 writer.writeAttribute("version", "1.0");
		 }
	 }
	 /**
	  * 返回type为 RESPONSE
	  * @param name
	  * @param writer
	  * @param type
	  * @throws XMLStreamException
	  */
	 public static void startElement( String name,XMLStreamWriter writer,String type) throws XMLStreamException{
		 writer.writeStartElement(name);
		 if("PACKET".equals(name)){
			 writer.writeAttribute("type", type);
			 writer.writeAttribute("version", "1.0");
		 }
	 }
	 /**
	  * 结束标签 添加name参数从新封装增加可读性 防止掉了结束标签
	  * @param name 
	  * @param writer
	  * @throws XMLStreamException
	  */
	 public static void endElement(String name,XMLStreamWriter writer) throws XMLStreamException{
		 writer.writeEndElement();
	 }
}
