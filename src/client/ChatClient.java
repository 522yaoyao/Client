package client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;//实现客户端的套接字；
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
			
		});//添加关闭窗口的方法；
		tfTxt.addActionListener(new TFListener());//在文本框输入信息后，回车执行将数据发送到服务端的操作；
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
			
			tRecv.join();//等待该线程终止
			
		
		} catch(InterruptedException  e){
			e.printStackTrace();
		}
		finally{
			try{
			dos.close();
			dis.close();//若输入的线程没有执行完成就关闭输入流，就会抛出异常，所以在执行此操作前要关闭线程；
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
/*发送方的数据也会发给自己*/
			//taContent.setText(str);
			tfTxt.setText("");//下面的文本框清空；
			
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
/*自己也会接收到所发的数据，文本框每次显示原本的数据加上接收到的数据*/			
			taContent.setText(taContent.getText() + str +'\n');
			
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
}
