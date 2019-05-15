package com.huawei.socket_tel;

import java.net.InetSocketAddress;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;


public class AgentSocketControllor {
	
	private MysocketServer mss = new MysocketServer(16488);
	private static AgentSocketControllor asc = new AgentSocketControllor();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(){
			@Override
			public void run() {
				asc.mss.start();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				MiniCall call = new MiniCall();
				call.start();
			}
		};
		t1.start();
		t2.start();
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
        }

        @Override
        public void onError(WebSocket webSocket, Exception e) {
            System.out.println("error");
//            ash.close();
        }
        
        
        @Override
        public void start() {
        	super.start();
        	System.out.println("the session for parsing xml is waiting for connecting");
        }
        
        public void sendMessage(String message) {
    		socket.send(message);
        }
	}
	
	
}
