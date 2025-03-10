package com.cripto.model;

import java.util.Random;

public class Carteira {
    private Integer id_carteira;
    private Integer id_cliente;
    private Double saldoContaCorrente;

    public Integer getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(Integer id_cliente) {
        this.id_cliente = id_cliente;
    }

    public Double getSaldoContaCorrente() {
        return saldoContaCorrente;
    }

    public void setSaldoContaCorrente(Double saldoContaCorrente) {
        this.saldoContaCorrente = saldoContaCorrente;
    }

    public Double gerarSaldoAleatoriamente() {
        Random random = new Random();
        return this.saldoContaCorrente = random.nextDouble(1000, 50000);
    }
}
