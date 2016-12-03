/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author lhries
 */
public class ServidorPedido {

    public static void main(String[] args) {
        DataInputStream in;
        DataOutputStream out;
        Socket clienteSocket = null;
        ServerSocket listenSocket = null;
        try {
            listenSocket = new ServerSocket(6789);
            System.out.println("Esperando conexao...");
            clienteSocket = listenSocket.accept();
            System.out.println("Cliente conectado! Aguardando pedido...");
            
            in = new DataInputStream(clienteSocket.getInputStream());
            out = new DataOutputStream(clienteSocket.getOutputStream());
            
            String pedido = "";
            do{                
                pedido = in.readUTF();
                if(pedido.equalsIgnoreCase("PING")){
                    System.out.println("Pedido: PING");
                    out.writeUTF("PONG");
                }
                else if(pedido.contains("+")){
                    System.out.println("Pedido: Soma("+pedido+")");
                    String[] soma = pedido.split(Pattern.quote("+"));
                    int op1 = Integer.parseInt(soma[0]);
                    int op2 = Integer.parseInt(soma[1]);
                    out.writeUTF(String.valueOf(op1+op2));                    
                }
                else if(pedido.startsWith("FILE ")){
                    String nomeArquivo = pedido.substring((5));
                    System.out.println("Pedido: FILE - "+nomeArquivo);
                    Scanner scanner = new Scanner(new FileReader("C:\\TEMP\\a.xml"));
                    String texto = scanner.nextLine();
                    while (scanner.hasNextLine()) {                
                        texto+="\n"+scanner.nextLine();
                    }
                    out.writeUTF(texto);
                }
                else if(pedido.equalsIgnoreCase("SAIR"))
                {
                    System.out.println("Saindo da aplicação");
                    out.writeUTF("SAIR");
                }
                else 
                {
                    System.out.println("Opcao Invalida");
                    out.writeUTF("Opcao Invalida");
                }
            }while(!pedido.equalsIgnoreCase("SAIR"));                    
                    
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            try {
                //fecha dentro de um finally para garantir que irá fechar mesmo ocorrendo uma exceção.
                clienteSocket.close();
                listenSocket.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

}
