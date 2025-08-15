/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import modelo.Equipamento;

/**
 *
 * @author robson
 */
public interface EquipamentoDAO {
    public int inserir(Equipamento equipamento) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
    public List<Equipamento> listar();
    public Equipamento listar(int id);
    public int editar(Equipamento equipamento);
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException;
}
