/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import dao.DAOFactory;
import dao.LogDAO;
import dao.UnidadeDAO;
import dao.UsuarioDAO;
import modelo.Unidade;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import modelo.Log;
import modelo.Usuario;





/**
 *
 * @author robson
 */
public class frmUnidade extends frmGenericomodal {
    
    UnidadeDAO unidadeDAO = DAOFactory.criarUnidadeDAO();
    DefaultTableModel modelo = null;
    private int idUnidade;
    private int linhaSelecionada;
    private String tConta;
    private String tUnidade;
    private String tTelefone;
    private String tEmail;
    private String tResponsavel;
    //private int txtResponsavel.getText();
    private int idUsuariosecao;
    private String nomeUsuariosecao;

     
    //PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
    
    // Pegue a lista de permissões
       // List<Permissao> permissoes = permissaoDAO.listar();
        
    
    /*
     * Construtor da classe frmUnidade.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmUnidade(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
        super(parent, modal);
        this.idUsuariosecao = idUsuariosecao;
        initComponents();
        initEstiloGlobal();
        // Aplica o filtro de maiúsculas ao JTextField      
        ((AbstractDocument) txtConta.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtUnidade.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtTelefone.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtEmail.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtResponsavel.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtBusca.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        
        // Define um modelo de tabela que não permite edição de células
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "CONTA", "UNIDADE", "TELEFONE", "EMAIL", "RESPONSÁVEL", "DATA"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição em todas as células
            }
        };
        tblUnidades.setModel(modelo);
        tblUnidades.setColumnSelectionAllowed(false);
        tblUnidades.isCellEditable(linhaSelecionada, 0);

        try {
            //tblUnidades.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblUnidades.getColumnModel().getColumn(0).setMaxWidth(50);
            tblUnidades.getColumnModel().getColumn(6).setMinWidth(100);
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblUnidades.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            tblUnidades.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
        } catch (Exception ex) {
            System.out.println("erro");
        }
    }

    /**
     * Preenche a tabela de usuários com os dados obtidos do banco de dados.
     * Utiliza formatação de data para exibição na tabela.
     */
    private void preencherTabela() {
        modelo.getDataVector().clear();
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        try {
            for (Unidade unidade : unidadeDAO.listar()) {
                
                String dataFormatada = "";
                if (unidade.getData() != null) {
                    dataFormatada = formatador.format(unidade.getData());
                }
            
                modelo.addRow(new Object[]{unidade.getId(),
                                           unidade.getConta(),
                                           unidade.getUnidade(),
                                           unidade.getTelefone(),
                                           unidade.getEmail(),
                                           unidade.getResponsavel(),
                                           dataFormatada});
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
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
        txtConta = new javax.swing.JTextField();
        lblConta = new javax.swing.JLabel();
        txtUnidade = new javax.swing.JTextField();
        lblUnidade = new javax.swing.JLabel();
        txtTelefone = new javax.swing.JTextField();
        lblTelefone = new javax.swing.JLabel();
        lblResponsavel = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblPesquisa = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtResponsavel = new javax.swing.JTextField();
        panInferior = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUnidades = new javax.swing.JTable();
        btnInserir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EstoqControl - Usuários");
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

        txtConta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtContaKeyPressed(evt);
            }
        });

        lblConta.setBackground(new java.awt.Color(51, 51, 51));
        lblConta.setText("CONTA*");

