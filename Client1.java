package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client1 {

	BufferedWriter writer;

	public Client1() {
		prepareStuff();
	}

	void prepareStuff() {
		try {
			Socket clientSocket = new Socket("192.168.8.158", 5000);
			System.out.println("Connected to the server!");

			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void sendMessage(String msg) {
		try {
			writer.write("Ahmad");

			writer.newLine();
			writer.write(msg);
			writer.newLine();
			writer.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class ClientReader extends Thread {
		private BufferedReader reader;

		public ClientReader(BufferedReader reader) {
			this.reader = reader;
		}

		public void run() {
			try {
				String message;
				while ((message = reader.readLine()) != null) {
					System.out.println(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}