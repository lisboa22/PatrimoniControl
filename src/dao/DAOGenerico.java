/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 *
 * @author robson
 */
public class DAOGenerico {
    private static final String CONFIG_FILE = "/recurso/configuracao/database.properties";
    
    
    public static Connection getConexao() throws SQLException, ClassNotFoundException {
        
        Connection conexao = null;
        Properties props = new Properties();
        
        try(InputStream input = DAOGenerico.class.getResourceAsStream(CONFIG_FILE)){
            if (input == null){
                System.err.println("Erro: Arquivo de configuração" + CONFIG_FILE + "não encontrado!");
                return null;
            }
            
            props.load(input);
            String URL = props.getProperty("db.url");
            String USUARIO = props.getProperty("db.user");
            String SENHA = props.getProperty("db.senha");
            String DRIVER = props.getProperty("db.driver");
            
            Class.forName(DRIVER);
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (IOException e){
           System.err.println("Erro ao ler o arquivos de configuração: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver JDBC: " + e.getMessage());
        } catch (SQLException e){
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            if (e.getSQLState().equals("28000")){
              System.err.println("Usuario e senha incorretos ");  
            }
        }
        
        return conexao;
  
    }
    
     public static int executarComando(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i+1,params[i]);
        }
        int result = sql.executeUpdate();
        sql.close();
        return result;
     }
     
     public static ResultSet executarConsulta(String query, Object... params) throws SQLException, ClassNotFoundException {
        PreparedStatement sql = (PreparedStatement)  getConexao().prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            sql.setObject(i+1,params[i]);
        }
        return sql.executeQuery();
    }
}
