package com.zxd.PrivateTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
	String UserId;
	Socket client = null;
	String talkTo;
	public ServerThread(Socket s) {
		this.client = s;
	}
	public void setUserId(String name) {
		this.UserId = name;
	}
	public void settalkTo(String target) {
		this.talkTo = target;
	}
	
	@Override
	public void run() {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = client.getInputStream();
			os = client.getOutputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			PrintWriter pw = null;
			while(true) {
				String line = br.readLine();
				pw = new PrintWriter(os, true);
				pw.println(this.UserId +": "+ line);
				System.out.println("接收到有人发消息，开始发送给目标用户");
				Socket s = Server.map.get(talkTo);
				pw = new PrintWriter(s.getOutputStream(),true);
				pw.println(this.UserId +": "+ line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
