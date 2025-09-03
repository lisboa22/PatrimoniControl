/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplicacao;

import static aplicacao.Validadores.isValidCelular;
import static aplicacao.Validadores.isValidEmail;
import dao.DAOFactory;
import dao.EquipamentoDAO;
import dao.ModuloDAO;
import dao.LogDAO;
import dao.PermissaoDAO;
import dao.PermissaomoduloDAO;
import dao.MovimentacaoDAO;
import dao.UnidadeDAO;
import dao.UsuarioDAO;
import modelo.Movimentacao;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import modelo.Equipamento;
import modelo.Modulo;
import modelo.Permissao;
import modelo.Log;
import modelo.Permissaomodulo;
import modelo.Unidade;
import modelo.Usuario;


/**
 *
 * @author robson
 */
public class frmMovimentacao extends frmGenericomodal {
    
    MovimentacaoDAO movimentacaoDAO = DAOFactory.criarMovimentacaoDAO();
    //LogDAO logDAO = DAOFactory.criarLogDAO();
    DefaultTableModel modelo = null;
    private int idMovimentacao;
    private int linhaSelecionada;
    private String tNome;
    private String tMovimentacao;
    private String tEmail;
    private String tCelular;
    private int tidPermissao;
    private String tSenha;
    private int idPermissao;
    private int idEquipamento;
    private String nomeEquipamento;
    private int idOrigem;
    private int idDestino;
    private int idUsuarioliberacao;
    private int idUsuariorecepcao;
    private String bEquipamento;
    private String bMovimentacao;
    private String bOrigem;
    private String bDestino;
    private int qtdModulos = 0;
    private int idUsuariosecao;
    private String nomeUsuariosecao;
    private Equipamento sEquipamento;
    private int idPermissaosecao;

    PermissaoDAO permissaoDAO = DAOFactory.criarPermissaoDAO();
    ModuloDAO moduloDAO = DAOFactory.criarModuloDAO();
    PermissaomoduloDAO permissaomoduloDAO = DAOFactory.criarPermissaomoduloDAO();
    LogDAO logDAO = DAOFactory.criarLogDAO();
    EquipamentoDAO equipamentoDAO = DAOFactory.criarEquipamentoDAO();
    UnidadeDAO unidadeDAO = DAOFactory.criarUnidadeDAO();
    UsuarioDAO usuarioDAO = DAOFactory.criarUsuarioDAO();

    // Pegue a lista de permissões
    List<Permissao> permissoes = permissaoDAO.listar();    
    List<Movimentacao> movimentacoes = movimentacaoDAO.listar(); 
    
