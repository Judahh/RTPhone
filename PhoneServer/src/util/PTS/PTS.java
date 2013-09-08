package util.PTS;

import java.util.Vector;

public class PTS {
	private String type;
	private int size;
	private Vector<PTS> pts;
	private String value;

	public PTS() {
		this.pts = new Vector<>();
		this.size = 0;
		this.type = new String();
		this.value = new String();
	}

	public PTS(String received) {
		this.pts = new Vector<>();
		this.size = 0;
		this.type = new String();
		this.value = new String();
		init(received);
	}

	public Vector<PTS> getPts() {
		if (!this.pts.isEmpty()) {
			this.value = new String();
		}
		return pts;
	}

	public int getSize() {
		return size;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		if (!this.value.isEmpty()) {
			this.pts = new Vector<>();
		}
		return value;
	}

	public boolean isValue() {
		return !value.isEmpty();
	}

	public void setValue(String value) {
		this.value = value;
		this.pts = new Vector<>();
	}

	public void setValue(PTS pts) {
		this.value = new String();
		this.pts = new Vector<>();
		this.pts.add(pts);
	}

	public void setValue(Vector<PTS> pts) {
		this.value = new String();
		this.pts = pts;
	}

	public void addValue(PTS pts) {
		this.value = new String();
		this.pts.add(pts);
	}

	public void addValue(Vector<PTS> pts) {
		this.value = new String();
		this.pts.addAll(pts);
	}

	public void init(String received) {
		for (int index = 0; index < received.length(); index++) {
			if (received.charAt(index) == '<') {
				index++;
				for (; (index < received.length())
						&& (received.charAt(index) != '>'); index++) {
					type += received.charAt(index);
				}

				int start = index + 1;

				String temporary = received.substring(start);
				String end = "<" + type + ">";

				String temporaryA = new String();
				temporaryA = temporary.substring(0, temporary.indexOf(end));
				this.size = received.indexOf(end, start) + end.length();

				for (int index2 = 0; index2 < temporaryA.length(); index2++) {
					if (temporaryA.charAt(index2) == '<') {
						PTS newPts = new PTS(temporaryA.substring(index2));
						this.pts.add(newPts);
						index2 += newPts.getSize() - 1;
					} else {
						this.value = temporaryA.substring(index2);
						index2 = temporaryA.length();
					}
				}

				index = received.length();// out
			}
		}
	}

	@Override
	public String toString() {
		String string = new String();
		string += "<" + type + ">";
		if (!this.value.isEmpty()) {
			string += this.value;
		} else {
			if (!this.pts.isEmpty()) {
				for (int index = 0; index < this.pts.size(); index++) {
					string += this.pts.get(index);
				}
			}
		}
		string += "<" + type + ">";
		return string;
	}

	public void setType(String type) {
		this.type=type;
	}
}