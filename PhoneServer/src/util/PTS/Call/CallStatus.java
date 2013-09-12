package util.PTS.Call;

import util.PTS.PTS;

public class CallStatus extends Call{
	public CallStatus(){
		super();
	}
	
	public CallStatus(PTS pts){
		super(pts);
	}
	
	public static String getError(){
		return "busy";
	}
	
	public static String getOk(){
		return "ok";
	}
}
