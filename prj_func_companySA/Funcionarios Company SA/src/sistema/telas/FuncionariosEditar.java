package sistema.telas;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import sistema.BancoDeDados;
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;


public class FuncionariosEditar extends JPanel
{
    Funcionario funcionarioAtual;
    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo, labelSalario;
    JComboBox comboboxCargo;
    JTextField campoNome, campoSobrenome,campoEmail;
    JFormattedTextField campoDataNascimento, campoSalario;
    JButton botaoGravar;
    
    public FuncionariosEditar(Funcionario funcionario)
    {
        funcionarioAtual = funcionario;
        criarComponentes();
        criarEventos();
    }
    
    private void criarComponentes()
    {
        setLayout(null); // definindo que não será usado nenhum gerenciador de layout
        
        // Instanciando os componentes da tela
        
        String textoLabel = "Editar funcionario " + funcionarioAtual.getNome() + " " + funcionarioAtual.getSobrenome();
        labelTitulo = new JLabel(textoLabel, JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelNome = new JLabel("Nome", JLabel.LEFT);
        campoNome = new JTextField(funcionarioAtual.getNome());
        labelSobrenome = new JLabel("Sobrenome", JLabel.LEFT);
        campoSobrenome = new JTextField(funcionarioAtual.getSobrenome());
        labelDataNascimento = new JLabel("Data de nascimento", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField(funcionarioAtual.getDataNascimento());
        try
        {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install(campoDataNascimento);
        }
        catch(ParseException ex)
        {
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("Email", JLabel.LEFT);
        campoEmail = new JTextField(funcionarioAtual.getEmail());
        labelCargo = new JLabel("Cargo", JLabel.LEFT);
        comboboxCargo = new JComboBox();
        labelSalario = new JLabel("Salário", JLabel.LEFT);
        DecimalFormat formatter = new DecimalFormat("###0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(formatter);
        campoSalario.setValue(funcionarioAtual.getSalario());
        botaoGravar = new JButton("Salvar");
        
        // Definindo o posicionamento e o tamanho dos componentes
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 20);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobrenome.setBounds(150, 140, 400, 20);
        campoSobrenome.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280, 400, 40);
        labelCargo.setBounds(150, 320, 400, 20);
        comboboxCargo.setBounds(150, 340, 400, 40);
        labelSalario.setBounds(150, 380, 400, 20);
        campoSalario.setBounds(150, 400, 400, 40);
        botaoGravar.setBounds(560, 400, 130, 40);
        
        // Adicionando os componentes na tela
        
        add(labelTitulo);
        add(labelNome);
        add(campoNome);
        add(labelSobrenome);
        add(campoSobrenome);
        add(labelDataNascimento);
        add(campoDataNascimento);
        add(labelEmail);
        add(campoEmail);
        add(labelCargo);
        add(comboboxCargo);
        add(labelSalario);
        add(campoSalario);
        add(botaoGravar);
        
        sqlCarregarCargos(funcionarioAtual.getCargo());
        
        setVisible(true); // Deixar a tela visivél
    }   
    
    private void criarEventos()
    {
        botaoGravar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                funcionarioAtual.setNome(campoNome.getText());
                funcionarioAtual.setSobrenome(campoSobrenome.getText());
                funcionarioAtual.setDataNascimento(campoDataNascimento.getText());
                funcionarioAtual.setEmail(campoEmail.getText());
                Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
                if(cargoSelecionado != null) funcionarioAtual.setCargo(cargoSelecionado.getId());
                funcionarioAtual.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));
                
                sqlAtualizaFuncionario();
                
            }
        });
    }
    
    private void sqlCarregarCargos(int cargoAtual)
    {
        // Conexão
        Connection conexao;
        // Instrução SQL
        Statement instrucaoSQL;
        // Rtesultados
        ResultSet resultados;
        
        try
        {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // Criando instrução SQL
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargo ORDER BY nome ASC");
            
            // Criando nova lista no combo box
            comboboxCargo.removeAll();
            
            while(resultados.next())
            {
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                comboboxCargo.addItem(cargo);
                if(cargoAtual == cargo.getId()) comboboxCargo.setSelectedItem(cargo);
            }
            
            comboboxCargo.updateUI();
            
            conexao.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao acessar os cargos");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sqlAtualizaFuncionario()
    {
        // Validando nome
        if(campoNome.getText().length() <= 3)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o nome corretamente.");
            return;
        }
        
        // Validando sobrenome
        if(campoSobrenome.getText().length() <= 3)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente");
            return;
        }
        
        // Validando o salario
        if(Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o salrio corretamente");
            return;
        }
        
        // Validando email
        Boolean emailValidado = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText());
        emailValidado = m.matches();
        
        if(!emailValidado)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente");
            return;
        }
        
        // Conexão
        Connection conexao;
        // Instrução SQL
        PreparedStatement instrucaoSQL;
        // resultados
        ResultSet resultados;
        
        try
        {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // Criando instrução SQL
            String template = "UPDATE funcionarios SET nome=?, sobrenome=?, data_nascimento=?, email=?, cargo=?, salario=?";
            template = template + " WHERE id=" + funcionarioAtual.getId();
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, campoNome.getText());
            instrucaoSQL.setString(2, campoSobrenome.getText());
            instrucaoSQL.setString(3, campoDataNascimento.getText());
            instrucaoSQL.setString(4, campoEmail.getText());
            Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
            if(cargoSelecionado != null) instrucaoSQL.setInt(5, cargoSelecionado.getId());
            else instrucaoSQL.setNull((5), java.sql.Types.INTEGER);
            instrucaoSQL.setString(6, campoSalario.getText().replace(",","."));
            instrucaoSQL.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionario atualizado com sucesso.");
            Navegador.inicio();
            
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao editar o funcionario.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
