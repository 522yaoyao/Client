package client;
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		boolean started=false;
		try {
			ServerSocket ss = new ServerSocket(8888);//此类实现服务器套接字；
			started=true;//服务器启动；
			while(started) {
				boolean bConnected=false;
				Socket s = ss.accept();//接收
				bConnected=true;//接收了客户端的连接；
System.out.println("a client connected!");
				DataInputStream dis = new DataInputStream(s.getInputStream());
				while(bConnected){
				String str = dis.readUTF();
				System.out.println(str);
				}
				dis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}