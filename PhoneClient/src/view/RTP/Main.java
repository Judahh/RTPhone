package view.RTP;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.PTS.ThreadPTSClient;
import util.RTP.Phone;

public class Main{
	public static ThreadPTSClient clientPTS;
	public static Phone phone;
	/**
	 * @param args
	 */
	public static void main(String[] args){
		// Phone phone = new Phone("172.31.4.202", 32766, 16384);
		// Phone phone = new Phone("172.31.4.201", 16384, 32766);
		// phone.start();

		try{
			// Set System L&F
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(UnsupportedLookAndFeelException e){
			// handle exception
		}catch(ClassNotFoundException e){
			// handle exception
		}catch(InstantiationException e){
			// handle exception
		}catch(IllegalAccessException e){
			// handle exception
		}
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try{
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
