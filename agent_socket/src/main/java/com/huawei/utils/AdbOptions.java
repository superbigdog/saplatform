package com.huawei.utils;

public class AdbOptions {

	/**
	 * adb键盘输入方式
	 * @param message
	 */
	public static void adbInput(String message) {
		//处理参数  input:ffff,x:100,y:300
		String[] arrs = message.split(",");
		message = arrs[0].substring(arrs[0].indexOf(":")+1);
		int x = Integer.parseInt(arrs[1].substring(arrs[1].indexOf(":")+1));
		int y = Integer.parseInt(arrs[2].substring(arrs[2].indexOf(":")+1));
		//执行adb命令操作   
		try {
			Runtime.getRuntime().exec("cmd /c adb shell input tap " + x + " " + y);
			Thread.sleep(200);
			Runtime.getRuntime().exec("cmd /c adb shell input text " + message);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}
