package com.zxd.AllTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
	private ServerSocket ss = null;
	static Map<String, Socket> map = new HashMap<>();
	public Server(ServerSocket ss) {
		this.ss = ss;
		System.out.println("服务器已启动");
	}
	public void listen() {
		Socket client = null;
		try {
			while(true) {
				client = ss.accept();
				BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
				String newconnectId = br.readLine();
				System.out.println(newconnectId+"上线了-"+client.getInetAddress());
				map.put(newconnectId, client);
				ServerThread t = new ServerThread(client);
				t.setUserId(newconnectId);
				t.start();
				System.out.println("当前连入服务器的用户有:"+map.size()+"个");
			} 
			
				
			
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Server s = null;
		try {
			s = new Server(new ServerSocket(8080));
			s.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
