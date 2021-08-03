package sistema.telas;

import javax.swing.JLabel;
import javax.swing.JPanel;
import sistema.Navegador;

public class Inicio extends JPanel
{
    JLabel labelTitulo;
    
    public Inicio()
    {
        criarComponentes();
        criarEventos();
        Navegador.habilitaMenu();
    }
    
    private void criarComponentes()
    {
        setLayout(null); // defindo que não será usado nenhum gerenciador de layout
        
        labelTitulo = new JLabel("escolha uma opção no menu superior", JLabel.CENTER);
        
        labelTitulo.setBounds(20, 100, 660, 40);
        
        add(labelTitulo);
        
        setVisible(true);
    }
    
    private void criarEventos(){}
}
