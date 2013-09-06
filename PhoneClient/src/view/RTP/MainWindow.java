package view.RTP;

import javax.swing.JFrame;
import javax.swing.SpringLayout;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JButton;

public class MainWindow {

	public JFrame frame;
	private String host;
	private String username;

	/**
	 * Create the application.
	 */
	public MainWindow(String host,String username) {
		this.host=host;
		this.username=username;
		initialize();
	}

	public String getHost() {
		return host;
	}
	
	public String getUsername() {
		return username;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 200, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPane, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, tabbedPane, 10, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPane, -44, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, tabbedPane, -10, SpringLayout.EAST, frame.getContentPane());
		frame.getContentPane().add(tabbedPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Logged Users", null, layeredPane, null);
		
		JLayeredPane layeredPane_1 = new JLayeredPane();
		tabbedPane.addTab("All Users", null, layeredPane_1, null);
		
		JButton btnCall = new JButton("Call");
		springLayout.putConstraint(SpringLayout.NORTH, btnCall, 6, SpringLayout.SOUTH, tabbedPane);
		springLayout.putConstraint(SpringLayout.WEST, btnCall, 10, SpringLayout.WEST, frame.getContentPane());
		frame.getContentPane().add(btnCall);
	}
}
