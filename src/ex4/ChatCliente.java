package ex4;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatCliente extends JFrame{
	private final static String ENDERECO="localhost";
	private final static int PORTA=6789;
	
	private String nomeUsuario;
	private JTextField textoEnviar;
	private JTextArea textoRecebido;
	private JButton botaoEnviar;
	private PrintWriter escritor;
	private Scanner leitor;
	
	public ChatCliente (String nome){
		super("Chat do "+nome);
		this.nomeUsuario = nome;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100,100,400,400);
		
		iniciaComponentes();
		
		iniciaConexao();
		
		this.setVisible(true);	
	}


	private void iniciaComponentes() {
		JPanel painelEnvio = new JPanel(new BorderLayout());
		textoEnviar  = new JTextField();
		textoEnviar.addActionListener(new EnviaMensagem());
		painelEnvio.add(textoEnviar, BorderLayout.CENTER);	
		botaoEnviar  = new JButton("Enviar");
		botaoEnviar.addActionListener(new EnviaMensagem());
		painelEnvio.add(botaoEnviar, BorderLayout.EAST);	
		this.add(painelEnvio, BorderLayout.NORTH);
		
		textoRecebido = new JTextArea();
		textoRecebido.setEditable(false);
		JScrollPane scroll = new JScrollPane(textoRecebido);
		this.add(scroll, BorderLayout.CENTER);
		
	}
	private void iniciaConexao() {
		try {
			
			Socket socket = new Socket(ENDERECO, PORTA);
			escritor = new PrintWriter(socket.getOutputStream());
			leitor = new Scanner(socket.getInputStream());
			new Thread(new RecebeServidor()).start();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class RecebeServidor implements Runnable{

		@Override
		public void run() {
			String texto;
			while((texto = leitor.nextLine())!=null)
			{
				textoRecebido.append(texto+"\n");
			}
		}
		
	}
	
	private class EnviaMensagem implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			escritor.println(nomeUsuario+": "+textoEnviar.getText());
			escritor.flush();
			textoEnviar.setText("");
			textoEnviar.setRequestFocusEnabled(true);
		}
		
	}
	
	public static void main(String args[]){
		new ChatCliente(JOptionPane.showInputDialog("Digite o nome:"));
	}
}
