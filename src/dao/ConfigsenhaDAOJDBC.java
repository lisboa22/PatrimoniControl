/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import modelo.Configsenha;
import java.util.ArrayList;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;



/**
 *
 * @author robson
 */
public class ConfigsenhaDAOJDBC implements ConfigsenhaDAO{
    
    private List<Configsenha> Configsenha;
    
    //8Listar Usuários
    @Override
    public List<Configsenha> listar() {
        ResultSet rset;
        String select = "SELECT * FROM configsenha";
        List<Configsenha> configsenhas = new ArrayList<>();
        try {        
            rset = DAOGenerico.executarConsulta(select);
            while (rset.next()) {
                
                Configsenha cs = new Configsenha();
                cs.setLetramaiuscula(rset.getBoolean("letramaiuscula"));
                cs.setLetraminuscula(rset.getBoolean("letraminuscula"));
                cs.setCaracterespecial(rset.getBoolean("caracterespecial"));
                cs.setNumeros(rset.getBoolean("numeros"));
                cs.setMinimocaracter(rset.getBoolean("minimocaracter"));
                configsenhas.add(cs);
                
            }
          
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return configsenhas;
    }
    
    @Override
    public int editar(Configsenha configsenha) {
      StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder
                .append("UPDATE configsenha SET ")
                .append("letramaiuscula = ?, ")
                .append("letraminuscula = ?, ")
                .append("caracterespecial = ?, ")
                .append("numeros = ?, ")
                .append("minimocaracter = ? ");
                //.append("WHERE id_permissao = ? and id_modulo = ?");
        String update = sqlBuilder.toString();
        int linha = 0;
        try {
            linha = DAOGenerico.executarComando(update, configsenha.isLetramaiuscula(),
                                                        configsenha.isLetraminuscula(),
                                                        configsenha.isCaracterespecial(),
                                                        configsenha.isNumeros(),
                                                        configsenha.isMinimocaracter());
                                                    
        } catch (Exception e) { 
            e.printStackTrace();
        } 
        return linha; 
    }
   
}
