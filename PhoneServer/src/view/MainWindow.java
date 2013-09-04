package view;

import javax.swing.JFrame;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;

public class MainWindow {

	public JFrame frame;
	private JLabel labelIP;
	private int serverPort;
	private int port0;
	private int port1;

	/**
	 * Create the application.
	 */
	public MainWindow(int serverPort, int port0, int port1) {
		this.serverPort=serverPort;
		this.port0=port0;
		this.port1=port1;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel labelIPLabel = new JLabel("IP:");

		JLabel lblPort_2 = new JLabel("Unknown");
		try {
			labelIP = new JLabel(Inet4Address.getLocalHost().getHostAddress()
					.toString());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								Alignment.TRAILING,
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																tabbedPane,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																164,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addComponent(
																				labelIPLabel)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				labelIP,
																				GroupLayout.DEFAULT_SIZE,
																				144,
																				Short.MAX_VALUE)))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										groupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(labelIPLabel)
												.addComponent(labelIP))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tabbedPane,
										GroupLayout.DEFAULT_SIZE, 220,
										Short.MAX_VALUE).addContainerGap()));

		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("All Users", null, layeredPane, null);

		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("Logged Users", null, layeredPane_1, null);
		frame.getContentPane().setLayout(groupLayout);
	}
}
