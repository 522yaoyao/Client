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
			clients.add(c);
System.out.println("��Ӻ�"+clients.size());			
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
							String str = dis.readUTF();//����ʽ
System.out.println(str);//��ӡ���յ�����Ϣ��
							for(int i=0;i<clients.size();i++){
								Client c=clients.get(i);
System.out.println("���ǰ"+clients.size());		
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