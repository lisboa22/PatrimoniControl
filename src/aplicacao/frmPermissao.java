/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import dao.DAOFactory;
import dao.LogDAO;
import dao.ModuloDAO;
import dao.PermissaoDAO;
import dao.PermissaomoduloDAO;
import dao.UsuarioDAO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import modelo.Log;
import modelo.Modulo;
import modelo.Permissao;
import modelo.Permissaomodulo;
import modelo.Usuario;





/**
 *
 * @author robson
 */
public class frmPermissao extends frmGenericomodal {
    
    PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
    ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
    PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
    DefaultTableModel modelo = null;
    private int idPermissao;
    private int linhaSelecionada;
    private String tPermissao;
    private int idUsuariosecao;
    private String nomeUsuariosecao;
    private int idPermissaosecao;
    
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
        
    /*
     * Construtor da classe frmUsuario.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmPermissao(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
        super(parent, modal);
        this.idUsuariosecao = idUsuariosecao;
        this.idPermissaosecao = idPermissaosecao;
        initComponents();
        initEstiloGlobal();
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtBusca.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        
        // Define um modelo de tabela que não permite edição de células
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "PERMISSÃO", "DATA"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição em todas as células
            }
        };
        tblPermissao.setModel(modelo);
        tblPermissao.setColumnSelectionAllowed(false);
        tblPermissao.isCellEditable(linhaSelecionada, 0);

        try {
            //tblPermissao.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblPermissao.getColumnModel().getColumn(0).setMaxWidth(50);
            tblPermissao.getColumnModel().getColumn(2).setMinWidth(100);
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblPermissao.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            tblPermissao.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
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
            for (Permissao permissao : permissaoDAO.listar()) {
                
                String dataFormatada = "";
                if (permissao.getData() != null) {
                    dataFormatada = formatador.format(permissao.getData());
                }
            
                modelo.addRow(new Object[]{permissao.getId(),
                                           permissao.getNome(),
                                           dataFormatada});
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        btnInserir.setEnabled(false);
        btnEditar.setEnabled(false);
        btnApagar.setEnabled(false);
                
        // 5. Verifique as permissões para o módulo "USUÁRIO" e habilite os botões
        Permissaomodulo permissaoUsuario = permissoesPorModulo.get("PERMISSÃO");
        if (permissaoUsuario != null) {
            btnInserir.setEnabled(permissaoUsuario.isInserir());
            btnEditar.setEnabled(permissaoUsuario.isAlterar());
            btnApagar.setEnabled(permissaoUsuario.isExcluir());
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
        txtNome = new javax.swing.JTextField();
        lblPermissao = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblPesquisa = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        panInferior = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPermissao = new javax.swing.JTable();
        btnInserir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patrim - Permissões");
        setBackground(new java.awt.Color(204, 204, 204));
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

        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        lblPermissao.setBackground(new java.awt.Color(51, 51, 51));
        lblPermissao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblPermissao.setText("PERMISSÃO*");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("PERMISSÕES");

        lblPesquisa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPesquisa.setText("Buscar Permissão");

        txtBusca.setToolTipText("Digite o que deseja pesquisar...");

        javax.swing.GroupLayout panSuperiorLayout = new javax.swing.GroupLayout(panSuperior);
        panSuperior.setLayout(panSuperiorLayout);
        panSuperiorLayout.setHorizontalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblPermissao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tblPermissao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "PERMISSÃO", "DATA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblPermissao.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPermissao.setShowGrid(false);
        tblPermissao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPermissaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblPermissao);
        if (tblPermissao.getColumnModel().getColumnCount() > 0) {
            tblPermissao.getColumnModel().getColumn(0).setMaxWidth(50);
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
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panInferiorLayout.createSequentialGroup()
                        .addComponent(btnInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(9, 9, 9))
        );
        panInferiorLayout.setVerticalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnApagar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimpar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnVoltar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnInserir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(75, 75, 75))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panInferior, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        setarPermissao();
        preencherTabela();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblPermissao.setRowSorter(sorter);
        
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
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a permissão.");
            txtNome.requestFocus();
            return;
        }
               
        try {       
            Permissao permissao = new Permissao();
            permissao.setNome(txtNome.getText());
            permissao.setData(new Date());
                 
            int linha = permissaoDAO.inserir(permissao);
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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> inseriu uma permissão.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);

                }catch (Exception e){

                }
                
                //Insere as permissões como false junto com a criação da permissão.
                try{     
                    int idUltimapermissao = 0 ;
                    List<Permissao> permissoes = permissaoDAO.listar();
                    
                    if (!permissoes.isEmpty()){
                        Permissao ultimaPermissao = permissoes.get(permissoes.size() - 1);
                        idUltimapermissao = ultimaPermissao.getId();
                    }
                    // Pegue a lista de permissões
                    List<Modulo> modulos = moduloDAO.listar();

                    for (Modulo m : modulos) {
                        // Cria o objeto Permissao e define o ID
                        //Permissao permissao = new Permissao();
                        permissao.setId(idUltimapermissao);
                        
                        Modulo modulo = new Modulo();
                        modulo.setId(m.getId());
    
                        Permissaomodulo permissaomodulo = new Permissaomodulo();
                        permissaomodulo.setPermissao(permissao);
                        permissaomodulo.setModulo(modulo);
                        permissaomodulo.setInserir(false);
                        permissaomodulo.setAlterar(false);
                        permissaomodulo.setExcluir(false);
                        permissaomodulo.setVisualizar(false);
                        permissaomodulo.setData(new Date());

                        int linha2 = permissaomoduloDAO.inserir(permissaomodulo);
                    }
                    
                } catch (Exception e){
                    
                }
                
                preencherTabela();
                JOptionPane.showMessageDialog(this, "Registro inserido com sucesso!");
                limparCampos();
                 
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
    private void tblPermissaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPermissaoMouseClicked
        /*int linha = tblUsuario.getSelectedRow();
        if(linha != -1) {
            String id = tblUsuario.getValueAt(linha,0).toString();
            //System.out.println(""+id);
        }*/
        
