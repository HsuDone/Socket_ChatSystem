package com.zxd.AllTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Collection;

public class ServerThread extends Thread{
	private String UserId;
	private Socket client = null;
	public ServerThread(Socket s) {
		this.client = s;
	}
	public void setUserId(String name) {
		this.UserId = name;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter pw = null;
			while(true) {
				String line = br.readLine();
				if(line == null || line.equalsIgnoreCase("exit"))
					break;
				System.out.println("接收到有人发消息，开始群发");
				Collection<Socket> c = Server.map.values();
				for(Socket s : c) {
					pw = new PrintWriter(s.getOutputStream(), true);
					pw.println(this.UserId+": "+line);
				}
			}
			if(pw != null)
				pw.close();
			if(br != null)
				br.close();
			client.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
