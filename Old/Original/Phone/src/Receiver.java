import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.TrackControl;
import javax.media.control.QualityControl;
import javax.media.rtp.*;
import javax.media.rtp.rtcp.*;
import com.sun.media.rtp.*;

	public class Receiver implements Runnable{
	JavaPhoneApp MyApp;
	Thread ReceiveThread;
	AVReceive AV;
	
	public Receiver(JavaPhoneApp JP){
		 MyApp = JP;
		 ReceiveThread = new Thread(this,"Audio-Phone");
		 ReceiveThread.start();
	}
	
	public void run(){
		String rp = MyApp.RecPort;
		String Ip = MyApp.IP;
		String temp = Ip+"/"+rp;
		String []info = new String[1];
		info[0] = temp;
		//System.out.println(temp);
		
		AV = new AVReceive(info, MyApp);
		MyApp.DisplayMess("Waiting for Incoming call..\n");
		if (!AV.initialize()) {
			MyApp.DisplayMess("Receiver Timing out!!!! Start again!!!\n");
            		//System.err.println("Failed to initialize the sessions.");
            		System.exit(-1);
        	}
		
        	// Check to see if AVReceive is done.
        	try {
            	while (!AV.isDone())
                Thread.sleep(1000);
        	} catch (Exception e) {}
	}
	public void stop(){
		AV.close();
		MyApp.DisplayMess("Receiver Thread stopped....\n");
		//ReceiveThread.destroy();
	}
}

		
		
		     