/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Permissaomodulo;
import java.util.ArrayList;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import modelo.Modulo;
import modelo.Permissao;


/**
 *
 * @author robson
 */
public class PermissaomoduloDAOJDBC implements PermissaomoduloDAO{
    
    private List<Permissaomodulo> permissaomodulo;
    
    //Inserir Usuário
    @Override
    public int inserir(Permissaomodulo permissaomodulo) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into permissaomodulo(id_permissao, id_modulo, inserir, alterar, excluir, visualizar, data) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?)");
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, permissaomodulo.getPermissao().getId(),
                                                        permissaomodulo.getModulo().getId(),
                                                        permissaomodulo.isVisualizar(),
                                                        permissaomodulo.isInserir(),
                                                        permissaomodulo.isAlterar(),
                                                        permissaomodulo.isExcluir(),
                                                        permissaomodulo.getData()
                                                        );
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro registro.");      
            ex.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro registro.");      
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return linha;
    }

    //Listar Usuários
   /* @Override
    public List<Permissaomodulo> listar() {
        ResultSet rset;
        String select = "SELECT m.nome, pm.ver, pm.criar, pm.editar, pm.deletar "
                      + "FROM usuarios u JOIN permissao p ON u.id_permissao = p.id JOIN permissaomodulo pm ON p.id = pm.id_permissao JOIN modulo m ON pm.id_modulo = m.id ;";
                      //+ "WHERE u.id = ?;";
        List<Permissaomodulo> permissaomodulos = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                Permissaomodulo permissaomodulo = new Permissaomodulo();
                //permissaomodulo. setUid(rset.getInt("uid"));
                //permissaomodulo.setId_permissao(rset.getInt("id_permissao"));
                permissaomodulo.setMnome(rset.getString("m.nome"));
                permissaomodulo.setVer(rset.getBoolean("ver"));
                permissaomodulo.setCriar(rset.getBoolean("criar"));
                permissaomodulo.setEditar(rset.getBoolean("editar"));
                permissaomodulo.setExcluir(rset.getBoolean("deletar"));
                permissaomodulos.add(permissaomodulo);
                
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return permissaomodulos;
    }*/
    
     //Listar um único Usuário.
    @Override
    public List<Permissaomodulo> listarPorPermissao(int id) {
        
        List<Permissaomodulo> permissaomodulos = new ArrayList<>();
        
        ResultSet rset;
        String select = "SELECT pm.*, m.nome AS nome_modulo "
                      + "FROM permissaomodulo pm JOIN modulo m ON pm.id_modulo = m.id "
                      + "WHERE pm.id_permissao = ?;";
         
        Permissaomodulo permissaomodulo = new Permissaomodulo();
        try {        
            rset = DAOGenerico.executarConsulta(select, id);
            
            while (rset.next()) {
                
                Modulo modulo = new Modulo();
                modulo.setId(rset.getInt("id_modulo"));
                modulo.setNome(rset.getString("nome_modulo"));

                Permissao permissao = new Permissao();
                permissao.setId(rset.getInt("id"));
                Permissaomodulo pm = new Permissaomodulo();
                pm.setId(rset.getInt("id"));
                pm.setModulo(modulo);
                pm.setPermissao(permissao);
                pm.setInserir(rset.getBoolean("inserir"));
                pm.setAlterar(rset.getBoolean("alterar"));
                pm.setExcluir(rset.getBoolean("excluir"));
                pm.setVisualizar(rset.getBoolean("visualizar"));

                permissaomodulos.add(pm);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return permissaomodulos;
    }
    
    //Listar um único Usuário.
   /* @Override
    public Permissaomodulo listar(int id) {
        ResultSet rset;
        String select = "SELECT * FROM permissaomodulo WHERE id = ?";
        Permissaomodulo permissaomodulo = new Permissaomodulo();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                
                permissaomodulo.setId(rset.getInt("id"));
                permissaomodulo.setId_permissao(rset.getInt("id_permissao"));
                permissaomodulo.setId_modulo(rset.getInt("id_modulo"));
                permissaomodulo.setVer(rset.getBoolean("ver"));
                permissaomodulo.setVer(rset.getBoolean("criar"));
                permissaomodulo.setVer(rset.getBoolean("editar"));
                permissaomodulo.setVer(rset.getBoolean("deletar"));
                permissaomodulo.setData(rset.getDate("data"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return permissaomodulo;
    }
   */ 
    //Editar Usuário
    @Override
    public int editar(Permissaomodulo permissaomodulo) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE permissaomodulo SET ")
                .append("inserir = ?, ")
                .append("alterar = ?, ")
                .append("excluir = ?, ")
                .append("visualizar = ? ")
                .append("WHERE id_permissao = ? and id_modulo = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, permissaomodulo.isInserir(),
                                                        permissaomodulo.isAlterar(),
                                                        permissaomodulo.isExcluir(),
                                                        permissaomodulo.isVisualizar(),
                                                        permissaomodulo.getPermissao().getId(),
                                                        permissaomodulo.getModulo().getId());
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
    
    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM permissaomodulo ")
                .append("WHERE id_permissao = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}
