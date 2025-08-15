/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Unidade;

/**
 *
 * @author robson
 */
public interface UnidadeDAO  {
    public int inserir(Unidade unidade) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Unidade> listar();
    public Unidade listar(int id);
    public int editar(Unidade unidade);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
}
