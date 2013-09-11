package util.PTS.Call;

import util.PTS.PTS;

public class User extends Call{
	public User(){
		super();
	}

	public User(PTS pts){
		super(pts);
	}
	
	static public PTS user(String user){
		PTS tempPTS= new PTS();
		tempPTS.setType("user");
		tempPTS.setValue(user);
		return tempPTS;
	}
}