    /*
     * Construtor da classe frmMovimentacao.
     * Inicializa os componentes da interface e configura a tabela de usuários.
     */
    public frmMovimentacao(java.awt.Frame parent, boolean modal, int idUsuariosecao, int idPermissaosecao) {
        super(parent, modal);
        this.idUsuariosecao = idUsuariosecao;
        this.idPermissaosecao = idPermissaosecao;
        initComponents();
        initEstiloGlobal();
        carregarCombo();
        // Aplica o filtro de maiúsculas ao JTextField      
        /*((AbstractDocument) txtNome.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtNumserie.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
        ((AbstractDocument) txtEmail.getDocument()).setDocumentFilter(new LowercaseDocumentFilter());
        ((AbstractDocument) txtBusca.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());*/
        
        // Define um modelo de tabela que não permite edição de células
        modelo = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "EQUIPAMENTO", "NÚMERO SÉRIE*", "MOVIMENTAÇÃO*", "ORIGEM*", "LIBERAÇÃO*", "DESTINO*", "RECEPÇÃO", "OBSERVAÇÕES", "DATA HORA"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Bloqueia edição em todas as células
            }
        };
        tblMovimentacao.setModel(modelo);
        tblMovimentacao.setColumnSelectionAllowed(false);
        tblMovimentacao.isCellEditable(linhaSelecionada, 0);
        

        try {
            tblMovimentacao.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
            tblMovimentacao.getColumnModel().getColumn(0).setMaxWidth(50);
            tblMovimentacao.getColumnModel().getColumn(9).setMinWidth(100);
        } catch (Exception ex) {
            System.out.println("Erro ao configurar renderizador.");
        }
        
        //Configura renderização personalizada para coluna de valores monetários
        try {
            modelo = (DefaultTableModel) tblMovimentacao.getModel();
            // Configura renderização personalizada para coluna de valores monetários
            tblMovimentacao.getColumnModel().getColumn(2).setCellRenderer(new CustomRenderer());
        } catch (Exception ex) {
            System.out.println("erro");
        }
   
    }
        
        private void carregarCombo() {
        try {
            Vector<Equipamento> equipamentos = new Vector<>(equipamentoDAO.listar());
            DefaultComboBoxModel comboModelEquipamento = new DefaultComboBoxModel(equipamentos);
            Vector<Unidade> unidades = new Vector<>(unidadeDAO.listar());
            DefaultComboBoxModel comboModelOrigem = new DefaultComboBoxModel(unidades);
            DefaultComboBoxModel comboModelDestino = new DefaultComboBoxModel(unidades);
            Vector<Usuario> usuarios = new Vector<>(usuarioDAO.listar());
            DefaultComboBoxModel comboModelOUsuarioliberacao = new DefaultComboBoxModel(usuarios);
            DefaultComboBoxModel comboModelUsuariorecepcao = new DefaultComboBoxModel(usuarios);
            cmbEquipamento.setModel(comboModelEquipamento);
            cmbOrigem.setModel(comboModelOrigem);
            cmbDestino.setModel(comboModelDestino);
            cmbUsuarioliberacao.setModel(comboModelOUsuarioliberacao);
            cmbUsuariorecepcao.setModel(comboModelUsuariorecepcao);
            /*cbDesafiante.setModel(comboModelDesafiante);
            Lutador desafiado = (Lutador) cbDesafiado.getSelectedItem();
            lblCategoriaDesafiado.setText("Categoria: " + desafiado.getCategoria());
            Lutador desafiante = (Lutador) cbDesafiante.getSelectedItem();
            lblCategoriaDesafiante.setText("Categoria: " + desafiante.getCategoria());*/
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar a lista de Lutadores");
        }

        // Pegue a lista de permissões
        //List<Permissao> permissoes = permissaoDAO.listar();
        
    }

    /**
     * Preenche a tabela de usuários com os dados obtidos do banco de dados.
     * Utiliza formatação de data para exibição na tabela.
     */
    
    private void preencherTabela() {
        modelo.getDataVector().clear();
        DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        try {
            for (Movimentacao movimentacao : movimentacaoDAO.listar()) {
                String dataFormatada = "";
                if (movimentacao.getData_hora() != null) {
                    dataFormatada = formatador.format(movimentacao.getData_hora());
                }
            
                modelo.addRow(new Object[]{ movimentacao.getId(),
                                            movimentacao.getEquipamento().getNome(),
                                            movimentacao.getNum_serie(),
                                            movimentacao.getTipo_movimentacao(),
                                            movimentacao.getOrigem().getUnidade(),
                                            movimentacao.getUsuarioliberacao().getNome(),
                                            movimentacao.getDestino().getUnidade(),
                                            movimentacao.getUsuariorecepcao().getNome(),
                                            movimentacao.getObservacoes(),
                                            dataFormatada});
                
            }
            
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(this, "A tabela está vazia!");
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
        Permissaomodulo permissaoUsuario = permissoesPorModulo.get("MOVIMENTAÇÃO");
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
        lblEquipamento = new javax.swing.JLabel();
        txtNumserie = new javax.swing.JTextField();
        lblNumserie = new javax.swing.JLabel();
        lblTipomovimentacao = new javax.swing.JLabel();
        lblUsuarioliberacao = new javax.swing.JLabel();
        lblOrigem = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblPesquisa = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        lblDestino = new javax.swing.JLabel();
        cmbUsuarioliberacao = new javax.swing.JComboBox<>();
        cmbEquipamento = new javax.swing.JComboBox<>();
        cmbTipomovimentacao = new javax.swing.JComboBox<>();
        cmbOrigem = new javax.swing.JComboBox<>();
        cmbDestino = new javax.swing.JComboBox<>();
        lblUsuariorecepcao = new javax.swing.JLabel();
        cmbUsuariorecepcao = new javax.swing.JComboBox<>();
        lblObservacao = new javax.swing.JLabel();
        txtObservacao = new javax.swing.JTextField();
        panInferior = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblMovimentacao = new javax.swing.JTable();
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
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        panSuperior.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblEquipamento.setBackground(new java.awt.Color(51, 51, 51));
        lblEquipamento.setText("EQUIPAMENTO*");

        txtNumserie.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNumserieMouseClicked(evt);
            }
        });
        txtNumserie.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumserieKeyPressed(evt);
            }
        });

        lblNumserie.setText("NÚMERO DE SÉRIE");

        lblTipomovimentacao.setText("MOVIMENTACAO*");

        lblUsuarioliberacao.setText("LIBERAÇÃO");

        lblOrigem.setText("ORIGEM*");

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitulo.setText("MOVIMENTAÇÃO");

        lblPesquisa.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblPesquisa.setText("Digite o nome:");

        txtBusca.setToolTipText("Digite o que deseja pesquisar...");

        lblDestino.setText("DESTINO");

        cmbUsuarioliberacao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUsuarioliberacaoKeyPressed(evt);
            }
        });

        cmbTipomovimentacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ENTRADA", "SAÍDA", "MANUTENÇÃO", "INSTALAÇÃO", "REMOÇÃO" }));

        lblUsuariorecepcao.setText("RECEPÇÃO");

        cmbUsuariorecepcao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbUsuariorecepcaoKeyPressed(evt);
            }
        });

        lblObservacao.setText("OBSERVAÇÃO");

        txtObservacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtObservacaoMouseClicked(evt);
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
                        .addComponent(lblPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumserie, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumserie, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipomovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbTipomovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblUsuarioliberacao, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUsuarioliberacao, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDestino, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbDestino, 0, 101, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(lblUsuariorecepcao, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbUsuariorecepcao, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(171, 171, 171))))
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 1028, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panSuperiorLayout.setVerticalGroup(
            panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panSuperiorLayout.createSequentialGroup()
                .addComponent(lblTitulo)
                .addGap(11, 11, 11)
                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addComponent(lblObservacao)
                        .addGap(28, 28, 28))
                    .addGroup(panSuperiorLayout.createSequentialGroup()
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPesquisa))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lblEquipamento)
                                    .addComponent(lblNumserie)
                                    .addComponent(lblTipomovimentacao)
                                    .addComponent(lblUsuarioliberacao)
                                    .addComponent(lblOrigem)
                                    .addComponent(lblDestino))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtNumserie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbUsuarioliberacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbTipomovimentacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbOrigem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panSuperiorLayout.createSequentialGroup()
                                .addComponent(lblUsuariorecepcao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cmbUsuariorecepcao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
        );

        jScrollPane1.setPreferredSize(new java.awt.Dimension(52, 402));

        tblMovimentacao.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "EQUIPAMENTO", "NÚMERO SÉRIE", "MOVIMENTAÇÃO", "ORIGEM", "LIBERAÇÃO", "DESTINO", "RCEPÇÃO", "OBSERVAÇÕES", "DATA HORA"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblMovimentacao.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblMovimentacao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMovimentacaoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblMovimentacao);
        if (tblMovimentacao.getColumnModel().getColumnCount() > 0) {
            tblMovimentacao.getColumnModel().getColumn(1).setMaxWidth(50);
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
            .addGroup(panInferiorLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panSuperior, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, Short.MAX_VALUE)
                    .addComponent(panInferior, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        tblMovimentacao.setRowSorter(sorter);
        
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
       // float numero = 0;
        
        //Verifica se há campo vazio.
        if (txtNumserie.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o numero de série.");
            txtNumserie.requestFocus();
            return;
        }
        
        List<Equipamento> equipamentos = equipamentoDAO.listar();
        
        for (Equipamento e : equipamentos) {
            if (e.getNome().equalsIgnoreCase(cmbEquipamento.getSelectedItem().toString())) {
                idEquipamento = e.getId();
                break; // achou, pode parar
            }
        }
        
        List<Unidade> unidades = unidadeDAO.listar();
        
        for (Unidade u : unidades) {
            if (u.getUnidade().equalsIgnoreCase(cmbOrigem.getSelectedItem().toString())) {
                idOrigem = u.getId();
                break; // achou, pode parar
            }
            
        }
        
        for (Unidade u : unidades) {
            if (u.getUnidade().equalsIgnoreCase(cmbDestino.getSelectedItem().toString())) {
                idDestino = u.getId();
                break; // achou, pode parar
            }
            
        }
        
        List<Usuario> usuarios = usuarioDAO.listar();
        
        for (Usuario us : usuarios) {
            
            if (us.getNome().equalsIgnoreCase(cmbUsuarioliberacao.getSelectedItem().toString())) {
                idUsuarioliberacao = us.getId();
                break; // achou, pode parar
            }
        }
        
        for (Usuario us : usuarios) {
            if (us.getNome().equalsIgnoreCase(cmbUsuariorecepcao.getSelectedItem().toString())) {
                idUsuariorecepcao = us.getId();
                break; // achou, pode parar
            }
        }
        
        //List<Usuario> usuarios = usuarioDAO.listar();
        
        for (Usuario p : usuarios) {
            if (p.getNome().equalsIgnoreCase(cmbUsuarioliberacao.getSelectedItem().toString())) {
                idUsuarioliberacao = p.getId();
                //break; // achou, pode parar
            }
            if (p.getNome().equalsIgnoreCase(cmbUsuarioliberacao.getSelectedItem().toString())) {
                idUsuariorecepcao = p.getId();
                break; // achou, pode parar
            }
        }
        
        try {       
            
            Equipamento equipamento = new Equipamento();
            equipamento.setId(idEquipamento);
            //equipamento.setNome(rset.getString("nome_equipamento"));

            Unidade origem = new Unidade();
            origem.setId(idOrigem);
            //origem.setUnidade(rset.getString("unidade_origem"));

            Unidade destino = new Unidade();
            destino.setId(idDestino);
            //destino.setUnidade(rset.getString("unidade_destino"));

            Usuario liberacao = new Usuario();
            liberacao.setId(idUsuarioliberacao);
            //liberacao.setNome(rset.getString("nome_usuario_liberacao"));

            Usuario recepcao = new Usuario();
            recepcao.setId(idUsuariorecepcao);
            //recepcao.setNome(rset.getString("nome_usuario_recepcao"));
                
            Movimentacao movimentacao = new Movimentacao();
           // Equipamento e = new Equipamento();
            movimentacao.setEquipamento(equipamento);
            movimentacao.setNum_serie(txtNumserie.getText());
            movimentacao.setTipo_movimentacao((String)cmbTipomovimentacao.getSelectedItem());
            movimentacao.setOrigem(origem);
            movimentacao.setUsuarioliberacao(liberacao);
            movimentacao.setDestino(destino);
            movimentacao.setUsuariorecepcao(recepcao);
            movimentacao.setObservacoes(txtObservacao.getText());
            movimentacao.setData_hora(new Date());
             
            int linha = movimentacaoDAO.inserir(movimentacao);
            if (linha > 0) {
               limparCampos();
               preencherTabela();
               //Registra informações no log. 
               try{     

                    //List<Usuario> usuarios = usuarioDAO.listar();

                    for (Usuario u : usuarios) {
                        if(u.getId() == idUsuariosecao){
                            nomeUsuariosecao = u. getNome();
                        }
                    }
                    
                    Log log = new Log();
                    log.setAcao("Usuário: <"+nomeUsuariosecao+"> inseriu uma movimentação.");
                    log.setData(new Date());

                    int linha2 = logDAO.inserir(log);
                    
                }catch (Exception ex){
                    
                }
                
                JOptionPane.showMessageDialog(this, "Registro inserido com sucesso!");
                 
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
    private void tblMovimentacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMovimentacaoMouseClicked
        linhaSelecionada = tblMovimentacao.getSelectedRow();
        String Equip="";
        String NumSerie="";
        String Movimentacao="";
        String Origem="";
        String Liberacao="";
        String Destino="";
        String Recepcao="";
        String Observacao="";
        if(linhaSelecionada != -1) {
            Equip = tblMovimentacao.getValueAt(linhaSelecionada, 1).toString();
            NumSerie = tblMovimentacao.getValueAt(linhaSelecionada, 2).toString();
            Movimentacao = tblMovimentacao.getValueAt(linhaSelecionada, 3).toString();
            Origem = tblMovimentacao.getValueAt(linhaSelecionada, 4).toString();
            Liberacao = tblMovimentacao.getValueAt(linhaSelecionada, 5).toString();
            Destino = tblMovimentacao.getValueAt(linhaSelecionada, 6).toString();
            Recepcao = tblMovimentacao.getValueAt(linhaSelecionada, 7).toString();
            Observacao = tblMovimentacao.getValueAt(linhaSelecionada, 8).toString();
        }
        
        
        //Object obEquip = tblMovimentacao.getValueAt(linhaSelecionada, 1);
        //String Equip = obEquip.toString();
   
        for (int i = 0;i < cmbEquipamento.getItemCount(); i++) {
            Object item = cmbEquipamento.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Equip)) {
                cmbEquipamento.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        txtNumserie.setText(NumSerie);
        
        for (int i = 0;i < cmbTipomovimentacao.getItemCount(); i++) {
            Object item = cmbTipomovimentacao.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Movimentacao)) {
                cmbTipomovimentacao.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        for (int i = 0;i < cmbOrigem.getItemCount(); i++) {
            Object item = cmbOrigem.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Origem)) {
                cmbOrigem.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        for (int i = 0;i < cmbUsuarioliberacao.getItemCount(); i++) {
            Object item = cmbUsuarioliberacao.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Liberacao)) {
                cmbUsuarioliberacao.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        for (int i = 0;i < cmbDestino.getItemCount(); i++) {
            Object item = cmbDestino.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Destino)) {
                cmbDestino.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        for (int i = 0;i < cmbUsuariorecepcao.getItemCount(); i++) {
            Object item = cmbUsuariorecepcao.getItemAt(i);
         
            if (item.toString().equalsIgnoreCase(Recepcao)) {
                cmbUsuariorecepcao.setSelectedIndex(i);
                break; // achou, pode parar
            }
        }
        
        txtObservacao.setText(Observacao);
           
    }//GEN-LAST:event_tblMovimentacaoMouseClicked

    /**
     * Abre a tela de edição de um usuário selecionado na tabela.
     * Exibe mensagem de erro caso nenhuma linha esteja selecionada.
     */
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        try {
            linhaSelecionada = tblMovimentacao.getSelectedRow();
            
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblMovimentacao.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    idMovimentacao = Integer.parseInt(idObj.toString());
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
        
        if (txtNumserie.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o numero de série.");
            txtNumserie.requestFocus();
            return;
        }
        
        try {
            // Cria o objeto Movimentacao com os dados dos campos
            
            List<Equipamento> equipamentos = equipamentoDAO.listar();
            
            for (Equipamento e : equipamentos) {
                
                if (e.getNome().equalsIgnoreCase(cmbEquipamento.getSelectedItem().toString())) {
                    idEquipamento = e.getId();
                    break; // achou, pode parar
                }
            }

            List<Unidade> unidades = unidadeDAO.listar();

            for (Unidade u : unidades) {
                if (u.getUnidade().equalsIgnoreCase(cmbOrigem.getSelectedItem().toString())) {
                    idOrigem = u.getId();
                    break; // achou, pode parar
                }
            }
            
            for (Unidade u : unidades) {
                if (u.getUnidade().equalsIgnoreCase(cmbDestino.getSelectedItem().toString())) {
                    idDestino = u.getId();
                    break; // achou, pode parar
                }
            }

            List<Usuario> usuarios = usuarioDAO.listar();

            for (Usuario ul : usuarios) {
                if (ul.getNome().equalsIgnoreCase(cmbUsuarioliberacao.getSelectedItem().toString())) {
                    idUsuarioliberacao = ul.getId();
                    break; // achou, pode parar
                }
            }
            
            for (Usuario ur : usuarios) {
                if (ur.getNome().equalsIgnoreCase(cmbUsuariorecepcao.getSelectedItem().toString())) {
                    idUsuariorecepcao = ur.getId();
                    break; // achou, pode parar
                }
            }
            
            //movimentacao.setData_hora(new Date());
            
            Equipamento equipamento = new Equipamento();
            equipamento.setId(idEquipamento);
            //equipamento.setNome(nomeEquipamento);

            Unidade origem = new Unidade();
            origem.setId(idOrigem);
            //origem.setUnidade(rset.getString("unidade_origem"));

            Unidade destino = new Unidade();
            destino.setId(idDestino);
            //destino.setUnidade(rset.getString("unidade_destino"));

            Usuario liberacao = new Usuario();
            liberacao.setId(idUsuarioliberacao);
            //liberacao.setNome(rset.getString("nome_usuario_liberacao"));

            Usuario recepcao = new Usuario();
            recepcao.setId(idUsuariorecepcao);
            //recepcao.setNome(rset.getString("nome_usuario_recepcao"));
            
            Movimentacao movimentacao = new Movimentacao();
            movimentacao.setId(idMovimentacao);
            movimentacao.setEquipamento(equipamento);
            movimentacao.setNum_serie(txtNumserie.getText());
            movimentacao.setTipo_movimentacao((String)cmbTipomovimentacao.getSelectedItem());
            movimentacao.setOrigem(origem);
            movimentacao.setUsuarioliberacao(liberacao);
            movimentacao.setDestino(destino);
            movimentacao.setUsuariorecepcao(recepcao);
            movimentacao.setObservacoes((String) txtObservacao.getText());
            // Chama a função editar
            MovimentacaoDAO movimentacaoDAO = DAOFactory.criarMovimentacaoDAO();
            int resultado = movimentacaoDAO.editar(movimentacao);
           
            if (resultado > 0) {
                limparCampos();
                preencherTabela();
                try{     

                    //List<Usuario> usuarios = usuarioDAO.listar();

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
        if (linha >= 0 && linha < tblMovimentacao.getRowCount()) {
            tblMovimentacao.setRowSelectionInterval(linha, linha);
            tblMovimentacao.scrollRectToVisible(tblMovimentacao.getCellRect(linha, 0, true));
        }
    }
    /**
     * Apaga o usuário selecionado na tabela após confirmação.
     * Trata erros de integridade e exibe mensagens apropriadas.
     */
    private void btnApagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarActionPerformed
        try{
            linhaSelecionada = tblMovimentacao.getSelectedRow();
            if (linhaSelecionada != -1) {
                // Suponha que o ID esteja na primeira coluna (índice 0)
                Object idObj = tblMovimentacao.getValueAt(linhaSelecionada, 0);
                if (idObj != null) {
                    int idMovimentacao = Integer.parseInt(idObj.toString());
                }
            }
        
            Object idObj = tblMovimentacao.getValueAt(linhaSelecionada, 0);
            int idMovimentacao = Integer.parseInt(idObj.toString());
            
            Object[] opcao = {"Não", "Sim"};
            int opcaoSelecionada = JOptionPane.showOptionDialog(this, "Deseja apagar este registro?", "Aviso",
            JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, opcao, opcao[0]);
            if (opcaoSelecionada == 1) {
                try {
                MovimentacaoDAO dao = DAOFactory.criarMovimentacaoDAO();
                    int resultado = dao.apagar(idMovimentacao);

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

    private void txtNumserieKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumserieKeyPressed
      
    }//GEN-LAST:event_txtNumserieKeyPressed

    private void btnEditarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btnEditarFocusGained
        //if (!txtNome.getText().isEmpty())
            //restaurarSelecaoTabela(linhaSelecionada);
    }//GEN-LAST:event_btnEditarFocusGained

    private void cmbUsuarioliberacaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUsuarioliberacaoKeyPressed
       
    }//GEN-LAST:event_cmbUsuarioliberacaoKeyPressed

    private void cmbUsuariorecepcaoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbUsuariorecepcaoKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUsuariorecepcaoKeyPressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
       // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void txtNumserieMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNumserieMouseClicked
        txtNumserie.selectAll();        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumserieMouseClicked

    private void txtObservacaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtObservacaoMouseClicked
        txtObservacao.selectAll();       // TODO add your handling code here:
    }//GEN-LAST:event_txtObservacaoMouseClicked
   
    //Limpa os campos da tabela e reseta combobox.
    private void limparCampos(){
        cmbEquipamento.setSelectedIndex(0);
        txtNumserie.setText("");
        cmbTipomovimentacao.setSelectedIndex(0);
        cmbOrigem.setSelectedIndex(0);
        cmbUsuarioliberacao.setSelectedIndex(0);
        cmbDestino.setSelectedIndex(0);
        cmbUsuariorecepcao.setSelectedIndex(0);
        txtObservacao.setText("");
    }
    
    
   
    private void carregarMovimentacao(int id) {
        MovimentacaoDAO movimentacaoDAO = DAOFactory.criarMovimentacaoDAO();
        Movimentacao movimentacao = movimentacaoDAO.listar(id); // chama a função que você forneceu
        
       if (movimentacao != null) {
           
           //Capiturar o Id da Movimentação salvo na tabela.
            Object Equip = tblMovimentacao.getValueAt(linhaSelecionada, 1);
        
            for (int i = 0;i < cmbEquipamento.getItemCount(); i++) {
                String item = cmbEquipamento.getItemAt(i);
                
                if (item.equalsIgnoreCase(Equip.toString())) {
                    cmbEquipamento.setSelectedIndex(i);
                    break; // achou, pode parar
                }
            }
            
            //Busca dentro do combobox item igual ao conteudo da variável bEquipamento e altera o index do combobox.
           /*for (int i = 0; i < cmbEquipamento.getItemCount(); i++) {
                Object item = cmbEquipamento.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bEquipamento)) {
                    cmbEquipamento.setSelectedIndex(i);
                    break;
                }
            }    
                     
            List<Movimentacao> movimentacoes = movimentacaoDAO.listar();
            //Transforma o valor do objeto obIndex no int cmbIndex e busca no banco Persmissoeso id igual a cmbIndex e salva a descricao em bPermissao.
            for (Movimentacao m : movimentacoes) {
                int cmbIndex = Integer.parseInt(obIndexE.toString());
                if (m.getId() == cmbIndex) {
                    //idPermissao = p.getId();
                    bMovimentacao = m.getTipo_movimentacao();
                    break; // achou, pode parar
                }
            }
            
            //Busca dentro do combobox item igual ao conteudo da variável bEquipamento e altera o index do combobox.
            for (int i = 0; i < cmbTipomovimentacao.getItemCount(); i++) {
                Object item = cmbTipomovimentacao.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bMovimentacao)) {
                    cmbTipomovimentacao.setSelectedIndex(i);
                    break;
                }
            }    
            
            //Capiturar o Id da Movimentação salvo na tabela.
            Object obIndexU1 = tblMovimentacao.getValueAt(linhaSelecionada, 4);
            Object obIndexU2 = tblMovimentacao.getValueAt(linhaSelecionada, 6);
         
            List<Unidade> unidades = unidadeDAO.listar();
            //Transforma o valor do objeto obIndex no int cmbIndex e busca no banco Persmissoeso id igual a cmbIndex e salva a descricao em bPermissao.
            for (Unidade u : unidades) {
                int cmbIndexU1 = Integer.parseInt(obIndexU1.toString());
                int cmbIndexU2 = Integer.parseInt(obIndexU2.toString());
             
                if (u.getId() == cmbIndexU1) {
                    //idPermissao = p.getId();
                    bOrigem = u.getUnidade();
                    //break; // achou, pode parar
                }
                if (u.getId() == cmbIndexU2) {
                    //idPermissao = p.getId();
                    bDestino = u.getUnidade();
                    break; // achou, pode parar
                }
            }
            
            //Busca dentro do combobox item igual ao conteudo da variável bEquipamento e altera o index do combobox.
            for (int i = 0; i < cmbOrigem.getItemCount(); i++) {
                Object item = cmbOrigem.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bOrigem)) {
                    cmbOrigem.setSelectedIndex(i);
                    break;
                }
            }    
            
            for (int i = 0; i < cmbDestino.getItemCount(); i++) {
                Object item = cmbDestino.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bDestino)) {
                    cmbDestino.setSelectedIndex(i);
                    break;
                }
            }    
            /*
            List<Equipamento> equipamentos = equipamentoDAO.listar();
            //Transforma o valor do objeto obIndex no int cmbIndex e busca no banco Persmissoeso id igual a cmbIndex e salva a descricao em bPermissao.
            for (Equipamento e : equipamentos) {
                int cmbIndex = Integer.parseInt(obIndex.toString());
                if (e.getId() == cmbIndex) {
                    //idPermissao = p.getId();
                    bEquipamento = e.getNome();
                    break; // achou, pode parar
                }
            }
            */
            //Busca dentro do combobox item igual ao conteudo da variável bEquipamento e altera o index do combobox.
            /*for (int i = 0; i < cmbEquipamento.getItemCount(); i++) {
                Object item = cmbEquipamento.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bEquipamento)) {
                    cmbEquipamento.setSelectedIndex(i);
                    break;
                }
            }    */
            

           /* //Busca dentro do combobox item igual ao conteudo da variável bPermissao e altera o index do combobox.
            for (int i = 0; i < cmbMovimentacaoliberacao.getItemCount(); i++) {
                Object item = cmbMovimentacaoliberacao.getItemAt(i);

                if (item.toString().equalsIgnoreCase(bPermissao)) {
                    cmbMovimentacaoliberacao.setSelectedIndex(i);
                    break;
                }
            }   
            cmbEquipamento.
            //Carrega o conteúdo da linha selecionada nos campos.
            txtNome.setText(movimentacao.getNome());
            txtNumserie.setText(movimentacao.getMovimentacao());
            txtEmail.setText(movimentacao.getEmail());
            ftxtCelular.setText(movimentacao.getCelular());
            ptxtSenha.setText(movimentacao.getSenha());
          
         
            tNome = txtNome.getText();
            tMovimentacao = txtNumserie.getText();
            tEmail = txtEmail.getText();
            tCelular = ftxtCelular.getText();
            tidPermissao = idPermissao;
            tSenha = ptxtSenha.getText();
        } else {
            JOptionPane.showMessageDialog(this, "Registro não encontrado.");*/
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
            java.util.logging.Logger.getLogger(frmMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMovimentacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new frmMovimentacao().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApagar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnInserir;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JComboBox<String> cmbDestino;
    private javax.swing.JComboBox<String> cmbEquipamento;
    private javax.swing.JComboBox<String> cmbOrigem;
    private javax.swing.JComboBox<String> cmbTipomovimentacao;
    private javax.swing.JComboBox<String> cmbUsuarioliberacao;
    private javax.swing.JComboBox<String> cmbUsuariorecepcao;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDestino;
    private javax.swing.JLabel lblEquipamento;
    private javax.swing.JLabel lblNumserie;
    private javax.swing.JLabel lblObservacao;
    private javax.swing.JLabel lblOrigem;
    private javax.swing.JLabel lblPesquisa;
    private javax.swing.JLabel lblTipomovimentacao;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuarioliberacao;
    private javax.swing.JLabel lblUsuariorecepcao;
    private javax.swing.JPanel panInferior;
    private javax.swing.JPanel panSuperior;
    private javax.swing.JTable tblMovimentacao;
    private javax.swing.JTextField txtBusca;
    private javax.swing.JTextField txtNumserie;
    private javax.swing.JTextField txtObservacao;
    // End of variables declaration//GEN-END:variables
}
