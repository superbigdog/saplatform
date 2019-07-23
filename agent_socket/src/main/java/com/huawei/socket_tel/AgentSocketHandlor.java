package com.huawei.socket_tel;

import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.WebSocket;

import com.huawei.utils.AdbOptions;
import com.huawei.utils.ParseXml;
import com.huawei.utils.RunAppium;
import com.huawei.utils.RunCase;

public class AgentSocketHandlor{
	
	private WebSocket socket = null;
	
	private Timer timer = null;
	
	private TimerTask task = null;
	
	public AgentSocketHandlor(WebSocket socket) {
		this.socket=socket;
	}
	
	public void dealTheMessage(String message) {
		if ("start".equals(message)) {
			handlor();
		}else if ("stop".equals(message)) {
			close();
			RunAppium.closeAppium();
		}else if ("input".equals(message.substring(0,message.indexOf(":")))) {
			AdbOptions.adbInput(message);
		}else if ("run".equals(message.substring(0,message.indexOf(":")))) {
			RunCase.runcase(message);
		}else if ("adb".equals(message.substring(0,message.indexOf(":")))) {
			AdbOptions.executeAdb(message);
		}else if ("openapp".equals(message.substring(0,message.indexOf(":")))) {
			AdbOptions.openApp(message);
		}else{
			System.out.println(message);
		}
	}
	
	public void handlor(){
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
//				System.out.println("parse xml file");
				String message = ParseXml.getXml();
				socket.send("data:"+message);
			}
		};
		
		timer.schedule(task, 0, 900);
	}
	
	public void close() {
		System.out.println("guangbi");
		task.cancel();
		task=null;
	}
}
