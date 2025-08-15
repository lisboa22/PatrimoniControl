/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Fabricante;

/**
 *
 * @author robson
 */
public class FabricanteDAOJDBC implements FabricanteDAO {
        //Inserir Marca
    @Override
    public int inserir(Fabricante fabricante) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into fabricante(nome, data_insercao) ")
                .append("VALUES (?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, fabricante.getNome(),
                                                        fabricante.getData_insercao());
        } catch (SQLIntegrityConstraintViolationException ex) {
            //JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro Usuário.");      
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "bNão é possível inserir: o email já está vinculado a outro Usuário.");      
        } catch (Exception e) {
            //e.printStackTrace();
        }
        
        return linha;
    }

    //Listar Fabricante
    @Override
    public List<Fabricante> listar() {
        ResultSet rset;
        String select = "SELECT * FROM fabricante ORDER BY id";
        List<Fabricante> fabricantes = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rset.getInt("id"));
                fabricante.setNome(rset.getString("nome"));
                fabricante.setData_insercao(rset.getTimestamp("data_insercao"));
                fabricantes.add(fabricante);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return fabricantes;
    }

    //Listar um único Permissão.
    @Override
    public Fabricante listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM fabricante WHERE id = ?";
        Fabricante fabricante = new Fabricante();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                fabricante.setId(rset.getInt("id"));
                fabricante.setNome(rset.getString("nome"));
                fabricante.setData_insercao(rset.getDate("data_insercao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return fabricante;
    }
    
    //Editar Permissão
    @Override
    public int editar(Fabricante fabricante) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE fabricante SET ")
                .append("nome = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, fabricante.getNome(),
                                                        fabricante.getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM fabricante ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}