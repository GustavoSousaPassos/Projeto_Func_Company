package sistema.entidades;

public class Funcionario 
{
    private int id;
    private String nome;
    private String sobrenome;
    private String dataNascimento;
    private String email;
    private int cargo;
    private double salario;
    
    public void setNome(String nome)
    {
        this.nome = nome;
    }
    public String getNome()
    {
        return nome;
    }
    
    public void setSobrenome(String sobrenome)
    {
        this.sobrenome = sobrenome;
    }
    public String getSobrenome()
    {
        return sobrenome;
    }
    
    public void setDataNascimento(String dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }
    public String getDataNascimento()
    {
        return dataNascimento;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return email;
    }
    
    public void setCargo(int cargo)
    {
        this.cargo = cargo;
    }
    public int getCargo()
    {
        return cargo;
    }
    
    public  void setSalario(double salario)
    {
        this.salario = salario;
    }
    
    public double getSalario()
    {
        return salario;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getId()
    {
        return id;
    }
}
