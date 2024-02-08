package mp9.uf3.tcp.exemples;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class TcpServer {
    public static void main(String[] args) {
        int portNumber = 12345;

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("Servidor iniciado. Esperando conexiones..");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado desde " + clientSocket.getInetAddress().getHostAddress());

                Thread clientHandler = new Thread(new ClientHandler(clientSocket));
                clientHandler.start();
            }
        }catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
    static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        @Override
        public void run() {
            try (
                    ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                    ) {
                Llista lista = (Llista) inFromClient.readObject();
                System.out.println("Lista recibida del cliente: " + lista.getNom() + " - " + lista.getNumberList());

                List<Integer> sortedUniqueList = lista.getNumberList().stream()
                        .sorted()
                        .distinct()
                        .collect(Collectors.toList());

                Llista result = new Llista(lista.getNom(), sortedUniqueList);

                outToClient.writeObject(result);
                System.out.println("Lista ordenada y sin duplicados enviada al cliente.");
            }catch (IOException | ClassNotFoundException e) {
                System.out.println("Error en la comunicación con el cliente: " + e.getMessage());
            }


        }
    }
}
