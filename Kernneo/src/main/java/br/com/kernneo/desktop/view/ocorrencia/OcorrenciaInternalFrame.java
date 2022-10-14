package br.com.kernneo.desktop.view.ocorrencia;

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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
import javax.swing.SpinnerDateModel;
import javax.swing.SpringLayout;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.cliente.ClienteFormCadPanel;
import br.com.kernneo.desktop.view.cliente.ClienteListInternalFrame;
import br.com.kernneo.desktop.view.financeiro.DatePickerDialog;
import br.com.kernneo.desktop.view.grupo.GrupoFormCadPanel;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JMoneyField;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;
import br.com.kernneo.server.negocio.Movimentacao;
import br.com.kernneo.server.negocio.PosicaoFinanceira;
import net.miginfocom.swing.MigLayout;

public class OcorrenciaInternalFrame extends JInternalFrame
    {
        private SpringLayout springLayout;
        private SpringLayout springLayout_1;
        private JTable jTableMovimentacoes;
        private JFormattedTextField formattedTextFieldOcorrenciaNumero;

        private MovimentacaoModel movimentacaoModel;
        private UtilDateModel model;
        private JComboBox<CategoriaModel> comboBoxCategoria;
        private JComboBox<CidadeModel> comboBoxCidade;
        private Component checkQTA;
        private JComboBox<ContaBancariaModel> comboBoxConta;
        private JMoneyField textFieldValor;

        private ArrayList<ContaBancariaModel> listaDeContasSelecionadas;
        private JTable tableSaldo;
        private JTable footerTable;

        private PosicaoFinanceiraModel posicaoFinanceiraModel;
        private JPanel panelInfoLancamento;
        private TableRowSorter<TableModel> sorter;
        protected int columnModelIndex;
        protected boolean descendente = true;
        private JButton buttonAddMovimentacao;
        private JButton buttonEditar;
        private JButton buttonExcluir;
        private AbstractButton buttonAlterarData;
        private JDatePickerImpl datePicker;
        private JDatePanelImpl datePanel;
        private ImageIcon imageIconCancelar;
        private ImageIcon imageIconEditar;
        private boolean obtendoPosicao = false;
        private JFormattedTextField formatText;

//	private Moviment

        public OcorrenciaInternalFrame(FuncionarioModel funcionarioModel) throws Exception {

            setTitle("Lançamento de Ocorrências");

//            if (funcionarioModel.getPermissaoMovFinanceiraModel().isPermiteAcesso() == false) {
//             //   throw new Exception("Usuário sem permissao para acessar esse modulo");
//            }

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

            datePanel = new JDatePanelImpl(model, p);
            datePanel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // System.out.println("da");
                    acaoObterPosicao();
                }
            });

            datePanel.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));

            // datePanel.setBounds(24, 64, 267, 193);

            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            datePicker.setLocation(24, 21);
            datePicker.setSize(267, 37);
            datePicker.setTextEditable(true);
            datePicker.getJFormattedTextField().addFocusListener(new FocusListener() {

                @Override
                public void focusLost(FocusEvent e) {
                    String dataString = datePicker.getJFormattedTextField().getText();
                    SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
                    Date data1;
                    try {
                        data1 = spf.parse(dataString);
                        Calendar cal1 = Calendar.getInstance();
                        cal1.setTime(data1);

                        Calendar cal2 = Calendar.getInstance();
                        cal2.setTime(model.getValue());

                        if (DateUtils.isSameDay(cal1, cal2) == false) {
                            datePicker.getInternalEventHandler().propertyChange(null);
                            acaoObterPosicao();
                        }

                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }

                }

                @Override
                public void focusGained(FocusEvent e) {
                    // TODO Auto-generated method stub

                }
            });

            datePicker.getJFormattedTextField().addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        datePicker.getInternalEventHandler().propertyChange(null);
                        acaoObterPosicao();
                    }
                }
            });

            panel.setLayout(null);
            panel.add(datePicker);
            // panel.add(datePanel);

            jTableMovimentacoes = new JTable() {
            };
            // jTableMovimentacoes.setAutoCreateRowSorter(true);
            jTableMovimentacoes.getTableHeader().setDefaultRenderer(new SortableHeaderRenderer(jTableMovimentacoes.getTableHeader().getDefaultRenderer()));
            // table.getColumnModel().getColumn(0).setMinWidth(0);
            // table.getColumnModel().getColumn(0).setMaxWidth(0);
            jTableMovimentacoes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTableMovimentacoes.setFillsViewportHeight(true);

            // table.setModel(
            jTableMovimentacoes.setModel(new MovimentacaoTableModel());
            jTableMovimentacoes.getColumnModel().getColumn(0).setMinWidth(0);
            jTableMovimentacoes.getColumnModel().getColumn(0).setMaxWidth(0);
            jTableMovimentacoes.getTableHeader().addMouseListener(new MouseListener() {

                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TableColumnModel colModel = table.getColumnModel();
                    // int columnModelIndex = colModel.getColumnIndexAtX(e.getX());
                    // int modelIndex = colModel.getColumn(columnModelIndex)
                    if (getMovimentacaoModel().getId() == null) {
                        TableColumnModel colModel = jTableMovimentacoes.getColumnModel();
                        int colIndex = colModel.getColumnIndexAtX(e.getX());
                        if (colIndex == 7) {
                            colIndex = 0;
                        }
                        if (colIndex == columnModelIndex) {
                            if (descendente == false) {
                                colIndex = 0;
                            }
                            descendente = !descendente;
                        } else {
                            descendente = true;
                        }
                        columnModelIndex = colIndex;

                        // jTableMovimentacoes.getTableHeader().repaint();
                        // jTableMovimentacoes.repaint();
                        acaoObterPosicao();
                    }

                }
            });

            // jTableMovimentacoes.setAutoCreateRowSorter(true);

            // table.getTableHeader().setReorderingAllowed(false);
            // table.setRowSorter(null);
            JScrollPane scrollPaneLancamentos = new JScrollPane(jTableMovimentacoes);
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
            panelInfoLancamento.setBounds(5, 80, 321, 539);
            panelInfoLancamento.setOpaque(false);
            getContentPane().add(panelInfoLancamento);
            panelInfoLancamento.setLayout(null);
            Color customColor = new Color(10, 10, 255);

            ButtonGroup buttonGroupLancamentoTipo = new ButtonGroup();

            JLabel lblValor = new JLabel("Valor:");
            lblValor.setBounds(10, 398, 46, 14);
            panelInfoLancamento.add(lblValor);

            textFieldValor = new JMoneyField();
            textFieldValor.setEditable(true);
            textFieldValor.setFont(new Font("Tahoma", Font.BOLD, 15));
            textFieldValor.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        int dialogButton = JOptionPane.YES_NO_OPTION;
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Confirma o Lançamento?", "Confirma", dialogButton);
                        if (dialogResult == JOptionPane.YES_OPTION) {
                            acaoAdicionarLancamento();
                        }
                    }

                }
            });

            textFieldValor.setBounds(10, 415, 301, 45);
            panelInfoLancamento.add(textFieldValor);

            comboBoxCidade = new JComboBox<CidadeModel>();
            comboBoxCidade.setBounds(10, 270, 240, 43);

            panelInfoLancamento.add(comboBoxCidade);
            AutoCompleteDecorator.decorate(comboBoxCidade);

            JLabel labelCidade = new JLabel("Cidade:");
            labelCidade.setBounds(10, 249, 240, 15);
            panelInfoLancamento.add(labelCidade);

            JLabel lblVeiculo = new JLabel("Veículo:");
            lblVeiculo.setBounds(10, 175, 240, 14);
            panelInfoLancamento.add(lblVeiculo);

            comboBoxCategoria = new JComboBox<CategoriaModel>();
            AutoCompleteDecorator.decorate(comboBoxCategoria);
            comboBoxCategoria.setSelectedIndex(-1);
            comboBoxCategoria.setBounds(10, 195, 240, 43);
            panelInfoLancamento.add(comboBoxCategoria);

            formattedTextFieldOcorrenciaNumero = new JFormattedTextField();
            formattedTextFieldOcorrenciaNumero.setBounds(10, 45, 149, 43);
            panelInfoLancamento.add(formattedTextFieldOcorrenciaNumero);
            formattedTextFieldOcorrenciaNumero.setColumns(10);

            JLabel labelDescricao = new JLabel("Nº:");
            labelDescricao.setBounds(10, 24, 80, 15);
            panelInfoLancamento.add(labelDescricao);

            checkQTA = new JCheckBox("QTA");
            checkQTA.setBounds(10, 470, 109, 23);
            panelInfoLancamento.add(checkQTA);

            JLabel labelConta = new JLabel("Conta:");
            labelConta.setBounds(10, 324, 46, 14);
            panelInfoLancamento.add(labelConta);

            comboBoxConta = new JComboBox<ContaBancariaModel>();
            comboBoxConta.setBounds(10, 344, 301, 43);
            panelInfoLancamento.add(comboBoxConta);

            JButton buttonAddCategoria = new JButton(Icone.novo("btAdic2.gif"));
            buttonAddCategoria.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        GrupoListInternalFrame grupoListInternalFrame = new GrupoListInternalFrame() {

                            @Override
                            public void acaoSalvar() {

                                try {
                                    Conexao.Executar(new Comando() {

                                        @Override
                                        public void execute(Session session) throws Exception {
                                            CategoriaModel model = getFormCadPanel().getModel();
                                            CategoriaModel modelAux = negocio.salvar(model);
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

            JLabel lblHora = new JLabel("Hora Envio:");
            lblHora.setBounds(169, 25, 80, 15);
            panelInfoLancamento.add(lblHora);

            MaskFormatter mask = null;
            try {
                mask = new MaskFormatter("##:##:##");//the # is for numeric values
                mask.setPlaceholderCharacter('#');
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //
            // Create a formatted text field that accept a valid time.
            //
            SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
            String dateString = formatter.format(new Date());
            
           
            
            try {
                MaskFormatter maskFormatter = new MaskFormatter("**:**");
                maskFormatter.setPlaceholderCharacter('_');

                formatText = new JFormattedTextField(maskFormatter);
                formatText.setFocusLostBehavior(JFormattedTextField.PERSIST);
                formatText.addKeyListener(new KeyAdapter() {
                    public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();
                    }
                    }
                });
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
           
            formatText.setBounds(169, 46, 142, 43);
            formatText.setText(dateString);
            
            panelInfoLancamento.add(formatText);
            
            
            buttonAddMovimentacao = new JButton(Icone.novo("chamado_concluido_16x16.png"));
            buttonAddMovimentacao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    acaoAdicionarLancamento();
                }
            });
            buttonAddMovimentacao.setText("Adicionar Lançamento");
            buttonAddMovimentacao.setBounds(5, 630, 321, 67);
            getContentPane().add(buttonAddMovimentacao);

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
            String[] colunasTableSaldo = new String[] { "Conta", "Ch. Especial", "Saldo + Ch. Especial", "Saldo" };
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
            // footerTable.getTableHeader()

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

            buttonExcluir = new JButton(Icone.novo("btExcluir.png"));
            buttonExcluir.setText("Excluir ");

            buttonAlterarData = new JButton(Icone.novo("btAgenda2-old.png"));
            buttonAlterarData.setText("Alterar Data");

            buttonEditar = new JButton(Icone.novo("btEditar.png"));

            imageIconCancelar = Icone.novo("btCancelarOP.gif");
            imageIconEditar = Icone.novo("btEditar.gif");
            // buttonEditar.setText("Editar ");
            buttonEditar.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    if (getMovimentacaoModel().getId() == null) {
                        MovimentacaoModel model = getModelSelecionado();
                        if (model == null) {
                            JOptionPane.showMessageDialog(null, "Selecione um registro");
                        } else {
                            setMovimentacaoModel(getModelSelecionado());
                        }
                    } else {
                        setMovimentacaoModel(factoryMovimentacaoModel());
                        acaoObterPosicao();
                    }

                }
            });

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

            buttonBarComponent.setLayout(new MigLayout("", "[150px][150px][150px][250][150px]", "[50px]"));

            // buttonBarComponent.btAlterar.setp
            buttonBarComponent.add(buttonAlterarData, "cell 0 0,grow");
            buttonBarComponent.add(buttonExcluir, "cell 1 0,grow");
            buttonBarComponent.add(buttonEditar, "cell 2 0,grow");
            buttonBarComponent.add(btSair, "cell 4 0,grow");

            buttonAlterarData.addActionListener(new ActionListener() {

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
            buttonExcluir.addActionListener(new ActionListener() {

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

                        ArrayList<CidadeModel> listaDeCidades = new Cidade().obterTodos(CidadeModel.class);
                        for (CidadeModel cidadeModel : listaDeCidades) {
                            comboBoxCidade.addItem(cidadeModel);
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
            // if
            // (funcionarioModel.getPermissaoMovFinanceiraModel().isVisualizarSaldoConta())
            // {
            // panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
            // panel_5.add(tabbedPaneSaldoEmConta, BorderLayout.SOUTH);
            // } else {
            panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
            jTableMovimentacoes.getColumnModel().getColumn(6).setMinWidth(0);
            jTableMovimentacoes.getColumnModel().getColumn(6).setMaxWidth(0);
            // }

        }

        protected void acaoAdicionarLancamento() {
            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {
                        MovimentacaoModel movimentacaoModel = getMovimentacaoModel();
                        if (movimentacaoModel.getId() == null) {
                            movimentacaoModel.setUsuarioSave(PrincipalDesktop.getUsarioLogado());
                        } else {
                            movimentacaoModel.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
                        }
                        new Movimentacao().merge(movimentacaoModel);
                    }
                });
                acaoObterPosicao();
                setMovimentacaoModel(factoryMovimentacaoModel());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(OcorrenciaInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
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

        private class SortableHeaderRenderer implements TableCellRenderer
            {
                private TableCellRenderer tableCellRenderer;

                public SortableHeaderRenderer(TableCellRenderer tableCellRenderer) {
                    this.tableCellRenderer = tableCellRenderer;
                }

                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                    Component c = tableCellRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (c instanceof JLabel) {
                        if (column == columnModelIndex) {
                            JLabel l = (JLabel) c;
                            l.setHorizontalTextPosition(JLabel.LEFT);
                            int modelColumn = table.convertColumnIndexToModel(column);
                            l.setIcon(new Arrow(descendente, 15, 0));
                            if (descendente) {
                                l.putClientProperty("Table.sortOrder", "DESCENDING");
                            } else {
                                l.putClientProperty("Table.sortOrder", "ASCENDING");
                            }
                            table.repaint();
                            table.getTableHeader().repaint();
                            l.repaint();
                        }
                        // .getSize()));
                    }
                    return c;
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
            MovimentacaoModel model = new MovimentacaoModel();
            model.setTipo(MovimentacaoFinanceiraTypes.debito);
            return model;
        }

        private void acaoObterPosicao() {
            if (obtendoPosicao == false) {
                obtendoPosicao = true;

                try {
                    Conexao.Executar(new Comando() {

                        @Override
                        public void execute(Session session) throws Exception {

                            String orderby = null;

                            if (columnModelIndex == 1) {
                                orderby = "descricao ";
                            }
                            if (columnModelIndex == 2) {
                                orderby = "p.conta.nome ";
                            }

                            if (columnModelIndex == 3) {
                                orderby = "tipo ";
                            }
                            if (columnModelIndex == 4) {
                                orderby = "valor ";
                            }

                            if (columnModelIndex == 5) {
                                orderby = "executado ";
                            }

                            if (columnModelIndex == 6) {
                                orderby = "dataHoraExecutado ";
                            }

                            PosicaoFinanceiraModel posicaoFinanceiraModel = new PosicaoFinanceira().obterPosicoesFinanceira(model.getValue(), orderby, descendente);
                            setPosicaoFinanceiraModel(posicaoFinanceiraModel);

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            obtendoPosicao = false;

        }

        public void setPosicaoFinanceiraModel(PosicaoFinanceiraModel posicaoFinanceiraModel) {
            this.posicaoFinanceiraModel = posicaoFinanceiraModel;
            MovimentacaoTableModel defaultTableModelMovimentacoes = (MovimentacaoTableModel) jTableMovimentacoes.getModel();
            DefaultTableModel defaultTableModelPosBancaria = (DefaultTableModel) tableSaldo.getModel();
            DefaultTableModel defaultTableModelPosBancariaFooter = (DefaultTableModel) footerTable.getModel();

            defaultTableModelMovimentacoes.setRowCount(0);
            defaultTableModelPosBancaria.setRowCount(0);
            defaultTableModelPosBancariaFooter.setRowCount(0);

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            if (posicaoFinanceiraModel != null) {

                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Inicial ", "", "", "", false, "", currencyFormat(posicaoFinanceiraModel.getSaldoInicialComPosicaoBancaria()) });
                BigDecimal bigDecimalSaldoGlobal = posicaoFinanceiraModel.getSaldoInicialComPosicaoBancaria();
                for (MovimentacaoModel movimentacaoModel : posicaoFinanceiraModel.getListaDeMovimentacoes()) {
                    String dataPagoRecebido = "";
                    if (movimentacaoModel.getDataHoraExecutado() != null) {
                        dataPagoRecebido = formatter.format(movimentacaoModel.getDataHoraExecutado());
                    }
                    if (movimentacaoModel.isExecutado()) {
                        bigDecimalSaldoGlobal = bigDecimalSaldoGlobal.add(movimentacaoModel.getValor());
                    }

                    defaultTableModelMovimentacoes.addRow(new Object[] { movimentacaoModel.getId().toString(), movimentacaoModel.getDescricao(), movimentacaoModel.getConta().getNome(), movimentacaoModel.getTipo().toString(), currencyFormat(movimentacaoModel.getValor()), movimentacaoModel.isExecutado(), dataPagoRecebido, currencyFormat(bigDecimalSaldoGlobal) });
                }
                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Final ", "", "", "", false, "", currencyFormat(posicaoFinanceiraModel.getSaldoFinalExecutado()) });
//              
                BigDecimal bigDecimalSaldoGloalMaisCheque = new BigDecimal(BigInteger.ZERO);
                for (ContaBancariaModel contaBancariaModel : posicaoFinanceiraModel.getListaDeContasBancarias()) {
                    BigDecimal bigDecimalSaldoContaComMov = contaBancariaModel.getSaldoInicial().add(contaBancariaModel.getPosicaoAux().getSaldo());
                    for (MovimentacaoModel movimentacaoModel : posicaoFinanceiraModel.getListaDeMovimentacoes()) {
                        if (movimentacaoModel.isExecutado()) {
                            if (movimentacaoModel.getConta().getId().compareTo(contaBancariaModel.getId()) == 0) {
                                bigDecimalSaldoContaComMov = bigDecimalSaldoContaComMov.add(movimentacaoModel.getValor());
                            }
                        }
                    }
                    BigDecimal bigDecimalSaldoMaisCheque = bigDecimalSaldoContaComMov.add(contaBancariaModel.getChequeEspecial());
                    String saldo = currencyFormat(bigDecimalSaldoContaComMov);
                    if (bigDecimalSaldoContaComMov.compareTo(BigDecimal.ZERO) == -1) {
                        saldo = "<HTML><FONT COLOR=RED>" + saldo + "</HTML>";
                    }
                    bigDecimalSaldoGloalMaisCheque = bigDecimalSaldoGloalMaisCheque.add(bigDecimalSaldoMaisCheque);
                    defaultTableModelPosBancaria.addRow(new String[] { contaBancariaModel.getNome(), currencyFormat(contaBancariaModel.getChequeEspecial()), currencyFormat(bigDecimalSaldoMaisCheque), saldo });
                }
                defaultTableModelPosBancariaFooter.addRow((new String[] { "<HTML><B>TOTAL</B></HTML> ", "", currencyFormat(bigDecimalSaldoGloalMaisCheque), currencyFormat(posicaoFinanceiraModel.getSaldoFinalExecutado()) }));

//                BigDecimal bigDecimalSaldoGlobal = posicaoFinanceiraModel.getCalcSaldoInicial();
//                BigDecimal bigDecimalSaldoGloalMaisCheque = new BigDecimal(BigInteger.ZERO);
//                for (PosicaoBancariaModel posBancariaModel : posicaoFinanceiraModel.getListaDePosicoesBancarias()) {
//                    BigDecimal bigDecimalSaldoMaisCheque = posBancariaModel.getContaBancariaSelecionada().getChequeEspecial().add(posBancariaModel.getCalcSaldoFinal());
//                    bigDecimalSaldoGloalMaisCheque = bigDecimalSaldoGloalMaisCheque.add(bigDecimalSaldoMaisCheque);
//
//                    String saldo = currencyFormat(posBancariaModel.getCalcSaldoFinal());
//                    if (posBancariaModel.getCalcSaldoFinal().compareTo(BigDecimal.ZERO) == -1) {
//                        saldo = "<HTML><FONT COLOR=RED>" + saldo + "</HTML>";
//                    }
//
//                    defaultTableModelPosBancaria.addRow(new String[] { posBancariaModel.getContaBancariaSelecionada().getNome(), currencyFormat(posBancariaModel.getContaBancariaSelecionada().getChequeEspecial()), currencyFormat(bigDecimalSaldoMaisCheque), saldo });
//
//                    for (MovimentacaoModel movimentacaoModel : posBancariaModel.getListaDeMovimentacao()) {
//                        if (movimentacaoModel.isExecutado()) {
//                            bigDecimalSaldoGlobal = bigDecimalSaldoGlobal.add(movimentacaoModel.getValor());
//                        }
//                        String dataPagoRecebido = "";
//                        if (movimentacaoModel.getDataHoraExecutado() != null) {
//                            dataPagoRecebido = formatter.format(movimentacaoModel.getDataHoraExecutado());
//                        }
//
//                        defaultTableModelMovimentacoes.addRow(new Object[] { movimentacaoModel.getId().toString(), movimentacaoModel.getDescricao(), movimentacaoModel.getConta().getNome(), movimentacaoModel.getTipo().toString(), currencyFormat(movimentacaoModel.getValor()), movimentacaoModel.isExecutado(), dataPagoRecebido, currencyFormat(bigDecimalSaldoGlobal) });
//                    }
//                }
//                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Final ", "", "", "", false, "", currencyFormat(posicaoFinanceiraModel.getCalcSaldoFinal()) });

            }

        }
        
        private MaskFormatter createFormatter(String s) {
            MaskFormatter formatter = null;
            try {
              formatter = new MaskFormatter(s);
              formatter.setPlaceholderCharacter('_');
            } catch (java.text.ParseException exc) {
              System.err.println("formatter is bad: " + exc.getMessage());
            }
            return formatter;
          }

        public PosicaoFinanceiraModel getPosicaoFinanceiraModel() {
            return posicaoFinanceiraModel;
        }

        private MovimentacaoModel getModelSelecionado() {

            if (jTableMovimentacoes.getSelectedRow() == -1) {
                return null;
            } else {
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTableMovimentacoes.getModel();
                String id = defaultTableModel.getValueAt(jTableMovimentacoes.getSelectedRow(), 0).toString().trim();

                MovimentacaoModel modelSelecionado = getModelPorID(id);

                return modelSelecionado;
            }
        }

        private MovimentacaoModel getModelPorID(String id) {
            MovimentacaoModel modelSelecionado = null;
            for (MovimentacaoModel model : getPosicaoFinanceiraModel().getListaDeMovimentacoes()) {
                if (model.getId().toString().equals(id)) {
                    modelSelecionado = model;
                    break;
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
            movimentacaoModel.setCliente((ClienteModel) comboBoxCidade.getSelectedItem());
            movimentacaoModel.setTipo(MovimentacaoFinanceiraTypes.credito);
          
            movimentacaoModel.setDescricao(formattedTextFieldOcorrenciaNumero.getText());
            movimentacaoModel.setValor(BigDecimal.valueOf(textFieldValor.getValor()));
            movimentacaoModel.setDataHora(model.getValue());
            return movimentacaoModel;
        }

        public void setMovimentacaoModel(MovimentacaoModel movimentacaoModel) {
            this.movimentacaoModel = movimentacaoModel;

            formattedTextFieldOcorrenciaNumero.setText(movimentacaoModel.getDescricao());
            // comboBoxCategoria.setSelectedItem(anObject);
            if (movimentacaoModel.getTipo() == null) {
              //  radioButtonCredito.setSelected(true);
            } else {
                if (movimentacaoModel.getTipo() == MovimentacaoFinanceiraTypes.credito) {
                  //  radioButtonCredito.setSelected(true);

                }
                if (movimentacaoModel.getTipo() == MovimentacaoFinanceiraTypes.debito) {
                    //radioButtonDebito.setSelected(true);

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

            if (movimentacaoModel.getId() != null) {
                buttonAlterarData.setEnabled(false);
                buttonExcluir.setEnabled(false);

                jTableMovimentacoes.setEnabled(false);
                jTableMovimentacoes.getTableHeader().setEnabled(false);

                buttonEditar.setText("Cancelar");
                buttonEditar.setIcon(imageIconCancelar);

                datePicker.getJFormattedTextField().setEnabled(false);
                datePicker.getButton().setEnabled(false);
                buttonAddMovimentacao.setText("Alterar Lançamento");

                comboBoxCategoria.setSelectedItem(movimentacaoModel.getCategoria());
                comboBoxCidade.setSelectedItem(movimentacaoModel.getCliente());
                comboBoxConta.setSelectedItem(movimentacaoModel.getConta());
            } else {
                buttonAlterarData.setEnabled(true);
                buttonExcluir.setEnabled(true);
                jTableMovimentacoes.setEnabled(true);
                jTableMovimentacoes.getTableHeader().setEnabled(true);

                buttonEditar.setText("Editar");
                buttonEditar.setIcon(imageIconEditar);

                datePicker.getJFormattedTextField().setEnabled(true);
                datePicker.getButton().setEnabled(true);
                buttonAddMovimentacao.setText("Adicionar Lançamento");

                comboBoxCategoria.setSelectedIndex(0);
                comboBoxCidade.setSelectedIndex(-1);
                comboBoxConta.setSelectedIndex(0);
            }

        }

        private static class Arrow implements Icon
            {
                private boolean descending;
                private int size;
                private int priority;

                public Arrow(boolean descending, int size, int priority) {
                    this.descending = descending;
                    this.size = size;
                    this.priority = priority;
                }

                public void paintIcon(Component c, Graphics g, int x, int y) {
                    Color color = c == null ? Color.DARK_GRAY : c.getBackground();

                    // In a compound sort, make each succesive triangle 20%
                    // smaller than the previous one.
                    int dx = (int) (size / 2 * Math.pow(0.8, priority));
                    int dy = descending ? dx : -dx;
                    // Align icon (roughly) with font baseline.
                    y = y + 5 * size / 6 + (descending ? -dy : 0);
                    int shift = descending ? 1 : -1;
                    g.translate(x, y);

                    // Right diagonal.
                    g.setColor(color.darker());
                    g.drawLine(dx / 2, dy, 0, 0);
                    g.drawLine(dx / 2, dy + shift, 0, shift);

                    // Left diagonal.
                    g.setColor(color.brighter());
                    g.drawLine(dx / 2, dy, dx, 0);
                    g.drawLine(dx / 2, dy + shift, dx, shift);

                    // Horizontal line.
                    if (descending) {
                        g.setColor(color.darker().darker());
                    } else {
                        g.setColor(color.brighter().brighter());
                    }
                    g.drawLine(dx, 0, 0, 0);

                    g.setColor(color);
                    g.translate(-x, -y);
                }

                public int getIconWidth() {
                    return size;
                }

                public int getIconHeight() {
                    return size;
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

                                confirmaAlteracao = false;
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
                                            movimentacao.executar(movimentacaoModel, (boolean) aValue, dataAux);
                                        }
                                    });
                                    rowData.set(5, (boolean) aValue);

                                    fireTableCellUpdated(row, column);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    JOptionPane.showMessageDialog(OcorrenciaInternalFrame.this, e.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);

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
