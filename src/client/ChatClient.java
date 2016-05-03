package client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;
	
	DataOutputStream dos=null;
	
	TextField tfTxt = new TextField();//����༭�����ı����ı������

	TextArea taContent = new TextArea();//��ʾ�ı��Ķ������򣬿��Խ�������Ϊ����༭��ֻ����

	public static void main(String[] args) {
		new ChatClient().launchFrame(); 
	}

	public void launchFrame() {
		setLocation(400, 300);//������Ƶ���λ�á�ͨ���������������ռ��е� x �� y ������ָ����λ�õ����Ͻǣ�
		this.setSize(300, 300);//��������Ĵ�С��ʹ����Ϊ d.width���߶�Ϊ d.height��
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();//�����˴��ڵĴ�С�����ʺ������������ѡ��С�Ͳ��֣�
		//����һ�������࣬ʹ�����������˳���
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disConnect();
				System.exit(0);//�����˳���
			}
			
		});
		tfTxt.addActionListener(new TFListener());//�س�����һЩ��Ӧ��
		setVisible(true);//���ݲ��� b ��ֵ��ʾ�����ش� Window��
		connect();
	}
	
	public void connect() {
		try {
/*
 * 127.0.0.1���IP��ַ���������壬���ǡ������������Կ�������̨�������ͬʱ���Կͻ��˺ͷ�������8888���õ�TCP�Ķ˿ںţ�
 */
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
System.out.println("connected!");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void  disConnect(){
		try{
		s.close();
		dos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		}
		
	private class TFListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String str = tfTxt.getText().trim();
			taContent.setText(str);
			tfTxt.setText("");
			
			try {
//System.out.println(s);
			
				dos.writeUTF(str);
				dos.flush();
			//	dos.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
		}
		
	}

}

