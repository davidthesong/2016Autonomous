package org.usfirst.frc.team295.robot;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.CharBuffer;

public class Server implements Runnable{
	Socket s;
	ServerSocket server;
	int increment = 0;
	String delimiter = ";";
	public Server(ServerSocket s){
		server = s;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try{
				s = server.accept();
				DataOutputStream dataOutputStream = new DataOutputStream(s.getOutputStream());
				DataInputStream in = new DataInputStream(s.getInputStream());
				System.out.println("Client Connected");

//				System.out.println(br.readLine());
//				System.out.println("Read Line");
				dataOutputStream.writeChars("1"+delimiter+"2"+delimiter+"3"+delimiter);
				//TODO:Establish Order
				System.out.println("Sent Line");
					
				System.out.println("Socket Closed ");
				s.close();
				
			}catch (SocketTimeoutException s){
				System.out.println("Timed Out");
				break;
			}
			catch(IOException e){
				System.out.println("Client Closed");
				e.printStackTrace();
//				break;
			}
			catch(Exception e){
				System.out.println("Was not able to Read");
				e.printStackTrace();
			}
			
		}
	}

}
