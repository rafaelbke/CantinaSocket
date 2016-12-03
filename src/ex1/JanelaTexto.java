package ex1;



import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class JanelaTexto extends JFrame {

    JTextArea textoArea;

    public JanelaTexto() {
        super("Janela do Escritor");
        setBounds(100, 100, 400, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        iniciaComponentes();
        JanelaTexto.this.setVisible(true);
    }
    
    public JanelaTexto(String nome) {
        this();
        this.setTitle(nome);
    }    

    private void iniciaComponentes() {
        textoArea = new JTextArea();
        this.add(new JScrollPane(textoArea));
    }

    public void adicionaTexto(String texto) {
        textoArea.append(texto + "\n");
    }

    public void limpaTexto() {
        textoArea.setText("");
    }
}
