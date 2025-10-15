/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import dao.ConfigsenhaDAO;
import dao.DAOFactory;
import dao.LogDAO;
import dao.ModuloDAO;
import dao.PermissaoDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.List;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import modelo.Permissao;
import modelo.Modulo;
import modelo.Permissaomodulo;
import dao.PermissaomoduloDAO;
import dao.UsuarioDAO;
import java.awt.Frame;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import modelo.Configsenha;
import modelo.Log;
import modelo.Usuario;


/**
 *
 * @author robson
 */
public class frmConfiguracao extends frmGenericomodal {
    
    PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
    ConfigsenhaDAO configsenhaDAO = DAOFactory.criarConfigsenhaDAO();
    DefaultTableModel modelo = null;
    private int linhaSelecionada;
    private int tIdpermissao;
    private String tPermissao;
    private int tIdmodulo;
    private int idUsuariosecao;
    private String nomeUsuariosecao;
    private int idPermissaosecao;
    
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
    PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
    ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
    
    // Pegue a lista de permissões
    List<Permissao> permissoes = permissaoDAO.listar();       
    
    /*
     * Construtor da classe frmUsuario.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmConfiguracao(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
        super(parent, modal);
        this.idUsuariosecao = idUsuariosecao;
        this.idPermissaosecao = idPermissaosecao;
        initComponents();
        initEstiloGlobal();
        //radModulo.setVisible(false);
         
        // Aplica o filtro de maiúsculas ao JTextField      
        //((AbstractDocument) txtModulo.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());

        // Define um modelo de tabela que não permite edição de células
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"MÓDULO*", "INSERIR*", "ALTERAR*", "EXCLUIR*", "VISUALIZAR*"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição em todas as células
            }
        };
        tblModulo.setModel(modelo);
        tblModulo.setColumnSelectionAllowed(false);
        tblModulo.isCellEditable(linhaSelecionada, 0);

        try {
            tblModulo.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblModulo.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            //tblModulo.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblModulo.getColumnModel().getColumn(0).setMinWidth(100);
            //tblModulo.getColumnModel().getColumn(2).setMaxWidth(100);
        } catch (Exception ex) {
            System.out.println("erro");
        }

        //Limpa combobox
        cmbPermissao.removeAllItems();
        
        //Adiciona usuários no combobox
        for (Permissao p : permissoes) {
            tPermissao = p.getNome();
            cmbPermissao.addItem(tPermissao);
        }
        
        //Pegar o id do primeiro elemento da lista
        for (Permissao p : permissoes) {
            if (permissoes != null && !permissoes.isEmpty()){
                Permissao primeiroElemento = permissoes.get(0);
                tIdpermissao = primeiroElemento.getId();
            }
        }
        
        /*//Limpa combobox
        jComboBox1.removeAllItems();
        
