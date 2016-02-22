package org.usfirst.frc.team295.robot;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;

public class Server implements Runnable{
	Socket s;
	ServerSocket server;
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
				dataOutputStream.writeChars("A;" + RobotMap.ahrs.getAngle());
				System.out.println("Client Connected");
				s.close();
			}catch (SocketTimeoutException s){
				System.out.println("Timed Out");
				break;
			}
			catch(IOException e){
				e.printStackTrace();
				break;
			}
		}
	}

}
