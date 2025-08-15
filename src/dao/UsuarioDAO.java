/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Usuario;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

/**
 *
 * @author robson
 */
public interface UsuarioDAO {
    public int inserir(Usuario usuario) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Usuario> listar();
    public Usuario listar(int id);
    public int editar(Usuario usuario);
    public int editarSenha(Usuario usuario);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    
}
