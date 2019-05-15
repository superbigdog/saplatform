package com.huawei.socket_tel;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.huawei.content.Content;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import com.huawei.utils.CMDutils;

public class MiniCall {
	public void start() {
		Thread t_init = new Thread() {
			@Override
			public void run() {
				initMinicap();
			}
		};
		Thread t_touch = new Thread() {
			@Override
			public void run() {
				startMiniTouch();
			}
		};
		Thread t_dbclick = new Thread() {
			@Override
			public void run() {
				dbClick();
			}
		};
		Thread t_app = new Thread() {
			@Override
			public void run() {
				app();
			}
		};
		try {
			t_init.start();
			Thread.sleep(1000*2);
			t_touch.start();
			Thread.sleep(1000*2);
			t_dbclick.start();
			Thread.sleep(1000);
			t_app.start();
			System.out.println("get ready!!!");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 用来替换init.js
	 */
	private void initMinicap() {
		Map<String, String> info = new HashMap<String, String>();
		info.put("apiLevel", getDevicesApiLevel());
		info.put("cpu", getCpu());
		info.put("size", getWMsize());
		info.put("width","" + Integer.valueOf(info.get("size").split("x")[0])/3);
		info.put("height", "" + Integer.valueOf(info.get("size").split("x")[1])/3);
		
		String soFile = ".\\saplatform\\minicap\\libs\\minicap\\so-libs\\android-" + info.get("apiLevel") + "\\" + info.get("cpu") + "\\minicap.so";
		CMDutils.excuteCMD(Content.ADB + " push " + soFile + " /data/local/tmp");
		CMDutils.excuteCMD(Content.ADB + " shell chmod 777 data/local/tmp/minicap.so");
		String cappath = ".\\saplatform\\minicap\\libs\\minicap\\"+info.get("cpu");
		String touchpath = ".\\saplatform\\minicap\\libs\\minitouch\\"+info.get("cpu");
		CMDutils.excuteCMD(Content.ADB + " push "+ cappath + "//minicap" + " /data/local/tmp");
		CMDutils.excuteCMD(Content.ADB + " push "+ touchpath + "/minitouch" + " /data/local/tmp");
		CMDutils.excuteCMD(Content.ADB + " push "+ touchpath + "/minitouch-nopie" + " /data/local/tmp");
		CMDutils.excuteCMD(Content.ADB + " shell chmod 777 data/local/tmp/minicap");
		CMDutils.excuteCMD(Content.ADB + " shell chmod 777 data/local/tmp/minitouch");
		CMDutils.excuteCMD(Content.ADB + " shell chmod 777 data/local/tmp/minitouch-nopie");
		System.out.println(info);
		CMDutils.excuteCMD(Content.ADB + " forward tcp:1717 localabstract:minicap");
		CMDutils.excuteCMD(Content.ADB + " forward tcp:2058 localabstract:minitouch");//准备反向控制
		CMDutils.excuteCMD(Content.ADB + " shell LD_LIBRARY_PATH=/data/local/tmp /data/local/tmp/minicap -P "
				+ info.get("size") +"@"+ info.get("width") +'x'+ info.get("height") +"/0");
	}
	private String getDevicesApiLevel() {
        return CMDutils.excuteCMD(Content.ADB + " shell getprop ro.build.version.sdk").trim();
	}
	private String getCpu() {
        return CMDutils.excuteCMD(Content.ADB + " shell getprop ro.product.cpu.abi").trim();
	}
	private String getWMsize() {
		String wmSize = CMDutils.excuteCMD(Content.ADB + " shell wm size").trim();
		if (wmSize.split(":").length>=2) {
			wmSize = wmSize.split(":")[1].trim();
		}
		return wmSize;
	}
	
	/**
	 * 置换minitoch.js
	 */
	private void startMiniTouch() {
		CMDutils.excuteCMD(Content.ADB + " shell /data/local/tmp/minitouch");
	}
	
	private void dbClick() {
		try {
			Socket socket = new Socket("localhost", 2058);
			MysocketServer mysocket = new MysocketServer(9003,socket);
			mysocket.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private class MysocketServer extends WebSocketServer{

		private WebSocket socket = null;
		
		private Socket client = null;
		
		BufferedOutputStream bro = null;
		
		private MysocketServer(int port,Socket socket) {
			super(new InetSocketAddress(port));
			this.client = socket;
			System.out.println("9003已创建");
		}
		
        @Override
        public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
            System.out.println("9003 open");
            this.socket= webSocket;
            sendMessage("Connecting!");
        }

        @Override
        public void onClose(WebSocket webSocket, int i, String s, boolean b) {
            System.out.println("9003close");
        }

        @Override
        public void onMessage(WebSocket webSocket, String s) {
        	System.out.println("message:"+s);
        	try {
        		bro  = new BufferedOutputStream(client.getOutputStream());
                byte[] buffer = s.getBytes("utf-8");
				bro.write(buffer);
				bro.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        @Override
        public void onError(WebSocket webSocket, Exception e) {
            System.out.println("error");
//            ash.close();
        }
        
        
        @Override
        public void start() {
        	super.start();
        	System.out.println("screenshot session is waiting for connecting");
        }
        
        public void sendMessage(String message) {
    		socket.send(message);
        }
	}
	
	//启动aap.js
	private void app() {
		InputStream in = CMDutils.excuteCMD4InStream(Content.NODE + " ./saplatform/minicap/example/app.js");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line=null;
			while ((line=br.readLine())!=null) {
//				System.out.println(line);
			}
		} catch (IOException e) {
            e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		MiniCall call = new MiniCall();
		call.start();
	}
}
