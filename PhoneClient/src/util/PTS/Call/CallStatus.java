package util.PTS.Call;

public class CallStatus extends Call{
	public CallStatus(){
		super();
	}
	
	public static String getError(){
		return "busy";
	}
	
	public static String getOk(){
		return "ok";
	}
}
