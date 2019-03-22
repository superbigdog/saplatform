package com.huawei.socket_tel;

import java.awt.print.Printable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.NotYetConnectedException;
import java.util.Scanner;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class AgentSocketControllor {
	
	private MysocketServer mss = new MysocketServer(16488);
	private static AgentSocketControllor asc = new AgentSocketControllor();
	
	public static void main(String[] args) {
		asc.mss.start();
	}
	
	private class MysocketServer extends WebSocketServer{

		private WebSocket socket = null;
		
		private AgentSocketHandlor ash = null;
		
		public MysocketServer(int port) {
            super(new InetSocketAddress(port));
        }

        @Override
        public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
            System.out.println("open");
            this.socket= webSocket;
            sendMessage("Connecting!");
            ash = new AgentSocketHandlor(webSocket);
        }

        @Override
        public void onClose(WebSocket webSocket, int i, String s, boolean b) {
            System.out.println("close");
        }

        @Override
        public void onMessage(WebSocket webSocket, String s) {
        	System.out.println("message:"+s);
			ash.dealTheMessage(s);
        	//这里接收到信息去处理
        }

        @Override
        public void onError(WebSocket webSocket, Exception e) {
            System.out.println("error");
//            ash.close();
        }
        
        
        @Override
        public void start() {
        	super.start();
        	System.out.println("等待客户端接入");
        }
        
        public void sendMessage(String message) {
    		socket.send(message);
        }
	}
	
	
}
