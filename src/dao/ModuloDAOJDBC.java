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
import modelo.Modulo;

/**
 *
 * @author robson
 */
public class ModuloDAOJDBC implements ModuloDAO {
    private List<Modulo> modulo;
    
    //Inserir Permissão
    @Override
    public int inserir(Modulo modulo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into modulo(nome, data) ")
                .append("VALUES (?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, modulo.getNome(),
                                                        modulo.getData());
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: Permissão já cadastrada.");      
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: Permissão já cadastrada.");      
        } catch (Exception e) {
            //e.printStackTrace();
        }
        
        return linha;
    }

    //Listar Permissão
    @Override
    public List<Modulo> listar() {
        ResultSet rset;
        String select = "SELECT * FROM modulo ORDER BY id";
        List<Modulo> modulos = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Modulo modulo = new Modulo();
                modulo.setId(rset.getInt("id"));
                modulo.setNome(rset.getString("nome"));
                modulo.setData(rset.getTimestamp("data"));
                modulos.add(modulo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return modulos;
    }

    //Listar um único Permissão.
    @Override
    public Modulo listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM modulo WHERE id = ?";
        Modulo modulo = new Modulo();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                modulo.setId(rset.getInt("id"));
                modulo.setNome(rset.getString("nome"));
                modulo.setData(rset.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return modulo;
    }
    
    //Editar Permissão
    @Override
    public int editar(Modulo modulo) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE modulo SET ")
                .append("nome = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, modulo.getNome(),
                                                        modulo.getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM modulo ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}