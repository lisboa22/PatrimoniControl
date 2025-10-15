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
import modelo.Unidade;

/**
 *
 * @author robson
 */
public class UnidadeDAOJDBC implements UnidadeDAO {
    
//Inserir Unidade
    @Override
    public int inserir(Unidade unidade) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into unidade(conta, unidade, telefone, email, responsavel, data) ")
                .append("VALUES (?, ?, ?, ?, ?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, unidade.getConta(),
                                                        unidade.getUnidade(),
                                                        unidade.getTelefone(),
                                                        unidade.getEmail(),
                                                        unidade.getResponsavel(),
                                                        unidade.getData());
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Conta duplicada: " + ex.getMessage().substring(17, 21)); 
            System.out.println(ex.getMessage().substring(17, 21));
            ex.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro Usuário.");      
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return linha;
    }

    //Listar Unidade
    @Override
    public List<Unidade> listar() {
        ResultSet rset;
        String select = "SELECT * FROM unidade ORDER BY id";
        List<Unidade> unidades = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Unidade unidade = new Unidade();
                unidade.setId(rset.getInt("id"));
                unidade.setConta(rset.getString("conta"));
                unidade.setUnidade(rset.getString("unidade"));
                unidade.setTelefone(rset.getString("telefone"));
                unidade.setEmail(rset.getString("email"));
                unidade.setResponsavel(rset.getString("responsavel"));
                unidade.setData(rset.getTimestamp("data"));
                unidades.add(unidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return unidades;
    }

    //Listar um único Permissão.
    @Override
    public Unidade listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM unidade WHERE id = ?";
        Unidade unidade = new Unidade();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                unidade.setId(rset.getInt("id"));
                unidade.setConta(rset.getString("conta"));
                unidade.setUnidade(rset.getString("unidade"));
                unidade.setTelefone(rset.getString("telefone"));
                unidade.setEmail(rset.getString("email"));
                unidade.setResponsavel(rset.getString("responsavel"));
                unidade.setData(rset.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return unidade;
    }
    
    //Editar Permissão
    @Override
    public int editar(Unidade unidade) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE unidade SET ")
                .append("conta = ?, ")
                .append("unidade = ?, ")
                .append("telefone = ?, ")
                .append("email = ?, ")
                .append("responsavel = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, unidade.getConta(),
                                                        unidade.getUnidade(),
                                                        unidade.getTelefone(),
                                                        unidade.getEmail(),
                                                        unidade.getResponsavel(),
                                                        unidade.getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM unidade ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}