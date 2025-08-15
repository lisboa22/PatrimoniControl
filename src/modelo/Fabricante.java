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
public class Fabricante {
    private int id;
    private String nome;
    private Date data_insercao;
    private Date data_alteracao;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_insercao() {
        return data_insercao;
    }

    public void setData_insercao(Date data_insercao) {
        this.data_insercao = data_insercao;
    }
    
    public Date getData_alteracao() {
        return data_alteracao;
    }

    public void setData_alteracao(Date data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    @Override
    public String toString() {
        return "Fabricante{" + "id=" + id + ", nome=" + nome + ", data_insercao=" + data_insercao + "data_alteracao=" + data_alteracao + '}';
    }
    
}