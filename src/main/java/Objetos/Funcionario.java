package Objetos;

public class Funcionario {
    private String nome;
    private String email;
    private String senha;
    private String telefone;
    private String cpf;
    private Funcionario administrador;
    private Area area;

    //Construtor padrão para o JDBC (Ele utiliza por padrão um construtor vazio)
    public Funcionario() {
    }


    //Construtor para criação de funcionario
    public Funcionario(String nome, String email, String senha, String telefone, String cpf, Funcionario administrador, Area area) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.administrador = administrador;
        this.area = area;
    }

    //Construtor para criacção de administrador
    public Funcionario(String nome, String email, String senha, String telefone, String cpf, Area area) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.cpf = cpf;
        this.area = area;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }



    @Override
    public String toString() {
        return "Nome: " + nome + "\n" +
                "Email: " + email + "\n" +
                "Senha: " + senha + "\n" +
                "Telefone: " + telefone + "\n" +
                "CPF: " + cpf + "\n";
    }

}
