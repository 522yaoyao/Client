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
			 ss = new ServerSocket(8888);//����ʵ�ַ������׽��֣�
		}
		 catch(BindException e){
             System.out.println(" �˿��Ա�ռ��.......");
		   }
		  
			catch(IOException e){
                e.printStackTrace(); 				
			}
		try{
			started=true;//������������
			while(started) {
				boolean bConnected=false;
				 s = ss.accept();//����
				bConnected=true;//�����˿ͻ��˵����ӣ�
System.out.println("a client connected!");
			    dis = new DataInputStream(s.getInputStream());
				while(bConnected){
				String str = dis.readUTF();//����ʽ
				System.out.println(str);
				}
				dis.close();
			}
		
		  
		}catch (EOFException e) {
			System.out.println("Client closed !");
		//	e.printStackTrace();
		}
		catch (IOException e) {
			//System.out.println("Client closed !");
			e.printStackTrace();
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