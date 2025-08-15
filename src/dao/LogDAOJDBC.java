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
import modelo.Log;

/**
 *
 * @author robson
 */
public class LogDAOJDBC implements LogDAO {
    private List<Log> log;
    
    //Inserir Permissão
    @Override
    public int inserir(Log log) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into log(acao, data) ")
                .append("VALUES (?, ?)");
     
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, log.getAcao(),
                                                        log.getData());
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
    public List<Log> listar() {
        ResultSet rset;
        String select = "SELECT * FROM log ORDER BY id";
        List<Log> logs = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Log log = new Log();
                log.setId(rset.getInt("id"));
                log.setAcao(rset.getString("acao"));
                log.setData(rset.getTimestamp("data"));
                logs.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return logs;
    }

    //Listar um único Permissão.
    @Override
    public Log listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM log WHERE id = ?";
        Log log = new Log();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                log.setId(rset.getInt("id"));
                log.setAcao(rset.getString("acao"));
                log.setData(rset.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return log;
    }
}
