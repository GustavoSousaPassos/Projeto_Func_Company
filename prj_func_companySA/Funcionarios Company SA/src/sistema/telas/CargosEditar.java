package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sistema.BancoDeDados;
import sistema.entidades.Cargo;

public class CargosEditar extends JPanel
{
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoGravar;
    
    public CargosEditar(Cargo cargo)
    {
        cargoAtual = cargo;
        criarComponentes();
        criarEventos();
    }
    
    private void criarComponentes()
    {
        setLayout(null); // não será usado nenhum gerenciadopr de layout
        
        // instanciando os componentes
        
        labelTitulo = new JLabel("Editar cargo", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField(cargoAtual.getNome());
        botaoGravar = new JButton("Salvar");
        
        // definindo o posicionamento e o tamanho
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoGravar.setBounds(250, 380, 200, 40);
        
        // Adicionando os componentes na tela
        
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(botaoGravar);
        
        // Deixar a tela visivel
        setVisible(true);
    }   
        
    private void criarEventos()
    {
        botaoGravar.addActionListener(new ActionListener()
        {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               cargoAtual.setNome(campoCargo.getText());
               
               sqlAtualizaCargo();
           }
        });
    }
    
    private void sqlAtualizaCargo()
    {
        // validando nome
        
        if(campoCargo.getText().length() <= 3)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente");
            return;
        }
        
        // Conexão
        Connection conexao;
        // Instrução SQl
        Statement instrucaoSQL;
        // Resultados
        ResultSet resultados;
        
        try
        {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // Criando instrução SQL
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            instrucaoSQL.executeUpdate("UPDATE cargo SET nome='"+campoCargo.getText()+"' WHERE id="+cargoAtual.getId());
            
            JOptionPane.showMessageDialog(null, "Cargo atualizado com sucesso!");
            
            conexao.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o cargo");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}    
