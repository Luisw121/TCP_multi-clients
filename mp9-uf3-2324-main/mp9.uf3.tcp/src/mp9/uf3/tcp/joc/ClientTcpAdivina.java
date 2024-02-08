package mp9.uf3.tcp.joc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientTcpAdivina extends Thread {
	public static void main(String[] args) {

		String hostname = "localhost";
		int port = 5558;
		boolean continueConnected = true;
		int intents = 0;

		try {
			Socket socket = new Socket(InetAddress.getByName(hostname), port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintStream out = new PrintStream(socket.getOutputStream());
			//el client atén el port fins que decideix finalitzar
			while (continueConnected) {
				String serverData = in.readLine();
				if (serverData.equals("Correcte")) {
					continueConnected = false;
					System.out.println("Campio!");
				} else {
					Scanner scanner = new Scanner(System.in);
					System.out.println("Di un número");
					String guess = scanner.next();
					out.println(guess);
					out.println(intents);
					out.flush();
					intents++;
				}
			}
			socket.close();
		} catch (IOException e) {
			System.out.println("Error de connexió: " + e.getMessage());
		}
	}
}
