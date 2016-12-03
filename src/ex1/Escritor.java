package ex1;


import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Escritor {

    //Escritor fica aguardando conexão do Leitor.
    public static void main(String args[]) {
        DataInputStream in;
        Socket clienteSocket = null;
        ServerSocket listenSocket = null;
        try {
            String portaString = JOptionPane.showInputDialog("Porta: ");
            int porta = Integer.parseInt(portaString);
            listenSocket = new ServerSocket(porta);

            JanelaTexto janela = new JanelaTexto(portaString);
            janela.adicionaTexto("Aguardando conexao ...");
            
            clienteSocket = listenSocket.accept();
            janela.adicionaTexto("Conectado com o cliente...");
            in = new DataInputStream(clienteSocket.getInputStream());
            String mensagem = "";
            do {
                mensagem = in.readUTF();
                janela.adicionaTexto(mensagem);
            } while (!mensagem.equalsIgnoreCase("SAIR"));
            janela.dispose();
            System.out.println("Fim da comunicação!");

        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                //fecha dentro de um finally para garantir que irá fechar mesmo ocorrendo uma exceção.
                clienteSocket.close();
                listenSocket.close();
            } catch (IOException e) {/*close failed*/

            }
        }
    }

}
