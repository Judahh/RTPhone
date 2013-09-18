package view;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainWindow{

	public JFrame			frame;
	public JList<String>	loggedUsersList;
	public JList<String>	allUsersList;
	public JButton			btnCall;

	/**
	 * Create the application.
	 */
	public MainWindow(){
		initialize();

	}

	public void run(){
		new Runnable(){
			@Override
			public void run(){
				while(Main.clientPTS.isConnected()){
					loggedUsersList = new JList<>(Main.clientPTS.getUserOn());
					allUsersList = new JList<>(Main.clientPTS.getUser());
					if(Main.clientPTS.getPhone() == null){
						btnCall = new JButton("Call");
					}else{
						btnCall = new JButton("Hang up");
					}
				}
				Main.startLoginWindow();
			}
		};
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -44,
				SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -10,
				SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);

		btnCall = new JButton("Call");
		btnCall.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				switch(btnCall.getText()){
					case "Call":
						try{
							Main.clientPTS.call(loggedUsersList
									.getSelectedIndex());
						}catch(IOException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					break;

					case "Hang up":
						Main.clientPTS.hangUp();
					break;

					default:
					break;
				}
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, btnCall, 6,
				SpringLayout.SOUTH, tabbedPane);

		loggedUsersList = new JList<>();
		tabbedPane.addTab("Logged Users", null, loggedUsersList, null);

		allUsersList = new JList<>();
		tabbedPane.addTab("All Users", null, allUsersList, null);
		springLayout.putConstraint(SpringLayout.WEST, btnCall, 10,
				SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnCall);
	}
}
