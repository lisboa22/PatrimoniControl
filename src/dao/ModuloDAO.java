/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Modulo;

/**
 *
 * @author robson
 */
public interface ModuloDAO {
    public int inserir(Modulo modulo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Modulo> listar();
    public Modulo listar(int id);
    public int editar(Modulo modulo);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
}
