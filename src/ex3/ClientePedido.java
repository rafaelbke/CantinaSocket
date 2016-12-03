package ex3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lhries
 */
public class ClientePedido {

    public static void main(String[] args) {
        DataInputStream in;
        DataOutputStream out;
        Socket socket = null;
        try {
            socket = new Socket("localhost", 6789);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            String resposta = "";

            do {
                //Le o pedido do usuario e envia para o servidor
                System.out.print("Pedido: ");
                String pedido = new Scanner(System.in).nextLine();
                out.writeUTF(pedido);
                //Aguarda pela resposta e mostra o resultado
                resposta = in.readUTF();
                System.out.println("Resposta: "+resposta);
            } while (!resposta.equalsIgnoreCase("SAIR"));

        } catch (IOException ex) {
            Logger.getLogger(ClientePedido.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
