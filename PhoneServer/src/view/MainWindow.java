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
import javax.swing.JList;

public class MainWindow{

	public JFrame			frame;
	private JLabel			labelIP;
	private JList<String>	loggedUsersList;
	private JList<String>	allUsersList;

	/**
	 * Create the application.
	 */
	public MainWindow(){
		initialize();
		while(!Main.server.isStopped()){
			loggedUsersList = new JList<>(Main.server.getLogged());
			allUsersList = new JList<>(Main.server.getResgistered());
		}
		Main.startServerWindow = new StartServerWindow();
		Main.startServerWindow.frame.setVisible(true);
		frame.setVisible(false);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel labelIPLabel = new JLabel("IP:");

		try{
			labelIP = new JLabel(Inet4Address.getLocalHost().getHostAddress()
					.toString());
		}catch(UnknownHostException e){
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

		loggedUsersList = new JList();
		tabbedPane.addTab("Logged Users", null, loggedUsersList, null);

		allUsersList = new JList();
		tabbedPane.addTab("All Users", null, allUsersList, null);
		frame.getContentPane().setLayout(groupLayout);
	}
}
