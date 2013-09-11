package util.PTS.Call;

import util.PTS.PTS;
import util.PTS.PTSBasicCommunication;

public class Call extends PTSBasicCommunication{
	public Call(){
		super();
	}

	public Call(PTS pts){
		super(pts);
	}
	
	public static String getError(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("call");
		ptsTemp.setValue(CallStatus.getError());
		return (ptsTemp.toString());
	}
	
	static public PTS call(String user){
		PTS tempPTS= new PTS();
		tempPTS.setType("call");
		tempPTS.setValue(User.user(user));
		return tempPTS;
	}
}
