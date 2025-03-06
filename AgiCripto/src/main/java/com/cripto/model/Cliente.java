package com.cripto.model;

public class Cliente {
    private Integer id_cliente;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private String status;
    private Integer id_assinatura;

    public Cliente() {}

    public Cliente(String nome, String email, String cpf, String senha) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
    }

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId_assinatura() {
        return id_assinatura;
    }

    public void setId_assinatura(Integer id_assinatura) {
        this.id_assinatura = id_assinatura;
    }
}
