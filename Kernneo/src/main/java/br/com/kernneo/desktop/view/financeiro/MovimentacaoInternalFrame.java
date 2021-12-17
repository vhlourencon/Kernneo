package br.com.kernneo.desktop.view.financeiro;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import javax.swing.text.TabExpander;
import javax.swing.JInternalFrame;
import javax.swing.JToolBar;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;

import org.hibernate.Session;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.widget.ButtonBarComponent;
import br.com.kernneo.desktop.view.widget.CheckboxPanel;

import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JMoneyField;
import br.com.kernneo.desktop.view.widget.JPanelPad;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;
import br.com.kernneo.server.negocio.Movimentacao;
import br.com.kernneo.server.negocio.PosicaoFinanceira;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.SpringLayout;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JToggleButton;
import java.awt.Panel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

//	private Moviment

        public MovimentacaoInternalFrame() {

            setTitle("Movimentação Diaria");

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
            panel.setBounds(10, 10, 321, 77);
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

            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(341, 108, 810, 313);
            // scrollPane.setLayout(new MigLayout("grow,fill"));
            getContentPane().add(scrollPane);

            JPanel panel_1 = new JPanel();
            panel_1.setBorder(new TitledBorder(null, "Informaçoes de Lançamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            panel_1.setBounds(10, 98, 321, 516);
            getContentPane().add(panel_1);
            panel_1.setLayout(null);

            JLabel labelTipo = new JLabel("Tipo:");
            labelTipo.setBounds(10, 25, 46, 15);
            panel_1.add(labelTipo);

            JPanel panel_2 = new JPanel();
            panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
            panel_2.setBackground(Color.WHITE);
            panel_2.setBounds(10, 45, 301, 45);
            panel_1.add(panel_2);
            panel_2.setLayout(null);

            radioButtonCredito = new JRadioButton("Crédito");
            radioButtonCredito.setFont(new Font("Tahoma", Font.BOLD, 15));
            radioButtonCredito.setBounds(23, 9, 109, 23);
            panel_2.add(radioButtonCredito);

            radioButtonDebito = new JRadioButton("Débito");
            radioButtonDebito.setFont(new Font("Tahoma", Font.BOLD, 15));
            radioButtonDebito.setBounds(166, 9, 109, 23);
            panel_2.add(radioButtonDebito);

            ButtonGroup buttonGroupLancamentoTipo = new ButtonGroup();
            buttonGroupLancamentoTipo.add(radioButtonCredito);
            buttonGroupLancamentoTipo.add(radioButtonDebito);
            radioButtonCredito.setSelected(true);

            JLabel lblValor = new JLabel("Valor:");
            lblValor.setBounds(10, 408, 46, 14);
            panel_1.add(lblValor);

            textFieldValor = new JMoneyField();
            textFieldValor.setEditable(true);
            textFieldValor.setFont(new Font("Tahoma", Font.BOLD, 15));

            textFieldValor.setBounds(10, 426, 301, 45);
            panel_1.add(textFieldValor);

            comboBoxCliente = new JComboBox<ClienteModel>();
            comboBoxCliente.setBounds(10, 270, 301, 43);
            panel_1.add(comboBoxCliente);

            JLabel labelCliente = new JLabel("Cliente:");
            labelCliente.setBounds(10, 250, 46, 14);
            panel_1.add(labelCliente);

            JLabel lblNewLabel_1_1 = new JLabel("Categoria:");
            lblNewLabel_1_1.setBounds(10, 175, 95, 14);
            panel_1.add(lblNewLabel_1_1);

            comboBoxCategoria = new JComboBox<CategoriaModel>();
            AutoCompleteDecorator.decorate(comboBoxCategoria);
            comboBoxCategoria.setSelectedIndex(-1);
            comboBoxCategoria.setBounds(10, 195, 301, 43);
            panel_1.add(comboBoxCategoria);

            textFieldDescricao = new JTextField();
            textFieldDescricao.setBounds(10, 121, 301, 43);
            panel_1.add(textFieldDescricao);
            textFieldDescricao.setColumns(10);

            JLabel labelDescricao = new JLabel("Descrição:");
            labelDescricao.setBounds(10, 100, 80, 15);
            panel_1.add(labelDescricao);

            checkBoxRepetir = new JCheckBox("Repetir");
            checkBoxRepetir.setBounds(10, 478, 109, 23);
            panel_1.add(checkBoxRepetir);

            JLabel labelConta = new JLabel("Conta:");
            labelConta.setBounds(10, 324, 46, 14);
            panel_1.add(labelConta);

            comboBoxConta = new JComboBox<ContaBancariaModel>();
            comboBoxConta.setBounds(10, 344, 301, 43);
            panel_1.add(comboBoxConta);

            JPanel panel_3 = new JPanel();
            panel_3.setBounds(116, 478, 195, 23);
            panel_1.add(panel_3);
            panel_3.setEnabled(false);
            panel_3.setLayout(null);

            comboboxRepetirPeriodo = new JComboBox();
            comboboxRepetirPeriodo.setBounds(0, 0, 89, 25);
            panel_3.add(comboboxRepetirPeriodo);

            spinnerRepetirQtde = new JSpinner();
            spinnerRepetirQtde.setBounds(99, 0, 55, 25);
            panel_3.add(spinnerRepetirQtde);

            JLabel lblVezes = new JLabel("Vezes");
            lblVezes.setBounds(155, 5, 40, 14);
            panel_3.add(lblVezes);

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
                        setMovimentacaoModel(new MovimentacaoModel());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(MovimentacaoInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            btnNewButton.setText("Adicionar Lançamento");

            btnNewButton.setBounds(10, 617, 321, 67);
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
            String[] colunasTableSaldo = new String[] { "Conta", "Saldo", "Ch. Especial", "Saldo + Ch. Especial" };
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

            JPanel holdingPanel = new JPanel(new BorderLayout());
            holdingPanel.add(jPanelSaldo, BorderLayout.CENTER);
            holdingPanel.add(jscrollPaneFooter, BorderLayout.SOUTH);

            JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
            tabbedPane.setBounds(341, 437, 810, 247);
            tabbedPane.addTab("Saldo em Conta", null, holdingPanel, null);

            getContentPane().add(tabbedPane);

            JPanel panel_4 = new JPanel();
            panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Barra de Ferramentas", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
            panel_4.setBounds(341, 10, 810, 77);
            panel_4.setLayout(new BorderLayout());

            ButtonBarComponent buttonBarComponent = new ButtonBarComponent();
            buttonBarComponent.setBorder(null);
            // setarComponentes();
            buttonBarComponent.btExcluir.addActionListener(new ActionListener() {

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
            }
            setListaDeContasSelecionadas(getListaDeContasSelecionadas());
            acaoObterPosicao();
            setMovimentacaoModel(new MovimentacaoModel());

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
                JOptionPane.showMessageDialog(this, "Registro Excluido com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
                acaoObterPosicao();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

            }

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
            if (posicaoFinanceiraModel != null) {

                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Inicial ", "", "", "", false, currencyFormat(posicaoFinanceiraModel.getCalcSaldoInicial()) });

                BigDecimal bigDecimalSaldoGlobal = posicaoFinanceiraModel.getCalcSaldoInicial();
                for (PosicaoBancariaModel posBancariaModel : posicaoFinanceiraModel.getListaDePosicoesBancarias()) {
                    defaultTableModelPosBancaria.addRow(new String[] { posBancariaModel.getContaBancariaSelecionada().getNome(), currencyFormat(posBancariaModel.getCalcSaldoFinal()) });
                    for (MovimentacaoModel movimentacaoModel : posBancariaModel.getListaDeMovimentacao()) {
                        bigDecimalSaldoGlobal = bigDecimalSaldoGlobal.add(movimentacaoModel.getValor());
                        defaultTableModelMovimentacoes.addRow(new Object[] { movimentacaoModel.getId().toString(), movimentacaoModel.getDescricao(), movimentacaoModel.getConta().getNome(), movimentacaoModel.getTipo().toString(), currencyFormat(movimentacaoModel.getValor()), false, currencyFormat(bigDecimalSaldoGlobal) });
                    }
                }
                defaultTableModelMovimentacoes.addRow(new Object[] { "", "-> Saldo Final ", "", "", "", false, currencyFormat(posicaoFinanceiraModel.getCalcSaldoFinal()) });
                defaultTableModelPosBancariaFooter.addRow((new String[] { "<HTML><B>TOTAL</B></HTML> ", currencyFormat(posicaoFinanceiraModel.getCalcSaldoFinal()), "" }));

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
            movimentacaoModel.setFornecedor((FornecedorModel) comboBoxCliente.getSelectedItem());
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
            }
            if (movimentacaoModel.getValor() == null) {
                BigDecimal bigDecimal = BigDecimal.ZERO;
                textFieldValor.setValor(bigDecimal.doubleValue());
            } else {
                textFieldValor.setValor(movimentacaoModel.getValor().doubleValue());
            }

        }

        public class DateLabelFormatter extends AbstractFormatter
            {

                private String datePattern = "dd/MM/yyyy";
                private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

                @Override
                public Object stringToValue(String text) throws ParseException {
                    return dateFormatter.parseObject(text);
                }

                @Override
                public String valueToString(Object value) throws ParseException {
                    if (value != null) {
                        Calendar cal = (Calendar) value;
                        return dateFormatter.format(cal.getTime());
                    }

                    return "";
                }

            }

        public class MovimentacaoTableModel extends DefaultTableModel
            {

                public MovimentacaoTableModel() {

                    super(new String[] { "id", "Descrição", "Conta", "Tipo", "Valor", "Pago/Recebido", "Saldo Global" }, 0);
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
                        System.out.println(aValue);
                        Vector rowData = (Vector) getDataVector().get(row);
                        rowData.set(5, (boolean) aValue);
                        fireTableCellUpdated(row, column);
                    }
                }
            }

    }
