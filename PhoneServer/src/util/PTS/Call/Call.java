package util.PTS.Call;

import util.PTS.PTS;
import util.PTS.PTSBasicCommunication;
import util.PTS.Log.In;
import util.PTS.Log.Off;
import util.PTS.Log.On;

public class Call extends PTSBasicCommunication{
	private Address	address;
	private User	callerUser;
	private User	user;

	public Call(){
		super();
	}

	public Call(PTS pts){
		super(pts);
	}

	public Call(PTS pts, String callerUsername, Address address){
		super(pts);
		this.address = address;
		this.callerUser = new User(callerUsername);
		if(pts.getPts().size()>0){
			if(pts.getPts().get(0).getType().equals("user")){
				this.user = new User(pts.getPts().get(0).getValue());
			}
		}
	}

	public static String getError(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("call");
		ptsTemp.setValue(CallStatus.getError());
		return(ptsTemp.toString());
	}

	public static String getOk(){
		PTS ptsTemp = new PTS();
		ptsTemp.setType("call");
		ptsTemp.setValue(CallStatus.getOk());
		return(ptsTemp.toString());
	}

	static public PTS call(String user){
		PTS tempPTS = new PTS();
		tempPTS.setType("call");
		tempPTS.setValue(User.user(user));
		return tempPTS;
	}

	@Override
	protected void start(){
		// TODO Auto-generated method stub
		if(!pts.isValue()){
			// System.out.println("received:"+pts.toString());
			if(pts.getValue().equals(CallStatus.getOk())
					|| pts.getValue().equals(CallStatus.getError())){
				CallStatus callStatus = new CallStatus(pts);
				addBroadcast(callStatus.getBroadcast());
				addToCheck(callStatus.getToCheck());
				setToSend(callStatus.getToSend());
			}else if(pts.getPts().get(0).getType().equals("user")){
				PTS tempPTS = new PTS();
				tempPTS.setType("call");
				tempPTS.addValue(this.address.getAddress());
				tempPTS.addValue(this.callerUser.getUser());
				tempPTS.addValue(this.user.getUser());
				addToCheck(tempPTS.toString());
			}
		}
	}
}
