package com.huawei.utils;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;


public class RunAppium {
	
	private static Thread t_appium = null;
	
	private static InputStream in;
	
	/**
	 * 启动appium
	 */
	public static void startAppium() {
		String port = getPort4Appium();
		if (port != "") {
			String cmd = "taskkill /f -pid "+ port;
			String result = CMDutils.excuteCMD(cmd);
		}
		t_appium = new Thread() {
			@Override
			public void run() {
				int num = 0;
				PrintWriter pr = null;
				try {
					in = CMDutils.excuteCMD4InStream("cmd /c cd .\\nodejs & appium");
					pr = new PrintWriter(new FileWriter("./logs/appium.log",true));
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String line=null;
					while ((line=br.readLine())!=null) {
						if(num<2) { //需要在控制台提示appium是否已经启动
							System.out.println(line);
							num++;
						}
						pr.print(line + "\n");
					}
				} catch (IOException e) {
		            e.printStackTrace();
				} finally {
					pr.flush();
					pr.close();
				}
			}
		};
		t_appium.start();
	}
	
	/**
	 * 关闭appium
	 * @throws InterruptedException
	 */
	public static void closeAppium()  {
		String port = getPort4Appium();
		if (port != "") {
			String cmd = "taskkill /f -pid "+ port;
			String result = CMDutils.excuteCMD(cmd);
		}
		if (t_appium.isAlive()) {
			in = null;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!t_appium.isAlive()) {
				System.out.println("appium已关闭");
			}else {
				System.out.println("appium未能正常关闭，请手动关闭");
			}
		}
	}
	
	private static String getPort4Appium() {
		String cmd = "cmd /c netstat -ano |findstr \"4723\"";
		String result = CMDutils.excuteCMD(cmd);
		if (result != "") {
			String[] strs = result.split(" ");
			return strs[strs.length-1].trim();
		}
		return "";
	}
	
	public static void main(String[] args) {
		try {
			startAppium();
			Thread.sleep(1000*5);
	//		closeAppium();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
