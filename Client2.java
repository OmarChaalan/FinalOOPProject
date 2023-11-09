package application;

import java.io.*;
import java.net.Socket;

public class Client2 {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private BufferedReader consoleReader;

    public Client2() {
        try {
            socket = new Socket("192.168.8.158", 5000);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            consoleReader = new BufferedReader(new InputStreamReader(System.in));

            Thread messageListener = new Thread(this::listenForMessages);
            messageListener.start();
			/*
			 * String userInput; while ((userInput = consoleReader.readLine()) != null) {
			 * sendMessage("Client1: " + userInput); }
			 * 
			 * reader.close(); writer.close(); socket.close();
			 */
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from Server: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}