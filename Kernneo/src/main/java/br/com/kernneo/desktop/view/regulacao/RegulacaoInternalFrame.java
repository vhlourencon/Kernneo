package br.com.kernneo.desktop.view.regulacao;

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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
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

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.financeiro.DatePickerDialog;
import br.com.kernneo.desktop.view.grupo.GrupoFormCadPanel;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.hospital.HospitalFormCadPanel;
import br.com.kernneo.desktop.view.hospital.HospitalListInternalFrame;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JPanelCustom;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.dao.RegulacaoDAO;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Estado;
import br.com.kernneo.server.negocio.Hospital;
import br.com.kernneo.server.negocio.Regulacao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;

public class RegulacaoInternalFrame extends JInternalFrame
	{

		private Regulacao regulacao;
		private RegulacaoDAO regulacaoDAO;
		private List<RegulacaoModel> listaDeOcorrencias;
		private JTable jTableOcorrencias;
		private JTextField jFormattedTextFieldOcorrenciaNumero;
		private JFormattedTextField jFormattedTextFieldOcorrenciaHora;

		private RegulacaoModel regulacaoModel;
		private UtilDateModel model;
		private JComboBox<HospitalModel> comboBoxHospital;

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
		protected boolean mostraOutros;
		private JTextArea textAreaObservacao;
		private JScrollPane scrollPaneObservacao;
		private JPanel panel_5;
		private JButton btSair;
		private JComboBox<CidadeModel> comboBoxCidade;
		private SimpleDateFormat formatterHora;
		protected ArrayList<CidadeModel> listaDeCidades;
		protected ArrayList<HospitalModel> listaDeHospital;
		private JComboBox<EstadoModel> comboBoxUF;
		private JLabel lblEspecialidade;
		private JComboBox<CategoriaModel> comboboxCategoria;
		private JCheckBox checkLiminar;
		private RegulacaoPendentePanel regulacaoPendentePanel;
		private JButton btResolvido;
		private JButton buttonResolvido;

//	private Moviment

		public RegulacaoInternalFrame(FuncionarioModel funcionarioModel) throws Exception {

			setTitle("Usuario Logado: " + funcionarioModel.getNome() + " (Controle de Regulação)");

//            if (funcionarioModel.getPermissaoMovFinanceiraModel().isPermiteAcesso() == false) {
//             //   throw new Exception("Usuário sem permissao para acessar esse modulo");
//            }

			setResizable(false);
			setClosable(true);
			setVisible(true);
			setMaximizable(true);
			setLocation(250, 50);
			setSize(1428, 717);
			setIconifiable(true);
			getContentPane().setLayout(null);

			regulacao = new Regulacao();
			regulacaoDAO = new RegulacaoDAO();

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
			datePicker.setSize(300, 37);
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
			panelOcorrenciaData.setBounds(10, 5, 346, 77);
			panelOcorrenciaData.setLayout(null);
			panelOcorrenciaData.add(datePicker);
			getContentPane().add(panelOcorrenciaData);

			jTableOcorrencias = new JTable();
			jTableOcorrencias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTableOcorrencias.setFillsViewportHeight(true);
			jTableOcorrencias.setModel(new RegulacaoTableModel());
			jTableOcorrencias.getColumnModel().getColumn(0).setMinWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(0).setMaxWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(1).setMinWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(1).setMaxWidth(0);

			jTableOcorrencias.getColumnModel().getColumn(2).setMinWidth(120);
			jTableOcorrencias.getColumnModel().getColumn(2).setMaxWidth(120);
			jTableOcorrencias.getColumnModel().getColumn(3).setMaxWidth(50);
			jTableOcorrencias.getColumnModel().getColumn(7).setMaxWidth(100);
			jTableOcorrencias.getColumnModel().getColumn(7).setMaxWidth(100);
			jTableOcorrencias.getColumnModel().getColumn(8).setMaxWidth(100);
			jTableOcorrencias.getColumnModel().getColumn(8).setMaxWidth(100);
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
			jPanelLancamento.setBounds(10, 85, 347, 507);
			jPanelLancamento.add(panelInfoLancamento);
			getContentPane().add(jPanelLancamento);
			panelInfoLancamento.setLayout(null);

			JLabel labelCidade = new JLabel("UF:");
			labelCidade.setBounds(25, 114, 56, 15);
			panelInfoLancamento.add(labelCidade);

			jFormattedTextFieldOcorrenciaNumero = new JTextField();
			jFormattedTextFieldOcorrenciaNumero.setFont(new Font("Tahoma", Font.BOLD, 15));
			// jFormattedTextFieldOcorrenciaNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
			jFormattedTextFieldOcorrenciaNumero.setBounds(25, 59, 177, 43);

			jFormattedTextFieldOcorrenciaNumero.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
						e.consume();
					}
				}
			});
			panelInfoLancamento.add(jFormattedTextFieldOcorrenciaNumero);

			JLabel labelDescricao = new JLabel("Cod. de Solicitação:");
			labelDescricao.setBounds(25, 39, 149, 15);
			panelInfoLancamento.add(labelDescricao);

			comboBoxHospital = new JComboBox<HospitalModel>();
			comboBoxHospital.setBounds(25, 294, 240, 43);
			AutoCompleteDecorator.decorate(comboBoxHospital);
			panelInfoLancamento.add(comboBoxHospital);

			JLabel lblHora = new JLabel("Hora:");
			lblHora.setBounds(212, 39, 80, 15);
			panelInfoLancamento.add(lblHora);

			formatterHora = new SimpleDateFormat("HH:mm");
			String dateString = formatterHora.format(new Date());

			MaskFormatter maskFormatter = new MaskFormatter("**:**");
			maskFormatter.setPlaceholderCharacter('_');

			jFormattedTextFieldOcorrenciaHora = new JFormattedTextField(maskFormatter);
			jFormattedTextFieldOcorrenciaHora.setFont(new Font("Tahoma", Font.BOLD, 15));
			jFormattedTextFieldOcorrenciaHora.setFocusLostBehavior(JFormattedTextField.PERSIST);
			jFormattedTextFieldOcorrenciaHora.setBounds(212, 59, 114, 43);
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
			comboBoxCidade.setBounds(97, 135, 229, 43);
			panelInfoLancamento.add(comboBoxCidade);

			textAreaObservacao = new JTextArea();

			scrollPaneObservacao = new JScrollPane(textAreaObservacao, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPaneObservacao.setBounds(25, 365, 301, 105);

			panelInfoLancamento.add(scrollPaneObservacao);

			JLabel lblObervaol = new JLabel("Obervação:");
			lblObervaol.setBounds(25, 345, 90, 14);
			panelInfoLancamento.add(lblObervaol);

			JLabel lblCidade = new JLabel("Cidade de Origem:");
			lblCidade.setBounds(97, 114, 229, 14);
			panelInfoLancamento.add(lblCidade);

			JButton buttonAddHospital = new JButton(Icone.novo("btAdic2.gif"));
			buttonAddHospital.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					acaoAdicionarHospital();
				}
			});
			buttonAddHospital.setBounds(270, 294, 56, 45);
			panelInfoLancamento.add(buttonAddHospital);

			comboBoxUF = new JComboBox<EstadoModel>();
			comboBoxUF.setBounds(25, 135, 62, 43);
			comboBoxUF.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					if (e.getStateChange() == ItemEvent.SELECTED) {
						atualizaCidadesDoEstadoSelecionado();
					}

				}
			});
			panelInfoLancamento.add(comboBoxUF);

			JLabel labelConta_1 = new JLabel("Local de Atendimento:");
			labelConta_1.setBounds(25, 273, 240, 14);
			panelInfoLancamento.add(labelConta_1);

			comboboxCategoria = new JComboBox<CategoriaModel>();
			comboboxCategoria.setBounds(25, 213, 240, 43);
			AutoCompleteDecorator.decorate(comboboxCategoria);
			panelInfoLancamento.add(comboboxCategoria);

			JButton buttonAddEspecialidade = new JButton(Icone.novo("btAdic2.gif"));
			buttonAddEspecialidade.setBounds(270, 213, 56, 45);
			panelInfoLancamento.add(buttonAddEspecialidade);
			buttonAddEspecialidade.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAdicionarEspecialidade();
				}
			});

			lblEspecialidade = new JLabel("Especialidade:");
			lblEspecialidade.setBounds(25, 192, 240, 14);
			panelInfoLancamento.add(lblEspecialidade);

			checkLiminar = new JCheckBox("LIMINAR");
			checkLiminar.setFont(new Font("Tahoma", Font.BOLD, 11));
			checkLiminar.setBounds(25, 475, 188, 23);
			panelInfoLancamento.add(checkLiminar);
			DefaultTableCellRenderer dtcrFooter = new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
					JLabel parent = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
					parent.getFont().deriveFont(Font.BOLD);
					return parent;

				}
			};

			// getContentPane().add(tabbedPaneSaldoEmConta);

			JPanel panel_4 = new JPanelCustom() {

			};
			panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Lista de Regula\u00E7\u00E3o", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panel_4.setBounds(366, 5, 1029, 410);
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

			buttonEditar.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoOcorrenciaEditar();
				}
			});

			buttonResolvido = new JButton(Icone.novo("btOk.png"));
			buttonResolvido.setText("Resolvido");
			buttonResolvido.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoOcorrenciaResolver();
				}
			});

			
			
			JPanel panelBarraDeFerramentas = new JPanel();
			panelBarraDeFerramentas.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panelBarraDeFerramentas.setLayout(new MigLayout("", "[150px][150px][150px][150px][450px][150px]", "[50px]"));
			panelBarraDeFerramentas.add(buttonAlterarData, "cell 0 0,grow");
			panelBarraDeFerramentas.add(buttonExcluir, "cell 1 0,grow");
			panelBarraDeFerramentas.add(buttonEditar, "cell 2 0,grow");
			panelBarraDeFerramentas.add(buttonResolvido, "cell 3 0,grow");
			panelBarraDeFerramentas.add(btSair, "cell 5 0,grow");
			panel_4.add(panelBarraDeFerramentas, BorderLayout.NORTH);

			getContentPane().add(panel_4);

			BorderLayout bl_panel_5 = new BorderLayout();

			panel_5 = new JPanel(bl_panel_5);
			panel_5.setBounds(1077, 546, 554, 212);
			panel_5.add(scrollPaneLancamentos, BorderLayout.CENTER);
			panel_4.add(panel_5, BorderLayout.CENTER);

			buttonAddMovimentacao = new JButton(Icone.novo("chamado_concluido_16x16.png"));
			buttonAddMovimentacao.setFont(new Font("Tahoma", Font.BOLD, 15));
			buttonAddMovimentacao.setBounds(11, 600, 345, 70);
			getContentPane().add(buttonAddMovimentacao);
			buttonAddMovimentacao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ocorrenciaAdicionar();
				}
			});
			buttonAddMovimentacao.setText("Adicionar Lançamento");

			regulacaoPendentePanel = new RegulacaoPendentePanel(this);
			JTabbedPane tabbedPaneSaldoEmConta = new JTabbedPane(JTabbedPane.TOP);
			tabbedPaneSaldoEmConta.setPreferredSize(new Dimension(250, 240));
			tabbedPaneSaldoEmConta.setBounds(366, 426, 1029, 244);
			tabbedPaneSaldoEmConta.addTab("Pendentes", null, regulacaoPendentePanel, null);
			getContentPane().add(tabbedPaneSaldoEmConta);

			// jTableOcorrencias.getColumnModel().getColumn(6).setMinWidth(0);
			// jTableOcorrencias.getColumnModel().getColumn(6).setMaxWidth(0);
			// }
			try {
				Conexao.Executar(new Comando() {

					@Override
					public void execute(Session session) throws Exception {

						List<EstadoModel> listaDeEstados = new Estado().obterTodos(EstadoModel.class);
						for (EstadoModel estadoModel : listaDeEstados) {
							comboBoxUF.addItem(estadoModel);
						}
						listaDeCidades = new Cidade().obterTodos(CidadeModel.class);

						listaDeHospital = new Hospital().obterTodos(HospitalModel.class);
						for (HospitalModel hospitalModel : listaDeHospital) {
							comboBoxHospital.addItem(hospitalModel);
						}

						List<CategoriaModel> listaDeCategorias = new Categoria().obterTodos(CategoriaModel.class);
						for (CategoriaModel categoriaModel : listaDeCategorias) {
							comboboxCategoria.addItem(categoriaModel);
						}

						setRegulacaoModel(factoryORegulacaoModel());
					}
				});
				acaoOcorrenciasAtualizar();
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

				throw e1;
			}
		}

		protected void atualizaCidadesDoEstadoSelecionado() {
			if (listaDeCidades != null) {
				comboBoxCidade.removeAllItems();
				for (CidadeModel cidadeAux : listaDeCidades) {
					if (cidadeAux.getEstado().getId().compareTo(getEstadoSelecionado().getId()) == 0) {
						comboBoxCidade.addItem(cidadeAux);
					}
				}
			}

		}

		private EstadoModel getEstadoSelecionado() {
			return (EstadoModel) comboBoxUF.getSelectedItem();
		}

		private void setCidadeSelecionada(CidadeModel cidadeModel) {
			if (cidadeModel != null) {
				for (int i = 0; i < comboBoxUF.getItemCount(); i++) {
					if (comboBoxUF.getItemAt(i).getId().compareTo(cidadeModel.getEstado().getId()) == 0) {
						comboBoxUF.setSelectedIndex(i);
					}
				}
				comboBoxCidade.setSelectedItem(cidadeModel);
			} else {
				setCuiabaSelecionada();
			}
		}

		private void setCuiabaSelecionada() {
			for (CidadeModel cidadeModel : listaDeCidades) {
				if (cidadeModel.getNome().toLowerCase().equals("cuiabá")) {
					setCidadeSelecionada(cidadeModel);
				}
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

		protected void acaoAdicionarEspecialidade() {
			try {
				GrupoListInternalFrame internalFrame = new GrupoListInternalFrame() {

					@Override
					public void acaoSalvar() {

						try {
							Long id = null;
							Conexao.Executar(new Comando() {

								@Override
								public void execute(Session session) throws Exception {
									CategoriaModel model = getFormCadPanel().getModel();
									CategoriaModel modelAux = negocio.salvar(model);
									comboboxCategoria.addItem(modelAux);
									comboboxCategoria.setSelectedItem(modelAux);

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
				internalFrame.eventoBotaoIncluir();
				internalFrame.setVisible(true);
				internalFrame.setModoForm();
				internalFrame.setTitle("Cadastro de Especialidade");
				internalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						internalFrame.setVisible(false);
						internalFrame.dispose();

					}
				});
				PrincipalDesktop.getjDesktopPane().add(internalFrame, 0);
				internalFrame.setSelected(true);
				PrincipalDesktop.getjDesktopPane().setSelectedFrame(internalFrame);
				// clienteListInternalFrame.setModoForm();
				GrupoFormCadPanel clienteFormCadPanel = (GrupoFormCadPanel) internalFrame.getFormCadPanel();
				clienteFormCadPanel.getTitledBorder().setTitle("Informações da Especialidade");
				clienteFormCadPanel.getTextFieldDescricao().requestFocus();
				clienteFormCadPanel.getTextFieldDescricao().selectAll();

			} catch (Exception excecao) {
				JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

			}
		}

		private RegulacaoModel factoryORegulacaoModel() {
			RegulacaoModel model = new RegulacaoModel();
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

		protected void acaoOcorrenciaResolver() {
			if (getModelSelecionado() == null) {
				JOptionPane.showMessageDialog(null, "Selecione um registro");
			} else {
				if (getModelSelecionado().isResolvido()) {
					JOptionPane.showMessageDialog(this, "Essa regulação já foi resolvida", "ERRO ", JOptionPane.ERROR_MESSAGE);
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Confirma a Resolução?", "Alerta", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						ocorrenciaResolver(getModelSelecionado());
					}
				}
			}
		}

		protected void acaoOcorrenciaEditar() {
			if (getModelSelecionado() == null) {
				JOptionPane.showMessageDialog(null, "Selecione um registro");
			} else {
				if (getRegulacaoModel().getId() == null) {
					setRegulacaoModel(getModelSelecionado());
				} else {
					setRegulacaoModel(factoryORegulacaoModel());
					acaoOcorrenciasAtualizar();
				}
			}
		}

		private void ocorrenciaExcluir(RegulacaoModel modelSelecionado) {
			try {
				Conexao.Executar(new Comando() {
					@Override
					public void execute(Session session) throws Exception {
						modelSelecionado.setUsuarioDelete(PrincipalDesktop.getUsarioLogado());
						regulacao.excluir(modelSelecionado);
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
						RegulacaoModel model = catchRegulacaoModel();
						if (model.getId() == null) {
							model.setUsuarioSave(PrincipalDesktop.getUsarioLogado());
							model.setResolvido(false);
						} else {
							model.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
						}
						regulacao.merge(model);
					}
				});
				acaoOcorrenciasAtualizar();
				setRegulacaoModel(factoryORegulacaoModel());
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(RegulacaoInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}

		private void ocorrenciaEditarData(RegulacaoModel modelSelecionado, Date dataSelecionada) {
			try {
				Conexao.Executar(new Comando() {
					@Override
					public void execute(Session session) throws Exception {
						modelSelecionado.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
						regulacao.alterarData(modelSelecionado, dataSelecionada);
					}
				});
				acaoOcorrenciasAtualizar();
				JOptionPane.showMessageDialog(this, "Registro Alterado com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);
			}
		}

		private void ocorrenciaResolver(RegulacaoModel modelSelecionado) {
			try {
				Conexao.Executar(new Comando() {
					@Override
					public void execute(Session session) throws Exception {
						modelSelecionado.setUsuarioEdit(PrincipalDesktop.getUsarioLogado());
						modelSelecionado.setResolvido(true);
						regulacao.merge(modelSelecionado);
						regulacaoDAO.resolverRegulacoesAnteriores(modelSelecionado);
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
							List<RegulacaoModel> lista = regulacaoDAO.obterPorDataEusuario(model.getValue(), PrincipalDesktop.getUsarioLogado(), "id", true);
							setListaDeOcorrencias(lista);
							List<RegulacaoModel> listaPendente = regulacaoDAO.obterPendente("id", true);
							regulacaoPendentePanel.setListaDeMovimentacoes(listaPendente);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			obtendoOcorrencias = false;
		}

		private RegulacaoModel getModelSelecionado() {
			if (jTableOcorrencias.getSelectedRow() == -1) {
				return null;
			} else {
				DefaultTableModel defaultTableModel = (DefaultTableModel) jTableOcorrencias.getModel();
				String id = defaultTableModel.getValueAt(jTableOcorrencias.getSelectedRow(), 0).toString().trim();

				RegulacaoModel modelSelecionado = getModelPorID(id);
				return modelSelecionado;
			}
		}

		private RegulacaoModel getModelPorID(String id) {
			RegulacaoModel modelSelecionado = null;
			for (RegulacaoModel model : getListaDeOcorrencias()) {
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

		public RegulacaoModel getRegulacaoModel() {
			return regulacaoModel;
		}

		public RegulacaoModel catchRegulacaoModel() {
			Calendar cHora = new GregorianCalendar();
			cHora.setTime(model.getValue());
			cHora.set(Calendar.HOUR_OF_DAY, Integer.parseInt(jFormattedTextFieldOcorrenciaHora.getText().replace(":", "").substring(0, 2)));
			cHora.set(Calendar.MINUTE, Integer.parseInt(jFormattedTextFieldOcorrenciaHora.getText().replace(":", "").substring(2, 4)));
			cHora.set(Calendar.SECOND, 0);

			regulacaoModel.setCodigo(jFormattedTextFieldOcorrenciaNumero.getText().replaceAll("\\D+", ""));
			regulacaoModel.setDataHora(cHora.getTime());
			regulacaoModel.setCidade((CidadeModel) comboBoxCidade.getSelectedItem());
			regulacaoModel.setEspecialidade((CategoriaModel) comboboxCategoria.getSelectedItem());
			regulacaoModel.setHospital((HospitalModel) comboBoxHospital.getSelectedItem());
			regulacaoModel.setObservacao(textAreaObservacao.getText());
			regulacaoModel.setFuncionario(PrincipalDesktop.getUsarioLogado());
			regulacaoModel.setLiminar(checkLiminar.isSelected());

			return regulacaoModel;
		}

		public void setRegulacaoModel(RegulacaoModel model) {

			this.regulacaoModel = model;
			setCidadeSelecionada(model.getCidade());
			selectComboBoxCategoria(model.getEspecialidade());
			selectComboBoxHospital(model.getHospital());

			Date dataHora = model.getDataHora();
			if (dataHora == null) {
				dataHora = new Date();
			}
			jFormattedTextFieldOcorrenciaHora.setText(formatterHora.format(dataHora));
			textAreaObservacao.setText(model.getObservacao());
			checkLiminar.setSelected(model.isLiminar());

			if (regulacaoModel.getId() != null) {
				buttonAlterarData.setEnabled(false);
				buttonExcluir.setEnabled(false);

				jTableOcorrencias.setEnabled(false);
				jTableOcorrencias.getTableHeader().setEnabled(false);

				buttonEditar.setText("Cancelar");
				buttonEditar.setIcon(imageIconCancelar);

				datePicker.getJFormattedTextField().setEnabled(false);
				datePicker.getButton().setEnabled(false);
				buttonAddMovimentacao.setText("Salvar Alterações");
				jFormattedTextFieldOcorrenciaNumero.setText(model.getCodigo());

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
				jFormattedTextFieldOcorrenciaNumero.setText("");

			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					jFormattedTextFieldOcorrenciaNumero.requestFocus();
					jFormattedTextFieldOcorrenciaNumero.selectAll();

				}
			});

		}

		private void selectComboBoxCategoria(CategoriaModel categoriaModel) {
			if (categoriaModel != null && categoriaModel.getId() != null)
				for (int i = 0; i < comboboxCategoria.getItemCount(); i++) {
					if (comboboxCategoria.getItemAt(i).getId().compareTo(categoriaModel.getId()) == 0) {
						comboboxCategoria.setSelectedIndex(i);
						break;
					}
				}
			else {
				comboboxCategoria.setSelectedIndex(0);
			}
		}

		private void selectComboBoxHospital(HospitalModel hospitalModel) {
			if (hospitalModel != null && hospitalModel.getId() != null)
				for (int i = 0; i < comboBoxHospital.getItemCount(); i++) {
					if (comboBoxHospital.getItemAt(i).getId().compareTo(hospitalModel.getId()) == 0) {
						comboBoxHospital.setSelectedIndex(i);
						break;
					}
				}
			else {
				comboBoxHospital.setSelectedIndex(0);
			}
		}

		public List<RegulacaoModel> getListaDeOcorrencias() {
			return listaDeOcorrencias;
		}

		public void setListaDeOcorrencias(List<RegulacaoModel> listaDeOcorrencias) {
			this.listaDeOcorrencias = listaDeOcorrencias;
			RegulacaoTableModel defaultTableModelOcorrencias = (RegulacaoTableModel) jTableOcorrencias.getModel();
			defaultTableModelOcorrencias.setRowCount(0);

			if (listaDeOcorrencias != null) {
				for (RegulacaoModel RegulacaoModel : listaDeOcorrencias) {
					defaultTableModelOcorrencias.addRow(RegulacaoModel.toRow());
				}
			}
		}
	}
