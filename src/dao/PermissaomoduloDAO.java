/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Permissaomodulo;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

/**
 *
 * @author robson
 */
public interface PermissaomoduloDAO {
    public int inserir(Permissaomodulo permissaomodulo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Permissaomodulo> listar();
    public List<Permissaomodulo> listarPorPermissao(int id);
    public int editar(Permissaomodulo permissaomodulo);
    //public int editarSenha(Permissaomodulo permissaomodulo);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    
}