        for (Permissao p : permissoes) {
            String permissao = p.getNome();
            jComboBox1.addItem(permissao);
        }*/
    }

    /**
     * Preenche a tabela de usuários com os dados obtidos do banco de dados.
     * Utiliza formatação de data para exibição na tabela.
     */
    
    private void verificaConfigsenha(){
        List<Configsenha> configsenhas = configsenhaDAO. listar();
   
        // Obtém o valor da célula (será um Object, então converte para String ou Integer)
        Object oLetramaiuscula = configsenhas.getFirst().isLetramaiuscula();
        Object oLetraminuscula = configsenhas.getFirst().isLetraminuscula();
        Object oCaracterespecial = configsenhas.getFirst().isCaracterespecial();
        Object oNumeros = configsenhas.getFirst().isNumeros();
        Object oMinimocaracter = configsenhas.getFirst().isMinimocaracter();

        chkLetramaiuscula.setSelected((boolean) oLetramaiuscula);
        chkLetraminuscula.setSelected((boolean) oLetraminuscula);
        chkCaracterespecial.setSelected((boolean) oCaracterespecial);
        chkNumeros.setSelected((boolean) oNumeros);
        chkMinimocaracter.setSelected((boolean) oMinimocaracter);
    }
    private void preencherTabela() {
        
        modelo.getDataVector().clear();
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        tblModulo.getColumnModel().getColumn(0).setPreferredWidth(50);
        try {
            List<Permissaomodulo> permissaomodulos = permissaomoduloDAO.listarPorPermissao(tIdpermissao);
    
            List<Modulo> modulos = moduloDAO.listar();
            int idPerm=-1;
            for (Modulo m : modulos) {
                idPerm = idPerm+1;
         
                modelo.addRow(new Object[]{//permissaomodulo.getId(),
                                            permissaomodulos.get(idPerm).getModulo().getNome(),
                                            permissaomodulos.get(idPerm).isInserir(),
                                            permissaomodulos.get(idPerm).isAlterar(),
                                            permissaomodulos.get(idPerm).isExcluir(),
                                            permissaomodulos.get(idPerm).isVisualizar(),
                                            });
                }
               
        } catch (Exception e) {
            e.printStackTrace();
           // System.out.println("fff");
            throw e;
        }
    }

    private void setarPermissao(){
        // Pegue a lista de permissões
        List<Permissaomodulo> permissaomodulos = permissaomoduloDAO.listarPorPermissao(idPermissaosecao); 

        // 2. Crie um mapa para armazenar as permissões consolidadas por módulo
        // A chave é o nome do módulo e o valor é o objeto Permissaomodulo
        Map<String, Permissaomodulo> permissoesPorModulo = new HashMap<>();

        // 3. Itere sobre a lista e preencha o mapa
        for (Permissaomodulo pm : permissaomodulos) {
            permissoesPorModulo.put(pm.getModulo().getNome(), pm);
        }

        // 4. Inicialize todos os botões como desabilitados para uma base limpa
        // Esta é a parte mais importante para evitar erros de estado.
        btnEditar.setEnabled(false);
                
        // 5. Verifique as permissões para o módulo "USUÁRIO" e habilite os botões
        Permissaomodulo permissaoUsuario = permissoesPorModulo.get("CONFIGURAÇÃO");
        if (permissaoUsuario != null) {
            btnEditar.setEnabled(permissaoUsuario.isAlterar());
        }             
            
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panSuperior = new javax.swing.JPanel();
        txtModulo = new javax.swing.JTextField();
        lblModulo = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        chkInserir = new javax.swing.JCheckBox();
        chkAlterar = new javax.swing.JCheckBox();
        chkExcluir = new javax.swing.JCheckBox();
        chkVisualizar = new javax.swing.JCheckBox();
        cmbPermissao = new javax.swing.JComboBox<>();
        lblPermissao = new javax.swing.JLabel();
        panInferior = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModulo = new javax.swing.JTable();
        btnEditar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        chkLetramaiuscula = new javax.swing.JCheckBox();
        chkCaracterespecial = new javax.swing.JCheckBox();
        chkNumeros = new javax.swing.JCheckBox();
        chkMinimocaracter = new javax.swing.JCheckBox();
        chkLetraminuscula = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patrim - Configurações");
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        panSuperior.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtModulo.setEditable(false);

        lblModulo.setBackground(new java.awt.Color(51, 51, 51));
        lblModulo.setText("MÓDULO*");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("CONFIGURAÇÃO");
        lblTitulo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        chkInserir.setText("INSERIR");
        chkInserir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkInserirMouseClicked(evt);
            }
        });

        chkAlterar.setText("ALTERAR");
        chkAlterar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkAlterarMouseClicked(evt);
            }
        });

        chkExcluir.setText("EXCLUIR");
        chkExcluir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkExcluirMouseClicked(evt);
            }
        });

        chkVisualizar.setText("VISUALIZAR");

        cmbPermissao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cmbPermissaoMouseClicked(evt);
            }
        });
        cmbPermissao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPermissaoActionPerformed(evt);
            }
        });

        lblPermissao.setText("PERMISSÃO");

        javax.swing.GroupLayout panSuperiorLayout = new javax.swing.GroupLayout(panSuperior);
        panSuperior.setLayout(panSuperiorLayout);
        panSuperiorLayout.setHorizontalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panSuperiorLayout.createSequentialGroup()
                        .addComponent(cmbPermissao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(358, 358, 358))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addComponent(lblPermissao)
                        .addGap(275, 498, Short.MAX_VALUE))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblModulo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addComponent(txtModulo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkInserir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkAlterar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkExcluir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chkVisualizar)))
                        .addGap(0, 51, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPermissao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblModulo)
                .addGap(4, 4, 4)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkInserir)
                    .addComponent(chkAlterar)
                    .addComponent(chkExcluir)
                    .addComponent(chkVisualizar))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        tblModulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "MÓDULO", "INSERIR", "ALTERAR", "EXCLUIR", "VISUALIZAR"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblModulo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblModulo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblModuloMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblModulo);

        btnEditar.setBackground(new java.awt.Color(255, 51, 0));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setForeground(new java.awt.Color(255, 255, 255));
        btnEditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\robson.SRVLISBOAINFO\\Desktop\\ProjPOO\\PatrimonioControl\\PatrimonioControl\\src\\recurso\\editar-codigo.png")); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(255, 0, 51));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltar.setIcon(new javax.swing.ImageIcon("C:\\Users\\robson.SRVLISBOAINFO\\Desktop\\ProjPOO\\PatrimonioControl\\PatrimonioControl\\src\\recurso\\sair.png")); // NOI18N
        btnVoltar.setText("Sair");
        btnVoltar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVoltar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnLimpar.setBackground(new java.awt.Color(255, 51, 0));
        btnLimpar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpar.setForeground(new java.awt.Color(255, 255, 255));
        btnLimpar.setIcon(new javax.swing.ImageIcon("C:\\Users\\robson.SRVLISBOAINFO\\Desktop\\ProjPOO\\PatrimonioControl\\PatrimonioControl\\src\\recurso\\limpar-limpo.png")); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Limpa os campos acima.");
        btnLimpar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        btnLog.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLog.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/formato-de-arquivo-log.png"))); // NOI18N
        btnLog.setText("Log");
        btnLog.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLog.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        chkLetramaiuscula.setText("LETRA MAIÚSCULA");
        chkLetramaiuscula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkLetramaiusculaMouseClicked(evt);
            }
        });

        chkCaracterespecial.setText("CARACTER ESPECIAL");
        chkCaracterespecial.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkCaracterespecialMouseClicked(evt);
            }
        });

        chkNumeros.setText("NÚMEROS");
        chkNumeros.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkNumerosMouseClicked(evt);
            }
        });

        chkMinimocaracter.setText("MÍNIMO DE CARACTERES");
        chkMinimocaracter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkMinimocaracterMouseClicked(evt);
            }
        });

        chkLetraminuscula.setText("LETRA MINÚSCULA");
        chkLetraminuscula.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkLetraminusculaMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chkLetramaiuscula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkCaracterespecial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkNumeros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkMinimocaracter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkLetraminuscula, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkLetramaiuscula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkLetraminuscula)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chkCaracterespecial)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkNumeros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkMinimocaracter)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("OPÇÕES DE SENHA");

        javax.swing.GroupLayout panInferiorLayout = new javax.swing.GroupLayout(panInferior);
        panInferior.setLayout(panInferiorLayout);
        panInferiorLayout.setHorizontalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35))
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panInferiorLayout.setVerticalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    /**
     * Evento disparado quando a janela ganha foco.
     * Atualiza a tabela de usuários e aplica filtro de busca.
     */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        preencherTabela();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblModulo.setRowSorter(sorter);
        verificaConfigsenha();
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * Evento disparado ao fechar a janela.
     * Retorna para a tela principal.
     */
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.dispose();
    }//GEN-LAST:event_formWindowClosed

    /**
     * Evento acionado ao clicar em uma linha da tabela.
     * Pode ser usado para capturar o ID do usuário selecionado.
     */
    private void tblModuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblModuloMouseClicked
        //String permissao = cmbPermissao
        
        linhaSelecionada = tblModulo.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Suponha que o ID esteja na primeira coluna (índice 0)
            String nome = tblModulo.getValueAt(linhaSelecionada, 0).toString();
            txtModulo.setText(nome);
            
            // Obtém o valor da célula (será um Object, então converte para String ou Integer)
            Object oInserir = tblModulo.getValueAt(linhaSelecionada, 1);
            Object oAlterar = tblModulo.getValueAt(linhaSelecionada, 2);
            Object oExcluir = tblModulo.getValueAt(linhaSelecionada, 3);
            Object oVisualizar = tblModulo.getValueAt(linhaSelecionada, 4);
 
        
            chkInserir.setSelected((boolean) oInserir);
            chkAlterar.setSelected((boolean) oAlterar);
            chkExcluir.setSelected((boolean) oExcluir);
            chkVisualizar.setSelected((boolean) oVisualizar);
            
            List<Modulo> modulos = moduloDAO.listar();
            //Pegar o id do elemento de acordo com o nome selecionado
            for (Modulo m : modulos) {
                if (txtModulo.getText().equals(m.getNome())){
                    tIdmodulo = m.getId();
                }
            }         
        }
    }//GEN-LAST:event_tblModuloMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblModulo.getSelectedRow();
            
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Para Alterar selecione a linha desejada!");
                return;
            }
            
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Para Alterar selecione a linha desejada!");
            return;
        }
        
        try {
            Permissao permissao = new Permissao();
            permissao.setId(tIdpermissao);
                        
            Modulo modulo = new Modulo();
            modulo.setId(tIdmodulo);
                        
            // Cria o objeto Usuario com os dados dos campos
            Permissaomodulo pm = new Permissaomodulo();
            pm.setPermissao(permissao);
            pm.setModulo(modulo);
            pm.setInserir(chkInserir.isSelected());
            pm.setAlterar(chkAlterar.isSelected());
            pm.setExcluir(chkExcluir.isSelected());
            pm.setVisualizar(chkVisualizar.isSelected());
            
            // Chama a função editar
            PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
            int resultado = permissaomoduloDAO.editar(pm);

            if (resultado > 0) {
                
                //Registra informações no log. 
                try{     

                    List<Usuario> usuarios = usuarioDAO.listar();

                    for (Usuario u : usuarios) {
                        if(u.getId() == idUsuariosecao){
                            nomeUsuariosecao = u.getNome();
                        }
                    }

                    Log log = new Log();
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou as permissões de acesso.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);

                }catch (Exception e){

                }
                
                JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso.");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar o registro.");
            }
             
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        }     
    }//GEN-LAST:event_btnEditarActionPerformed

    private void editarConfigsenha(){
        
        try {
                        
            // Cria o objeto Usuario com os dados dos campos
            Configsenha cs = new Configsenha();
            cs.setLetramaiuscula(chkLetramaiuscula.isSelected());
            cs.setLetraminuscula(chkLetraminuscula.isSelected());
            cs.setCaracterespecial(chkCaracterespecial.isSelected());
            cs.setNumeros(chkNumeros.isSelected());
            cs.setMinimocaracter(chkMinimocaracter.isSelected());
            
            // Chama a função editar
            ConfigsenhaDAO configsenhaDAO = DAOFactory.criarConfigsenhaDAO();
            int resultado = configsenhaDAO.editar(cs);

            if (resultado > 0) {
                
                //Registra informações no log. 
                try{     

                    List<Usuario> usuarios = usuarioDAO.listar();

                    for (Usuario u : usuarios) {
                        if(u.getId() == idUsuariosecao){
                            nomeUsuariosecao = u.getNome();
                        }
                    }

                    Log log = new Log();
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou as opções de senha de acesso.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);

                }catch (Exception e){

                }
                
                //JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso.");
                //limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar o registro.");
            }
             
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        }
    }
    
    private void restaurarSelecaoTabela(int linha) {
        //preencherTabela();
        if (linha >= 0 && linha < tblModulo.getRowCount()) {
            tblModulo.setRowSelectionInterval(linha, linha);
            tblModulo.scrollRectToVisible(tblModulo.getCellRect(linha, 0, true));
        }
    }

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void cmbPermissaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cmbPermissaoMouseClicked
        cmbPermissao.getSelectedItem();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbPermissaoMouseClicked

    private void cmbPermissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPermissaoActionPerformed
        // Pegue a lista de permissões.
        List<Permissao> permissoes = permissaoDAO.listar();

        //Pegar o id do elemento de acordo com o nome selecionado
        for (Permissao p : permissoes) {
            if (cmbPermissao.getSelectedItem().equals(p.getNome())){
                tIdpermissao = p.getId();
            }
        }
        limparCampos();
        preencherTabela();
    }//GEN-LAST:event_cmbPermissaoActionPerformed

    private void chkAlterarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkAlterarMouseClicked
        if (chkAlterar.isSelected()){
            chkVisualizar.setSelected(true);
        } else {
            if (!chkInserir.isSelected() && !chkExcluir.isSelected()){
                chkVisualizar.setSelected(false);
            }
        }
    }//GEN-LAST:event_chkAlterarMouseClicked

    private void chkInserirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkInserirMouseClicked
        if (chkInserir.isSelected()){
            chkVisualizar.setSelected(true);
        } else {
            if (!chkAlterar.isSelected() && !chkExcluir.isSelected()){
                chkVisualizar.setSelected(false);
            }
        }
    }//GEN-LAST:event_chkInserirMouseClicked

    private void chkExcluirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkExcluirMouseClicked
        if (chkExcluir.isSelected()){
            chkVisualizar.setSelected(true);
        } else {
            if (!chkAlterar.isSelected() && !chkInserir.isSelected()){
                chkVisualizar.setSelected(false);
            }
        }
    }//GEN-LAST:event_chkExcluirMouseClicked

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
       // Obtém a referência da janela pai, que é o frame atual.
        Frame parentFrame = (Frame) SwingUtilities.getWindowAncestor(this);

        // Instancia o frmLog, passando o frame pai e 'true' para torná-lo modal.
        frmLog log = new frmLog(parentFrame, true);

        // Exibe a nova janela modal.
        log.setVisible(true);
        //new frmLog().setVisible(true);
    }//GEN-LAST:event_btnLogActionPerformed

    private void chkLetramaiusculaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkLetramaiusculaMouseClicked
        editarConfigsenha();
    }//GEN-LAST:event_chkLetramaiusculaMouseClicked

    private void chkCaracterespecialMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkCaracterespecialMouseClicked
        editarConfigsenha();
    }//GEN-LAST:event_chkCaracterespecialMouseClicked

    private void chkNumerosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkNumerosMouseClicked
        editarConfigsenha();
    }//GEN-LAST:event_chkNumerosMouseClicked

    private void chkMinimocaracterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkMinimocaracterMouseClicked
        editarConfigsenha();
    }//GEN-LAST:event_chkMinimocaracterMouseClicked

    private void chkLetraminusculaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkLetraminusculaMouseClicked
        editarConfigsenha();
    }//GEN-LAST:event_chkLetraminusculaMouseClicked
   
    //Limpa os campos da tabela e reseta combobox.
    private void limparCampos(){
        txtModulo.setText("");
        txtModulo.requestFocus();
        chkInserir. setSelected(false);
        chkAlterar.setSelected(false);
        chkExcluir.setSelected(false);
        chkVisualizar.setSelected(false);
    }
        
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmConfiguracao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JCheckBox chkAlterar;
    private javax.swing.JCheckBox chkCaracterespecial;
    private javax.swing.JCheckBox chkExcluir;
    private javax.swing.JCheckBox chkInserir;
    private javax.swing.JCheckBox chkLetramaiuscula;
    private javax.swing.JCheckBox chkLetraminuscula;
    private javax.swing.JCheckBox chkMinimocaracter;
    private javax.swing.JCheckBox chkNumeros;
    private javax.swing.JCheckBox chkVisualizar;
    private javax.swing.JComboBox<String> cmbPermissao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblModulo;
    private javax.swing.JLabel lblPermissao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblModulo;
    private javax.swing.JTextField txtModulo;
    // End of variables declaration//GEN-END:variables
}
