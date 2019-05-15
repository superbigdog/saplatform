package com.huawei.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CMDutils {

	private static Runtime rt = Runtime.getRuntime();

	public static String excuteCMD(String command) {
		InputStream in = null;
		StringBuffer b=new StringBuffer();
		try {
			Process pro = rt.exec(command);
			in = pro.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line=br.readLine())!=null) {
				b.append(line+"\n");
			}
		} catch (IOException e) {

		}
		return b.toString();
	}
	
	public static InputStream excuteCMD4InStream(String command) {
		InputStream in = null;
		Process pro;
		try {
			pro = rt.exec(command);
			in = pro.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
}
