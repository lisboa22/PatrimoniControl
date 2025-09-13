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
public class frmModulo extends frmGenericomodal {
    
    ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
    DefaultTableModel modelo = null;
    private int idModulo;
    private int linhaSelecionada;
    private String tModulo;
    private int idUsuariosecao;
    private String nomeUsuariosecao;
    private int idPermissaosecao;
    
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
    PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
        
    /*
     * Construtor da classe frmUsuario.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmModulo(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
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
            new String[]{"ID", "NOME", "DATA"}
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
            //tblModulo.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblModulo.getColumnModel().getColumn(0).setMaxWidth(50);
            tblModulo.getColumnModel().getColumn(2).setMinWidth(100);
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblModulo.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            tblModulo.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
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
            for (Modulo modulo : moduloDAO.listar()) {
                
                String dataFormatada = "";
                if (modulo.getData() != null) {
                    dataFormatada = formatador.format(modulo.getData());
                }
            
                modelo.addRow(new Object[]{modulo.getId(),
                                           modulo.getNome(),
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
        Permissaomodulo permissaoUsuario = permissoesPorModulo.get("FUNCIONALIDADE");
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
        lblNome = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblPesquisa = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblModulo = new javax.swing.JTable();
        panInferior = new javax.swing.JPanel();
        btnInserir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patrim - Funções");
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

        lblNome.setBackground(new java.awt.Color(51, 51, 51));
        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNome.setText("NOME*");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("FUNCIONALIDADES");

        lblPesquisa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPesquisa.setText("Buscar Nome:");

        txtBusca.setToolTipText("Digite o que deseja pesquisar...");

        tblModulo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "NOME", "DATA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class
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
        if (tblModulo.getColumnModel().getColumnCount() > 0) {
            tblModulo.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout panSuperiorLayout = new javax.swing.GroupLayout(panSuperior);
        panSuperior.setLayout(panSuperiorLayout);
        panSuperiorLayout.setHorizontalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTitulo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPesquisa)
                .addGap(4, 4, 4)
                .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(lblNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
                .addComponent(btnInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panInferiorLayout.setVerticalGroup(
            panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
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
            .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(panSuperior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        setarPermissao();
        preencherTabela();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        tblModulo.setRowSorter(sorter);
        
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
            Modulo modulo = new Modulo();
            modulo.setNome(txtNome.getText());
            modulo.setData(new Date());
                 
            int linha = moduloDAO.inserir(modulo);
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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> inseriu um módulo.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);

                }catch (Exception e){

                }
                
                //Insere as permissões como false junto com a criação da permissão.
                try{  
                    List<Permissao> permissoes = permissaoDAO.listar();

                    int idUltimomodulo = 0 ;
                    List<Modulo> modulos = moduloDAO.listar();
                    
                    
                    if (!modulos.isEmpty()){
                        Modulo ultimoModulo = modulos.get(modulos.size() - 1);
                        idUltimomodulo = ultimoModulo.getId();
                    }
          
                    for (Permissao p : permissoes){
                        
                        Permissao permissao = new Permissao();
                        permissao.setId(p.getId());
                        
                        //Modulo modulo = new Modulo();
                        modulo.setId(idUltimomodulo);
                        
                        Permissaomodulo permissaomodulo = new Permissaomodulo();
                        
                        //permissaomodulo.setId(pm.getId());
    
                     
                        permissaomodulo.setPermissao(permissao);
                        permissaomodulo.setModulo(modulo);
                        permissaomodulo.setInserir(false);
                        permissaomodulo.setAlterar(false);
                        permissaomodulo.setExcluir(false);
                        permissaomodulo.setVisualizar(false);
                        permissaomodulo.setData(new Date());

                        int linha2 = permissaomoduloDAO.inserir(permissaomodulo);
                    }
                    
                    
                }catch (Exception e){
                    
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
    private void tblModuloMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblModuloMouseClicked
        /*int linha = tblUsuario.getSelectedRow();
        if(linha != -1) {
            String id = tblUsuario.getValueAt(linha,0).toString();
            //System.out.println(""+id);
        }*/
        
        linhaSelecionada = tblModulo.getSelectedRow();
        if (linhaSelecionada != -1) {
            // Suponha que o ID esteja na primeira coluna (índice 0)
            Object idObj = tblModulo.getValueAt(linhaSelecionada, 0);
            if (idObj != null) {
                idModulo = Integer.parseInt(idObj.toString());
            }
        }
        
        /*Object idObj = tblModulo.getValueAt(linhaSelecionada, 0);
        idModulo = Integer.parseInt(idObj.toString());*/
        
        carregarModulo(idModulo);
    }//GEN-LAST:event_tblModuloMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblModulo.getSelectedRow();
       
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblModulo.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idModulo = Integer.parseInt(idObj.toString());
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
        
        if (txtNome.getText().equals(tModulo)){
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
            Modulo modulo = new Modulo();
            modulo.setId(idModulo);
            modulo.setNome(txtNome.getText());
            
            // Chama a função editar
            ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
            int resultado = moduloDAO.editar(modulo);

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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou um módulo.");
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
        if (linha >= 0 && linha < tblModulo.getRowCount()) {
            tblModulo.setRowSelectionInterval(linha, linha);
            tblModulo.scrollRectToVisible(tblModulo.getCellRect(linha, 0, true));
        }
    }
    /**
     * Apaga o usuário selecionado na tabela após confirmação.
     * Trata erros de integridade e exibe mensagens apropriadas.
     */
    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        try{
            linhaSelecionada = tblModulo.getSelectedRow();
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblModulo.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idModulo = Integer.parseInt(idObj.toString());
                }
            }
        
            Object idObj = tblModulo.getValueAt(linhaSelecionada, 0);
            int idModulo = Integer.parseInt(idObj.toString());
            
            Object[] opcao = {"Não", "Sim"};
            int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja apagar este registro?", "Aviso",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
            if (opcaoSelecionada == 1) {
                System.out.println(idModulo);
                try{ 
                    PermissaomoduloDAO del = DAOFactory.criarPermissaomoduloDAO();
                    int apagar = del.apagarModulo(idModulo);
                }catch (Exception e){

                }
                
                try {
                ModuloDAO dao = DAOFactory.criarModuloDAO();
                    int resultado = dao.apagar(idModulo);

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
                            log.setAcao("Usuário: <"+nomeUsuariosecao+"> excluiu um módulo.");
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
                    JOptionPane.showMessageDialog(null, "Não é possível excluir: Este registro está vinculado a outros registros.");
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
    
    private void carregarModulo(int id) {
        ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
        Modulo modulo = moduloDAO.listar(id); // chama a função que você forneceu
        
        if (modulo != null) {
            txtNome.setText(modulo.getNome());
            
            tModulo = txtNome.getText();
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
            java.util.logging.Logger.getLogger(frmModulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmModulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmModulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmModulo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmModulo().setVisible(true);
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
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblModulo;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtNome;
    // End of variables declaration//GEN-END:variables
}
