package client;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {
	Socket s = null;
	
	DataOutputStream dos=null;
	
	TextField tfTxt = new TextField();//允许编辑单行文本的文本组件；

	TextArea taContent = new TextArea();//显示文本的多行区域，可以将它设置为允许编辑或只读；

	public static void main(String[] args) {
		new ChatClient().launchFrame(); 
	}

	public void launchFrame() {
		setLocation(400, 300);//将组件移到新位置。通过此组件父级坐标空间中的 x 和 y 参数来指定新位置的左上角；
		this.setSize(300, 300);//调整组件的大小，使其宽度为 d.width，高度为 d.height；
		add(tfTxt, BorderLayout.SOUTH);
		add(taContent, BorderLayout.NORTH);
		pack();//调整此窗口的大小，以适合其子组件的首选大小和布局；
		//定义一个匿名类，使窗口能正常退出；
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent arg0) {
				disConnect();
				System.exit(0);//正常退出；
			}
			
		});
		tfTxt.addActionListener(new TFListener());//回车做出一些相应；
		setVisible(true);//根据参数 b 的值显示或隐藏此 Window；
		connect();
	}
	
	public void connect() {
		try {
/*
 * 127.0.0.1这个IP地址有特殊意义，就是“本机”，所以可以在这台计算机上同时测试客户端和服务器；8888设置的TCP的端口号；
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

