package mp9.uf3.tcp.exemples;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.List;

public class TCPClient {
    public static void main(String[] args) {
        String serverName = "localhost";
        int portNumber = 12345;

        try {
            Socket socket = new Socket(serverName, portNumber);
            ObjectOutputStream outToServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(socket.getInputStream());

            List<Integer> numbers = Arrays.asList(5, 2, 7, 2, 9, 5, 1, 8);
            Llista lista = new Llista("Lista de numeros ", numbers);

            outToServer.writeObject(lista);
            System.out.println("Lista enviada al servidor");

            Llista result = (Llista) inFromServer.readObject();
            System.out.println("Lista ordenada y sin duplicados recibida del servidor: " + result.getNom() + " - " + result.getNumberList());
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Error en la comunicación con el servidor: " + e.getMessage());
        }
    }
}
