/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import static aplicacao.Validadores.isValidCelular;
import static aplicacao.Validadores.isValidEmail;
import static aplicacao.Validadores.isValidCpf;
import dao.DAOFactory;
import dao.ModuloDAO;
import dao.LogDAO;
import dao.PermissaoDAO;
import dao.PermissaomoduloDAO;
import dao.UsuarioDAO;
import modelo.Usuario;
import java.awt.event.KeyEvent;
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
import javax.swing.text.AbstractDocument;
import modelo.Modulo;
import modelo.Permissao;
import modelo.Log;
import modelo.Permissaomodulo;


/**
 *
 * @author robson
 */
public class frmUsuario extends frmGenericomodal {
    
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
    //LogDAO logDAO = DAOFactory.criarLogDAO();
    DefaultTableModel modelo = null;
    private int idUsuario;
    private int linhaSelecionada;
    private String tNome;
    private String tUsuario;
    private String tEmail;
    private String tCelular;
    private int idPermissaosecao;
    private String tSenha;
    private int idPermissao;
    private String bPermissao;
    private int qtdModulos = 0;
    private int idUsuariosecao;
    private String nomeUsuariosecao;
    
    PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
    ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
    PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
    
    
    
    // Pegue a lista de permissões
    List<Permissao> permissoes = permissaoDAO.listar();    
    
