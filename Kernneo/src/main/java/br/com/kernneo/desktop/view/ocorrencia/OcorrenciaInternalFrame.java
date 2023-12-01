package br.com.kernneo.desktop.view.ocorrencia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.model.VeiculoModel;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.financeiro.DatePickerDialog;
import br.com.kernneo.desktop.view.hospital.HospitalFormCadPanel;
import br.com.kernneo.desktop.view.hospital.HospitalListInternalFrame;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JPanelCustom;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.dao.OcorrenciaDAO;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Hospital;
import br.com.kernneo.server.negocio.Ocorrencia;
import br.com.kernneo.server.negocio.Veiculo;
import net.miginfocom.swing.MigLayout;

public class OcorrenciaInternalFrame extends JInternalFrame
    {

        private Ocorrencia    ocorrencia; 
        private List<OcorrenciaModel> listaDeOcorrencias; 
        private JTable jTableOcorrencias;
        private JFormattedTextField jFormattedTextFieldOcorrenciaNumero;
        private JFormattedTextField jFormattedTextFieldOcorrenciaHora;
        
        private OcorrenciaModel ocorrenciaModel;
        private UtilDateModel model;
        private JComboBox<VeiculoModel> comboboxVeiculo;
        private JComboBox<HospitalModel> comboBoxHospital;

        private JCheckBox checkQTA;

        private ArrayList<ContaBancariaModel> listaDeContasSelecionadas;

        private PosicaoFinanceiraModel posicaoFinanceiraModel;
        private JPanelCustom panelInfoLancamento;
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
        private boolean obtendoOcorrencias = false;
        private JTextField textField;
        private JTextField textFieldOutros;
        protected boolean mostraOutros;
        private JTextArea textAreaObservacao;
        private JScrollPane scrollPaneObservacao;
        private JPanel panel_5;
        private JButton btSair;
        private JComboBox<CidadeModel> comboBoxCidade;
        private SimpleDateFormat formatterHora;
        private OcorrenciaDetalheNaturezaPanel ocorrenciaDetalhePanel;

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
            setSize(1657, 910);
            setIconifiable(true);
            getContentPane().setLayout(null);
            
            ocorrencia = new Ocorrencia();
      

            Properties p = new Properties();
            p.put("text.today", "Hoje");
            p.put("text.month", "Mês");
            p.put("text.year", "Ano");

            model = new UtilDateModel();
            model.setSelected(true);

            datePanel = new JDatePanelImpl(model, p);
            datePanel.getInternalView().setPreferredSize(new java.awt.Dimension(267, 193));
            datePanel.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    acaoOcorrenciasAtualizar();
                }
            });

            datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
            datePicker.getJFormattedTextField().setFont(new Font("Tahoma", Font.BOLD, 11));
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
                            acaoOcorrenciasAtualizar();
                        }

                    } catch (ParseException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, e1.getMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
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
                                acaoOcorrenciasAtualizar();
                            }

                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            JOptionPane.showMessageDialog(null, e1.getMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            });
            JPanel panelOcorrenciaData = new JPanel();
            panelOcorrenciaData.setBorder(new TitledBorder(null, "Selecione uma Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panelOcorrenciaData.setBounds(10, 5, 315, 77);
            panelOcorrenciaData.setLayout(null);
            panelOcorrenciaData.add(datePicker);
            getContentPane().add(panelOcorrenciaData);

            jTableOcorrencias = new JTable();
            jTableOcorrencias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTableOcorrencias.setFillsViewportHeight(true);
            jTableOcorrencias.setModel(new OcorrenciaTableModel());
            jTableOcorrencias.getColumnModel().getColumn(0).setMinWidth(0);
            jTableOcorrencias.getColumnModel().getColumn(0).setMaxWidth(0);

            JScrollPane scrollPaneLancamentos = new JScrollPane(jTableOcorrencias);
            scrollPaneLancamentos.setMinimumSize(new Dimension(341, 100));

            JPanel jPanelLancamento = new JPanel();
            jPanelLancamento.setBackground(SystemColor.controlHighlight);

            BorderLayout bl_panel_lancamento = new BorderLayout();
            bl_panel_lancamento.setHgap(10);
            bl_panel_lancamento.setVgap(10);
            jPanelLancamento.setLayout(bl_panel_lancamento);

            panelInfoLancamento = new JPanelCustom() {

            };

            // panelInfoLancamento.setBackground();
            panelInfoLancamento.setBorder(new TitledBorder(null, "Informaçoes de Lançamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            jPanelLancamento.setBounds(10, 85, 585, 677);
            jPanelLancamento.add(panelInfoLancamento);
            getContentPane().add(jPanelLancamento);
            panelInfoLancamento.setLayout(null);

            JLabel lblValor = new JLabel("Natureza do Chamado: ");
            lblValor.setBounds(10, 250, 301, 14);
            panelInfoLancamento.add(lblValor);

            JLabel labelCidade = new JLabel("Bairro:");
            labelCidade.setBounds(10, 100, 240, 15);
            panelInfoLancamento.add(labelCidade);

            JLabel lblVeiculo = new JLabel("Veículo:");
            lblVeiculo.setBounds(321, 25, 240, 14);
            panelInfoLancamento.add(lblVeiculo);

            comboboxVeiculo = new JComboBox<VeiculoModel>();
            comboboxVeiculo.setSelectedIndex(-1);
            comboboxVeiculo.setBounds(321, 44, 254, 43);
            AutoCompleteDecorator.decorate(comboboxVeiculo);

            panelInfoLancamento.add(comboboxVeiculo);

            MaskFormatter maskFormatterNumeroOcorrencia = new MaskFormatter("****");
            maskFormatterNumeroOcorrencia.setPlaceholderCharacter('_');

            jFormattedTextFieldOcorrenciaNumero = new JFormattedTextField(maskFormatterNumeroOcorrencia);
            jFormattedTextFieldOcorrenciaNumero.setFont(new Font("Tahoma", Font.BOLD, 15));
            jFormattedTextFieldOcorrenciaNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
            jFormattedTextFieldOcorrenciaNumero.setBounds(10, 45, 149, 43);
            jFormattedTextFieldOcorrenciaNumero.setColumns(10);
            jFormattedTextFieldOcorrenciaNumero.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();
                    }
                }
            });
            panelInfoLancamento.add(jFormattedTextFieldOcorrenciaNumero);

            JLabel labelDescricao = new JLabel("Nº:");
            labelDescricao.setBounds(10, 25, 80, 15);
            panelInfoLancamento.add(labelDescricao);

            JLabel labelConta = new JLabel("Hospital:");
            labelConta.setBounds(10, 175, 124, 14);
            panelInfoLancamento.add(labelConta);

            comboBoxHospital = new JComboBox<HospitalModel>();
            comboBoxHospital.setBounds(10, 195, 240, 43);
            AutoCompleteDecorator.decorate(comboBoxHospital);
            panelInfoLancamento.add(comboBoxHospital);

            JLabel lblHora = new JLabel("Hora Envio:");
            lblHora.setBounds(169, 25, 80, 15);
            panelInfoLancamento.add(lblHora);

            formatterHora = new SimpleDateFormat("HH:mm");
            String dateString = formatterHora.format(new Date());

            MaskFormatter maskFormatter = new MaskFormatter("**:**");
            maskFormatter.setPlaceholderCharacter('_');

            jFormattedTextFieldOcorrenciaHora = new JFormattedTextField(maskFormatter);
            jFormattedTextFieldOcorrenciaHora.setFont(new Font("Tahoma", Font.BOLD, 15));
            jFormattedTextFieldOcorrenciaHora.setFocusLostBehavior(JFormattedTextField.PERSIST);
            jFormattedTextFieldOcorrenciaHora.setBounds(169, 45, 142, 43);
            jFormattedTextFieldOcorrenciaHora.setText(dateString);
            jFormattedTextFieldOcorrenciaHora.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();
                    }
                }
            });

            panelInfoLancamento.add(jFormattedTextFieldOcorrenciaHora);

            comboBoxCidade = new JComboBox<CidadeModel>();
            AutoCompleteDecorator.decorate(comboBoxCidade);
            comboBoxCidade.setBounds(321, 120, 254, 43);
            panelInfoLancamento.add(comboBoxCidade);

            JComboBox<CidadeModel> comboBoxNaturezaChamado = new JComboBox<CidadeModel>();
            comboBoxNaturezaChamado.setBounds(10, 270, 301, 43);
            panelInfoLancamento.add(comboBoxNaturezaChamado);

            textAreaObservacao = new JTextArea();

            scrollPaneObservacao = new JScrollPane(textAreaObservacao, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPaneObservacao.setBounds(321, 195, 254, 69);

            panelInfoLancamento.add(scrollPaneObservacao);

            JLabel lblObervaol = new JLabel("Obervação:");
            lblObervaol.setBounds(321, 175, 124, 14);
            panelInfoLancamento.add(lblObervaol);

            JLabel lblCidade = new JLabel("Cidade:");
            lblCidade.setBounds(321, 100, 46, 14);
            panelInfoLancamento.add(lblCidade);

            checkQTA = new JCheckBox("QTA");
            checkQTA.setBounds(529, 171, 46, 23);
            panelInfoLancamento.add(checkQTA);

            textField = new JTextField();
            textField.setText((String) null);
            textField.setColumns(10);
            textField.setBounds(10, 120, 301, 43);
            panelInfoLancamento.add(textField);

            JButton buttonAddCliente = new JButton(Icone.novo("btAdic2.gif"));
            buttonAddCliente.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    acaoAdicionarHospital();
                }
            });
            buttonAddCliente.setBounds(255, 195, 56, 45);
            panelInfoLancamento.add(buttonAddCliente);

            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
            tabbedPane.setBounds(10, 324, 565, 259);
            panelInfoLancamento.add(tabbedPane);

             ocorrenciaDetalhePanel = new OcorrenciaDetalheNaturezaPanel();
            tabbedPane.addTab("Detalhe da Natureza", null, ocorrenciaDetalhePanel, null);

            textFieldOutros = new JTextField();
            textFieldOutros.setText((String) null);
            textFieldOutros.setColumns(10);
            textFieldOutros.setBounds(321, 270, 254, 43);
            panelInfoLancamento.add(textFieldOutros);

            buttonAddMovimentacao = new JButton(Icone.novo("chamado_concluido_16x16.png"));
            buttonAddMovimentacao.setBounds(316, 594, 259, 70);
            panelInfoLancamento.add(buttonAddMovimentacao);
            buttonAddMovimentacao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ocorrenciaAdicionar();
                }
            });
            buttonAddMovimentacao.setText("Adicionar Lançamento");
            DefaultTableCellRenderer dtcrFooter = new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    parent.getFont().deriveFont(Font.BOLD);
                    return parent;

                }
            };
            String[] colunasTableSaldo = new String[] { "Conta", "Ch. Especial", "Saldo + Ch. Especial", "Saldo" };

            // getContentPane().add(tabbedPaneSaldoEmConta);

            JPanel panel_4 = new JPanelCustom() {

            };
            panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lista de Ocorr\u00EAncias", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4.setBounds(600, 5, 1031, 757);
            BorderLayout bl_panel_4 = new BorderLayout();
            bl_panel_4.setVgap(5);
            panel_4.setLayout(bl_panel_4);

            buttonExcluir = new JButton(Icone.novo("btExcluir.png"));
            buttonExcluir.setText("Excluir ");

            buttonAlterarData = new JButton(Icone.novo("btAgenda2-old.png"));
            buttonAlterarData.setText("Alterar Data");

            buttonEditar = new JButton(Icone.novo("btEditar.png"));
            buttonEditar.setText("Editar");

            imageIconCancelar = Icone.novo("btCancelarOP.gif");
            imageIconEditar = Icone.novo("btEditar.gif");
            buttonEditar.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
