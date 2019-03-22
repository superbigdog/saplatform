package com.huawei.utils;

import java.io.IOException;
import java.io.InputStream;

public class CMDutils {
	
	private static Runtime rt = Runtime.getRuntime();
	
	public static InputStream excuteCMD(String command) {
		InputStream in = null;
		try {
			Process pro = rt.exec(command);
			in = pro.getInputStream();
		} catch (IOException e) {
			
		}
		return in;
	}
}