    /*
     * Construtor da classe frmUsuario.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmUsuario(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
        super(parent, modal);
        this.idUsuariosecao = idUsuariosecao;
        this.idPermissaosecao = idPermissaosecao;
        initComponents();
        initEstiloGlobal();
        // Aplica o filtro de maiúsculas ao JTextField      
        ((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtUsuario.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtEmail.getDocument()).setDocumentFilter(new LowercaseDocumentFilter());
        ((AbstractDocument) txtBusca.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        
        
        // Define um modelo de tabela que não permite edição de células
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID*", "NOME*", "CPF*", "USUÁRIO*", "EMAIL*", "CELULAR*", "PERMISSÃO*", "DATA*"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição em todas as células
            }
        };
        tblUsuario.setModel(modelo);
        tblUsuario.setColumnSelectionAllowed(false);
        tblUsuario.isCellEditable(linhaSelecionada, 0);

        try {
            //tblUsuario.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblUsuario.getColumnModel().getColumn(0).setMaxWidth(50);
            tblUsuario.getColumnModel().getColumn(6).setMinWidth(100);
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblUsuario.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            tblUsuario.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
        } catch (Exception ex) {
            System.out.println("erro");
        }
        

        // Pegue a lista de permissões
        //List<Permissao> permissoes = permissaoDAO.listar();
        
        //Limpa combobox
        cmbPermissao.removeAllItems();
        
        for (Permissao p : permissoes) {
            String permissao = p.getNome();
            cmbPermissao.addItem(permissao);
        }
        
        
        // Pegue a lista de permissões
        List<Modulo> modulos = moduloDAO.listar();
        
        for (Modulo m : modulos) {
            qtdModulos++;// = qtdPermissoes++;
            
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
            for (Usuario usuario : usuarioDAO.listar()) {
                String dataFormatada = "";
                if (usuario.getData() != null) {
                    dataFormatada = formatador.format(usuario.getData());
                }
            
                modelo.addRow(new Object[]{usuario.getId(),
                                           usuario.getNome(),
                                           usuario.getCpf(),
                                           usuario.getUsuario(),
                                           usuario.getEmail(),
                                           usuario.getCelular(),
                                           usuario.getPermissao().getNome(),
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
        Permissaomodulo permissaoUsuario = permissoesPorModulo.get("USUÁRIO");
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
        txtUsuario = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblEmail = new javax.swing.JLabel();
        lblFuncao = new javax.swing.JLabel();
        lblCelular = new javax.swing.JLabel();
        lblPesquisa = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        lblSenha = new javax.swing.JLabel();
        txtCelular = new javax.swing.JFormattedTextField();
        txtSenha = new javax.swing.JPasswordField();
        cmbPermissao = new javax.swing.JComboBox<>();
        lblTitulo = new javax.swing.JLabel();
        txtCpf = new javax.swing.JFormattedTextField();
        lblCpf = new javax.swing.JLabel();
        cmbFiltro = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panInferior = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuario = new javax.swing.JTable();
        btnInserir = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnApagar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();
        btnLimpar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Patrim - Usuários");
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

        txtNome.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNomeKeyPressed(evt);
            }
        });

        lblNome.setBackground(new java.awt.Color(51, 51, 51));
        lblNome.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNome.setText("NOME*");

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });

        lblUsuario.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblUsuario.setText("USUARIO*");

        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtEmailKeyPressed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblEmail.setText("EMAIL*");

        lblFuncao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblFuncao.setText("PERMISSÃO*");

        lblCelular.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCelular.setText("CELULAR*");

        lblPesquisa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblPesquisa.setText("Buscar Nome");

        txtBusca.setToolTipText("Digite o que deseja pesquisar...");

        lblSenha.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSenha.setText("SENHA*");

        try {
            txtCelular.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCelular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCelularFocusLost(evt);
            }
        });
        txtCelular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCelularKeyPressed(evt);
            }
        });

        txtSenha.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSenhaMouseClicked(evt);
            }
        });

        cmbPermissao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbPermissaoKeyPressed(evt);
            }
        });

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("USUÁRIOS");

        try {
            txtCpf.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("###.###.###-##")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        txtCpf.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCpfFocusLost(evt);
            }
        });
        txtCpf.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCpfMouseClicked(evt);
            }
        });
        txtCpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCpfKeyPressed(evt);
            }
        });

        lblCpf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCpf.setText("CPF*");

        cmbFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOME", "CPF", "USUÁRIO", "EMAIL" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("FILTRO:");

        javax.swing.GroupLayout panSuperiorLayout = new javax.swing.GroupLayout(panSuperior);
        panSuperior.setLayout(panSuperiorLayout);
        panSuperiorLayout.setHorizontalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addComponent(lblNome, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addComponent(lblFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(cmbPermissao, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(309, 309, 309))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPesquisa)
                    .addComponent(jLabel1))
                .addGap(1, 1, 1)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(lblUsuario)
                    .addComponent(lblEmail)
                    .addComponent(lblFuncao)
                    .addComponent(lblCelular)
                    .addComponent(lblSenha)
                    .addComponent(lblCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbPermissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tblUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "NOME", "CPF", "USUÁRIO", "EMAIL", "CELULAR", "PERMISSÃO", "DATA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUsuario.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuario);
        if (tblUsuario.getColumnModel().getColumnCount() > 0) {
            tblUsuario.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        btnInserir.setBackground(new java.awt.Color(0, 153, 153));
        btnInserir.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnInserir.setForeground(java.awt.Color.white);
        btnInserir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/salve-.png"))); // NOI18N
        btnInserir.setText("Inserir");
        btnInserir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInserir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInserir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInserirActionPerformed(evt);
            }
        });

        btnEditar.setBackground(new java.awt.Color(255, 51, 0));
        btnEditar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnEditar.setForeground(java.awt.Color.white);
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

        btnApagar.setBackground(new java.awt.Color(0, 102, 153));
        btnApagar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnApagar.setForeground(java.awt.Color.white);
        btnApagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/lixo.png"))); // NOI18N
        btnApagar.setText("Excluir");
        btnApagar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnApagar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnApagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarActionPerformed(evt);
            }
        });

        btnVoltar.setBackground(new java.awt.Color(255, 0, 51));
        btnVoltar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnVoltar.setForeground(java.awt.Color.white);
        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/recurso/sair.png"))); // NOI18N
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
        btnLimpar.setForeground(java.awt.Color.white);
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
                .addGroup(panInferiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panInferiorLayout.createSequentialGroup()
                        .addGap(34, 290, Short.MAX_VALUE)
                        .addComponent(btnInserir, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnApagar, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVoltar, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panInferiorLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
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
            .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 823, Short.MAX_VALUE)
            .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        tblUsuario.setRowSorter(sorter);
        
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
            String coluna = cmbFiltro.getSelectedItem().toString();
            int nColuna;
            switch (coluna){
                case "NOME": 
                    nColuna = 1;
                    break;
                case "CPF":
                    nColuna = 2;
                    break;
                case "USUÁRIO":
                    nColuna = 3;
                    break;
                case "EMAIL":
                    nColuna = 4;
                    break;
                default:
                    nColuna = 1;
                    break;
            }
            String texto = txtBusca.getText();
            if (texto.trim().length() == 0) {
                sorter.setRowFilter(null); // mostra todos os dados
            } else {
                sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, nColuna)); // coluna 1 = nome do usuário
            }
        }
        });
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * Abre a tela de inserção de novo usuário.
     * Oculta a janela atual.
     */
    private void btnInserirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInserirActionPerformed
       // float numero = 0;
        
