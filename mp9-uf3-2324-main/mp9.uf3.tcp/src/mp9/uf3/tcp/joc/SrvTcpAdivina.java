package mp9.uf3.tcp.joc;

import mp9.uf3.udp.unicast.joc.SecretNum;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SrvTcpAdivina {
public static void main(String[] args) {
	
	int port = 5558;
	SecretNum ns = new SecretNum(100);
	
	try {
		ServerSocket serverSocket = new ServerSocket(port);
		while (true) {
			Socket clientSocket = serverSocket.accept();
			ThreadSevidorAdivina thread = new ThreadSevidorAdivina(clientSocket, ns);
			thread.run();
		}
	}catch (IOException e) {
		System.out.println("Error de conexió" + e.getMessage());
	}
	

}

}