        txtUnidade.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUnidadeKeyPressed(evt);
            }
        });

        lblUnidade.setText("UNIDADE*");

        txtTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTelefoneKeyPressed(evt);
            }
        });

        lblTelefone.setText("TELEFONE*");

        lblResponsavel.setText("RESPONSÁVEL*");

        lblEmail.setText("EMAIL*");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("UNIDADES");

        lblPesquisa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPesquisa.setText("Digite o nome:");

        txtBusca.setToolTipText("Digite o que deseja pesquisar...");

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout panSuperiorLayout = new javax.swing.GroupLayout(panSuperior);
        panSuperior.setLayout(panSuperiorLayout);
        panSuperiorLayout.setHorizontalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblConta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblResponsavel, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                            .addComponent(txtResponsavel)))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisa))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConta)
                    .addComponent(lblUnidade)
                    .addComponent(lblTelefone)
                    .addComponent(lblResponsavel)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtConta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtResponsavel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblUnidades.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CONTA", "UNIDADE", "TELEFONE", "EMAIL", "RESPONSÁVEL", "DATA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUnidades.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUnidades.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUnidadesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUnidades);
        if (tblUnidades.getColumnModel().getColumnCount() > 0) {
            tblUnidades.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        btnInserir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/salve-.png"))); // NOI18N
        btnInserir.setText("Inserir");
        btnInserir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInserir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/editar-codigo.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btnEditarFocusGained(evt);
            }
        });
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnApagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/lixo.png"))); // NOI18N
        btnApagar.setText("Excluir");
        btnApagar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnApagar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/sair.png"))); // NOI18N
        btnVoltar.setText("Sair");
        btnVoltar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVoltar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        btnLimpar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/limpar-limpo.png"))); // NOI18N
        btnLimpar.setText("Limpar");
        btnLimpar.setToolTipText("Limpa os campos acima.");
        btnLimpar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLimpar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panInferiorLayout = new javax.swing.GroupLayout(panInferior);
        panInferior.setLayout(panInferiorLayout);
        panInferiorLayout.setHorizontalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInferiorLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panInferiorLayout.setVerticalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnInserir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInferior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        tblUnidades.setRowSorter(sorter);
        
        // Adiciona filtro dinâmico de busca conforme digitação no campo txtBusca
        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            filtrar();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filtrar();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filtrar();
        }

        private void filtrar() {
            String texto = txtBusca.getText();
            if (texto.trim().length() == 0) {
                sorter.setRowFilter(null); // mostra todos os dados
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1)); // coluna 1 = nome do usuário
            }
        }
        });
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * Abre a tela de inserção de novo usuário.
     * Oculta a janela atual.
     */
    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
        float numero = 0;
        if (txtConta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a conta.");
            txtConta.requestFocus();
            return;
        }
        if (txtUnidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o unidade.");
            txtUnidade.requestFocus();
            return;
        }
        if (txtTelefone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o telefone");
            txtTelefone.requestFocus();
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o email");
            txtEmail.requestFocus();
            return;
        }
        if (txtResponsavel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o responsável");
            txtResponsavel.requestFocus();
            return;
        }
        
        /*
        if (txtValor.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o Valor do Frete");
            return;
        } else {
            try {
                String texto = txtValor.getText().replace(",", ".");
                numero = Float.parseFloat(texto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Digite um Valor válido!");
            }
        }
        */        
        
        try {       
            Unidade unidade = new Unidade();
            unidade.setConta(txtConta.getText());
            unidade.setUnidade(txtUnidade.getText());
            unidade.setTelefone(txtTelefone.getText());
            unidade.setEmail(txtEmail.getText());
            unidade.setResponsavel(txtResponsavel.getText());
            unidade.setData(new Date());
                 
            int linha = unidadeDAO.inserir(unidade);
            if (linha > 0) {
                
                //Registra informações no log. 
                try{     

                    List<Usuario> usuarios = usuarioDAO.listar();

                    for (Usuario u : usuarios) {
                        if(u.getId() == idUsuariosecao){
                            nomeUsuariosecao = u.getNome();
                        }
                    }
                    
                    Log log = new Log();
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> inseriu uma unidade.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);
                    
                }catch (Exception e){
                    
                }
               
                JOptionPane.showMessageDialog(this, "Unidade inserida com sucesso!");
                limparCampos();
                preencherTabela(); 
            } 
        } catch (Exception ex) {    
            JOptionPane.showMessageDialog(this, "Erro ao Inserir!");
        }
    }//GEN-LAST:event_btnInserirActionPerformed

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
    private void tblUnidadesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUnidadesMouseClicked
        /*int linha = tblUnidade.getSelectedRow();
        if(linha != -1) {
            String id = tblUnidade.getValueAt(linha,0).toString();
            //System.out.println(""+id);
        }*/
        
        linhaSelecionada = tblUnidades.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Suponha que o ID esteja na primeira coluna (índice 0)
            Object idObj = tblUnidades.getValueAt(linhaSelecionada, 0);
            if (idObj != null) {
                idUnidade = Integer.parseInt(idObj.toString());
            }
        }
        
        Object idObj = tblUnidades.getValueAt(linhaSelecionada, 0);
        idUnidade = Integer.parseInt(idObj.toString());
        carregarUnidade(idUnidade);
    }//GEN-LAST:event_tblUnidadesMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblUnidades.getSelectedRow();
            System.out.println(""+linhaSelecionada);
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblUnidades.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idUnidade = Integer.parseInt(idObj.toString());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Para Editar selecione a linha desejada!");
                return;
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Para Editar selecione a linha desejada!");
            return;
        }
        
        float numero = 0;
        if (txtConta.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a conta.");
            return;
        }
        if (txtUnidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o unidade.");
            return;
        }
        if (txtTelefone.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o telefone");
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o email");
            return;
        }
        if (txtResponsavel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o resposável");
            return;
        }
        
        if (txtConta.getText().equals(tConta) && txtUnidade.getText().equals(tUnidade) && txtTelefone.getText().equals(tTelefone) && txtEmail.getText().equals(tEmail) && txtResponsavel.getText().toString().equals(tResponsavel)){
            JOptionPane.showMessageDialog(this, "Não houve alteração nos dados acima!");
            
           /* try {
                Thread.sleep(2000); // pausa de 2 segundos
            } catch (InterruptedException e) {
                e.printStackTrace(); // ou tratar de outra forma, como ignorar
            }*/
    
            return;
        }

        try {
            // Cria o objeto Unidade com os dados dos campos
            Unidade unidade = new Unidade();
            unidade.setId(idUnidade);
            unidade.setConta(txtConta.getText());
            unidade.setUnidade(txtUnidade.getText());
            unidade.setTelefone(txtTelefone.getText());
            unidade.setEmail(txtEmail.getText());
            unidade.setResponsavel(txtResponsavel.getText());
            // Chama a função editar
            UnidadeDAO unidadeDAO = DAOFactory.criarUnidadeDAO();
            int resultado = unidadeDAO.editar(unidade);

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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou uma unidade.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);
                    
                }catch (Exception e){
                    
                }
                
                JOptionPane.showMessageDialog(null, "Unidade atualizada com sucesso.");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(null, "Falha ao atualizar a unidade.");
            }
             
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex.getMessage());
        }     
    }//GEN-LAST:event_btnEditarActionPerformed

    private void restaurarSelecaoTabela(int linha) {
        //preencherTabela();
        if (linha >= 0 && linha < tblUnidades.getRowCount()) {
            tblUnidades.setRowSelectionInterval(linha, linha);
            tblUnidades.scrollRectToVisible(tblUnidades.getCellRect(linha, 0, true));
        }
    }
    /**
     * Apaga o usuário selecionado na tabela após confirmação.
     * Trata erros de integridade e exibe mensagens apropriadas.
     */
    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        try{
            linhaSelecionada = tblUnidades.getSelectedRow();
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblUnidades.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idUnidade = Integer.parseInt(idObj.toString());
                }
            }
        
            Object idObj = tblUnidades.getValueAt(linhaSelecionada, 0);
            idUnidade = Integer.parseInt(idObj.toString());
            
            Object[] opcao = {"Não", "Sim"};
            int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja apagar este registro?", "Aviso",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
            if (opcaoSelecionada == 1) {
                try {
                UnidadeDAO dao = DAOFactory.criarUnidadeDAO();
                    int resultado = dao.apagar(idUnidade);

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
                            log.setAcao("Usuário: <"+nomeUsuariosecao+"> excluiu uma unidade.");
                            log.setData(new Date());

                            int linha2 = logDAO.inserir(log);

                        }catch (Exception e){

                        }
                        
                        JOptionPane.showMessageDialog(null, "Unidade excluída com sucesso.");
                        preencherTabela(); // Atualiza a tabela após exclusão
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível excluir a unidade.");
                    }

                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(null, "Não é possível excluir: a unidade está vinculada a outros registros.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao excluir a unidade: " + ex.getMessage());
                }
                return;
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Para Apagar selecione a linha desejada!");
        }
    }//GEN-LAST:event_btnApagarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        limparCampos();
    }//GEN-LAST:event_btnLimparActionPerformed

    private void txtContaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtContaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtUnidade.requestFocus();
    }//GEN-LAST:event_txtContaKeyPressed

    private void txtUnidadeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnidadeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtTelefone.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUnidadeKeyPressed

    private void txtTelefoneKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTelefoneKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtEmail.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtTelefoneKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        if (!txtConta.getText().isEmpty())
            restaurarSelecaoTabela(linhaSelecionada);
    }//GEN-LAST:event_btnEditarFocusGained

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtResponsavel.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void limparCampos(){
        txtConta.setText("");
        txtUnidade.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtResponsavel.setText("");
        txtConta.requestFocus();
    }
    
    private void carregarUnidade(int id) {
        UnidadeDAO unidadeDAO = DAOFactory.criarUnidadeDAO();
        Unidade unidade = unidadeDAO.listar(id); // chama a função que você forneceu
           
        if (unidade != null) {
            txtConta.setText(unidade.getConta());
            txtUnidade.setText(unidade.getUnidade());
            txtTelefone.setText(unidade.getTelefone());
            txtEmail.setText(unidade.getEmail());
            txtResponsavel.setText(unidade.getResponsavel());
            
            tConta = txtConta.getText();
            tUnidade = txtUnidade.getText();
            tTelefone = txtTelefone.getText();
            tEmail = txtEmail.getText();
            tResponsavel = txtResponsavel.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Unidade não encontrada.");
        }
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
            java.util.logging.Logger.getLogger(frmUnidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmUnidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmUnidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmUnidade.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmUnidade().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConta;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblResponsavel;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUnidade;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblUnidades;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtConta;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtResponsavel;
    private javax.swing.JTextField txtTelefone;
    private javax.swing.JTextField txtUnidade;
    // End of variables declaration//GEN-END:variables
}
