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
import sistema.Navegador;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.BancoDeDados;

public class FuncionariosInserir extends JPanel 
{
    JLabel labelTitulo, labelNome, labelSobrenome, labelDataNascimento, labelEmail, labelCargo,labelSalario;
    JTextField campoNome, campoSobrenome, campoDataNascimento, campoEmail, campoCargo, campoSalario;
    JComboBox comboboxCargo;
    JButton botaoGravar;
    
    public FuncionariosInserir()
    {
        criarComponentes();
        criarEventos();
    }
    
    private void criarComponentes()
    {
        setLayout(null); // Indica que não será usado nenhum gerenciador de layout
        
        // Instanciando os componentes da tela
        
        labelTitulo = new JLabel("Cadastro de Funcionario", JLabel.CENTER);
        labelTitulo.setFont(new Font(labelTitulo.getFont().getName(), Font.PLAIN, 20));
        labelNome = new JLabel("Nome:", JLabel.LEFT);
        campoNome = new JTextField();
        labelSobrenome = new JLabel("Sobrenome:", JLabel.LEFT);
        campoSobrenome = new JTextField();
        labelDataNascimento = new JLabel("Data de nascimento:", JLabel.LEFT);
        campoDataNascimento = new JFormattedTextField();
        try
        {
            MaskFormatter dateMask = new MaskFormatter("##/##/####");
            dateMask.install((JFormattedTextField) campoDataNascimento);
        }
        catch(ParseException ex)
        {
            Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        labelEmail = new JLabel("Email:", JLabel.LEFT);
        campoEmail = new JTextField();
        labelCargo = new JLabel("Cargo:", JLabel.LEFT);
        comboboxCargo = new JComboBox();
        labelSalario = new JLabel("Salario", JLabel.LEFT);
        DecimalFormat fomatter = new DecimalFormat("##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        campoSalario = new JFormattedTextField(fomatter);
        //String val = "0.00";
        //campoSalario.setValue(val);
        botaoGravar = new JButton("Adicionar");
        
        // Defindo o posicionamento e o tamanho dos componentes
        
        labelTitulo.setBounds(20, 20, 660, 40);
        labelNome.setBounds(150, 80, 400, 20);
        campoNome.setBounds(150, 100, 400, 40);
        labelSobrenome.setBounds(150, 140, 400, 20);
        campoSobrenome.setBounds(150, 160, 400, 40);
        labelDataNascimento.setBounds(150, 200, 400, 20);
        campoDataNascimento.setBounds(150, 220, 400, 40);
        labelEmail.setBounds(150, 260, 400, 20);
        campoEmail.setBounds(150, 280,400, 40);
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
        
        sqlCarregarCargos();
        
        setVisible(true); // Deixar a tela visivél
        
    }
    
    private void criarEventos()
    {
        botaoGravar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Funcionario novoFuncionario = new Funcionario();
                novoFuncionario.setNome(campoNome.getText());
                novoFuncionario.setSobrenome(campoSobrenome.getText());
                novoFuncionario.setDataNascimento(campoDataNascimento.getText());
                novoFuncionario.setEmail(campoEmail.getText());
                Cargo cargoSelecionado = (Cargo) comboboxCargo.getSelectedItem();
                if(cargoSelecionado != null) novoFuncionario.setCargo(cargoSelecionado.getId());
                
                novoFuncionario.setSalario(Double.valueOf(campoSalario.getText().replace(",", ".")));
                
                sqlInserirFuncionario(novoFuncionario);
            }
        });
    }
    
    private void sqlCarregarCargos()
    {
        // Conexão
        Connection conexao;
        // instrução SQL
        Statement instrucaoSQL;
        // Resultado
        ResultSet resultados;
        
        try
        {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            // Criando instrução SQL
            instrucaoSQL = conexao.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultados = instrucaoSQL.executeQuery("SELECT * FROM cargo ORDER BY  nome ASC");
            comboboxCargo.removeAll();
            
            while(resultados.next())
            {
                Cargo cargo = new Cargo();
                cargo.setId(resultados.getInt("id"));
                cargo.setNome(resultados.getString("nome"));
                comboboxCargo.addItem(cargo);
            }
            
            comboboxCargo.updateUI();
            
            conexao.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao carregar os cargos");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sqlInserirFuncionario(Funcionario novoFuncionario)
    {
       // Validando nome
        if(campoNome.getText().length() <= 3)
        {
            JOptionPane.showMessageDialog(null, " Por favor, preencha o nome corretamente.");
            return;
        }
        
        // Validando sobrenome
        if(campoSobrenome.getText().length() <= 3)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o sobrenome corretamente.");
            return;
        }
        
        // Validando salario
        if(Double.parseDouble(campoSalario.getText().replace(",", ".")) <= 100)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o salário corretamente.");
            return;
        }
        
        // Validando email                 VERIFICAR LINHA 205
        Boolean emailValido = false;
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(campoEmail.getText()); 
        emailValido = m.matches();
        
        if(!emailValido)
        {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o email corretamente");  
            return;
        }
        
        // Conexao
        Connection conexao;
        // Instrução SQl
        PreparedStatement instrucaoSQL;
        
        try
        {
            // Conectando ao banco de dados
            conexao = DriverManager.getConnection(BancoDeDados.stringDeConexao, BancoDeDados.usuario, BancoDeDados.senha);
            
            String template = "INSERT INTO funcionarios(nome, sobrenome, data_nascimento, email, cargo, salario)";
            template = template+" VALUES (?,?,?,?,?,?)";
            instrucaoSQL = conexao.prepareStatement(template);
            instrucaoSQL.setString(1, novoFuncionario.getNome());
            instrucaoSQL.setString(2, novoFuncionario.getSobrenome());
            instrucaoSQL.setString(3, novoFuncionario.getDataNascimento());
            instrucaoSQL.setString(4, novoFuncionario.getEmail());
            if(novoFuncionario.getCargo() > 0)
                instrucaoSQL.setInt(5, novoFuncionario.getCargo());
            else
                instrucaoSQL.setNull(5, java.sql.Types.INTEGER);
            instrucaoSQL.setString(6, Double.toString(novoFuncionario.getSalario()));
            instrucaoSQL.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Funcionario adicionado com sucesso!");
            Navegador.inicio();
            
            conexao.close();
        }
        catch(SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Ocorreu um erro ao adicionar o funcionario.");
            Logger.getLogger(FuncionariosInserir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
