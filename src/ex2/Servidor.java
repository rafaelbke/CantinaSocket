package ex2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lhries
 */
public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket listenSocket = new ServerSocket(6789);
            
            Socket socket = listenSocket.accept();
            
            new Emissor(socket.getOutputStream()).start();
            new Receptor(socket.getInputStream()).start();
            
            
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