        linhaSelecionada = tblPermissao.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Suponha que o ID esteja na primeira coluna (índice 0)
            Object idObj = tblPermissao.getValueAt(linhaSelecionada, 0);
            if (idObj != null) {
                idPermissao = Integer.parseInt(idObj.toString());
            }
        }
        
        /*Object idObj = tblPermissao.getValueAt(linhaSelecionada, 0);
        idPermissao = Integer.parseInt(idObj.toString());*/
        
        carregarPermissao(idPermissao);
    }//GEN-LAST:event_tblPermissaoMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblPermissao.getSelectedRow();
       
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblPermissao.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idPermissao = Integer.parseInt(idObj.toString());
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
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.");
            return;
        }
        
        if (txtNome.getText().equals(tPermissao)){
            JOptionPane.showMessageDialog(this, "Não houve alteração nos dados acima!");
            
           /* try {
                Thread.sleep(2000); // pausa de 2 segundos
            } catch (InterruptedException e) {
                e.printStackTrace(); // ou tratar de outra forma, como ignorar
            }*/
    
            return;
        }

        try {
            // Cria o objeto Usuario com os dados dos campos
            Permissao permissao = new Permissao();
            permissao.setId(idPermissao);
            permissao.setNome(txtNome.getText());
            
            // Chama a função editar
            PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
            int resultado = permissaoDAO.editar(permissao);

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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou uma permissão.");
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

    private void restaurarSelecaoTabela(int linha) {
        //preencherTabela();
        if (linha >= 0 && linha < tblPermissao.getRowCount()) {
            tblPermissao.setRowSelectionInterval(linha, linha);
            tblPermissao.scrollRectToVisible(tblPermissao.getCellRect(linha, 0, true));
        }
    }
    /**
     * Apaga o usuário selecionado na tabela após confirmação.
     * Trata erros de integridade e exibe mensagens apropriadas.
     */
    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        try{
            linhaSelecionada = tblPermissao.getSelectedRow();
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblPermissao.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idPermissao = Integer.parseInt(idObj.toString());
                }
            }
        
            Object idObj = tblPermissao.getValueAt(linhaSelecionada, 0);
            int idPermissao = Integer.parseInt(idObj.toString());
            
            Object[] opcao = {"Não", "Sim"};
            int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja apagar este registro?", "Aviso",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
            if (opcaoSelecionada == 1) {
                try {
                    
                    if (idPermissao != 1){
                        try{ 
                            PermissaomoduloDAO del = DAOFactory.criarPermissaomoduloDAO();
                            int apagar = del.apagar(idPermissao);
                        }catch (Exception e){

                        }
                    }
                    
                    PermissaoDAO dao = DAOFactory.criarPermissaoDAO();
                    int resultado = dao.apagar(idPermissao);

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
                            log.setAcao("Usuário: <"+nomeUsuariosecao+"> excluiu uma permissão.");
                            log.setData(new Date());

                            int linha2 = logDAO.inserir(log);

                        }catch (Exception e){

                        }
                        
                        JOptionPane.showMessageDialog(null, "Registro excluído com sucesso.");
                        preencherTabela(); // Atualiza a tabela após exclusão
                        limparCampos();
                    } else {
                        JOptionPane.showMessageDialog(null, "Não foi possível excluir o registro.");
                    }

                } catch (SQLIntegrityConstraintViolationException ex) {
                    JOptionPane.showMessageDialog(null, "Não é possível excluir: Este registro está vinculado a algum usuário.");
                    ex.printStackTrace();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao excluir o registro: " + ex.getMessage());
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

    private void txtNomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyPressed
        
    }//GEN-LAST:event_txtNomeKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        if (!txtNome.getText().isEmpty())
            restaurarSelecaoTabela(linhaSelecionada);
    }//GEN-LAST:event_btnEditarFocusGained

    private void limparCampos(){
        txtNome.setText("");
        txtNome.requestFocus();
    }
    
    private void carregarPermissao(int id) {
        PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
        Permissao permissao = permissaoDAO.listar(id); // chama a função que você forneceu
        
        if (permissao != null) {
            txtNome.setText(permissao.getNome());
            
            tPermissao = txtNome.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Registro não encontrado.");
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
            java.util.logging.Logger.getLogger(frmPermissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPermissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPermissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPermissao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmPermissao().setVisible(true);
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
    private javax.swing.JLabel lblPermissao;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblPermissao;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
