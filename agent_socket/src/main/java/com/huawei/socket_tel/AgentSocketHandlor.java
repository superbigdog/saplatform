package com.huawei.socket_tel;

import java.util.Timer;
import java.util.TimerTask;

import org.java_websocket.WebSocket;

import com.huawei.utils.AdbOptions;
import com.huawei.utils.ParseXml;

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
		}else if ("input".equals(message.substring(0,message.indexOf(":")))) {
			AdbOptions.adbInput(message);
		}else {
			System.out.println(message);
		}
	}
	
	public void handlor(){
		timer = new Timer();
		task = new TimerTask() {
			@Override
			public void run() {
				System.out.println("处理文件中");
				String message = ParseXml.getXml();
				socket.send("data:"+message);
			}
		};
		
		timer.schedule(task, 1000, 1200);
	}
	
	public void close() {
		System.out.println("guangbi");
		task.cancel();
		task=null;
	}
}
