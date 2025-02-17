import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int port = 123; // Port d'écoute

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Serveur en attente de connexions sur le port " + port);

            while (true) { // Boucle infinie pour accepter plusieurs clients
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());

                // Lancer un thread pour gérer le client
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// Classe qui gère chaque client dans un thread séparé
class ClientHandler extends Thread {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Message reçu : " + message);

                if (message.equalsIgnoreCase("fin")) {
                    System.out.println("Un client s'est déconnecté.");
                    break;
                }

                // Répondre au client
                out.println("Message bien reçu : " + message);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
