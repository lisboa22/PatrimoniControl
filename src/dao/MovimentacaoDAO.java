/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Movimentacao;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

/**
 *
 * @author robson
 */
public interface MovimentacaoDAO {
    public int inserir(Movimentacao movimentacao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Movimentacao> listar();
    public Movimentacao listar(int id);
    public int editar(Movimentacao movimentacao);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    
}
