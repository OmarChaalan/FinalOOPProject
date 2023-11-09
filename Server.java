package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Map<String, BufferedWriter> clientWriters = new HashMap<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server is up and running!");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientName = reader.readLine();  
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                clientWriters.put(clientName, writer);  

                ClientHandler clientHandler = new ClientHandler(clientSocket, reader, clientName);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader reader;
        private String clientName;
        private String recipientName;

        public ClientHandler(Socket clientSocket, BufferedReader reader, String clientName) {
            this.clientSocket = clientSocket;
            this.reader = reader;
            this.clientName = clientName;
        }

        public void run() {
            try {
                recipientName = reader.readLine();  

                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Received from " + clientName + ": " + message);
                    sendPrivateMessage(clientName, message);
                }

                reader.close();
                clientSocket.close();
                clientWriters.remove(clientName);  
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void sendPrivateMessage(String senderName, String message) {
            try {
                BufferedWriter recipientWriter = clientWriters.get(recipientName);
                if (recipientWriter != null) {
                    recipientWriter.write("Private message from " + senderName + ": " + message);
                    recipientWriter.newLine();
                    recipientWriter.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
