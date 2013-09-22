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

	public static void startStartServerWindow(){
		if(Main.mainWindow != null){
			Main.mainWindow.frame.setVisible(false);
		}
		Main.startServerWindow.frame.setVisible(true);
	}

	public static void startMainWindow(){
		if(Main.startServerWindow != null){
			Main.startServerWindow.frame.setVisible(false);
		}
		Main.mainWindow.frame.setVisible(true);
		Main.mainWindow.run();
	}

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
					Main.startServerWindow = new StartServerWindow();
					Main.mainWindow = new MainWindow();
					startStartServerWindow();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
	}

}
