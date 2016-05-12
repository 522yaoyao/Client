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
			 ss = new ServerSocket(8888);//����ʵ�ַ������׽��֣�
			 started=true;//������������
		}
		 catch(BindException e){
            System.out.println(" �˿��ѱ�ռ��.......");
            System.out.println(" ��ر���Ӧ�����������з�����");
            System.exit(0);
            
		   }
		  
			catch(IOException e){
               e.printStackTrace(); 				
			}
		try{
		//	started=true;
			while(started) {
				
			 Socket	 s = ss.accept();//����
			 Client c=new Client(s);
System.out.println("a client connected!");
			 new Thread(c).start();
 System.out.println("add ǰ"+clients.size());		
			clients.add(c);
System.out.println("add��"+clients.size());			
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
			 bConnected=true;//���ͻ����������ݺ��̱߳�������
System.out.println("Clientʵ����");			 
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		public void send(String str)  {
			
				try {
					dos.writeUTF(str);
					
				} catch(SocketException e){
					if(clients!=null)
/*��������run�������Ƴ�������run�����ﱨ��Ķ���һ���ǹرյĿͻ��ˣ��п����Ǵ��ڵĿͻ����ڵ����ѹرյĿͻ��˵�
 * ʱ���׳��쳣����ʱ�Ƴ�this,�п����Ƴ��Ĳ��ǹرյĿͻ���*/						
		                   clients.remove(this);//�ǵ�ǰ���󱻹رգ�Ӧ�Ƴ����ǵ�ǰ����
							System.out.println(" �Է��˳��ˣ���list�Ƴ���ʵ��");
				}
				catch (IOException e) {
					if(clients!=null)
		                   clients.remove(this);//�ǵ�ǰ���󱻹رգ�Ӧ�Ƴ����ǵ�ǰ����
							System.out.println(" �Է��˳��ˣ���list�Ƴ���ʵ��");
					e.printStackTrace();
				}
		
		}
		public void run(){
			Client c=null;
			try{
                          while(bConnected){
							String str = dis.readUTF();//����ʽ
System.out.println(str);//��ӡ���յ�����Ϣ��
							for(int i=0;i<clients.size();i++){
							 c=clients.get(i);
								c.send(str);
							}
System.out.println("��ǰ�ͻ��˵ĸ���"+clients.size());								
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
System.out.println("һ���ͻ��˹ر���");
					}
					catch(IOException e1){
						e1.printStackTrace();
					}
				}
						//	dis.close();
		}
	}
}