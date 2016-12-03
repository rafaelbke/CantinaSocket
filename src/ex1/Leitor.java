package ex1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Leitor {

    //Leitor se conecta aos Escritores (três)
    public static void main(String[] args) {
        Socket conexao[] = new Socket[3];
        DataOutputStream out[] = new DataOutputStream[3];
        int[] serverPort = {7771, 7772, 7773};
        String[] serverIP = {"localhost", "localhost", "localhost"};
        
        String mensagem1 = "";
            
                mensagem1 = JOptionPane.showInputDialog("Rodando: ");
        
        try {
            for (int i = 0; i < 3; i++) {
                conexao[i] = new Socket(serverIP[i], serverPort[i]);
                System.out.println("Conectado com o servidor da porta " + serverPort[i]);
                out[i] = new DataOutputStream(conexao[i].getOutputStream());
            }

            String mensagem = "";
            do {
                mensagem = JOptionPane.showInputDialog("Mensagem: ");
                for (int i = 0; i < 3; i++) {
                    out[i].writeUTF(mensagem);
                }
            } while (!mensagem.equalsIgnoreCase("SAIR"));

            System.out.println("Finalizando a aplicação ...");
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        } finally {
            for (int i = 0; i < 3; i++) {
                if (conexao[i] != null) {
                    try {
                        conexao[i].close();
                    } catch (IOException e) {
                        System.out.println("Close falhou!");
                    }
                }
            }
        }
    }

}