//                    // TODO Auto-generated method stub
//                    if (getMovimentacaoModel().getId() == null) {
//                        MovimentacaoModel model = getModelSelecionado();
//                        if (model == null) {
//                            JOptionPane.showMessageDialog(null, "Selecione um registro");
//                        } else {
//                            setMovimentacaoModel(getModelSelecionado());
//                        }
//                    } else {
//                        setMovimentacaoModel(factoryMovimentacaoModel());
//                        acaoOcorrenciasAtualizar();
//                    }

                }
            });

            btSair = new JButton(Icone.novo("btSair.png"));
            btSair.setText("Sair");
            btSair.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();

                }
            });

            buttonAlterarData.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    acaoOcorrenciaeditarData();
                }
            });

            buttonExcluir.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    acaoOcorrenciaExcluir();
                }
            });

            JPanel panelBarraDeFerramentas = new JPanel();
            panelBarraDeFerramentas.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
            panelBarraDeFerramentas.setLayout(new MigLayout("", "[150px][150px][150px][450px][150px]", "[50px]"));
            panelBarraDeFerramentas.add(buttonAlterarData, "cell 0 0,grow");
            panelBarraDeFerramentas.add(buttonExcluir, "cell 1 0,grow");
            panelBarraDeFerramentas.add(buttonEditar, "cell 2 0,grow");
            panelBarraDeFerramentas.add(btSair, "cell 4 0,grow");
            panel_4.add(panelBarraDeFerramentas, BorderLayout.NORTH);

            getContentPane().add(panel_4);

            BorderLayout bl_panel_5 = new BorderLayout();

            panel_5 = new JPanel(bl_panel_5);
            panel_5.setBounds(1077, 546, 554, 212);
            panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
            panel_4.add(panel_5, BorderLayout.CENTER);

            JPanelCustom panel_1 = new JPanelCustom() {

            };
            panel_1.setBorder(new TitledBorder(null, "E-SUS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel_1.setBounds(331, 5, 264, 77);
            getContentPane().add(panel_1);
            panel_1.setLayout(new BorderLayout(0, 0));

            JLabel lblNewLabel = new JLabel("SAMU 192");
            lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 27));
            lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel_1.add(lblNewLabel, BorderLayout.CENTER);

           // jTableOcorrencias.getColumnModel().getColumn(6).setMinWidth(0);
           // jTableOcorrencias.getColumnModel().getColumn(6).setMaxWidth(0);
            // }
            try {
                Conexao.Executar(new Comando() {

                    @Override
                    public void execute(Session session) throws Exception {

                        ArrayList<VeiculoModel> listaDeVeiculos = new Veiculo().obterTodos(VeiculoModel.class);
                        for (VeiculoModel veiculoModel : listaDeVeiculos) {
                            comboboxVeiculo.addItem(veiculoModel);
                        }

                        ArrayList<CidadeModel> listaDeCidades = new Cidade().obterTodos(CidadeModel.class);
                        for (CidadeModel cidadeModel : listaDeCidades) {
                            comboBoxCidade.addItem(cidadeModel);
                        }

                        ArrayList<HospitalModel> listaDeHospital = new Hospital().obterTodos(HospitalModel.class);
                        for (HospitalModel hospitalModel : listaDeHospital) {
                            comboBoxHospital.addItem(hospitalModel);
                        }
                        
                        setOcorrenciaModel(factoryOocorrenciaModel());
                    }
                });
                acaoOcorrenciasAtualizar();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

                throw e1;
            }
        }

        protected void acaoAdicionarHospital() {
            try {
                HospitalListInternalFrame hospitalListInternalFrame = new HospitalListInternalFrame() {

                    @Override
                    public void acaoSalvar() {

                        try {
                            Long id = null;
                            Conexao.Executar(new Comando() {

                                @Override
                                public void execute(Session session) throws Exception {
                                    HospitalModel model = getFormCadPanel().getModel();
                                    HospitalModel modelAux = negocio.salvar(model);
                                    comboBoxHospital.addItem(modelAux);
                                    comboBoxHospital.setSelectedItem(modelAux);

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
                hospitalListInternalFrame.eventoBotaoIncluir();
                hospitalListInternalFrame.setVisible(true);
                hospitalListInternalFrame.setModoForm();

                hospitalListInternalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        hospitalListInternalFrame.setVisible(false);
                        hospitalListInternalFrame.dispose();

                    }
                });
                PrincipalDesktop.getjDesktopPane().add(hospitalListInternalFrame, 0);
                hospitalListInternalFrame.setSelected(true);
                PrincipalDesktop.getjDesktopPane().setSelectedFrame(hospitalListInternalFrame);
                // clienteListInternalFrame.setModoForm();
                HospitalFormCadPanel clienteFormCadPanel = (HospitalFormCadPanel) hospitalListInternalFrame.getFormCadPanel();
                clienteFormCadPanel.getTextFieldDescricao().requestFocus();
                clienteFormCadPanel.getTextFieldDescricao().selectAll();

            } catch (Exception excecao) {
                JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

            }
        }

        private OcorrenciaModel factoryOocorrenciaModel() {
            OcorrenciaModel model = new OcorrenciaModel();
            return model;
        }

        protected void acaoOcorrenciaeditarData() {
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
                                ocorrenciaEditarData(getModelSelecionado(), dataSelecionada);
                            }
                            this.setVisible(false);
                            this.dispose();
                        }
                    }
                };
                datePickerDialog.centerOnScreen(true);
                datePickerDialog.setVisible(true);
            }
        }

        protected void acaoOcorrenciaExcluir() {
            if (getModelSelecionado() == null) {
                JOptionPane.showMessageDialog(null, "Selecione um registro");
            } else {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir?", "Alerta", dialogButton);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    ocorrenciaExcluir(getModelSelecionado());
                }
            }
        }

        private void ocorrenciaExcluir(OcorrenciaModel modelSelecionado) {
            try {
                Conexao.Executar(new Comando() {
                    @Override
                    public void execute(Session session) throws Exception {
                        modelSelecionado.setUsuarioDelete(PrincipalDesktop.getUsarioLogado());
                        ocorrencia.excluir(modelSelecionado);
                    }
                });
                acaoOcorrenciasAtualizar();
                JOptionPane.showMessageDialog(this, "Registro Excluido com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
            }
        }

        protected void ocorrenciaAdicionar() {
            try {
                Conexao.Executar(new Comando() {
                    @Override
                    public void execute(Session session) throws Exception {
                        OcorrenciaModel model = catchOcorrenciaModel();
                        if (model.getId() == null) {
                            model.setUsuarioSave(PrincipalDesktop.getUsarioLogado());
                        } else {
                            model.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
                        }
                        ocorrencia.merge(model);
                    }
                });
                acaoOcorrenciasAtualizar();
                setOcorrenciaModel(factoryOocorrenciaModel());
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(OcorrenciaInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
            }
        }

        private void ocorrenciaEditarData(OcorrenciaModel modelSelecionado, Date dataSelecionada) {
            try {
                Conexao.Executar(new Comando() {
                    @Override
                    public void execute(Session session) throws Exception {
                        modelSelecionado.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
                        ocorrencia.alterarData(modelSelecionado, dataSelecionada);
                    }
                });
                acaoOcorrenciasAtualizar();
                JOptionPane.showMessageDialog(this, "Registro Alterado com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
            }
        }

       
        private void acaoOcorrenciasAtualizar() {
            if (obtendoOcorrencias == false) {
                obtendoOcorrencias = true;
                try {
                    Conexao.Executar(new Comando() {
                        @Override
                        public void execute(Session session) throws Exception {
                           OcorrenciaDAO ocorrenciaDAO  = new OcorrenciaDAO(); 
                           List<OcorrenciaModel> lista = ocorrenciaDAO.obterPorData(model.getValue(), "id", true);
                           setListaDeOcorrencias(lista);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            obtendoOcorrencias = false;
        }

       
        private OcorrenciaModel getModelSelecionado() {
            if (jTableOcorrencias.getSelectedRow() == -1) {
                return null;
            } else {
                DefaultTableModel defaultTableModel = (DefaultTableModel) jTableOcorrencias.getModel();
                String id = defaultTableModel.getValueAt(jTableOcorrencias.getSelectedRow(), 0).toString().trim();

                OcorrenciaModel modelSelecionado = getModelPorID(id);
                return modelSelecionado;
            }
        }

        private OcorrenciaModel getModelPorID(String id) {
            OcorrenciaModel modelSelecionado = null;
            for (OcorrenciaModel model : getListaDeOcorrencias()) {
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

       
        public OcorrenciaModel getOcorrenciaModel() {
            return ocorrenciaModel;
        }
        
        public OcorrenciaModel catchOcorrenciaModel() { 
            Calendar cHora = new GregorianCalendar();
            cHora.setTime(model.getValue());
            cHora.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jFormattedTextFieldOcorrenciaHora.getText().replace(":", "").substring(0, 2)));
            cHora.set(Calendar.MINUTE, Integer.parseInt(jFormattedTextFieldOcorrenciaHora.getText().replace(":", "").substring(2, 4)));
            cHora.set(Calendar.SECOND, 0);
            
            ocorrenciaModel.setNumero(jFormattedTextFieldOcorrenciaNumero.getText());
            ocorrenciaModel.setDataHora(cHora.getTime());
            ocorrenciaModel.setDetalhe(ocorrenciaModel.getDetalhe());
            ocorrenciaModel.setVeiculo((VeiculoModel) comboboxVeiculo.getSelectedItem());
            ocorrenciaModel.setCidade((CidadeModel) comboBoxCidade.getSelectedItem());
            
            ocorrenciaModel.setDetalhe(ocorrenciaDetalhePanel.getDetalheModel());
            
            
            return ocorrenciaModel; 
        }
       

        public void setOcorrenciaModel(OcorrenciaModel model) {
           
            
            this.ocorrenciaModel = model;
            ocorrenciaDetalhePanel.setDetalheModel(model.getDetalhe());
            
            if (ocorrenciaModel.getId() != null) {
                buttonAlterarData.setEnabled(false);
                buttonExcluir.setEnabled(false);

                jTableOcorrencias.setEnabled(false);
                jTableOcorrencias.getTableHeader().setEnabled(false);

                buttonEditar.setText("Cancelar");
                buttonEditar.setIcon(imageIconCancelar);

                datePicker.getJFormattedTextField().setEnabled(false);
                datePicker.getButton().setEnabled(false);
                buttonAddMovimentacao.setText("Alterar Lançamento");

                //comboboxVeiculo.setSelectedItem(ocorrenciaModel());

               // comboBoxHospital.setSelectedItem(movimentacaoModel.getConta());
            } else {
                buttonAlterarData.setEnabled(true);
                buttonExcluir.setEnabled(true);
                jTableOcorrencias.setEnabled(true);
                jTableOcorrencias.getTableHeader().setEnabled(true);

                buttonEditar.setText("Editar");
                buttonEditar.setIcon(imageIconEditar);

                datePicker.getJFormattedTextField().setEnabled(true);
                datePicker.getButton().setEnabled(true);
                buttonAddMovimentacao.setText("Adicionar Lançamento");

                comboboxVeiculo.setSelectedIndex(0);

                comboBoxHospital.setSelectedIndex(0);
                jFormattedTextFieldOcorrenciaHora.setText(formatterHora.format(new Date()));
                jFormattedTextFieldOcorrenciaNumero.setText("");
            }

        }

        public List<OcorrenciaModel> getListaDeOcorrencias() {
            return listaDeOcorrencias;
        }

        public void setListaDeOcorrencias(List<OcorrenciaModel> listaDeOcorrencias) {
            this.listaDeOcorrencias = listaDeOcorrencias;
            OcorrenciaTableModel defaultTableModelOcorrencias = (OcorrenciaTableModel) jTableOcorrencias.getModel();    
            defaultTableModelOcorrencias.setRowCount(0);
       
            if(listaDeOcorrencias != null) { 
                for (OcorrenciaModel ocorrenciaModel : listaDeOcorrencias) {
                    defaultTableModelOcorrencias.addRow(toRow(ocorrenciaModel));
                }
            }
        }
        
        public  Object[] toRow(OcorrenciaModel ocorrenciaModel) { 
            String veiculo = ocorrenciaModel.getVeiculo() != null ? ocorrenciaModel.getVeiculo().getNome() : ""; 
            String cidade = ocorrenciaModel.getCidade() != null ? ocorrenciaModel.getCidade().getNome() : ""; 
            return new Object[]{ocorrenciaModel.getId(),ocorrenciaModel.getNumero(),formatterHora.format(ocorrenciaModel.getDataHora()), veiculo, cidade};
        }
       

        
    }
