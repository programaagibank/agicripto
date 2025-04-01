package com.cripto.agi.agi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Assinatura {
    private Integer idAssinatura;
    private Integer idCliente;
    private Double valor;
    private String beneficios;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
    private Integer idCripto;

    public Assinatura(Integer idCliente, Double valor, String beneficios, LocalDate dataInicio, LocalDate dataFim, String status, Integer idCripto) {
        this.idCliente = idCliente;
        this.valor = valor;
        this.beneficios = beneficios;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.idCripto = idCripto;
    }

    public Assinatura(Integer idAssinatura, Integer idCliente, Double valor, String beneficios, LocalDate dataInicio, LocalDate dataFim, String status, Integer idCripto) {
        this.idAssinatura = idAssinatura;
        this.idCliente = idCliente;
        this.valor = valor;
        this.beneficios = beneficios;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.idCripto = idCripto;
    }

    public Integer getIdAssinatura() {
        return idAssinatura;
    }

    public void setIdAssinatura(Integer idAssinatura) {
        this.idAssinatura = idAssinatura;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(String beneficios) {
        this.beneficios = beneficios;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIdCripto() {
        return idCripto;
    }

    public void setIdCripto(Integer idCripto) {
        this.idCripto = idCripto;
    }

    @Override
    public String toString() {
        return "Assinatura{" +
                "Valor = " + valor +
                ", Beneficios = " + beneficios + '\'' +
                ", Data Inicio = " + dataInicio +
                ", Data Fim = " + dataFim +
                ", Status = " + status + '\'' +
                ", Cripto = " + idCripto +
                '}';
    }
}
