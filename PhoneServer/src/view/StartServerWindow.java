package view;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartServerWindow {

	public JFrame frame;
	private JButton btnStart;
	private JLabel labelServerPort;
	private JTextField textFieldServerPort;
	private JLabel labelPort0;
	private JTextField textFieldPort0;
	private JLabel lblPort;
	private JTextField textFieldPort1;
	private JLabel lblIp;
	private JLabel lblUnknown;

	/**
	 * Create the application.
	 */
	public StartServerWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);

		labelServerPort = new JLabel("Server Port:");
		springLayout.putConstraint(SpringLayout.NORTH, labelServerPort, 10,
				SpringLayout.NORTH, frame.getContentPane());
		frame.getContentPane().add(labelServerPort);

		btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainWindow window = new MainWindow(Integer.parseInt(textFieldServerPort.getText()),Integer.parseInt(textFieldPort0.getText()),Integer.parseInt(textFieldPort1.getText()));
				window.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		springLayout.putConstraint(SpringLayout.WEST, labelServerPort, 0,
				SpringLayout.WEST, btnStart);
		springLayout.putConstraint(SpringLayout.WEST, btnStart, 10,
				SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, btnStart, -10,
				SpringLayout.SOUTH, frame.getContentPane());
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

		labelPort0 = new JLabel("Port 1:");
		springLayout.putConstraint(SpringLayout.NORTH, labelPort0, 12,
				SpringLayout.SOUTH, labelServerPort);
		springLayout.putConstraint(SpringLayout.WEST, labelPort0, 0,
				SpringLayout.WEST, labelServerPort);
		frame.getContentPane().add(labelPort0);

		textFieldPort0 = new JTextField();
		textFieldPort0.setText("16384");
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPort0, 6,
				SpringLayout.SOUTH, textFieldServerPort);
		springLayout.putConstraint(SpringLayout.WEST, textFieldPort0, 10,
				SpringLayout.EAST, labelPort0);
		springLayout.putConstraint(SpringLayout.EAST, textFieldPort0, 0,
				SpringLayout.EAST, textFieldServerPort);
		frame.getContentPane().add(textFieldPort0);
		textFieldPort0.setColumns(10);

		lblPort = new JLabel("Port 2:");
		springLayout.putConstraint(SpringLayout.NORTH, lblPort, 12,
				SpringLayout.SOUTH, labelPort0);
		springLayout.putConstraint(SpringLayout.WEST, lblPort, 0,
				SpringLayout.WEST, labelServerPort);
		frame.getContentPane().add(lblPort);

		textFieldPort1 = new JTextField();
		textFieldPort1.setText("32766");
		springLayout.putConstraint(SpringLayout.NORTH, textFieldPort1, 6,
				SpringLayout.SOUTH, textFieldPort0);
		springLayout.putConstraint(SpringLayout.WEST, textFieldPort1, 0,
				SpringLayout.WEST, textFieldPort0);
		springLayout.putConstraint(SpringLayout.EAST, textFieldPort1, 0,
				SpringLayout.EAST, textFieldServerPort);
		frame.getContentPane().add(textFieldPort1);
		textFieldPort1.setColumns(10);

		lblIp = new JLabel("IP:");
		springLayout.putConstraint(SpringLayout.NORTH, lblIp, 6,
				SpringLayout.SOUTH, lblPort);
		springLayout.putConstraint(SpringLayout.WEST, lblIp, 0,
				SpringLayout.WEST, labelServerPort);
		frame.getContentPane().add(lblIp);

		lblUnknown = new JLabel("Unknown");
		try {
			lblUnknown = new JLabel(Inet4Address.getLocalHost()
					.getHostAddress().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		springLayout.putConstraint(SpringLayout.NORTH, lblUnknown, 0,
				SpringLayout.NORTH, lblIp);
		springLayout.putConstraint(SpringLayout.WEST, lblUnknown, 6,
				SpringLayout.EAST, lblIp);
		springLayout.putConstraint(SpringLayout.EAST, lblUnknown, 0,
				SpringLayout.EAST, textFieldServerPort);
		frame.getContentPane().add(lblUnknown);
	}

}
