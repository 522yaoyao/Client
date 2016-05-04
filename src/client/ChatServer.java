package client;
import java.io.*;
import java.net.*;

public class ChatServer {

	public static void main(String[] args) {
		boolean started=false;
		ServerSocket ss=null;
		Socket s =null;
		DataInputStream dis =null;
		try {
			 ss = new ServerSocket(8888);//此类实现服务器套接字；
		}
		
			catch(IOException e){
                e.printStackTrace(); 				
			}
		try{
			started=true;//服务器启动；
			while(started) {
				boolean bConnected=false;
				 s = ss.accept();//接收
				bConnected=true;//接收了客户端的连接；
System.out.println("a client connected!");
			    dis = new DataInputStream(s.getInputStream());
				while(bConnected){
				String str = dis.readUTF();//阻塞式
				System.out.println(str);
				}
				dis.close();
			}
		
		} catch (IOException e) {
			System.out.println("Client closed !");
		//	e.printStackTrace();
		}
	finally{
		try{
			s.close();
			dis.close();
		}
		catch(IOException e1){
			e1.printStackTrace();
			
		}
	}

}
}