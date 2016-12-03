package ex4;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ChatServer {
	private final static String ENDERECO = "localhost";
	private final static int PORTA = 6789;
	List<PrintWriter> escritorClientes = new ArrayList<PrintWriter>();
	
	public ChatServer() {
		ServerSocket listenSocket;
		try {
			listenSocket = new ServerSocket(PORTA);
			while (true) {
				Socket clientSocket = listenSocket.accept();
				
				PrintWriter printwriter = new PrintWriter(clientSocket.getOutputStream());
				escritorClientes.add(printwriter);
				
				EscutaCliente escuta = new EscutaCliente(clientSocket);
				new Thread(escuta).start();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void enviaParaTodos(String msg){
		for(PrintWriter e: escritorClientes){
			e.println(msg);
			e.flush();
		}
	}

	private class EscutaCliente implements Runnable{
		private Scanner leitor;
		public EscutaCliente(Socket cliente)
		{
			try {
				leitor = new Scanner(cliente.getInputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			String texto;
			while((texto=leitor.nextLine())!=null)
			{
				System.out.println(texto);
				ChatServer.this.enviaParaTodos(texto);
			}
			
		}
	
	}
	public static void main(String args[]) {
		new ChatServer();
	}
}
