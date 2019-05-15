package com.huawei.utils;

import java.util.HashMap;
import java.util.Map;

import com.huawei.content.Content;


public class AdbOptions {
	

	/**
	 * 启动app
	 * @param message
	 */
	public static void openApp(String message) {
		message = message.substring(message.indexOf(":")+1);
		//执行adb命令操作   
		try {
			Runtime.getRuntime().exec("cmd /c " + Content.ADB +" shell am start -n " + message);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * adb键盘输入方式
	 * @param message
	 */
	public static void adbInput(String message) {
		//处理参数  input:ffff,x:100,y:300     adb:tap:x:11,y:22,fingers:33,duration:44,
		String[] arrs = message.split(",");
		message = arrs[0].substring(arrs[0].indexOf(":")+1);
		int x = Integer.parseInt(arrs[1].substring(arrs[1].indexOf(":")+1));
		int y = Integer.parseInt(arrs[2].substring(arrs[2].indexOf(":")+1));
		//执行adb命令操作   
		try {
			Runtime.getRuntime().exec("cmd /c " + Content.ADB + " shell input tap " + x + " " + y);
			Thread.sleep(200);
			Runtime.getRuntime().exec("cmd /c " + Content.ADB +" shell input text " + message);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * adb键盘输入方式
	 * @param message
	 */
	public static void executeAdb(String message) {
		//处理参数  input:ffff,x:100,y:300     adb:tap:x:11,y:22,fingers:33,duration:44,
		Map<String, Object> maps = formateMap(message);
		if (maps!=null) {
			//执行adb命令操作   
			try {
				switch (maps.get("adb").toString().trim()) {
				case "tap":
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input tap " +
							Double.valueOf(maps.get("x").toString().trim()).intValue() + " " + Double.valueOf(maps.get("y").toString().trim()).intValue());
					break;
				case "key event":
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input keyevent " + maps.get("keys").toString().trim());
					break;
				case "type text":
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input tap " +
							Math.round(Float.parseFloat(maps.get("x").toString().trim())) + " " + Math.round(Float.parseFloat(maps.get("y").toString().trim())));
					Thread.sleep(200);
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input text " + maps.get("keys").toString().trim());
					break;
				case "long press":
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input swipe  " +
							(Double.valueOf(maps.get("x").toString().trim()).intValue()-130) + " " +
							Double.valueOf(maps.get("y").toString().trim()).intValue() + " " +
							(Double.valueOf(maps.get("x").toString().trim()).intValue()-130) + " " +
							Double.valueOf(maps.get("y").toString().trim()).intValue()+ " " + maps.get("keys").toString().trim());
					break;
				case "double click":
					break;
				case "swipe":
					Runtime.getRuntime().exec("cmd /c" + Content.ADB + "shell input swipe  " + maps.get("startX").toString().trim()
							+ " " + maps.get("startY").toString().trim() + maps.get("endX").toString().trim() + " " + maps.get("endY").toString().trim());
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 处理参数
	 * 方法名：formateMap<BR>
	 * 创建人：Marlon <BR>
	 * 时间：2019年3月29日-下午2:59:12 <BR>
	 * @param str
	 * @return Map<String,Object><BR>
	 */
	public static Map<String, Object> formateMap(String str) {
		String[] arrs = str.split(",");
		Map<String, Object> maps = new HashMap<String, Object>();
		for (String obj : arrs) {
			String[] temp = obj.split(":");
			maps.put(temp[0], temp[1]);
		}
		return maps;
	}
}
