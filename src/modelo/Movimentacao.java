/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Date;

/**
 *
 * @author robson
 */
public class Movimentacao {
    private int id;
    //private Equipamento equipamento;
    private Equipamento equipamento;
    private String num_serie;
    private String tipo_movimentacao;
    private Unidade origem;
    private Usuario usuarioliberacao;
    private Unidade destino;
    private Usuario usuariorecepcao;
    private String observacoes;
    private Date data_hora;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }*/
    
    public Equipamento getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }

    public String getNum_serie() {
        return num_serie;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }

    public String getTipo_movimentacao() {
        return tipo_movimentacao;
    }

    public void setTipo_movimentacao(String tipo_movimentacao) {
        this.tipo_movimentacao = tipo_movimentacao;
    }

    public Unidade getOrigem() {
        return origem;
    }

    public void setOrigem(Unidade origem) {
        this.origem = origem;
    }

    public Usuario getUsuarioliberacao() {
        return usuarioliberacao;
    }

    public void setUsuarioliberacao(Usuario usuarioliberacao) {
        this.usuarioliberacao = usuarioliberacao;
    }

    public Unidade getDestino() {
        return destino;
    }

    public void setDestino(Unidade destino) {
        this.destino = destino;
    }

    public Usuario getUsuariorecepcao() {
        return usuariorecepcao;
    }

    public void setUsuariorecepcao(Usuario usuariorecepcao) {
        this.usuariorecepcao = usuariorecepcao;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Date getData_hora() {
        return data_hora;
    }

    public void setData_hora(Date data_hora) {
        this.data_hora = data_hora;
    }

    @Override
    public String toString() {
        return "Movimentacao{" + "id=" + id + ", equipamento=" + equipamento + ", num_serie=" + num_serie + ", tipo_movimentacao=" + tipo_movimentacao + ", origem=" + origem + ", usuarioliberacao=" + usuarioliberacao + ", destino=" + destino + ", usuariorecepcao=" + usuariorecepcao + ", observacoes=" + observacoes + ", data_hora=" + data_hora + '}';
    }
    
    
}
