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
public class Permissaomodulo {
    
    private int id;
    private Permissao permissao;
    private Modulo modulo;
    private String mnome;
    private boolean visualizar;
    private boolean inserir;
    private boolean alterar;
    private boolean excluir;
    private Date data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public String getMnome() {
        return mnome;
    }

    public void setMnome(String mnome) {
        this.mnome = mnome;
    }

    public boolean isVisualizar() {
        return visualizar;
    }

    public void setVisualizar(boolean visualizar) {
        this.visualizar = visualizar;
    }

    public boolean isInserir() {
        return inserir;
    }

    public void setInserir(boolean inserir) {
        this.inserir = inserir;
    }

    public boolean isAlterar() {
        return alterar;
    }

    public void setAlterar(boolean alterar) {
        this.alterar = alterar;
    }

    public boolean isExcluir() {
        return excluir;
    }

    public void setExcluir(boolean excluir) {
        this.excluir = excluir;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Permissaomodulo{" + "id=" + id + ", permissao=" + permissao + ", modulo=" + modulo + ", mnome=" + mnome + ", visualizar=" + visualizar + ", inserir=" + inserir + ", alterar=" + alterar + ", excluir=" + excluir + ", data=" + data + '}';
    }
    
    

    
    
}
