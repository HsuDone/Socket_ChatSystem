package com.zxd.AllTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
	public static void main(String[] args) {
		try {
			Socket s = new Socket("localhost",8080);
			
			System.out.println("客户端已开启");
			System.out.println(s);
			PrintWriter pw = new PrintWriter(s.getOutputStream(),true);
			Scanner sc = new Scanner(System.in);
			String line = null;
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					while(true) {
					try {
						BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
						System.out.println(br.readLine());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				}
			});
			t.start();
			System.out.println("输入一个用户名:");
			pw.println(sc.nextLine());
			while((line = sc.nextLine())!= null) {
				if(line.equalsIgnoreCase("exit"))
					break;
				pw.println(line);
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
