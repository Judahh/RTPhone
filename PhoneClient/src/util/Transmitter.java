package util;

import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.TrackControl;
import javax.media.control.QualityControl;
import javax.media.rtp.*;
import javax.media.rtp.rtcp.*;
import com.sun.media.rtp.*;

public class Transmitter implements Runnable {

	private int port;
	private String host;
	private Thread transmitThread;
	private AVTransmit AVTransmit;

	public Transmitter(String host, int port) {
		this.transmitThread = new Thread(this, "Audio Phone");
		this.transmitThread.start();
		this.host = host;
		this.port = port;
	}

	public void run() {
		Format fmt = null;
		AVTransmit = new AVTransmit(new MediaLocator("javasound://8000"),
				this.host, this.port, fmt);
		String result = AVTransmit.start();

		// result will be non-null if there was an error. The return
		// value is a String describing the possible error. Print it.
		if (result != null) {
			System.err.println("Error : " + result);
			System.exit(0);
		}
	}

	public void stop() {
		AVTransmit.stop();
	}

	public AVTransmit getAVTransmit() {
		return AVTransmit;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public Thread getTransmitThread() {
		return transmitThread;
	}
}