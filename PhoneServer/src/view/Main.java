package view;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.PTS.ThreadPTSServer;
import util.TCP.ThreadTCPServer;

public class Main{

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void main(String[] args) throws InterruptedException,
			IOException{
		// try {
		// // Set System L&F
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (UnsupportedLookAndFeelException e) {
		// // handle exception
		// } catch (ClassNotFoundException e) {
		// // handle exception
		// } catch (InstantiationException e) {
		// // handle exception
		// } catch (IllegalAccessException e) {
		// // handle exception
		// }
		//
		// EventQueue.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// StartServerWindow window = new StartServerWindow();
		// window.frame.setVisible(true);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// }
		// });

		ThreadPTSServer server = new ThreadPTSServer(9000);
		server.start();

//		for(int i = 0;; i++){
			// Thread.sleep(1);
//			for(int index = 0; index < server.getWorkerRunnable().size(); index++){
//				Thread.sleep(1);
//				System.out.println("sent from serverMain: Test" + i + " of "
//						+ index + "Thread");
//				server.getWorkerRunnable().get(index)
//						.send("Test" + i + " of " + index + "Thread");
//			}
//		}
	}

}
