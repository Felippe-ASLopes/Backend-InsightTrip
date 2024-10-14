package Objetos;

import java.util.ArrayList;
import java.util.List;

public class Agencia {


    private int id;
    private String nome;
    private String cnpj;
    private String cep;
    private Funcionario administrador;
    private List<Aeroporto> aeroportos = new ArrayList<>();

    //Contrutor vazio para o Hibernate
    public Agencia() {
    }

    //Construtor
    public Agencia(int id, String nome, String cnpj, String cep, Funcionario administrador) {
        this.id = id;
        this.nome = nome;
        this.cnpj = cnpj;
        this.cep = cep;
        this.administrador = administrador;
    }


    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCep() {
        return cep;
    }

    public Funcionario getAdministrador() {
        return administrador;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public void setAdministrador(Funcionario administrador) {
        this.administrador = administrador;
    }
}
