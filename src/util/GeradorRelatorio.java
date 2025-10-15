/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Map;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import dao.DAOGenerico;
import javax.swing.JToolBar;
import net.sf.jasperreports.swing.JRViewerToolbar;

/**
 * @author robson
 */
public class GeradorRelatorio {

    /**
     * Cria e exibe um frame customizado com o relatório e botões próprios
     */
    private void exibirRelatorioCustomizado(JasperPrint jasperPrint, String tituloJanela, Component parent) {
        JDialog dialog = new JDialog();
        dialog.setTitle(tituloJanela);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(1080, 800);
        dialog.setLocationRelativeTo(parent);

        // Criar o JRViewer
        JRViewer viewer = new JRViewer(jasperPrint);

        // Remover a toolbar
        Component[] components = viewer.getComponents();
        for (Component comp : components) {
            if (comp instanceof JRViewerToolbar) {
                viewer.remove(comp);
                break;
            }
        }

        // Atualizar visualização
        viewer.revalidate();
        viewer.repaint();

        // Adicionar ao dialog
        dialog.add(viewer, BorderLayout.CENTER);

        // Criar barra de botões customizada no topo
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        
        // Botão Imprimir
        JButton btnImprimir = new JButton("Imprimir");
        btnImprimir.setToolTipText("Imprimir relatório");
        btnImprimir.addActionListener(e -> imprimirRelatorioRapido(jasperPrint, dialog));
        
        // Botão Salvar PDF
        JButton btnSalvarPDF = new JButton("Salvar PDF");
        btnSalvarPDF.setToolTipText("Exportar para PDF");
        btnSalvarPDF.addActionListener(e -> salvarPDF(jasperPrint, dialog));
        
        // Botão Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setToolTipText("Fechar janela");
        btnFechar.addActionListener(e -> dialog.dispose());
        
        painelBotoes.add(btnImprimir);
        painelBotoes.add(btnSalvarPDF);
        painelBotoes.add(btnFechar);
        
        // Adiciona painel de botões na parte SUPERIOR
        dialog.add(painelBotoes, BorderLayout.NORTH);
        
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    /**
     * Imprime o relatório usando PrinterJob do Java (RÁPIDO)
     */
    private void imprimirRelatorioRapido(JasperPrint jasperPrint, Component parent) {
        new Thread(() -> {
            try {
                // Cria o PrinterJob do Java
                PrinterJob printerJob = PrinterJob.getPrinterJob();
                
                // Configura atributos de impressão
                PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
                
                // Cria um printable a partir do JasperPrint
                net.sf.jasperreports.engine.export.JRPrintServiceExporter exporter = 
                    new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                
                // Usa o PrinterJob nativo do Java - MUITO MAIS RÁPIDO
                if (printerJob.printDialog(printRequestAttributeSet)) {
                    // Exporta usando o serviço selecionado
                    net.sf.jasperreports.export.SimpleExporterInput exporterInput = 
                        new net.sf.jasperreports.export.SimpleExporterInput(jasperPrint);
                    exporter.setExporterInput(exporterInput);
                    
                    net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration configuration = 
                        new net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration();
                    configuration.setPrintService(printerJob.getPrintService());
                    configuration.setDisplayPageDialog(Boolean.FALSE);
                    configuration.setDisplayPrintDialog(Boolean.FALSE); // Já exibimos antes
                    
                    exporter.setConfiguration(configuration);
                    exporter.exportReport();
                    
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        JOptionPane.showMessageDialog(parent,
                                "Relatório enviado para impressão!",
                                "Sucesso",
                                JOptionPane.INFORMATION_MESSAGE);
                    });
                }
                
            } catch (Exception e) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(parent,
                            "Erro ao imprimir: " + e.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                });
                e.printStackTrace();
            }
        }, "FastPrintThread").start();
    }

    /**
     * Salva o relatório como PDF
     */
    private void salvarPDF(JasperPrint jasperPrint, Component parent) {
        new Thread(() -> {
            try {
                javax.swing.SwingUtilities.invokeAndWait(() -> {
                    try {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setDialogTitle("Salvar Relatório como PDF");
                        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos PDF", "pdf");
                        fileChooser.setFileFilter(filter);
                        fileChooser.setSelectedFile(new File("relatorio.pdf"));

                        int userSelection = fileChooser.showSaveDialog(parent);

                        if (userSelection == JFileChooser.APPROVE_OPTION) {
                            File fileToSave = fileChooser.getSelectedFile();
                            String caminho = fileToSave.getAbsolutePath();

                            if (!caminho.toLowerCase().endsWith(".pdf")) {
                                caminho += ".pdf";
                            }

                            JasperExportManager.exportReportToPdfFile(jasperPrint, caminho);

                            JOptionPane.showMessageDialog(parent,
                                    "Relatório salvo com sucesso!\nLocal: " + caminho,
                                    "Sucesso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(parent,
                                "Erro ao salvar PDF: " + e.getMessage(),
                                "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "SavePDFThread").start();
    }

    /**
     * Gera e exibe o relatório em uma janela customizada
     */
    public void gerarRelatorio(String caminhoRelatorio, Map<String, Object> parametros, String tituloJanela, Component parentComponent) {
        new Thread(() -> gerarRelatorioInterno(caminhoRelatorio, parametros, tituloJanela, parentComponent)).start();
    }

    private void gerarRelatorioInterno(String caminhoRelatorio, Map<String, Object> parametros, String tituloJanela, Component parentComponent) {
        Connection conexao = null;

        try {
            conexao = DAOGenerico.getConexao();
            InputStream relatorioStream = getClass().getResourceAsStream(caminhoRelatorio);
            JasperPrint impressao = JasperFillManager.fillReport(relatorioStream, parametros, conexao);

            javax.swing.SwingUtilities.invokeLater(() -> {
                if (impressao.getPages().isEmpty()) {
                    JOptionPane.showMessageDialog(parentComponent,
                            "ATENÇÃO: O relatório foi gerado mas não contém dados!\n",
                            "Relatório Vazio",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    exibirRelatorioCustomizado(impressao, tituloJanela, parentComponent);
                }
            });

        } catch (Exception e) {
            javax.swing.SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(parentComponent,
                        "Erro ao gerar relatório: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            });
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sobrecarga do método para gerar relatório sem parâmetros
     */
    public void gerarRelatorio(String caminhoRelatorio, String tituloJanela, Component parentComponent) {
        gerarRelatorio(caminhoRelatorio, null, tituloJanela, parentComponent);
    }

    /**
     * Gera relatório de usuários
     */
    public void gerarRelatorioUsuarios(Component parentComponent) {
        gerarRelatorio("/relatorios/relatorio_usuario.jasper", "Relatório de Usuários", parentComponent);
    }

    /**
     * Gera relatório de equipamentos
     */
    public void gerarRelatorioEquipamentos(Component parentComponent) {
        gerarRelatorio("/relatorios/Relatorio-equipamento.jasper", "Relatório de Equipamentos", parentComponent);
    }

    /**
     * Gera relatório de usuários com filtro por ID
     */
    public void gerarRelatorioUsuarioPorId(Integer idUsuario, Component parentComponent) {
        Map<String, Object> parametros = new java.util.HashMap<>();
        parametros.put("ID_USUARIO", idUsuario);
        gerarRelatorio("/relatorios/relatorio_usuario.jasper", parametros, "Relatório de Usuário", parentComponent);
    }

    /**
     * Gera relatório de equipamentos com filtro por status
     */
    public void gerarRelatorioEquipamentoPorStatus(String status, Component parentComponent) {
        Map<String, Object> parametros = new java.util.HashMap<>();
        parametros.put("STATUS", status);
        gerarRelatorio("/relatorios/relatorio_equipamento.jasper", parametros, "Relatório de Equipamentos", parentComponent);
    }

    /**
     * Exporta relatório para PDF
     */
    public void exportarParaPDF(String caminhoRelatorio, Map<String, Object> parametros, Component parentComponent) {
        Connection conexao = null;

        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Salvar Relatório como PDF");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos PDF", "pdf");
            fileChooser.setFileFilter(filter);

            int userSelection = fileChooser.showSaveDialog(parentComponent);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                String caminho = fileToSave.getAbsolutePath();

                if (!caminho.toLowerCase().endsWith(".pdf")) {
                    caminho += ".pdf";
                }

                conexao = DAOGenerico.getConexao();
                InputStream relatorioStream = getClass().getResourceAsStream(caminhoRelatorio);
                JasperPrint impressao = JasperFillManager.fillReport(relatorioStream, parametros, conexao);

                JasperExportManager.exportReportToPdfFile(impressao, caminho);

                JOptionPane.showMessageDialog(parentComponent,
                        "Relatório exportado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(parentComponent,
                    "Erro ao exportar relatório: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}