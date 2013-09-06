package util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Receive extends Thread {
	BufferedReader input;

	public Receive(InputStream input) {
		this.input = new BufferedReader(new InputStreamReader(input));
	}

	public String receive() throws IOException {
		return this.input.readLine();
	}

	@Override
	public void run() {
		try {
			while (true) {
				System.out.println("received:" + receive());
			}
		} catch (IOException e) {

		}
	}
}
