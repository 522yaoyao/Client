package client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;//ʵ�ֿͻ��˵��׽��֣�
	DataOutputStream dos = null;
	DataInputStream dis=null;
	private boolean bConnected=false;

	TextField tfTxt = new TextField();

	TextArea taContent = new TextArea();

	Thread   tRecv=new Thread(new RecvThread());
	
	public static void main(String[] args) {
		new ChatClient().launchFrame(); 
	}

	public void launchFrame() {
		setLocation(400, 300);
	//	this.setSize(300, 300);
		setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}
			
		});//��ӹرմ��ڵķ�����
		tfTxt.addActionListener(new TFListener());//���ı���������Ϣ�󣬻س�ִ�н����ݷ��͵�����˵Ĳ�����
		setVisible(true);
		connect();
		tRecv.start();
	}
	
	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis=new DataInputStream(s.getInputStream());
			bConnected=true;
			
System.out.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void disconnect() {
		try {
			bConnected=false;
			
			tRecv.join();//�ȴ����߳���ֹ
			
		
		} catch(InterruptedException  e){
			e.printStackTrace();
		}
		finally{
			try{
			dos.close();
			dis.close();//��������߳�û��ִ����ɾ͹ر����������ͻ��׳��쳣��������ִ�д˲���ǰҪ�ر��̣߳�
			s.close();
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
/*���ͷ�������Ҳ�ᷢ���Լ�*/
			//taContent.setText(str);
			tfTxt.setText("");//������ı�����գ�
			
			try {
//System.out.println(s);
				dos.writeUTF(str);
				dos.flush();
				//dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}
private class RecvThread implements Runnable{
	public void run(){
		try{
			while(bConnected){
			String str=dis.readUTF();
/*�Լ�Ҳ����յ����������ݣ��ı���ÿ����ʾԭ�������ݼ��Ͻ��յ�������*/			
			taContent.setText(taContent.getText() + str +'\n');
			
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
}
