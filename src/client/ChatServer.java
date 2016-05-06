package client;
import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
	boolean started=false;
	ServerSocket ss=null;
	List<Client> clients=new ArrayList<Client>();
	public static void main(String[] args) {
	       new ChatServer().start();
	}
	public void start(){
		try {
			 ss = new ServerSocket(8888);//此类实现服务器套接字；
			 started=true;//服务器启动；
		}
		 catch(BindException e){
            System.out.println(" 端口已被占用.......");
            System.out.println(" 请关闭相应程序并重新运行服务器");
            System.exit(0);
            
		   }
		  
			catch(IOException e){
               e.printStackTrace(); 				
			}
		try{
		//	started=true;
			while(started) {
				
			 Socket	 s = ss.accept();//接收
			 Client c=new Client(s);
System.out.println("a client connected!");
			 new Thread(c).start();
			clients.add(c);
			//	dis.close();
			}
		
		  
		}
		catch (IOException e) {
			//System.out.println("Client closed !");
			e.printStackTrace();
		}
	finally{
		try{
			  ss.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	}
	class Client implements Runnable{
		private Socket s;
		private DataInputStream dis=null;
		private DataOutputStream dos=null;
		private boolean bConnected=false;
		public Client(Socket s){
			this.s=s;
			try{
			 dis=new DataInputStream(s.getInputStream());
			 dos=new DataOutputStream(s.getOutputStream());
			 bConnected=true;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		public void send(String str){
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		public void run(){
			try{
                          while(bConnected){
							String str = dis.readUTF();//阻塞式
							System.out.println(str);
							for(int i=0;i<clients.size();i++){
								Client c=clients.get(i);
						       //  c. dos.writeUTF(str);
								c.send(str);
							}
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
						if(s!=null)
						s.close();
						if(dis!=null)
						dis.close();
						if(dos!=null)
							dos.close();
					}
					catch(IOException e1){
						e1.printStackTrace();
					}
				}
						//	dis.close();
		}
	}
}