/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Fabricante;

/**
 *
 * @author robson
 */
public interface FabricanteDAO  {
    public int inserir(Fabricante fabricante) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Fabricante> listar();
    public Fabricante listar(int id);
    public int editar(Fabricante fabricante);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
}
