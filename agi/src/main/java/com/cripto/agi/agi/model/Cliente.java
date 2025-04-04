package com.cripto.agi.agi.model;

import java.util.Random;

public class Cliente {
    private Integer id_cliente;
    private String nome;
    private String email;
    private String cpf;
    private String senha;
    private String status;
    private Integer id_assinatura;

    public Cliente() {}

    public Cliente(String nome, String email, String cpf) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
    }

    public Cliente(Integer id_cliente, String nome, String email, String cpf, String senha, String status, Integer id_assinatura) {
        this.id_cliente = id_cliente;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.status = status;
        this.id_assinatura = id_assinatura;
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

    public String criptografarSenha(String senha) {
        long hash = 0;

        for (int i = 0; i < senha.length(); i++) {
            hash = hash *  31 + senha.charAt(i);
            hash = hash ^ (hash >> 16);
        }
        hash *= hash & 0x7FFFFFFF;

        return Long.toHexString(hash);
    }

    public boolean verificar(String senha, String senhaC) {
        return criptografarSenha(senha).equals(senhaC);
    }

    public String formatarCpf(String cpf) {
        StringBuilder cpfFormatado = new StringBuilder();
        int i = 0;

        while (i < cpf.length()) {
            cpfFormatado.append(cpf.charAt(i));
            if (i == 2 || i == 5) {
                cpfFormatado.append('.');
            } else if (i == 8) {
                cpfFormatado.append('-');
            }
            i++;
        }
        return cpfFormatado.toString();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id_cliente=" + id_cliente +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                ", status='" + status + '\'' +
                ", id_assinatura=" + id_assinatura +
                '}';
    }
}
