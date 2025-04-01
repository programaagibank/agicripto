package com.cripto.agi.agi.model;

import java.time.LocalDateTime;

public class Transacao {
    private Integer idTransacao;
    private Integer idCarteira;
    private Integer idCliente;
    private Integer idCripto;
    private String status;
    private Integer idTipoTransacao;
    private Double valor;
    private LocalDateTime data;

    public Transacao(Integer idCarteira, Integer idCliente, Integer idTipoTransacao, Double valor, LocalDateTime data) {
        this.idCarteira = idCarteira;
        this.idCliente = idCliente;
        this.idTipoTransacao = idTipoTransacao;
        this.valor = valor;
        this.data = data;
    }

    public Transacao(Integer idCarteira, Integer idCliente, Integer idCripto, String status, Integer idTipoTransacao, Double valor, LocalDateTime data) {
        this.idCarteira = idCarteira;
        this.idCliente = idCliente;
        this.idCripto = idCripto;
        this.status = status;
        this.idTipoTransacao = idTipoTransacao;
        this.valor = valor;
        this.data = data;
    }

    public Transacao(Integer idTransacao, Integer idCarteira, Integer idCliente, Integer idCripto, String status, Integer idTipoTransacao, Double valor, LocalDateTime data) {
        this.idTransacao = idTransacao;
        this.idCarteira = idCarteira;
        this.idCliente = idCliente;
        this.idCripto = idCripto;
        this.status = status;
        this.idTipoTransacao = idTipoTransacao;
        this.valor = valor;
        this.data = data;
    }

    public Integer getIdTransacao() {
        return idTransacao;
    }

    public void setIdTransacao(Integer idTransacao) {
        this.idTransacao = idTransacao;
    }

    public Integer getIdCarteira() {
        return idCarteira;
    }

    public void setIdCarteira(Integer idCarteira) {
        this.idCarteira = idCarteira;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdCripto() {
        return idCripto;
    }

    public void setIdCripto(Integer idCripto) {
        this.idCripto = idCripto;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdTipoTransacao() {
        return idTipoTransacao;
    }

    public void setIdTipoTransacao(Integer idTipoTransacao) {
        this.idTipoTransacao = idTipoTransacao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Transacao{" +
                "idCripto=" + idCripto +
                ", status='" + status + '\'' +
                ", idTipoTransacao=" + idTipoTransacao +
                ", valor=" + valor +
                ", data=" + data +
                "}\n";
    }
}
