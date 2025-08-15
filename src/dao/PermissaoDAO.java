/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Permissao;

/**
 *
 * @author robson
 */
public interface PermissaoDAO {
    public int inserir(Permissao permissao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Permissao> listar();
    public Permissao listar(int id);
    public int editar(Permissao permissao);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
}
