package br.unitins.projeto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Compra extends DefaultEntity {

    @Column(name = "data_pagamento", columnDefinition = "TIMESTAMP", nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDateTime dataPagamento;

    @Column(name = "total_compra", nullable = false)
    private Double totalCompra = 0.0;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_endereco", nullable = false)
    private EnderecoCompra enderecoCompra;

    @Column(name = "status", nullable = false)
    private StatusCompra statusCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<ItemCompra> itensCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<HistoricoEntrega> historicoEntrega;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_metodo_pagamento", unique = true)
    private MetodoDePagamento metodoDePagamento;

    private Boolean sinPix;

    private Boolean sinBoleto;

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime data) {
        this.dataPagamento = data;
    }

    public Double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(Double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public EnderecoCompra getEnderecoCompra() {
        return enderecoCompra;
    }

    public void setEnderecoCompra(EnderecoCompra enderecoCompra) {
        this.enderecoCompra = enderecoCompra;
    }

    public StatusCompra getStatusCompra() {
        return statusCompra;
    }

    public void setStatusCompra(StatusCompra statusCompra) {
        this.statusCompra = statusCompra;
    }

    public List<ItemCompra> getItensCompra() {
        return itensCompra;
    }

    public void setItensCompra(List<ItemCompra> itensCompra) {
        this.itensCompra = itensCompra;
    }

    public List<HistoricoEntrega> getHistoricoEntrega() {
        return historicoEntrega;
    }

    public void setHistoricoEntrega(List<HistoricoEntrega> historicoEntrega) {
        this.historicoEntrega = historicoEntrega;
    }

    public MetodoDePagamento getMetodoDePagamento() {
        return metodoDePagamento;
    }

    public void setMetodoDePagamento(MetodoDePagamento metodoDePagamento) {
        this.metodoDePagamento = metodoDePagamento;
    }

    public Boolean getSinPix() {
        return sinPix;
    }

    public void setSinPix(Boolean sinPix) {
        this.sinPix = sinPix;
    }

    public Boolean getSinBoleto() {
        return sinBoleto;
    }

    public void setSinBoleto(Boolean sinBoleto) {
        this.sinBoleto = sinBoleto;
    }
}
