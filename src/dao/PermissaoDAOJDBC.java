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
import modelo.Permissao;

/**
 *
 * @author robson
 */
public class PermissaoDAOJDBC implements PermissaoDAO {
    private List<Permissao> permissao;
    
    //Inserir Permissão
    @Override
    public int inserir(Permissao permissao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into permissao(nome, data) ")
                .append("VALUES (?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, permissao.getNome(),
                                                        permissao.getData());
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
    public List<Permissao> listar() {
        ResultSet rset;
        String select = "SELECT * FROM permissao ORDER BY id";
        List<Permissao> permissoes = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Permissao permissao = new Permissao();
                permissao.setId(rset.getInt("id"));
                permissao.setNome(rset.getString("nome"));
                permissao.setData(rset.getDate("data"));
                permissao.setData(rset.getTimestamp("data"));
                permissoes.add(permissao);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return permissoes;
    }

    //Listar um único Permissão.
    @Override
    public Permissao listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM permissao WHERE id = ?";
        Permissao permissao = new Permissao();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                permissao.setId(rset.getInt("id"));
                permissao.setNome(rset.getString("nome"));
                permissao.setData(rset.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return permissao;
    }
    
    //Editar Permissão
    @Override
    public int editar(Permissao permissao) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE permissao SET ")
                .append("nome = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, permissao.getNome(),
                                                        permissao.getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM permissao ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}
