/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ex5;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author lhries
 */
class Emissor {
    ObjectOutputStream objetoOutputStream;

    public Emissor(OutputStream outputStream) {
        try {        
            this.objetoOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(Emissor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void enviar(Object objeto){
        try {
            objetoOutputStream.writeObject(objeto);
        } catch (IOException ex) {
            Logger.getLogger(Emissor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
