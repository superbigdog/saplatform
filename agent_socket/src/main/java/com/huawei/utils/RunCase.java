package com.huawei.utils;

import com.huawei.content.Content;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunCase {
	
	public static void runcase(String message) {
		
		String deviceName = "";
		 Process p; 
    	 String cmd="cmd /c " + Content.ADB + " devices";
    	 try {
			p=Runtime.getRuntime().exec(cmd);
			//鍙栧緱鍛戒护缁撴灉鐨勮緭鍑烘祦    
            InputStream fis=p.getInputStream();    
           //鐢ㄤ竴涓杈撳嚭娴佺被鍘昏    
            InputStreamReader isr=new InputStreamReader(fis);    
           //鐢ㄧ紦鍐插櫒璇昏    
            BufferedReader br=new BufferedReader(isr);    
            String line=null;    
           //鐩村埌璇诲畬涓烘    
           while((line=br.readLine())!=null) {
        	   if(!line.contains("List")) {
        		   if(line.contains("device")){
        			   deviceName = line.substring(0,line.indexOf("device")).trim();
                      /*System.out.println(line.substring(0,line.indexOf("device")).trim()); 
                      System.out.println("璁惧杩炴帴鎴愬姛");*/
                     break;
        		   }else if(line.contains("offline")){
        			   System.out.println("璁惧杩炴帴澶辫触");
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
		//这里的python命令还需要重新设置路径
		String cmdStr = "cmd /c "+ Content.python +" .\\pyutils\\sapCode\\download.py" + " " + "./pyutils/sapCode/temp"
				+ " " + "./saplatCodeModel/" + appname + "/" + caseName + "/class"+ " " +deviceName;

		byte[] buffer = new byte[1024];
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		ByteArrayOutputStream outerrStream = new ByteArrayOutputStream();

		try {
			proc = Runtime.getRuntime().exec(cmdStr);
			InputStream errStream = proc.getErrorStream();
			InputStream stream = proc.getInputStream();

			// 娴佽鍙栦笌鍐欏叆
			int len = -1;
			while ((len = errStream.read(buffer)) != -1) {
				outerrStream.write(buffer, 0, len);
			}
			while ((len = stream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			proc.waitFor();// 绛夊緟鍛戒护鎵ц瀹屾垚

			// 鎵撳嵃娴佷俊鎭�
			System.out.println(outStream.toString());
			System.out.println("澶嶅埗鏂囦欢缁撴潫");
			//Runtime.getRuntime().exec("cmd /c rmdir /s/q C:\\sapCode\\temp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