        //Verifica se há campo vazio.
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.");
            txtNome.requestFocus();
            return;
        }
        if (txtCpf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF.");
            txtCpf.requestFocus();
            return;
        }
        if (txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o usuario.");
            txtUsuario.requestFocus();
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o email");
            txtEmail.requestFocus();
            return;
        }
        if (txtCelular.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o celular");
            txtCelular.requestFocus();
            return;
        }
        if (cmbPermissao.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a permissão");
            cmbPermissao.requestFocus();
            return;
        }
        if (txtSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a senha");
            txtSenha.requestFocus();
        }
        
        for (Permissao p : permissoes) {
            if (p.getNome().equalsIgnoreCase(cmbPermissao.getSelectedItem().toString())) {
                idPermissao = p.getId();
                break; // achou, pode parar
            }
        }
        
        try {    
            
            Permissao permissao = new Permissao();
            permissao.setId(idPermissao);
            //equipamento.setNome(rset.getString("nome_equipamento"));
            
            Usuario usuario = new Usuario();
            usuario.setNome(txtNome.getText());
            usuario.setCpf(txtCpf.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setCelular(txtCelular.getText());
            usuario.setPermissao(permissao);
            usuario.setSenha(Seguranca.hashSenha(txtSenha.getText()));
            usuario.setAltersenha(1);
            usuario.setData(new Date());
                 
            if (!isValidCpf(txtCpf.getText())){
                JOptionPane.showMessageDialog(this, "CPF Inválido!");
                return;
            }
            
             if (!isValidEmail(txtEmail.getText())){
                JOptionPane.showMessageDialog(this, "Email Inválido!");
                return;
            }
             
             if (!isValidCelular(txtCelular.getText())){
                JOptionPane.showMessageDialog(this, "Celular Inválido!");
                return;
            }
            
            int linha = usuarioDAO.inserir(usuario);
            
            
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
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> inseriu um usuário.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);
                    
                }catch (Exception e){
                    
                }
                
                JOptionPane.showMessageDialog(this, "Registro inserido com sucesso!");
                
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
    private void tblUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuarioMouseClicked
        linhaSelecionada = tblUsuario.getSelectedRow();
        String Nome="";
        String Cpf="";
        String Usuario="";
        String Email="";
        String Celular="";
        String Permissao="";
        String Senha="";
        
        if(linhaSelecionada != -1) {
            Nome = tblUsuario.getValueAt(linhaSelecionada, 1).toString();
            Cpf = tblUsuario.getValueAt(linhaSelecionada, 2).toString();
            Usuario = tblUsuario.getValueAt(linhaSelecionada, 3).toString();
            Email = tblUsuario.getValueAt(linhaSelecionada, 4).toString();
            Celular = tblUsuario.getValueAt(linhaSelecionada, 5).toString();
            Permissao = tblUsuario.getValueAt(linhaSelecionada, 6).toString();
            Senha = tblUsuario.getValueAt(linhaSelecionada, 7).toString();
            
        }
        
        txtNome.setText(Nome);
        txtCpf.setText(Cpf);
        txtUsuario.setText(Usuario);
        txtEmail.setText(Email);
        txtCelular.setText(Celular);
   
        for (int i = 0;i < cmbPermissao.getItemCount(); i++) {
            Object item = cmbPermissao.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Permissao)) {
                cmbPermissao.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        txtSenha.setText(Senha);
        
        carregarUsuario(idUsuario);
    }//GEN-LAST:event_tblUsuarioMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblUsuario.getSelectedRow();
            
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblUsuario.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idUsuario = Integer.parseInt(idObj.toString());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Para Editar selecione a linha desejada!");
                return;
            }
        }catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Para Editar selecione a linha desejada!");
            return;
        }
        
        //Verifica se há campo vazio.
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.");
            txtNome.requestFocus();
            return;
        }
        if (txtCpf.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF.");
            txtCpf.requestFocus();
            return;
        }
        if (txtUsuario.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o usuario.");
            txtUsuario.requestFocus();
            return;
        }
        if (txtEmail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o email");
            txtEmail.requestFocus();
            return;
        }
        if (txtCelular.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o celular");
            txtCelular.requestFocus();
            return;
        }
        if (cmbPermissao.getSelectedItem().toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a permissão");
            cmbPermissao.requestFocus();
            return;
        }
        if (txtSenha.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a senha");
            txtSenha.requestFocus();
        }
        
        if (txtNome.getText().equals(tNome) && txtUsuario.getText().equals(tUsuario) && txtEmail.getText().equals(tEmail) && txtCelular.getText().equals(tCelular) && cmbPermissao.getSelectedItem().toString().equalsIgnoreCase(bPermissao) && txtSenha.getText().equals(tSenha)){
            JOptionPane.showMessageDialog(this, "Não houve alteração nos dados acima!");
    
            return;
        }
        
        for (Permissao p : permissoes) {
            if (p.getNome().equalsIgnoreCase(cmbPermissao.getSelectedItem().toString())) {
                idPermissao = p.getId();
                break; // achou, pode parar
            }
        }
        
        try {
            
            Permissao permissao = new Permissao();
            permissao.setId(idPermissao);
            
            // Cria o objeto Usuario com os dados dos campos
            Usuario usuario = new Usuario();
            usuario.setId(idUsuario);
            usuario.setNome(txtNome.getText());
            usuario.setCpf(txtCpf.getText());
            usuario.setUsuario(txtUsuario.getText());
            usuario.setEmail(txtEmail.getText());
            usuario.setCelular(txtCelular.getText());
            usuario.setPermissao(permissao);
            usuario.setSenha(Seguranca.hashSenha(txtSenha.getText())); // Se houver campo senha
            usuario.setAltersenha(1);
            // Chama a função editar
            UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
            int resultado = usuarioDAO.editar(usuario);

            if (resultado > 0) {
                preencherTabela();
                limparCampos();
                try{     

                    List<Usuario> usuarios = usuarioDAO.listar();

                    for (Usuario u : usuarios) {
                        if(u.getId() == idUsuariosecao){
                            nomeUsuariosecao = u.getNome();
                        }
                    }
                    
                    Log log = new Log();
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> editou um usuário.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);
                    
                }catch (Exception e){
                    
                }
                
                JOptionPane.showMessageDialog(null, "Registro atualizado com sucesso.");
                
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
        if (linha >= 0 && linha < tblUsuario.getRowCount()) {
            tblUsuario.setRowSelectionInterval(linha, linha);
            tblUsuario.scrollRectToVisible(tblUsuario.getCellRect(linha, 0, true));
        }
    }
    /**
     * Apaga o usuário selecionado na tabela após confirmação.
     * Trata erros de integridade e exibe mensagens apropriadas.
     */
    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        try{
            linhaSelecionada = tblUsuario.getSelectedRow();
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblUsuario.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    int idUsuario = Integer.parseInt(idObj.toString());
                }
            }
        
            Object idObj = tblUsuario.getValueAt(linhaSelecionada, 0);
            int idUsuario = Integer.parseInt(idObj.toString());
            
            Object[] opcao = {"Não", "Sim"};
            int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja apagar este registro?", "Aviso",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
            if (opcaoSelecionada == 1) {
                try {
                UsuarioDAO dao = DAOFactory.criarUsuarioDAO();
                    int resultado = dao.apagar(idUsuario);

                    if (resultado > 0) {
                        
                        try{     

                            List<Usuario> usuarios = usuarioDAO.listar();

                            for (Usuario u : usuarios) {
                                if(u.getId() == idUsuariosecao){
                                    nomeUsuariosecao = u.getNome();
                                }
                            }

                            Log log = new Log();
                            log.setAcao("Usuário: <"+nomeUsuariosecao+"> deletou um usuário.");
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
                    JOptionPane.showMessageDialog(null, "Não é possível excluir: o usuário está vinculado a outros registros.");
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
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtCpf.requestFocus();
    }//GEN-LAST:event_txtNomeKeyPressed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtEmail.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsuarioKeyPressed

    private void txtEmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtCelular.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailKeyPressed

    private void txtCelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCelularKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            cmbPermissao.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        if (!txtNome.getText().isEmpty())
            restaurarSelecaoTabela(linhaSelecionada);
    }//GEN-LAST:event_btnEditarFocusGained

    private void cmbPermissaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbPermissaoKeyPressed
        txtSenha.requestFocus();
    }//GEN-LAST:event_cmbPermissaoKeyPressed

    private void txtSenhaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSenhaMouseClicked
        txtSenha.selectAll();
    }//GEN-LAST:event_txtSenhaMouseClicked

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        /*if (!isValidEmail(txtEmail.getText())){
            JOptionPane.showMessageDialog(this, "E-mail inválido!.");
            //txtEmail.requestFocus();
        }*/        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailFocusLost

    private void txtCelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCelularFocusLost
        /*if (!isValidCelular(txtCelular.getText())){
            JOptionPane.showMessageDialog(this, "Celular inválido!.");
            //ftxtCelular.requestFocus();
        }*/
                // TODO add your handling code here:
    }//GEN-LAST:event_txtCelularFocusLost

    private void txtCpfKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER)
            txtUsuario.requestFocus();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfKeyPressed

    private void txtCpfFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCpfFocusLost
        /*if (!isValidCpf(txtCpf.getText())){
            JOptionPane.showMessageDialog(this, "CPF inválido!.");
            //txtCpf.requestFocus();
        } */       // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfFocusLost

    private void txtCpfMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCpfMouseClicked
        txtCpf.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfMouseClicked
   
    //Limpa os campos da tabela e reseta combobox.
    private void limparCampos(){
        txtNome.setText("");
        txtCpf.setText("");
        txtUsuario.setText("");
        txtEmail.setText("");
        txtCelular.setText("");
        cmbPermissao.setSelectedIndex(0);
        txtSenha.setText("");
        txtNome.requestFocus();
        cmbFiltro.setSelectedIndex(0);
    }
    
    
   
    private void carregarUsuario(int id) {
        UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();
        Usuario usuario = usuarioDAO.listar(id); // chama a função que você forneceu
        
       if (usuario != null) {
           
           //Capiturar o Id da Permissão salvo na tabela.
            Object Perm = tblUsuario.getValueAt(linhaSelecionada, 5);
        
            for (int i = 0;i < cmbPermissao.getItemCount(); i++) {
                String item = cmbPermissao.getItemAt(i);
                
                if (item.equalsIgnoreCase(Perm.toString())) {
                    cmbPermissao.setSelectedIndex(i);
                    break; // achou, pode parar
                }
            }
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
            java.util.logging.Logger.getLogger(frmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbFiltro;
    private javax.swing.JComboBox<String> cmbPermissao;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCelular;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFuncao;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblSenha;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblUsuario;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
