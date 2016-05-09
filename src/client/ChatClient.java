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

	public static void main(String[] args) {
		new ChatClient().launchFrame(); 
	}

	public void launchFrame() {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}
			
		});
		tfTxt.addActionListener(new TFListener());
		setVisible(true);
		connect();
		new Thread(new RecvThread()).start();
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
			dos.close();
			s.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
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
			taContent.setText(taContent.getText() + str +'\n');
			
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
}
