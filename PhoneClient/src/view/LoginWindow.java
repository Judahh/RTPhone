package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginWindow {

	public JFrame frame;
	private JTextField textFieldUserName;
	private JTextField textFieldHost;

	/**
	 * Create the application.
	 */
	public LoginWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 125);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		textFieldHost = new JTextField();
		springLayout.putConstraint(SpringLayout.EAST, textFieldHost, -6, SpringLayout.EAST, frame.getContentPane());
		textFieldHost.setText("localhost");
		textFieldHost.setColumns(10);
		frame.getContentPane().add(textFieldHost);
		
		JLabel labelHost = new JLabel("Host:");
		springLayout.putConstraint(SpringLayout.NORTH, textFieldHost, -3, SpringLayout.NORTH, labelHost);
		springLayout.putConstraint(SpringLayout.WEST, textFieldHost, 6, SpringLayout.EAST, labelHost);
		springLayout.putConstraint(SpringLayout.NORTH, labelHost, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, labelHost, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(labelHost);
		
		textFieldUserName = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldUserName, 6, SpringLayout.SOUTH, textFieldHost);
		springLayout.putConstraint(SpringLayout.EAST, textFieldUserName, 0, SpringLayout.EAST, textFieldHost);
		textFieldUserName.setText("user");
		textFieldUserName.setColumns(10);
		frame.getContentPane().add(textFieldUserName);
		
		JLabel labelUserName = new JLabel("User name:");
		springLayout.putConstraint(SpringLayout.NORTH, labelUserName, 9, SpringLayout.SOUTH, textFieldHost);
		springLayout.putConstraint(SpringLayout.WEST, textFieldUserName, 6, SpringLayout.EAST, labelUserName);
		springLayout.putConstraint(SpringLayout.WEST, labelUserName, 0, SpringLayout.WEST, labelHost);
		frame.getContentPane().add(labelUserName);
		
		JButton buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MainWindow mainwindow = new MainWindow(textFieldUserName.getText(),textFieldHost.getText());
				mainwindow.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		springLayout.putConstraint(SpringLayout.NORTH, buttonLogin, 6, SpringLayout.SOUTH, textFieldUserName);
		springLayout.putConstraint(SpringLayout.WEST, buttonLogin, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(buttonLogin);
		
		JButton buttonNewUser = new JButton("New User");
		springLayout.putConstraint(SpringLayout.EAST, buttonLogin, -6, SpringLayout.WEST, buttonNewUser);
		springLayout.putConstraint(SpringLayout.NORTH, buttonNewUser, 6, SpringLayout.SOUTH, textFieldUserName);
		springLayout.putConstraint(SpringLayout.EAST, buttonNewUser, 0, SpringLayout.EAST, textFieldUserName);
		frame.getContentPane().add(buttonNewUser);
	}
}
