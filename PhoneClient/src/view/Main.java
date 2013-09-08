package view;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import util.RTP.*;

public class Main{

	/**
	 * @param args
	 */
	public static void main(String[] args){
		// Phone phone = new Phone("localhost", 32766, 16384);
		Phone phone = new Phone("localhost", 16384, 32766);
		phone.start();

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
		for(;;);
	}

}
