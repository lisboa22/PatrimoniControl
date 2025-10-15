/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Movimentacao;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;
import modelo.Equipamento;
//import modelo.Modulo;
import modelo.Unidade;
import modelo.Usuario;


/**
 *
 * @author robson
 */
public class MovimentacaoDAOJDBC implements MovimentacaoDAO{
    
    /*private String Equipamento;
    private String Origem;
    private String Destino;
    private String Usuarioliberacao;
    private String Usuariorecepcao;*/
    
    /*EquipamentoDAO equipamentoDAO = DAOFactory.criarEquipamentoDAO();
    UnidadeDAO unidadeDAO = DAOFactory.criarUnidadeDAO();
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();*/
    
    //private List<Movimentacao> movimentacao;
    
    //Inserir Usuário
    @Override
    public int inserir(Movimentacao movimentacao) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("insert into movimentacao(id_equipamento, num_serie, tipo_movimentacao, id_origem, id_usuario_liberacao, id_destino, id_usuario_recepcao, observacoes, data_hora) ")
                .append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
        String insert = sqlBuilder.toString();
        int linha = 0;
        try {  
            linha = DAOGenerico.executarComando(insert, movimentacao.getEquipamento().getId(),
                                                        movimentacao.getNum_serie(),
                                                        movimentacao.getTipo_movimentacao(),
                                                        movimentacao.getOrigem().getId(),
                                                        movimentacao.getUsuarioliberacao().getId(),
                                                        movimentacao.getDestino().getId(),
                                                        movimentacao.getUsuariorecepcao().getId(),
                                                        movimentacao.getObservacoes(),
                                                        movimentacao.getData_hora()
                                                        );
        } catch (SQLIntegrityConstraintViolationException ex) {
            JOptionPane.showMessageDialog(null, "Não é possível inserir: o email já está vinculado a outro Registro.");      
            ex.printStackTrace();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "bNão é possível inserir: o email já está vinculado a outro Registro.");      
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return linha;
    }

    //Listar Usuários
    @Override
    public List<Movimentacao> listar() {
        ResultSet rset;
        String select = "SELECT m.id, m.num_serie, m.tipo_movimentacao, m.observacoes, m.data_hora, e.id AS id_equipamento, e.nome AS nome_equipamento, \n" +
                        "uo.id AS id_origem, uo.unidade AS unidade_origem, ud.id AS id_destino, ud.unidade AS unidade_destino, ul.id AS id_usuario_liberacao, \n" +
                        "ul.nome AS nome_usuario_liberacao, ur.id AS id_usuario_recepcao, ur.nome AS nome_usuario_recepcao\n" +
                        "FROM movimentacao m\n" +
                        "JOIN equipamento e ON m.id_equipamento = e.id\n" +
                        "JOIN unidade uo ON m.id_origem = uo.id\n" +
                        "JOIN unidade ud ON m.id_destino = ud.id\n" +
                        "JOIN usuario ul ON m.id_usuario_liberacao = ul.id\n" +
                        "JOIN usuario ur ON m.id_usuario_recepcao = ur.id\n" +
                        "ORDER BY m.id";
        List<Movimentacao> movimentacoes = new ArrayList<>();
        try {      
            rset = DAOGenerico.executarConsulta(select);
            
            while (rset.next()) {
                
                Equipamento equipamento = new Equipamento();
                equipamento.setId(rset.getInt("id_equipamento"));
                equipamento.setNome(rset.getString("nome_equipamento"));
           
                Unidade origem = new Unidade();
                origem.setId(rset.getInt("id_origem"));
                origem.setUnidade(rset.getString("unidade_origem"));
                
                Unidade destino = new Unidade();
                destino.setId(rset.getInt("id_destino"));
                destino.setUnidade(rset.getString("unidade_destino"));
                
                Usuario liberacao = new Usuario();
                liberacao.setId(rset.getInt("id_usuario_liberacao"));
                liberacao.setNome(rset.getString("nome_usuario_liberacao"));
                
                Usuario recepcao = new Usuario();
                recepcao.setId(rset.getInt("id_usuario_recepcao"));
                recepcao.setNome(rset.getString("nome_usuario_recepcao"));
                
                Movimentacao movimentacao = new Movimentacao();
                movimentacao.setId(rset.getInt("id"));
                movimentacao.setEquipamento(equipamento);
                movimentacao.setNum_serie(rset.getString("num_serie"));
                movimentacao.setTipo_movimentacao(rset.getString("tipo_movimentacao"));
                movimentacao.setOrigem(origem);
                movimentacao.setUsuarioliberacao(liberacao);
                movimentacao.setDestino(destino);
                movimentacao.setUsuariorecepcao(recepcao);
                movimentacao.setObservacoes(rset.getString("observacoes"));
                movimentacao.setData_hora(rset.getTimestamp("data_hora"));
                movimentacoes.add(movimentacao);
            }
        } catch (SQLIntegrityConstraintViolationException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "A lista está vazia!.");      
            ex.printStackTrace(); 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    
        return movimentacoes;
    }
    
    
    //Listar um único Usuário.
    @Override
    public Movimentacao listar(int id) {
        ResultSet rset;
        String select = "SELECT m.id, m.num_serie, m.tipo_movimentacao, m.observacoes, m.data_hora, e.id AS id_equipamento, e.nome AS nome_equipamento, \n" +
                        "uo.id AS id_origem, uo.unidade AS unidade_origem, ud.id AS id_destino, ud.unidade AS unidade_destino, ul.id AS id_usuario_liberacao, \n" +
                        "ul.nome AS nome_usuario_liberacao, ur.id AS id_usuario_recepcao, ur.nome AS nome_usuario_recepcao\n" +
                        "FROM movimentacao m\n" +
                        "JOIN equipamento e ON m.id_equipamento = e.id\n" +
                        "JOIN unidade uo ON m.id_origem = uo.id\n" +
                        "JOIN unidade ud ON m.id_destino = ud.id\n" +
                        "JOIN usuario ul ON m.id_usuario_liberacao = ul.id\n" +
                        "JOIN usuario ur ON m.id_usuario_recepcao = ur.id\n" +
                        "WHERE id = ?";
        Movimentacao movimentacao = new Movimentacao();
        try {        
            rset = DAOGenerico.executarConsulta(select,id);
            
            while (rset.next()) {
                Equipamento equipamento = new Equipamento();
                equipamento.setId(rset.getInt("id_equipamento"));
                equipamento.setNome(rset.getString("nome_equipamento"));
           
                Unidade origem = new Unidade();
                origem.setId(rset.getInt("id_origem"));
                origem.setUnidade(rset.getString("unidade_origem"));
                
                Unidade destino = new Unidade();
                destino.setId(rset.getInt("id_destino"));
                destino.setUnidade(rset.getString("unidade_destino"));
                
                Usuario liberacao = new Usuario();
                liberacao.setId(rset.getInt("id_usuario_liberacao"));
                liberacao.setNome(rset.getString("nome_usuario_liberacao"));
                
                Usuario recepcao = new Usuario();
                recepcao.setId(rset.getInt("id_usuario_recepcao"));
                recepcao.setNome(rset.getString("nome_usuario_recepcao"));
                
                movimentacao.setId(rset.getInt("id"));
                movimentacao.setEquipamento(equipamento);
                movimentacao.setNum_serie(rset.getString("num_serie"));
                movimentacao.setTipo_movimentacao(rset.getString("tipo_movimentacao"));
                movimentacao.setOrigem(origem);
                movimentacao.setUsuarioliberacao(liberacao);
                movimentacao.setDestino(destino);
                movimentacao.setUsuariorecepcao(recepcao);
                movimentacao.setObservacoes(rset.getString("observacoes"));
                movimentacao.setData_hora(rset.getDate("data_hora"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return movimentacao;
    }
    
    //Editar Usuário
    @Override
    public int editar(Movimentacao movimentacao) {
        
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder 
                .append("UPDATE movimentacao SET ")
                .append("id_equipamento = ?, ")
                .append("num_serie = ?, ")
                .append("tipo_movimentacao = ?, ")
                .append("id_origem = ?, ")
                .append("id_usuario_liberacao = ?, ")
                .append("id_destino = ?, ")
                .append("id_usuario_recepcao = ?, ")
                .append("observacoes = ? ")
                .append("WHERE id = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        
        try {
            
            linha = DAOGenerico.executarComando(update, movimentacao.getEquipamento().getId(),
                                                        movimentacao.getNum_serie(),
                                                        movimentacao.getTipo_movimentacao(),
                                                        movimentacao.getOrigem().getId(),
                                                        movimentacao.getUsuarioliberacao().getId(),
                                                        movimentacao.getDestino().getId(),
                                                        movimentacao.getUsuariorecepcao().getId(),
                                                        movimentacao.getObservacoes(),
                                                        movimentacao.getId());
                        
        } catch (Exception e) { 
            e.printStackTrace();
        } 
      
        return linha;
    }

    @Override
    public int apagar(int id) throws ClassNotFoundException, SQLException, SQLIntegrityConstraintViolationException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("DELETE FROM movimentacao ")
                .append("WHERE id = ?");
        String delete = sqlBuilder.toString();
        int linha = 0;        
        linha = DAOGenerico.executarComando(delete, id);
        return linha;
    }
}
