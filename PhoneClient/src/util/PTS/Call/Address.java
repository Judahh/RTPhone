package util.PTS.Call;

import util.PTS.PTS;

public class Address extends Call {
	private String address;

	public Address() {
		super();
	}

	public Address(PTS pts) {
		super(pts);
	}

	public Address(String address) {
		super();
		this.address = address;
	}

	public PTS getAddress() {
		PTS tempPTS = new PTS();
		tempPTS.setType("address");
		tempPTS.setValue(address);
		return tempPTS;
	}

	public boolean isAddress() {
		if (pts.getType().equals("log")) {
			if (pts.getPts().size() > 0) {
				if (pts.getPts().get(0).getType().equals("address")) {
					return true;
				}
			}
		}
		return false;
	}
}
