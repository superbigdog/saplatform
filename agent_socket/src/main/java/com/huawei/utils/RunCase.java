package com.huawei.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunCase {
	
	public static void runcase(String message) {
		
		String deviceName = "";
		 Process p; 
    	 String cmd="cmd /c adb devices";
    	 try {
			p=Runtime.getRuntime().exec(cmd);
			//取得命令结果的输出流    
            InputStream fis=p.getInputStream();    
           //用一个读输出流类去读    
            InputStreamReader isr=new InputStreamReader(fis);    
           //用缓冲器读行    
            BufferedReader br=new BufferedReader(isr);    
            String line=null;    
           //直到读完为止    
           while((line=br.readLine())!=null) {
        	   if(!line.contains("List")) {
        		   if(line.contains("device")){
        			   deviceName = line.substring(0,line.indexOf("device")).trim();
                      /*System.out.println(line.substring(0,line.indexOf("device")).trim()); 
                      System.out.println("设备连接成功");*/
                     break;
        		   }else if(line.contains("offline")){
        			   System.out.println("设备连接失败");
                       break;
        		   }       
        	   }
        	   
            }    
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] arrs = message.split(",");
		String appname = arrs[0].substring(arrs[0].indexOf(":")+1);;
		String caseName = arrs[1];
		Process proc;
		String cmdStr = "python C:\\sapCode\\download.py" + " " + "c:/sapCode/temp"
				+ " " + "./saplatCodeModel/" + appname + "/" + caseName + "/class"+ " " +deviceName;

		byte[] buffer = new byte[1024];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayOutputStream outerrStream = new ByteArrayOutputStream();

		try {
			proc = Runtime.getRuntime().exec(cmdStr);
			InputStream errStream = proc.getErrorStream();
			InputStream stream = proc.getInputStream();

			// 流读取与写入
			int len = -1;
			while ((len = errStream.read(buffer)) != -1) {
				outerrStream.write(buffer, 0, len);
			}
			while ((len = stream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			proc.waitFor();// 等待命令执行完成

			// 打印流信息
			System.out.println(outStream.toString());
			System.out.println("复制文件结束");
			//Runtime.getRuntime().exec("cmd /c rmdir /s/q C:\\sapCode\\temp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
