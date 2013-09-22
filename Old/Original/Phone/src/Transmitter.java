import javax.media.*;
import javax.media.protocol.*;
import javax.media.format.*;
import javax.media.control.TrackControl;
import javax.media.control.QualityControl;
import javax.media.rtp.*;
import javax.media.rtp.rtcp.*;
import com.sun.media.rtp.*;

public class Transmitter implements Runnable{

	JavaPhoneApp MyApp;
	Thread TransmitThread;
	AVTransmit AV;
	
	public Transmitter(JavaPhoneApp JP){
		MyApp = JP;
		 TransmitThread = new Thread(this,"Audio Phone");
		 TransmitThread.start();
	}
	
	public void run(){
	
	String tp = MyApp.TranPort;
	String Ip = MyApp.IP;                  
	Format fmt = null;
	AV = new AVTransmit(MyApp,new MediaLocator("javasound://8000"),Ip,tp,fmt);	
	String result = AV.start();

        // result will be non-null if there was an error. The return
        // value is a String describing the possible error. Print it.
        if (result != null) {
            System.err.println("Error : " + result);
            System.exit(0);
            //MyApp.display("error")
        }
}
	public void stop(){
		AV.stop();
		MyApp.DisplayMess("Stopping the Transmission...\n");
		//TransmitThread.destroy();
	}	
}	