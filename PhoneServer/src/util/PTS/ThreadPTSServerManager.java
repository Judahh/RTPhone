package util.PTS;

import java.util.Vector;

import util.TCP.ThreadTCPServer;

public class ThreadPTSServerManager {
	private ThreadTCPServer threadTCPServer;
	private Vector<String> userOn;
	private Vector<String> userOff;
	private ThreadSinglePTSServerManager threadSinglePTSServerManager;
}
