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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;


public class CargosConsultar extends JPanel
{
    Cargo cargoAtual;
    JLabel labelTitulo, labelCargo;
    JTextField campoCargo;
    JButton botaoPesquisar, botaoEditar, botaoExcluir;
    DefaultListModel<Cargo> listasCargoModelo = new DefaultListModel();
    JList<Cargo> listaCargos;
    
    public CargosConsultar()
    {
        criarComponentes();
        criarEventos();
    }
    
    private void criarComponentes()
    {
        setLayout(null); // não será utilizado nunhum gerenciador de layout
        
        // instanciando os componentes
        
        labelTitulo = new JLabel("Consulta de cargo", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelCargo = new JLabel("Nome do cargo", JLabel.LEFT);
        campoCargo = new JTextField();
        botaoPesquisar = new JButton("Pesquisar cargo");
        botaoEditar = new JButton("Editar cargo");
        botaoEditar.setEnabled(false);
        botaoExcluir = new JButton("Excluir cargo");
        botaoExcluir.setEnabled(false);
        listasCargoModelo = new DefaultListModel();
        listaCargos = new JList();
        listaCargos.setModel(listasCargoModelo);
        listaCargos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        // definindo o posicionamento e o tamanho dos componentes
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelCargo.setBounds(150, 120, 400, 20);
        campoCargo.setBounds(150, 140, 400, 40);
        botaoPesquisar.setBounds(560, 140, 130, 40);
        listaCargos.setBounds(150, 200, 400, 240);
        botaoEditar.setBounds(560, 360, 130, 40);
        botaoExcluir.setBounds(560, 400, 130, 40);
        
        // Adicionando os componentes na tela
        
        add(labelTitulo);
        add(labelCargo);
        add(campoCargo);
        add(listaCargos);
        add(botaoPesquisar);
        add(botaoEditar);
        add(botaoExcluir);
        
        setVisible(true); // torna a tela visivel
    }
    
    private void criarEventos()
    {
        botaoPesquisar.addActionListener(new ActionListener()
        {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               sqlPesquisarCargos(campoCargo.getText());
           }
        });
        
        botaoEditar.addActionListener(new ActionListener()
        {
           @Override
           public void actionPerformed(ActionEvent e)
           {
               Navegador.cargosEditar(cargoAtual);
           }
        });
        
        botaoExcluir.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                sqlDeletarCargo();
            }
        });
        
        // Ao selecionar algum cargo na lista, o botão de editar e excluir serão habilitados
        listaCargos.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                cargoAtual = listaCargos.getSelectedValue();
                if(cargoAtual == null)
                {
                    botaoEditar.setEnabled(false);
                    botaoExcluir.setEnabled(false);
                }
                else
                {
                    botaoEditar.setEnabled(true);
                    botaoExcluir.setEnabled(true);
                }
                
            }
        });
    }
    
    private void sqlPesquisarCargos(String nome)
    {
        // Conexão
        Connection conexao;
        // Instrução SQL
        Statement instrucaoSQL;
        // Resultados
        ResultSet resultados;
        
        try
        {
            // conectando ao banoc de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // Criando instrução SQL
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargo WHERE nome like '%"+nome+"%'");
            
            listasCargoModelo.clear();
            
            while(resultados.next())
            {
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                
                listasCargoModelo.addElement(cargo);
            }
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao consultar os cargos");
            Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    private void sqlDeletarCargo()
    {
        int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o cargo "
        + cargoAtual.getNome() + "?", "Excluir", JOptionPane.YES_NO_OPTION);
        
        if(confirmacao == JOptionPane.YES_OPTION)
        {
            // Conexão
            Connection conexao;
            // Instrução SQL
            Statement instrucaoSQL;
            // Resultados
            ResultSet resultados;
            
            try
            {
                // Conectando ao banco de dados
                conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
                
                // Criando instrução SQL
                instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                instrucaoSQL.executeUpdate("DELETE FROM cargo WHERE id="+cargoAtual.getId()+"");
                
                JOptionPane.showMessageDialog(null, "Cargo deletado com sucesso");
            }
            catch(SQLException ex)
            {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao excluir o cargo");
                Logger.getLogger(CargosInserir.class.getName()).log(Level.SEVERE, null, ex);
                
            }
        }
    }
}
