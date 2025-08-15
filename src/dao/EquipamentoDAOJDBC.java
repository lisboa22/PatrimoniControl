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
import modelo.Equipamento;
import modelo.Fabricante;

/**
 *
 * @author robson
 */
public class EquipamentoDAOJDBC implements EquipamentoDAO{
    private List<Equipamento> equipamento;
    
    //Inserir Usuário
    @Override
    public int inserir(Equipamento equipamento) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into equipamentos(nome, id_fabricante, data_insercao, data_alteracao) ")
                .append("VALUES (?, ?, ?, ?)");
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, equipamento.getNome(),
                                                        equipamento.getFabricante().getId(),
                                                        equipamento.getData_insercao(),
                                                        equipamento.getData_alteracao()
                                                        );
        } catch (SQLIntegrityConstraintViolationException ex) {
            //JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro Usuário.");      
            ex.printStackTrace();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "bNão é possível inserir: o email já está vinculado a outro Usuário.");      
            //ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return linha;
    }

    //Listar Usuários
    @Override
    public List<Equipamento> listar() {
        ResultSet rset;
        String select = "SELECT e.id, e.nome, e.data_insercao, e.data_alteracao, f.id AS id_fabricante, f.nome AS nome_fabricante \n" +
                        "FROM equipamentos e \n" +
                        "JOIN fabricante f ON e.id_fabricante = f.id\n" +
                        "ORDER BY e.id";
        List<Equipamento> equipamentos = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rset.getInt("id"));
                fabricante.setNome(rset.getString("nome_fabricante"));
         
                Equipamento equipamento = new Equipamento();
                equipamento.setId(rset.getInt("id"));
                equipamento.setNome(rset.getString("nome"));
                equipamento.setFabricante(fabricante);
                equipamento.setData_insercao(rset.getTimestamp("data_insercao"));
                equipamento.setData_alteracao(rset.getTimestamp("data_alteracao"));
                equipamentos.add(equipamento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return equipamentos;
    }

    //Listar um único Usuário.
    @Override
    public Equipamento listar(int id) {
        ResultSet rset;
        String select = "SELECT e.id, e.nome, e.data_insercao, e.data_alteracao, f.id AS id_fabricante, f.nome AS nome_fabricante \n" +
                        "FROM equipamentos e \n" +
                        "JOIN fabricante f ON e.id_fabricante = f.id\n" +
                        "WHERE e.id = ?";
        Equipamento equipamento = new Equipamento();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                Fabricante fabricante = new Fabricante();
                fabricante.setId(rset.getInt("id"));
                fabricante.setNome(rset.getString("nome_fabricante"));
                
                equipamento.setId(rset.getInt("id"));
                equipamento.setNome(rset.getString("nome"));
                equipamento.setFabricante(fabricante);
                equipamento.setData_insercao(rset.getDate("data_insercao"));
                equipamento.setData_alteracao(rset.getDate("data_alteracao"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return equipamento;
    }
    
    //Editar Usuário
    @Override
    public int editar(Equipamento equipamento) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE equipamentos SET ")
                .append("nome = ?, ")
                .append("id_fabricante = ?, ")
                .append("data_alteracao = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, equipamento.getNome(),
                                                        equipamento.getFabricante().getId(),
                                                        equipamento.getData_alteracao(),
                                                        equipamento.getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM equipamentos ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}