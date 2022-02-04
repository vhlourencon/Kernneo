package br.com.kernneo.desktop.view.financeiro;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.fasterxml.jackson.databind.BeanProperty.Bogus;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.cliente.ClienteFormCadPanel;
import br.com.kernneo.desktop.view.cliente.ClienteListInternalFrame;
import br.com.kernneo.desktop.view.grupo.GrupoFormCadPanel;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JMoneyField;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;
import br.com.kernneo.server.negocio.Movimentacao;
import br.com.kernneo.server.negocio.PosicaoFinanceira;
import net.miginfocom.swing.MigLayout;
import javax.swing.Icon;

public class MovimentacaoInternalFrame extends JInternalFrame
    {
        private SpringLayout springLayout;
        private SpringLayout springLayout_1;
        private JTable table;
        private JTextField textFieldDescricao;

        private MovimentacaoModel movimentacaoModel;
        private UtilDateModel model;
        private JComboBox<CategoriaModel> comboBoxCategoria;
        private JComboBox<ClienteModel> comboBoxCliente;
        private Component checkBoxRepetir;
        private JComboBox<ContaBancariaModel> comboBoxConta;
        private JComboBox comboboxRepetirPeriodo;
        private JSpinner spinnerRepetirQtde;
        private JRadioButton radioButtonCredito;
        private JRadioButton radioButtonDebito;
        private JMoneyField textFieldValor;

        private ArrayList<ContaBancariaModel> listaDeContasSelecionadas;
        private JTable tableSaldo;
        private JTable footerTable;

        private PosicaoFinanceiraModel posicaoFinanceiraModel;
        private JPanel panelInfoLancamento;

//	private Moviment

        public MovimentacaoInternalFrame(FuncionarioModel funcionarioModel) throws Exception {

            setTitle("Movimentação Diaria");

            if (funcionarioModel.getPermissaoMovFinanceiraModel().isPermiteAcesso() == false) {
                throw new Exception("Usuário sem permissao para acessar esse modulo");
            }

            setResizable(false);
            setClosable(true);
            setVisible(true);
            setMaximizable(true);
            setLocation(250, 50);
            setSize(1196, 738);
            setIconifiable(true);
            getContentPane().setLayout(null);

            model = new UtilDateModel();
            model.setSelected(true);

            // model.setDate(20,04,2014);
            // Need this...
            Properties p = new Properties();
            p.put("text.today", "Hoje");
            p.put("text.month", "Mês");
            p.put("text.year", "Ano");

            JPanel panel = new JPanel();
            panel.setBounds(5, 5, 321, 77);
            getContentPane().add(panel);

            // panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1,
            // 1, 1, 1, Color.red), BorderFactory.createEtchedBorder()));
            panel.setBorder(new TitledBorder(null, "Selecione uma Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));

            // Don't know about the formatter, but there it is...
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            datePanel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    acaoObterPosicao();

                }
            });
            datePanel.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

            // datePanel.setBounds(24, 64, 267, 193);

            JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            datePicker.setLocation(24, 21);
            datePicker.setSize(267, 37);
            datePicker.setTextEditable(true);

            panel.setLayout(null);

            panel.add(datePicker);
            // panel.add(datePanel);

            table = new JTable();
            // table.getColumnModel().getColumn(0).setMinWidth(0);
            // table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            table.setFillsViewportHeight(true);

            // table.setModel(
            table.setModel(new MovimentacaoTableModel());
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);

            JScrollPane scrollPaneLancamentos = new JScrollPane(table);
            // scrollPaneLancamentos.setBounds(341, 250, 810, 313);
            // scrollPaneLancamentos.setPreferredSize(new Dimension(-1,300));

            scrollPaneLancamentos.setMinimumSize(new Dimension(341, 100));
            // scrollPane.setLayout(new MigLayout("grow,fill"));
            // getContentPane().add(scrollPaneLancamentos);

            panelInfoLancamento = new JPanel() {

                /** Stroke size. it is recommended to set it to 1 for better view */
                protected int strokeSize = 1;
                /** Color of shadow */
                protected Color shadowColor = Color.black;
                /** Sets if it drops shadow */
                protected boolean shady = true;
                /** Sets if it has an High Quality view */
                protected boolean highQuality = true;
                /** Double values for Horizontal and Vertical radius of corner arcs */
                protected Dimension arcs = new Dimension(20, 20);
                /** Distance between shadow border and opaque panel border */
                protected int shadowGap = 5;
                /** The offset of shadow. */
                protected int shadowOffset = 4;
                /** The transparency value of shadow. ( 0 - 255) */
                protected int shadowAlpha = 150;

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    int width = getWidth();
                    int height = getHeight();
                    int shadowGap = this.shadowGap;
                    Color shadowColorA = new Color(shadowColor.getRed(), shadowColor.getGreen(), shadowColor.getBlue(), shadowAlpha);
                    Graphics2D graphics = (Graphics2D) g;

                    // Sets antialiasing if HQ.
                    if (highQuality) {
                        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    }

                    // Draws shadow borders if any.
                    if (shady) {
                        graphics.setColor(shadowColorA);
                        graphics.fillRoundRect(shadowOffset, // X position
                                shadowOffset, // Y position
                                width - strokeSize - shadowOffset, // width
                                height - strokeSize - shadowOffset, // height
                                arcs.width, arcs.height);// arc Dimension
                    } else {
                        shadowGap = 1;
                    }

                    // Draws the rounded opaque panel with borders.
                    graphics.setColor(getBackground());
                    graphics.fillRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);
                    graphics.setColor(getForeground());
                    graphics.setStroke(new BasicStroke(strokeSize));
                    graphics.drawRoundRect(0, 0, width - shadowGap, height - shadowGap, arcs.width, arcs.height);

                    // Sets strokes to default, is better.
                    graphics.setStroke(new BasicStroke());
                }
            };
            // panelInfoLancamento.setBackground();
            panelInfoLancamento.setBorder(new TitledBorder(null, "Informaçoes de Lançamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panelInfoLancamento.setBounds(5, 80, 321, 505);
            panelInfoLancamento.setOpaque(false);
            getContentPane().add(panelInfoLancamento);
            panelInfoLancamento.setLayout(null);

            JLabel labelTipo = new JLabel("Tipo:");
            labelTipo.setBounds(10, 25, 46, 15);
            panelInfoLancamento.add(labelTipo);

            JPanel panel_2 = new JPanel();
            panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
            panel_2.setBackground(Color.WHITE);
            panel_2.setBounds(10, 45, 301, 45);
            panelInfoLancamento.add(panel_2);
            panel_2.setLayout(null);

            radioButtonCredito = new JRadioButton("Crédito");
            // adioButtonCredito.setBackground(S);
            radioButtonCredito.setFont(new Font("Tahoma", Font.BOLD, 15));
            radioButtonCredito.setBounds(23, 9, 109, 23);
            panel_2.add(radioButtonCredito);
            Color customColor = new Color(10, 10, 255);

            radioButtonCredito.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    if (radioButtonCredito.isSelected()) {
                        panelInfoLancamento.setBackground(new Color(102, 153, 102));

                    } else {
                        panelInfoLancamento.setBackground(new Color(153, 102, 102));
                    }
                    validate();
                    panelInfoLancamento.repaint();

                }
            });

            radioButtonDebito = new JRadioButton("Débito");
            // radioButtonDebito.setBackground();
            radioButtonDebito.setFont(new Font("Tahoma", Font.BOLD, 15));
            radioButtonDebito.setBounds(166, 9, 109, 23);

            panel_2.add(radioButtonDebito);

            ButtonGroup buttonGroupLancamentoTipo = new ButtonGroup();
            buttonGroupLancamentoTipo.add(radioButtonCredito);
            buttonGroupLancamentoTipo.add(radioButtonDebito);
            radioButtonCredito.setSelected(true);

            JLabel lblValor = new JLabel("Valor:");
            lblValor.setBounds(10, 398, 46, 14);
            panelInfoLancamento.add(lblValor);

            textFieldValor = new JMoneyField();
            textFieldValor.setEditable(true);
            textFieldValor.setFont(new Font("Tahoma", Font.BOLD, 15));

            textFieldValor.setBounds(10, 415, 301, 45);
            panelInfoLancamento.add(textFieldValor);

            comboBoxCliente = new JComboBox<ClienteModel>();
            comboBoxCliente.setBounds(10, 270, 240, 43);
            
            panelInfoLancamento.add(comboBoxCliente);
            AutoCompleteDecorator.decorate(comboBoxCliente);

            JLabel labelCliente = new JLabel("Cliente:");
            labelCliente.setBounds(10, 250, 46, 14);
            panelInfoLancamento.add(labelCliente);

            JLabel lblNewLabel_1_1 = new JLabel("Categoria:");
            lblNewLabel_1_1.setBounds(10, 175, 95, 14);
            panelInfoLancamento.add(lblNewLabel_1_1);

            comboBoxCategoria = new JComboBox<CategoriaModel>();
            AutoCompleteDecorator.decorate(comboBoxCategoria);
            comboBoxCategoria.setSelectedIndex(-1);
            comboBoxCategoria.setBounds(10, 195, 240, 43);
            panelInfoLancamento.add(comboBoxCategoria);

            textFieldDescricao = new JTextField();
            textFieldDescricao.setBounds(10, 121, 301, 43);
            panelInfoLancamento.add(textFieldDescricao);
            textFieldDescricao.setColumns(10);

            JLabel labelDescricao = new JLabel("Descrição:");
            labelDescricao.setBounds(10, 100, 80, 15);
            panelInfoLancamento.add(labelDescricao);

            checkBoxRepetir = new JCheckBox("Repetir");
            checkBoxRepetir.setEnabled(false);
            checkBoxRepetir.setBounds(10, 470, 109, 23);
            panelInfoLancamento.add(checkBoxRepetir);

            JLabel labelConta = new JLabel("Conta:");
            labelConta.setBounds(10, 324, 46, 14);
            panelInfoLancamento.add(labelConta);

            comboBoxConta = new JComboBox<ContaBancariaModel>();
            comboBoxConta.setBounds(10, 344, 301, 43);
            panelInfoLancamento.add(comboBoxConta);

            JPanel panel_3 = new JPanel();
            panel_3.setBounds(116, 470, 195, 23);
            panelInfoLancamento.add(panel_3);
            panel_3.setEnabled(false);
            panel_3.setLayout(null);

            comboboxRepetirPeriodo = new JComboBox();
            comboboxRepetirPeriodo.setEnabled(false);
            comboboxRepetirPeriodo.setBounds(0, 0, 89, 25);
            panel_3.add(comboboxRepetirPeriodo);

            spinnerRepetirQtde = new JSpinner();
            spinnerRepetirQtde.setEnabled(false);
            spinnerRepetirQtde.setBounds(99, 0, 55, 25);
            panel_3.add(spinnerRepetirQtde);

            JLabel lblVezes = new JLabel("Vezes");
            lblVezes.setEnabled(false);
            lblVezes.setBounds(155, 5, 40, 14);
            panel_3.add(lblVezes);
            
            JButton buttonAddCliente = new JButton(Icone.novo("btAdic2.gif"));
            buttonAddCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ClienteListInternalFrame clienteListInternalFrame = new ClienteListInternalFrame() {

                            @Override
                            public void acaoSalvar() {
                               
                                    try {
                                        Long id = null ; 
                                        Conexao.Executar(new Comando() {

                                            @Override
                                            public void execute(Session session) throws Exception {
                                                ClienteModel model = getFormCadPanel().getModel();
                                               ClienteModel modelAux =  negocio.salvar( model);
                                               comboBoxCliente.addItem(modelAux);
                                               comboBoxCliente.setSelectedItem(modelAux);

                                             
                                            }
                                        });
                                     
                                        setVisible(false);
                                        dispose();
                                        JOptionPane.showMessageDialog(this, "Inserido com sucesso!");
                                      

                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, e.getMessage());
                                        e.printStackTrace();

                                    }

                                
                            }
                            
                        };
                        clienteListInternalFrame.eventoBotaoIncluir();
                        clienteListInternalFrame.setVisible(true);
                        clienteListInternalFrame.setModoForm();
                     
                        clienteListInternalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {
                            
                            @Override
                            public void actionPerformed(ActionEvent e) {
                               clienteListInternalFrame.setVisible(false);
                               clienteListInternalFrame.dispose();
                                
                            }
                        });
                        PrincipalDesktop.getjDesktopPane().add(clienteListInternalFrame, 0);
                        clienteListInternalFrame.setSelected(true);
                        PrincipalDesktop.getjDesktopPane().setSelectedFrame(clienteListInternalFrame);
                     //   clienteListInternalFrame.setModoForm();
                        ClienteFormCadPanel clienteFormCadPanel = (ClienteFormCadPanel) clienteListInternalFrame.getFormCadPanel();    
                        clienteFormCadPanel.getTextFieldDescricao().requestFocus();
                        clienteFormCadPanel.getTextFieldDescricao().selectAll();
                      
                        

                    } catch (Exception excecao) {
                        JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

                    }
                }
            });
            buttonAddCliente.setBounds(256, 269, 56, 45);
            panelInfoLancamento.add(buttonAddCliente);
            
            JButton buttonAddCategoria = new JButton(Icone.novo("btAdic2.gif"));
            buttonAddCategoria.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        GrupoListInternalFrame grupoListInternalFrame = new GrupoListInternalFrame() {

                            @Override
                            public void acaoSalvar() {
                               
                                    try {
                                        Long id = null ; 
                                        Conexao.Executar(new Comando() {

                                            @Override
                                            public void execute(Session session) throws Exception {
                                                CategoriaModel model = getFormCadPanel().getModel();
                                                CategoriaModel modelAux =  negocio.salvar( model);
                                               comboBoxCategoria.addItem(modelAux);
                                               comboBoxCategoria.setSelectedItem(modelAux);

                                             
                                            }
                                        });
                                     
                                        setVisible(false);
                                        dispose();
                                        JOptionPane.showMessageDialog(this, "Inserido com sucesso!");
                                      

                                    } catch (Exception e) {
                                        JOptionPane.showMessageDialog(this, e.getMessage());
                                        e.printStackTrace();

                                    }

                                
                            }
                            
                        };
                        grupoListInternalFrame.eventoBotaoIncluir();
                        grupoListInternalFrame.setVisible(true);
                        
                        grupoListInternalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {
                            
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                grupoListInternalFrame.setVisible(false);
                               grupoListInternalFrame.dispose();
                                
                            }
                        });
                        
                        PrincipalDesktop.getjDesktopPane().add(grupoListInternalFrame, 0);
                       grupoListInternalFrame.setSelected(true);
                        PrincipalDesktop.getjDesktopPane().setSelectedFrame(grupoListInternalFrame);
                        
                        GrupoFormCadPanel formCadPanel = (GrupoFormCadPanel) grupoListInternalFrame.getFormCadPanel();    
                        formCadPanel.getTextFieldDescricao().requestFocus();
                        formCadPanel.getTextFieldDescricao().selectAll();

                    } catch (Exception excecao) {
                        JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

                    }
                }
            });
            buttonAddCategoria.setBounds(255, 193, 56, 46);
            panelInfoLancamento.add(buttonAddCategoria);

            JButton btnNewButton = new JButton(Icone.novo("chamado_concluido_16x16.png"));
            btnNewButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        Conexao.Executar(new Comando() {

                            @Override
                            public void execute(Session session) throws Exception {
                                MovimentacaoModel movimentacaoModel = getMovimentacaoModel();
                                movimentacaoModel.setUsuarioSave(PrincipalDesktop.getUsarioLogado());
                                new Movimentacao().salvar(movimentacaoModel);

                            }
                        });
                        acaoObterPosicao();
                        setMovimentacaoModel(factoryMovimentacaoModel());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(MovimentacaoInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btnNewButton.setText("Adicionar Lançamento");

            btnNewButton.setBounds(5, 585, 321, 67);
            getContentPane().add(btnNewButton);

            tableSaldo = new JTable();
            tableSaldo.setFillsViewportHeight(true);
            tableSaldo.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
            DefaultTableCellRenderer dtcrFooter = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    parent.getFont().deriveFont(Font.BOLD);
                    return parent;

                }
            };

            footerTable = new JTable();
            footerTable.setFillsViewportHeight(true);
            footerTable.setColumnModel(tableSaldo.getColumnModel());
            // Disable selection in the footer. Otherwise you can select the footer row
            // along with a row in the table and that can look quite strange.
            footerTable.setRowSelectionAllowed(false);
            footerTable.setColumnSelectionAllowed(false);
            footerTable.setTableHeader(null);
            footerTable.setRowHeight(24);
            String[] colunasTableSaldo = new String[] { "Conta",  "Ch. Especial", "Saldo + Ch. Especial" ,"Saldo"};
            tableSaldo.setModel(new DefaultTableModel(new Object[][] {

            }, colunasTableSaldo) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }

            });

            footerTable.setModel(new DefaultTableModel(new Object[][] {

            }, colunasTableSaldo) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });

            footerTable.setDefaultRenderer(String.class, dtcrFooter);

            JScrollPane jPanelSaldo = new JScrollPane(tableSaldo);
            // JScrollPane jPanelSaldofooter = new JScrollPane(footerTable);
            // jPanelSaldofooter.setPreferredSize(new Dimension(-1,30));
            // jPanelSaldo.add(BorderLayout.CENTER, tableSaldo);

            JScrollPane jscrollPaneFooter = new JScrollPane(footerTable);
            // jscrollPaneFooter.setBorder(new CompoundBorder(new
            // MatteBorder(1,1,1,1,Color.gray),new EmptyBorder(0,0,0,0)));
            jscrollPaneFooter.setPreferredSize(new Dimension(-1, 30));

            BorderLayout bl_holdingPanel = new BorderLayout();
            bl_holdingPanel.setVgap(3);
            JPanel holdingPanel = new JPanel(bl_holdingPanel);
            holdingPanel.add(jPanelSaldo, BorderLayout.CENTER);
            holdingPanel.add(jscrollPaneFooter, BorderLayout.SOUTH);

            JTabbedPane tabbedPaneSaldoEmConta = new JTabbedPane(JTabbedPane.TOP);
            // tabbedPaneSaldoEmConta.setBounds(341, 437, 810, 247);
            tabbedPaneSaldoEmConta.addTab("Saldo em Conta", null, holdingPanel, null);
            tabbedPaneSaldoEmConta.setSize(-1, 100);
            tabbedPaneSaldoEmConta.setPreferredSize(new Dimension(-1, 240));

            // getContentPane().add(tabbedPaneSaldoEmConta);

            JPanel panel_4 = new JPanel();
            panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Barra de Ferramentas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4.setBounds(330, 5, 816, 77);
            panel_4.setLayout(new BorderLayout());

            JButton btExcluir = new JButton(Icone.novo("btExcluir.png"));
            btExcluir.setText("Excluir ");
            JButton btAlterar = new JButton(Icone.novo("btAgenda2-old.png"));
            btAlterar.setText("Alterar Data");

            JButton btSair = new JButton(Icone.novo("btSair.png"));
            btSair.setText("Sair");
            btSair.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();

                }
            });

            JPanel buttonBarComponent = new JPanel();
            // buttonBarComponent.setBorder(BorderFactory.createEmptyBorder());

            buttonBarComponent.setLayout(new MigLayout("", "[150px][150px][400px][150px]", "[50px]"));

            // buttonBarComponent.btAlterar.setp
            buttonBarComponent.add(btAlterar, "cell 0 0,grow");
            buttonBarComponent.add(btExcluir, "cell 1 0,grow");
            buttonBarComponent.add(btSair, "cell 3 0,grow");

            btAlterar.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    try {
                        if (getModelSelecionado() == null) {
                            JOptionPane.showMessageDialog(null, "Selecione um registro");
                        } else {
                            DatePickerDialog datePickerDialog = new DatePickerDialog() {

                                @Override
                                public void getDataSelecionada(Date dataSelecionada) {
                                    if (dataSelecionada == null) {
                                        JOptionPane.showMessageDialog(this, "Data Inválida", "ERRO ", JOptionPane.ERROR_MESSAGE);

                                    } else {
                                        int dialogButton = JOptionPane.YES_NO_OPTION;
                                        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja Alterar parada data Selecionada?", "Alerta", dialogButton);
                                        if (dialogResult == JOptionPane.YES_OPTION) {
                                            acaoEditarData(getModelSelecionado(), dataSelecionada);
                                        }
                                        this.setVisible(false);
                                        this.dispose();
                                    }

                                }

                            };
                            datePickerDialog.centerOnScreen(true);
                            datePickerDialog.setVisible(true);

                        }

                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }
            });
            btExcluir.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (getModelSelecionado() == null) {
                        JOptionPane.showMessageDialog(null, "Selecione um registro");
                    } else {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir?", "Alerta", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            acaoExcluir(getModelSelecionado());
                        }

                    }

                }
            });
            panel_4.add(buttonBarComponent, BorderLayout.CENTER);

            getContentPane().add(panel_4);

            BorderLayout bl_panel_5 = new BorderLayout();
            bl_panel_5.setVgap(5);
            JPanel panel_5 = new JPanel(bl_panel_5);
            panel_5.setBounds(333, 85, 810, 565);
            panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
            panel_5.add(tabbedPaneSaldoEmConta, BorderLayout.SOUTH);

            // panel_5.add(comp)
            getContentPane().add(panel_5);

            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {

                        ArrayList<CategoriaModel> lista = new Categoria().obterTodos(CategoriaModel.class);
                        for (CategoriaModel categoriaModel : lista) {
                            comboBoxCategoria.addItem(categoriaModel);
                        }

                        ArrayList<ContaBancariaModel> listaContaBancaria = new ContaBancaria().obterTodos(ContaBancariaModel.class);
                        for (ContaBancariaModel contaBancariaModel : listaContaBancaria) {
                            comboBoxConta.addItem(contaBancariaModel);
                        }

                        ArrayList<ClienteModel> listaDeClientes = new Cliente().obterTodos(ClienteModel.class);
                        for (ClienteModel clienteModel : listaDeClientes) {
                            comboBoxCliente.addItem(clienteModel);
                        }
                        listaDeContasSelecionadas = new ContaBancaria().obterTodos(ContaBancariaModel.class);
                        // acaoObterPosicao();
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

                throw e1;
            }
            setListaDeContasSelecionadas(getListaDeContasSelecionadas());
            acaoObterPosicao();
            setMovimentacaoModel(factoryMovimentacaoModel());

            panel_5.removeAll();
            if (funcionarioModel.getPermissaoMovFinanceiraModel().isVisualizarSaldoConta()) {
                panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
                panel_5.add(tabbedPaneSaldoEmConta, BorderLayout.SOUTH);
            } else {
                panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
                table.getColumnModel().getColumn(6).setMinWidth(0);
                table.getColumnModel().getColumn(6).setMaxWidth(0);
            }
            
           

        }

        private void acaoEditarData(MovimentacaoModel modelSelecionado, Date dataSelecionada) {
            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {
                        modelSelecionado.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
                        new Movimentacao().alterarData(modelSelecionado, dataSelecionada);
                    }
                });
                acaoObterPosicao();
                JOptionPane.showMessageDialog(this, "Registro Alterado com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

            }

        }

        protected void acaoExcluir(MovimentacaoModel modelSelecionado) {
            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {
                        modelSelecionado.setUsuarioDelete(PrincipalDesktop.getUsarioLogado());
                        new Movimentacao().excluir(modelSelecionado);
                    }
                });
                acaoObterPosicao();
                JOptionPane.showMessageDialog(this, "Registro Excluido com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

            }

        }

        
        private MovimentacaoModel factoryMovimentacaoModel() { 
           MovimentacaoModel model =  new MovimentacaoModel();
           model.setTipo(MovimentacaoFinanceiraTypes.debito);
           return model; 
        }
        private void acaoObterPosicao() {
            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {
                        // TODO Auto-generated method stub

                        PosicaoFinanceiraModel posicaoFinanceiraModel = new PosicaoFinanceira().obterPosicoesFinanceira(model.getValue(), getListaDeContasSelecionadas());
                        setPosicaoFinanceiraModel(posicaoFinanceiraModel);

                    }
                });
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void setPosicaoFinanceiraModel(PosicaoFinanceiraModel posicaoFinanceiraModel) {
            this.posicaoFinanceiraModel = posicaoFinanceiraModel;
            MovimentacaoTableModel defaultTableModelMovimentacoes = (MovimentacaoTableModel) table.getModel();
            DefaultTableModel defaultTableModelPosBancaria = (DefaultTableModel) tableSaldo.getModel();
            DefaultTableModel defaultTableModelPosBancariaFooter = (DefaultTableModel) footerTable.getModel();

            defaultTableModelMovimentacoes.setRowCount(0);
            defaultTableModelPosBancaria.setRowCount(0);
            defaultTableModelPosBancariaFooter.setRowCount(0);
            
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (posicaoFinanceiraModel != null) {

                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Inicial ", "", "", "", false, "", currencyFormat(posicaoFinanceiraModel.getCalcSaldoInicial()) });

                BigDecimal bigDecimalSaldoGlobal = posicaoFinanceiraModel.getCalcSaldoInicial();
                BigDecimal bigDecimalSaldoGloalMaisCheque = new BigDecimal(BigInteger.ZERO); 
                for (PosicaoBancariaModel posBancariaModel : posicaoFinanceiraModel.getListaDePosicoesBancarias()) {
                    BigDecimal bigDecimalSaldoMaisCheque = posBancariaModel.getContaBancariaSelecionada().getChequeEspecial().add(posBancariaModel.getCalcSaldoFinal()); 
                    bigDecimalSaldoGloalMaisCheque = bigDecimalSaldoGloalMaisCheque.add(bigDecimalSaldoMaisCheque);
                    
                    String saldo =  currencyFormat(posBancariaModel.getCalcSaldoFinal()); 
                    if (posBancariaModel.getCalcSaldoFinal().compareTo(BigDecimal.ZERO) == -1) { 
                        saldo = "<HTML><FONT COLOR=RED>" + saldo + "</HTML>";
                    }
                    
                    defaultTableModelPosBancaria.addRow(new String[] { posBancariaModel.getContaBancariaSelecionada().getNome(), currencyFormat(posBancariaModel.getContaBancariaSelecionada().getChequeEspecial()), currencyFormat(bigDecimalSaldoMaisCheque), saldo });
                    
                    for (MovimentacaoModel movimentacaoModel : posBancariaModel.getListaDeMovimentacao()) {
                        if (movimentacaoModel.isExecutado()) {
                            bigDecimalSaldoGlobal = bigDecimalSaldoGlobal.add(movimentacaoModel.getValor());
                        }
                        String dataPagoRecebido  = "";
                        if(movimentacaoModel.getDataHoraExecutado() !=null) { 
                            dataPagoRecebido = formatter.format(movimentacaoModel.getDataHoraExecutado());
                        }
                        
                        defaultTableModelMovimentacoes.addRow(new Object[] { movimentacaoModel.getId().toString(), movimentacaoModel.getDescricao(), movimentacaoModel.getConta().getNome(), movimentacaoModel.getTipo().toString(), currencyFormat(movimentacaoModel.getValor()), movimentacaoModel.isExecutado(),dataPagoRecebido, currencyFormat(bigDecimalSaldoGlobal) });
                    }
                }
                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Final ", "", "", "", false, "",  currencyFormat(posicaoFinanceiraModel.getCalcSaldoFinal()) });
                defaultTableModelPosBancariaFooter.addRow((new String[] { "<HTML><B>TOTAL</B></HTML> ",  "" , currencyFormat(bigDecimalSaldoGloalMaisCheque),currencyFormat(posicaoFinanceiraModel.getCalcSaldoFinal())}));

            }

        }

        public PosicaoFinanceiraModel getPosicaoFinanceiraModel() {
            return posicaoFinanceiraModel;
        }

        private MovimentacaoModel getModelSelecionado() {

            if (table.getSelectedRow() == -1) {
                return null;
            } else {
                DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
                String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

                MovimentacaoModel modelSelecionado = getModelPorID(id);

                return modelSelecionado;
            }
        }

        private MovimentacaoModel getModelPorID(String id) {
            MovimentacaoModel modelSelecionado = null;
            for (PosicaoBancariaModel posicaoBancariaModel : getPosicaoFinanceiraModel().getListaDePosicoesBancarias()) {
                for (MovimentacaoModel model : posicaoBancariaModel.getListaDeMovimentacao()) {
                    if (model.getId().toString().equals(id)) {
                        modelSelecionado = model;
                        break;
                    }
                }
            }
            return modelSelecionado;
        }

        public String currencyFormat(BigDecimal n) {
            return NumberFormat.getCurrencyInstance().format(n);
        }

        public ArrayList<ContaBancariaModel> getListaDeContasSelecionadas() {
            if (listaDeContasSelecionadas == null) {

                try {
                    Conexao.Executar(new Comando() {
                        @Override
                        public void execute(Session session) throws Exception {

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            return listaDeContasSelecionadas;
        }

        public void setListaDeContasSelecionadas(ArrayList<ContaBancariaModel> listaDeContasSelecionadas) {
            this.listaDeContasSelecionadas = listaDeContasSelecionadas;
        }

        public MovimentacaoModel getMovimentacaoModel() {
            movimentacaoModel.setCategoria((CategoriaModel) comboBoxCategoria.getSelectedItem());
            movimentacaoModel.setConta((ContaBancariaModel) comboBoxConta.getSelectedItem());
            movimentacaoModel.setCliente((ClienteModel) comboBoxCliente.getSelectedItem());
            movimentacaoModel.setTipo(MovimentacaoFinanceiraTypes.credito);
            if (radioButtonDebito.isSelected()) {
                movimentacaoModel.setTipo(MovimentacaoFinanceiraTypes.debito);
            }

            movimentacaoModel.setDescricao(textFieldDescricao.getText());
            movimentacaoModel.setValor(BigDecimal.valueOf(textFieldValor.getValor()));
            movimentacaoModel.setDataHora(model.getValue());
            return movimentacaoModel;
        }

        public void setMovimentacaoModel(MovimentacaoModel movimentacaoModel) {
            this.movimentacaoModel = movimentacaoModel;

            textFieldDescricao.setText(movimentacaoModel.getDescricao());
            // comboBoxCategoria.setSelectedItem(anObject);
            if (movimentacaoModel.getTipo() == null) {
                radioButtonCredito.setSelected(true);
            } else {
                if (movimentacaoModel.getTipo() == MovimentacaoFinanceiraTypes.credito) {
                    radioButtonCredito.setSelected(true);

                }
                if (movimentacaoModel.getTipo() == MovimentacaoFinanceiraTypes.debito) {
                    radioButtonDebito.setSelected(true);

                }
                panelInfoLancamento.repaint();
                panelInfoLancamento.validate();
            }
            if (movimentacaoModel.getValor() == null) {
                BigDecimal bigDecimal = BigDecimal.ZERO;
                textFieldValor.setValor(bigDecimal.doubleValue());
            } else {
                textFieldValor.setValor(movimentacaoModel.getValor().doubleValue());
            }

        }

        public class MovimentacaoTableModel extends DefaultTableModel
            {
                private Movimentacao movimentacao;
                private Date dataAux;
                private boolean confirmaAlteracao;

                public MovimentacaoTableModel() {

                    super(new String[] { "id", "Descrição", "Conta", "Tipo", "Valor", "Pago/Recebido", "Data Pago/Recebido", "Saldo Global" }, 0);
                    movimentacao = new Movimentacao();
                }

                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    Class clazz = String.class;
                    switch (columnIndex) {

                    case 5:
                        clazz = Boolean.class;
                        break;
                    }
                    return clazz;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return column == 5;
                }

                @Override
                public void setValueAt(Object aValue, int row, int column) {
                    if (aValue instanceof Boolean && column == 5) {
                        // System.out.println(aValue);
                        Vector rowData = (Vector) getDataVector().get(row);
                        // System.out.println();

                        MovimentacaoModel movimentacaoModel = getModelPorID((String) rowData.get(0));
                        if (movimentacaoModel != null) {
                            dataAux = null;
                            confirmaAlteracao = true;
                            if ((boolean) aValue == true) {
                                
                                confirmaAlteracao =false; 
                                DatePickerDialog datePickerDialog = new DatePickerDialog() {

                                    @Override
                                    public void getDataSelecionada(Date dataSelecionada) {
                                        if (dataSelecionada == null) {
                                            JOptionPane.showMessageDialog(this, "Data Inválida", "ERRO ", JOptionPane.ERROR_MESSAGE);
                                            confirmaAlteracao = false;

                                        } else {
                                            dataAux = dataSelecionada;
                                            confirmaAlteracao = true;
                                            this.setVisible(false);
                                            this.dispose();
                                        }
                                    }
                                };
                                datePickerDialog.getModel().setValue(model.getValue());
                                datePickerDialog.setTitelBorder("Informe uma data para o Pgto/Recbto");
                                datePickerDialog.setTextBottom("Clique \"Ok\" para  CONFIRMAR");
                                datePickerDialog.centerOnScreen(true);
                                datePickerDialog.setVisible(true);
                                
                            }
                            if (confirmaAlteracao) {
                                try {
                                    Conexao.Executar(new Comando() {

                                        @Override
                                        public void execute(Session session) throws Exception {
                                            movimentacao.executar(movimentacaoModel, (boolean) aValue,dataAux);
                                        }
                                    });
                                    rowData.set(5, (boolean) aValue);
                                    fireTableCellUpdated(row, column);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    JOptionPane.showMessageDialog(MovimentacaoInternalFrame.this, e.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

                                } finally {
                                    acaoObterPosicao();
                                }
                            }

                            // acaoObterPosicao();

                        }

                    }
                }
            }
    }
