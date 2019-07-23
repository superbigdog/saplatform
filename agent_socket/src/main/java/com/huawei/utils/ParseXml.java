package com.huawei.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huawei.content.Content;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;

import com.huawei.bean.Widget;
import com.huawei.utils.CMDutils;

public class ParseXml {
	
	private static String xmlPath = "./tmp/";
	
	public static String getXml() {
		getLogicInterfaceXML("0.xml");
		Map<String, Widget> map = parseXML(xmlPath+"0.xml");
		String message = transforJson(map);
//		System.out.println(list.get(0));
//		System.out.println(message);
		return message;
	}
	
	public static String transforJson(Map<String, Widget> map) {
		JSONObject json = null;
		if (map.size()>0) {
			json = new JSONObject(map);
		}else {
			json = new JSONObject();
		}
		return json.toString();
	}
	
	/**
	 * 将xml文件内容读进list,并且该list符合树状建构
	 * @param xmlPath
	 * @return
	 */
	public static Map<String, Widget> parseXML(String xmlPath) {
		SAXReader sax = new SAXReader();
		File file = new File(xmlPath);
		Map<String, Widget> map = new HashMap<String, Widget>();
		if (file.exists()) {
			try {
				Document dom = sax.read(file);
				Element root = dom.getRootElement();
				map = getElement(root);
//				System.out.println("xml解析完成");
			} catch (DocumentException e) {
				e.printStackTrace();
				System.out.println(xmlPath + "parse has occurd some error");
			}
		}else {
			System.out.print(xmlPath + "Not found");
		}
		return map;
	}
	
	/**
	 *
	 * @param root
	 */
	private static Map<String,Widget> getElement(Element root){
		Map<String, Widget> map = new HashMap<String, Widget>();
		getElement(map, root,"/");
//		System.out.println(list.size());
		return map;
	}
	private static void getElement(Map<String, Widget> map,Element root,String xPath){
		List<Element> nodelist = root.elements("node");
		if (nodelist.size()>0) {
			for (Element e:nodelist) {
				String index = e.attributeValue("index");
				Widget widget = new Widget();
				widget.setIndex(e.attributeValue("index"));
				widget.setText(e.attributeValue("text"));
				widget.setResource_id(e.attributeValue("resource-id"));
				widget.setKlass(e.attributeValue("class"));
				widget.setPackageName(e.attributeValue("package"));
				widget.setContent_desc(e.attributeValue("content-desc"));
				
				if (Boolean.valueOf(e.attributeValue("checkable"))) {
					widget.setBindFunction("01");
				}
				if (Boolean.valueOf(e.attributeValue("checked"))) {
					widget.setBindFunction("02");
				}
				if (Boolean.valueOf(e.attributeValue("clickable"))) {
					widget.setBindFunction("03");	
				}
				if (Boolean.valueOf(e.attributeValue("enabled"))) {
					widget.setBindFunction("04");
				}
				if (Boolean.valueOf(e.attributeValue("focusable"))) {
					widget.setBindFunction("05");
				}
				if (Boolean.valueOf(e.attributeValue("focused"))) {
					widget.setBindFunction("06");
				}
				if (Boolean.valueOf(e.attributeValue("scrollable"))) {
					widget.setBindFunction("07");
				}
				if (Boolean.valueOf(e.attributeValue("long-clickable"))) {
					widget.setBindFunction("08");
				}				
				if (Boolean.valueOf(e.attributeValue("password"))) {
					widget.setBindFunction("09");
				}
				if (Boolean.valueOf(e.attributeValue("selected"))) {
					widget.setBindFunction("10");
				}
				widget.setBounds(dealBounds(e.attributeValue("bounds")));
				//这里拼装xPath
				if ("0".equals(e.attributeValue("index"))) {
					widget.setWidgetXpath(xPath+"/"+e.attributeValue("class") + "[1]");
				}else {
					int num = 1;
					for (Element e2 : nodelist) {
						//如果e2的index不小于e的index，则break
						if (Integer.valueOf(e2.attributeValue("index"))>=Integer.valueOf(e.attributeValue("index"))) {
							break;
						}else if(e.attributeValue("class").equals(e2.attributeValue("class"))) {
							num += 1;
						}
					}
					widget.setWidgetXpath(xPath+"/"+e.attributeValue("class")+"["+num+"]");
				}
				map.put(index,widget);
				if (e.elements("node")!=null&&e.elements("node").size()>0) {
					Map<String, Widget> newMap = new HashMap<String, Widget>();
					widget.setChildMap(newMap);
					getElement(newMap, e,widget.getWidgetXpath());
				}
			}
		}
	}
	
	private static void getLogicInterfaceXML(String file_name)  {
//		System.out.println("获取逻辑界面文件");
		String adbDump = "cmd /c " + Content.ADB + " shell /system/bin/uiautomator dump /data/local/tmp/app.xml";
		String adbpull = "cmd /c " + Content.ADB + " pull /data/local/tmp/app.xml " + xmlPath + file_name ;
		File file = new File(xmlPath + file_name);
		if (file.exists()) {
			file.delete();
		}
		CMDutils.excuteCMD(adbDump);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		CMDutils.excuteCMD(adbpull);
		try {
//			InputStream inputStream = CMDutils.excuteCMD(adbpull);
//			BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
//			String string = bReader.readLine();
//			while (string!=null&&string.contains("ERROR")) {
//				inputStream = CMDutils.excuteCMD(adbpull);
//				bReader = new BufferedReader(new InputStreamReader(inputStream));
//				string = bReader.readLine();
				Thread.sleep(200);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static List<Integer> dealBounds(String bounds){
		List<Integer> list = new ArrayList<Integer>();
		bounds = bounds.replace("][", ",").replace("[", "").replace("]", "");
		for (String str : bounds.split(",")) {
			list.add(Integer.parseInt(str));
		}
		return list;
	}
	
	public static void main(String[] args) {
//		dealBounds("[1,2,3,4]");
		getLogicInterfaceXML("0.xml");
	}
}
