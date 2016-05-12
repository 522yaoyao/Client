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
 System.out.println("add 前"+clients.size());		
			clients.add(c);
System.out.println("add后"+clients.size());			
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
			 bConnected=true;//当客户端输入数据后，线程被启动；
System.out.println("Client实例化");			 
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		public void send(String str)  {
			
				try {
					dos.writeUTF(str);
					
				} catch(SocketException e){
					if(clients!=null)
/*不可以在run方法里移除，当在run方法里报错的对象不一定是关闭的客户端，有可能是存在的客户端在调用已关闭的客户端的
 * 时候抛出异常，这时移除this,有可能移除的不是关闭的客户端*/						
		                   clients.remove(this);//是当前对象被关闭，应移除的是当前对象；
							System.out.println(" 对方退出了，从list移除此实例");
				}
				catch (IOException e) {
					if(clients!=null)
		                   clients.remove(this);//是当前对象被关闭，应移除的是当前对象；
							System.out.println(" 对方退出了，从list移除此实例");
					e.printStackTrace();
				}
		
		}
		public void run(){
			Client c=null;
			try{
                          while(bConnected){
							String str = dis.readUTF();//阻塞式
System.out.println(str);//打印接收到的信息；
							for(int i=0;i<clients.size();i++){
							 c=clients.get(i);
								c.send(str);
							}
System.out.println("当前客户端的个数"+clients.size());								
                           }
				}
			catch (EOFException e) {
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
System.out.println("一个客户端关闭了");
					}
					catch(IOException e1){
						e1.printStackTrace();
					}
				}
						//	dis.close();
		}
	}
}