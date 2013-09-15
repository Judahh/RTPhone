package view;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import util.PTS.ThreadPTSServer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartServerWindow{

	public JFrame		frame;
	private JButton		btnStart;
	private JLabel		labelServerPort;
	private JTextField	textFieldServerPort;
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
		frame.setBounds(100, 100, 200, 125);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		labelServerPort = new JLabel("Server Port:");
		springLayout.putConstraint(SpringLayout.NORTH, labelServerPort, 10,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, labelServerPort, 10,
				SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(labelServerPort);

		btnStart = new JButton("Start");
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 0,
				SpringLayout.WEST, labelServerPort);
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){

				if(textFieldServerPort.getText() == null){
					textFieldServerPort.setText("9000");

				}

				Main.server = new ThreadPTSServer(Integer
						.parseInt(textFieldServerPort.getText()));
				Main.server.start();

				Main.mainWindow = new MainWindow();
				Main.mainWindow.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(btnStart);

		textFieldServerPort = new JTextField();
		springLayout.putConstraint(SpringLayout.NORTH, textFieldServerPort, 7,
				SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, textFieldServerPort, 6,
				SpringLayout.EAST, labelServerPort);
		springLayout.putConstraint(SpringLayout.EAST, textFieldServerPort, 105,
				SpringLayout.EAST, labelServerPort);
		textFieldServerPort.setText("56789");
		frame.getContentPane().add(textFieldServerPort);
		textFieldServerPort.setColumns(10);

		lblIp = new JLabel("IP:");
		springLayout.putConstraint(SpringLayout.NORTH, btnStart, 6,
				SpringLayout.SOUTH, lblIp);
		springLayout.putConstraint(SpringLayout.NORTH, lblIp, 6,
				SpringLayout.SOUTH, labelServerPort);
		springLayout.putConstraint(SpringLayout.WEST, lblIp, 0,
				SpringLayout.WEST, labelServerPort);
		frame.getContentPane().add(lblIp);

		try{
			lblUnknown_1 = new JLabel(Inet4Address.getLocalHost()
					.getHostAddress().toString());
			springLayout.putConstraint(SpringLayout.NORTH, lblUnknown_1, 0,
					SpringLayout.NORTH, lblIp);
			springLayout.putConstraint(SpringLayout.WEST, lblUnknown_1, 6,
					SpringLayout.EAST, lblIp);
			springLayout.putConstraint(SpringLayout.EAST, lblUnknown_1, 0,
					SpringLayout.EAST, textFieldServerPort);
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		frame.getContentPane().add(lblUnknown_1);
	}

}
