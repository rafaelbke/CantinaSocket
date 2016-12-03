package ex5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lhries
 */
public class Servidor {
    public static void main(String[] args) {
         System.out.println("servidor Rodando");
        try {
            ServerSocket listenSocket = new ServerSocket(6789);
            
            Socket socket = listenSocket.accept();
            
           
            
            
            Receptor r = new Receptor(socket.getInputStream());
            
            List<Login> lista = (List<Login>) r.receber();
            System.out.println(lista);
            
            
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
