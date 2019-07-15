package com.zxd.AllTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketDemo {
	//Exception: Address already in use : 套接字重复(一般是端口重复)
	public static void main(String[] args) {
		ServerSocket ss = null;
		Scanner sc = new Scanner(System.in);
		try {
			ss = new ServerSocket(8080);//服务器套接字
			
			System.out.println("服务器已经开启，等待客户端连接.....");
			System.out.println(ss);
			Socket s = ss.accept();
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
						try {
							PrintWriter pw = new PrintWriter(new OutputStreamWriter(s.getOutputStream()),true);
							pw.println(sc.nextLine());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
			t.start();
			while(true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				System.out.println("客户端说："+br.readLine());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
