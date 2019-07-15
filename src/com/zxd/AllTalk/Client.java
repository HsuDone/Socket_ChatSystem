package com.zxd.AllTalk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
	private Socket s = null;
	private String Id = null;
	private BufferedReader br = null;
	private PrintWriter pw = null;
	public Client() {
		try {
			s = new Socket("localhost",8080);
			System.out.print("客户端已开启");
			pw = new PrintWriter(s.getOutputStream(),true);
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send() {
		System.out.println(s);
		Scanner sc = new Scanner(System.in);
		String line = null;
		System.out.println("请输入用户名:");
		Id = sc.nextLine();
		pw.println(Id);
		while((line = sc.nextLine())!= null) 
		{
			if(line.equalsIgnoreCase("exit"))
				break;
			pw.println(line);
		}
		sc.close(); 
	}
	
	public void receive() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while( true) 
						System.out.println(br.readLine());
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		c.receive();
		c.send();
	}
}
