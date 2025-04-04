package com.cripto.agi.agi.model;

import com.cripto.agi.agi.api.Criptomoedas;

public class CarteiraCripto {
    private Integer idCarteiraCripto;
    private Integer idCliente;
    private Double saldoBRL;
    private Double saldoBTC;
    private Double saldoETH;
    private Double saldoSOl;
    private Double saldoAGICOIN;

    public CarteiraCripto(
            Integer idCliente,
            Double saldoBRL,
            Double saldoBTC,
            Double saldoETH,
            Double saldoSOl,
            Double saldoAGICOIN
    ) {
        this.idCliente = idCliente;
        this.saldoBRL = saldoBRL;
        this.saldoBTC = saldoBTC;
        this.saldoETH = saldoETH;
        this.saldoSOl = saldoSOl;
        this.saldoAGICOIN = saldoAGICOIN;
    }

    public Integer getIdCarteiraCripto() {
        return idCarteiraCripto;
    }

    public void setIdCarteiraCripto(Integer idCarteiraCripto) {
        this.idCarteiraCripto = idCarteiraCripto;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getSaldoBRL() {
        return saldoBRL;
    }

    public void setSaldoBRL(Double saldoBRL) {
        this.saldoBRL = saldoBRL;
    }

    public Double getSaldoBTC() {
        return saldoBTC;
    }

    public void setSaldoBTC(Double saldoBTC) {
        this.saldoBTC = saldoBTC;
    }

    public Double getSaldoETH() {
        return saldoETH;
    }

    public void setSaldoETH(Double saldoETH) {
        this.saldoETH = saldoETH;
    }

    public Double getSaldoSOl() {
        return saldoSOl;
    }

    public void setSaldoSOl(Double saldoSOl) {
        this.saldoSOl = saldoSOl;
    }

    public Double getSaldoAGICOIN() {
        return saldoAGICOIN;
    }

    public void setSaldoAGICOIN(Double saldoAGICOIN) {
        this.saldoAGICOIN = saldoAGICOIN;
    }

    public double conversao(int o, double v) {
        Criptomoedas criptomoedas = new Criptomoedas();
        criptomoedas.consultarPrecoBitcoin();
        criptomoedas.consultarPrecoEthereum();
        criptomoedas.consultarPrecoSolana();

        if (o == 1) {
            return v / criptomoedas.getPrecoBtc();
        } else if (o == 2) {
            return v / criptomoedas.getPrecoEth();
        } else {
            return v / criptomoedas.getPrecoSol();
        }

    }
}
