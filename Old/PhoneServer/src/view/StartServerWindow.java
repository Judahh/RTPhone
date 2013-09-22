package view;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JButton;

import util.PTS.ThreadPTSServer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartServerWindow{

	public JFrame		frame;
	private JButton		btnStart;
	private JLabel		lblIp;
	private JLabel		lblUnknown_1;

	/**
	 * Create the application.
	 */
	public StartServerWindow(){
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.server = new ThreadPTSServer();
				Main.server.start();
				
				Main.startMainWindow();
			}
		});
		frame.getContentPane().add(btnStart);

		lblIp = new JLabel("IP:");
		springLayout.putConstraint(SpringLayout.WEST, lblIp, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 6, SpringLayout.SOUTH, lblIp);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 0, SpringLayout.WEST, lblIp);
		springLayout.putConstraint(SpringLayout.NORTH, lblIp, 10, SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(lblIp);

		try{
			lblUnknown_1 = new JLabel(Inet4Address.getLocalHost()
					.getHostAddress().toString());
			springLayout.putConstraint(SpringLayout.NORTH, lblUnknown_1, 0, SpringLayout.NORTH, lblIp);
			springLayout.putConstraint(SpringLayout.WEST, lblUnknown_1, 6, SpringLayout.EAST, lblIp);
			springLayout.putConstraint(SpringLayout.EAST, lblUnknown_1, -10, SpringLayout.EAST, frame.getContentPane());
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		frame.getContentPane().add(lblUnknown_1);
	}

}
