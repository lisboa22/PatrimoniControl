/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.DAOGenerico;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;
/**
 *
 * @author robson
 */
public class RelatoriosController implements ActionListener{

    private JTextField tfUsuario;
    
    public RelatoriosController(JTextField tfUsuario) {
        this.tfUsuario=tfUsuario;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        geraRealtorio();
        
    }

    public void geraRealtorio() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        String erro = "";
        String empresa = tfUsuario.getText();
        String jasper = "C:\\Users\\robson.SRVLISBOAINFO\\JaspersoftWorkspace\\relatorio-patrimonio-control\\relatorio-usuarios.jasper";
        
        HashMap<String, Object> param = new HashMap<String, Object>();
        param.put("EMPRESA", empresa);
        
        byte[] bytes = null;
        
        /*try {
            JasperReport relatorio = (JasperReport) JRLoader.loadObjectFromFile(jasper);
                bytes = JasperRunManager.runReportToPdf(relatorio, param,
                        DAOGenerico.getConexao());
            File arq = new File("C:\\TEMP",empresa+".pdf");
            if(arq.exists()){
                arq.delete();
            }
            FileOutputStream fos = new FileOutputStream(arq); 
            fos.write(bytes);
            fos.flush();
            fos.close();
            Desktop desk = Desktop.getDesktop();
            desk.open(arq);
        } catch (JRException | IOException | ClassNotFoundException e){
            erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro);
        } catch (SQLException e){
            erro = e.getMessage();
            JOptionPane.showMessageDialog(null, erro);
        }*/
    }
}
