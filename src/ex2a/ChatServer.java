package ex2a;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author lhries
 */
public class ChatServer extends JFrame {

    private JTextArea textoArea;
    private JTextField textoMensagem;
    private JButton botaoEnviar;

    private String nomeUsuario;

    private PrintWriter escritor;
    private Scanner leitor;

    public ChatServer(String nome) {
        super("Chat do " + nome);
        this.nomeUsuario = nome;
        this.setBounds(100, 100, 500, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        iniciaComponentes();
        iniciaConexao();
        this.setVisible(true);
    }

    public void adicionaTexto(String texto) {
        textoArea.append(texto + "\n");
    }

    public void limpaTexto() {
        textoArea.setText("");
    }

    private void iniciaComponentes() {
        textoMensagem = new JTextField(30);
        textoMensagem.addActionListener(new EnviaMensagem());
        botaoEnviar = new JButton("Enviar");
        botaoEnviar.addActionListener(new EnviaMensagem());
        JPanel painelMensagem = new JPanel();
        painelMensagem.add(new JLabel("Mensagem"));
        painelMensagem.add(textoMensagem);
        painelMensagem.add(botaoEnviar);
        this.add(painelMensagem, BorderLayout.NORTH);

        textoArea = new JTextArea();
        textoArea.setEditable(false);
        this.add(new JScrollPane(textoArea));

    }

    private void iniciaConexao() {
        try {
            ServerSocket listenSocket = new ServerSocket(6789);
            System.out.println("Esperando conexão...");
            Socket socket = listenSocket.accept();

            escritor = new PrintWriter(socket.getOutputStream());
            leitor = new Scanner(socket.getInputStream());

            new Thread(new RecebeMensagem()).start();

            System.out.println("Conectado..");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private class EnviaMensagem implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            escritor.println(nomeUsuario + ": " + textoMensagem.getText());
            escritor.flush();
            textoMensagem.setText("");
            textoMensagem.setRequestFocusEnabled(true);
        }
    }

    private class RecebeMensagem implements Runnable {

        @Override
        public void run() {
            String texto;
            try {
                while ((texto = leitor.nextLine()) != null && !texto.contains("EXIT")) {
                    ChatServer.this.adicionaTexto(texto);
                }
            } catch (Exception e) {
                ChatServer.this.dispose();
            }

        }
    }

    public static void main(String[] args) {
        new ChatServer(JOptionPane.showInputDialog("Digite o nome [servidor]:"));
    }
}
