package view;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.PTS.ThreadPTSServer;

public class Main{
	public static ThreadPTSServer	server;
	public static MainWindow		mainWindow;
	public static StartServerWindow	startServerWindow;

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException{
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
					startServerWindow = new StartServerWindow();
					startServerWindow.frame.setVisible(true);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
