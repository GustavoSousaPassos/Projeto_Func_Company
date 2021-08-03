package sistema;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import sistema.entidades.Cargo;
import sistema.entidades.Funcionario;
import sistema.telas.CargosConsultar;
import sistema.telas.CargosEditar;
import sistema.telas.CargosInserir;
import sistema.telas.Inicio;
import sistema.telas.Login;
import sistema.telas.FuncionariosInserir;
import sistema.telas.FuncionariosEditar;
import sistema.telas.FuncionariosConsultar;
import sistema.telas.RelatoriosCargos;
import sistema.telas.RelatoriosSalarios;

public class Navegador 
{
    // Menu
    
    private static boolean menuConstruido;
    private static boolean menuHabilitado;
    private static JMenuBar menuBar;
    private static JMenu menuArquivo, menuFuncionarios, menuCargos, menuRelatorios;
    private static JMenuItem miSair, miFuncionariosConsultar, miFuncionariosCadastrar, miCargosConsultar;
    private static JMenuItem miCargosCadastrar, miRelatoriosCargos, miRelatoriosSalario;
    
    // metodos para instanciar a classe da tela e alterar o titulo da janela
    public static void login()
    {
        Sistema.tela = new Login();
        Sistema.frame.setTitle("Funcionarios Company SA");
        Navegador.atualizaTela();
    }
    
    public static void inicio()
    {
        Sistema.tela = new Inicio();
        Sistema.frame.setTitle("Funcionarios Company SA");
        Navegador.atualizaTela();
    }
    
    public static void cargosCadastrar()
    {
        Sistema.tela = new CargosInserir();
        Sistema.frame.setTitle("Funcionarios Company SA - Cadastrar cargos");
        Navegador.atualizaTela();
    }
    
    public static void cargosConsultar()
    {
        Sistema.tela = new CargosConsultar();
        Sistema.frame.setTitle("Funcionarios Company SA - Consultar cargos");
        Navegador.atualizaTela();
    }
    
    public static void cargosEditar(Cargo cargo)
    {
        Sistema.tela = new CargosEditar(cargo);
        Sistema.frame.setTitle("Funcionarios Company SA - Editar cargos");
        Navegador.atualizaTela();
    }
    
    public static void funcionariosCadastrar()
    {
        Sistema.tela = new FuncionariosInserir();
        Sistema.frame.setTitle("Funcionarios Company SA - Cadastrar funcionario");
        Navegador.atualizaTela();
    }
    
    public static void funcionariosEditar(Funcionario funcionario)
    {
        Sistema.tela = new FuncionariosEditar(funcionario);
        Sistema.frame.setTitle("Funcionarios Comapany SA - Editar funcionario");
        Navegador.atualizaTela();
    }
    
    public static void funcionariosConsultar()
    {   
        Sistema.tela = new FuncionariosConsultar();
        Sistema.frame.setTitle("Funcionarios Company SA - Consultar FUncionario");
        Navegador.atualizaTela();
    }
    
    public static void relatoriosCargos()
    {
        Sistema.tela = new RelatoriosCargos();
        Sistema.frame.setTitle("Funcionarios Company SA - Relatorios de Funcionarios");
        Navegador.atualizaTela();
    }
    
    public static void relatoriosSalarios()
    {
        Sistema.tela = new RelatoriosSalarios();
        Sistema.frame.setTitle("Funcionarios Company SA - Relatorios de Salários");
        Navegador.atualizaTela();
    }
    
    // Metodo que remove a tela atual e adiciona a proxima tela que foi instanciada na variavel Sistemas.tela pelos metodos anteriores
    private static void atualizaTela()
    {
        Sistema.frame.getContentPane().removeAll();
        Sistema.tela.setVisible(true);
        Sistema.frame.add(Sistema.tela);
        
        Sistema.frame.setVisible(true);
    }
    
    private static void construirMenu()
    {
        if(!menuConstruido)
        {
            menuConstruido = true;
            
            menuBar = new JMenuBar();
            
            // Menu arquivo
            menuArquivo = new JMenu("Arquivo");
            menuBar.add(menuArquivo);
            miSair = new JMenuItem("Sair");
            menuArquivo.add(miSair);
            
            // Menu funcionarios
            menuFuncionarios = new JMenu("Funcionarios");
            menuBar.add(menuFuncionarios);
            miFuncionariosCadastrar = new JMenuItem("Cadastrar");
            menuFuncionarios.add(miFuncionariosCadastrar);
            miFuncionariosConsultar = new JMenuItem("Consultar");
            menuFuncionarios.add(miFuncionariosConsultar);
            
            // Menu cargos
            menuCargos = new JMenu("Cargos");
            menuBar.add(menuCargos);
            miCargosCadastrar = new JMenuItem("Cadastrar");
            menuCargos.add(miCargosCadastrar);
            miCargosConsultar = new JMenuItem("Consultar");
            menuCargos.add(miCargosConsultar);
            
            // Menu relatórios
            menuRelatorios = new JMenu("Relatórios");
            menuBar.add(menuRelatorios);
            miRelatoriosCargos = new JMenuItem("Funcionarios por cargos");
            menuRelatorios.add(miRelatoriosCargos);
            miRelatoriosSalario = new JMenuItem("Salarios dos funcionarios");
            menuRelatorios.add(miRelatoriosSalario);
            
            criarEventosMenu();     
        }        
    }
    
    public static void habilitaMenu()
    {
        if(!menuConstruido) construirMenu();
        if(!menuHabilitado)
        {
            menuHabilitado = true;
            Sistema.frame.setJMenuBar((menuBar));
        }
    }
    
    public static void desabilitaMenu()
    {
        if(menuHabilitado)
        {
            menuHabilitado = false;
            Sistema.frame.setMenuBar(null);
        }
    }
    
    private static void criarEventosMenu()
    {
        miSair.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        
        // Funcionarios
        miFuncionariosCadastrar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                funcionariosCadastrar();
            }
        });
        miFuncionariosConsultar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                funcionariosConsultar();
            }
        });
        
        // Cargos
        miCargosCadastrar.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cargosCadastrar();
            }
        });
        miCargosConsultar.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                cargosConsultar();
            }
        });
        
        // Relatorios
        miRelatoriosCargos.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               relatoriosCargos();
            }
        });
        miRelatoriosSalario.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                relatoriosSalarios();
            }
        });
        
    }
   
}
