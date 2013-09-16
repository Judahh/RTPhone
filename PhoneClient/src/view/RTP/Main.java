package view.RTP;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import util.PTS.ThreadPTSClient;

public class Main{
	public static ThreadPTSClient clientPTS;
	public static LoginWindow loginWindow;
	public static MainWindow mainWindow;
	
	public static void startLoginWindow(){
		if(Main.mainWindow != null){
			Main.mainWindow.frame.setVisible(false);
		}
		Main.loginWindow.frame.setVisible(true);
	}

	public static void startMainWindow(){
		if(Main.loginWindow != null){
			Main.loginWindow.frame.setVisible(false);
		}
		Main.mainWindow.frame.setVisible(true);
		Main.mainWindow.run();
	}
	
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
					Main.loginWindow = new LoginWindow();
					Main.mainWindow = new MainWindow();
					startLoginWindow();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
