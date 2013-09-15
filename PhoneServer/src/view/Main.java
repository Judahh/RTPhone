package view;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.PTS.ThreadPTSServer;

public class Main{
	public static ThreadPTSServer server;
	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException{
		 try {
		 // Set System L&F
		 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		 } catch (UnsupportedLookAndFeelException e) {
		 // handle exception
		 } catch (ClassNotFoundException e) {
		 // handle exception
		 } catch (InstantiationException e) {
		 // handle exception
		 } catch (IllegalAccessException e) {
		 // handle exception
		 }
		
		 EventQueue.invokeLater(new Runnable() {
		 public void run() {
		 try {
		 StartServerWindow window = new StartServerWindow();
		 window.frame.setVisible(true);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
		 }
		 });
	}

}
