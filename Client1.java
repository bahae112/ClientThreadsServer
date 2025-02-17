import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client1 {
    public static void main(String[] args) {
        String host = "localhost"; // Adresse du serveur
        int port = 123; // Même port que le serveur

        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Client 1 connecté au serveur. Tapez vos messages ('fin' pour quitter) :");

            String message;
            while (true) {
                System.out.print("Client 1 > ");
                message = scanner.nextLine();

                out.println(message);

                if (message.equalsIgnoreCase("fin")) {
                    System.out.println("Client 1 se déconnecte.");
                    break;
                }

                // Lire la réponse du serveur
                String response = in.readLine();
                System.out.println("Serveur : " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
