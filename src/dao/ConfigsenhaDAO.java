/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Configsenha;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

/**
 *
 * @author robson
 */
public interface ConfigsenhaDAO {
    public List<Configsenha> listar();
    public int editar(Configsenha configsenha);
    //public int editarSenha(Permissaomodulo permissaomodulo);
    
}
