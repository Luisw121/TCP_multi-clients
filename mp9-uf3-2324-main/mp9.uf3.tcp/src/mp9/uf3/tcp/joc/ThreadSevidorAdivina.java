package mp9.uf3.tcp.joc;

import mp9.uf3.udp.unicast.joc.SecretNum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ThreadSevidorAdivina implements Runnable {
/* Thread que gestiona la comunicació de SrvTcPAdivina.java i un cllient ClientTcpAdivina.java */
	
	Socket clientSocket;
	BufferedReader in;
	PrintStream out;
	String msgEntrant, msgSortint;
	SecretNum ns;
	boolean acabat;
	int intentsJugador;
	
	public ThreadSevidorAdivina(Socket clientSocket, SecretNum ns) throws IOException {
		this.clientSocket = clientSocket;
		this.ns = ns;
		acabat = false;
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		out= new PrintStream(clientSocket.getOutputStream());
		
	}

	@Override
	public void run() {
		try {
			while(!acabat) {
				
				msgSortint = in.readLine();
				intentsJugador = Integer.parseInt(in.readLine());
				String msgSortint = generaResposta(msgEntrant);
				out.println(msgSortint);
				out.flush();
				
			}
		}catch(IOException e){
			System.out.println("Error de connexió: " + e.getMessage());
		}finally {
			try {
				clientSocket.close();
			}catch (IOException e) {
				System.out.println("Error de tencament de la connexió: " + e.getMessage());
			}
		}

	}
	
	public String generaResposta(String en) {
		String ret;
		
		if(en == null) ret="Benvingut al joc!";
		else {
			ret = ns.comprova(en);
			if(ret.equals("Correcte")) {
				acabat = true;
			}
		}
		return ret;
	}

}
