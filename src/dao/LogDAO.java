/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Log;

/**
 *
 * @author robson
 */
public interface LogDAO {
    public int inserir(Log log) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Log> listar();
    public Log listar(int id);
}
