/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author robson
 */
public class DAOFactory {
    public static UsuarioDAO criarUsuarioDAO() {
        return (UsuarioDAO) new UsuarioDAOJDBC();
    }
    
    public static PermissaoDAO criarPermissaoDAO() {
        return (PermissaoDAO) new PermissaoDAOJDBC();
    }
    
    public static FabricanteDAO criarFabricanteDAO() {
        return (FabricanteDAO) new FabricanteDAOJDBC();
    }
     
    public static EquipamentoDAO criarEquipamentoDAO() {
        return (EquipamentoDAO) new EquipamentoDAOJDBC();
    }
    
    public static UnidadeDAO criarUnidadeDAO() {
        return (UnidadeDAO) new UnidadeDAOJDBC();
    }
    
    public static ModuloDAO criarModuloDAO() {
        return (ModuloDAO) new ModuloDAOJDBC();
    }
    
    public static PermissaomoduloDAO criarPermissaomoduloDAO() {
        return (PermissaomoduloDAO) new PermissaomoduloDAOJDBC();
    }
   
    public static LogDAO criarLogDAO() {
        return (LogDAO) new LogDAOJDBC();
    }
    
    public static MovimentacaoDAO criarMovimentacaoDAO() {
        return (MovimentacaoDAO) new MovimentacaoDAOJDBC();
    }
    
    public static ConfigsenhaDAO criarConfigsenhaDAO() {
        return (ConfigsenhaDAO) new ConfigsenhaDAOJDBC();
    }
    /*
    public static FormaPagamentoDAO criarFormaPagamentoDAO() {
        return (FormaPagamentoDAO) new FormaPagamentoDAOJDBC();
    }
    
    public static VendaDAO criarVendaDAO() {
        return (VendaDAO) new VendaDAOJDBC();
    }*/
}