package br.com.kernneo.desktop.view.solicitacao;

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
import java.math.BigInteger;
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
import javax.swing.JToggleButton;
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
import br.com.kernneo.client.model.SolicitacaoItemModel;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.HospitalModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.PrincipalDesktop;
import br.com.kernneo.desktop.view.departamento.DepartamentoFormCadPanel;
import br.com.kernneo.desktop.view.departamento.DepartamentoListInternalFrame;
import br.com.kernneo.desktop.view.financeiro.DatePickerDialog;
import br.com.kernneo.desktop.view.grupo.GrupoFormCadPanel;
import br.com.kernneo.desktop.view.grupo.GrupoListInternalFrame;
import br.com.kernneo.desktop.view.hospital.HospitalFormCadPanel;
import br.com.kernneo.desktop.view.hospital.HospitalListInternalFrame;
import br.com.kernneo.desktop.view.regulacao.RegulacaoPendentePanel;
import br.com.kernneo.desktop.view.regulacao.RegulacaoTableModel;
import br.com.kernneo.desktop.view.util.DateLabelFormatter;
import br.com.kernneo.desktop.view.widget.Icone;
import br.com.kernneo.desktop.view.widget.JPanelCustom;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.dao.SolicitacaoItemDAO;
import br.com.kernneo.server.dao.RegulacaoDAO;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Solicitacao;
import br.com.kernneo.server.negocio.SolicitacaoItem;
import br.com.kernneo.server.negocio.Departamento;
import br.com.kernneo.server.negocio.Estado;
import br.com.kernneo.server.negocio.Hospital;
import br.com.kernneo.server.negocio.Regulacao;
import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import javax.swing.JTabbedPane;
import javax.swing.JSpinner;
import javax.swing.Icon;
import javax.swing.SpinnerNumberModel;

public class SolicitacaoItemInternalFrame extends JInternalFrame
	{

		private SolicitacaoItem compiladoItem;
		private SolicitacaoItemDAO compiladoItemDAO;
		private List<SolicitacaoItemModel> listaDeItens;
		private JTable jTableOcorrencias;

		private SolicitacaoItemModel compiladoItemModel;
		private JComboBox<HospitalModel> comboBoxUnidade;

		private JPanelCustom panelInfoLancamento;

		protected int columnModelIndex;
		protected boolean descendente = true;
		private JButton buttonAddMovimentacao;
		private JButton buttonEditar;
		private JButton buttonExcluir;
		private ImageIcon imageIconCancelar;
		private ImageIcon imageIconEditar;
		private boolean obtendoOcorrencias = false;
		protected boolean mostraOutros;
		private JPanel panel_5;
		private JButton btSair;
		protected ArrayList<HospitalModel> listaDeUnidade;
		private JLabel lblEspecialidade;
		private JComboBox<CategoriaModel> comboboxDocumento;
		private JComboBox<DepartamentoModel> comboBoxEquipamento;
		private JComboBox<SolicitacaoModel> comboboxCompilado;
		protected ArrayList<SolicitacaoModel> listaDeCompilados;
		private JSpinner spinnerQuantidade;
		private SolicitacaoItemFiltroPanel compiladoFiltroPanel;
		private JTable footerTable;
		private JScrollPane jscrollPaneFooter;

//	private Moviment

		public SolicitacaoItemInternalFrame() throws Exception {

			setTitle("Cadastro de Item");

			setResizable(false);
			setClosable(true);
			setVisible(true);
			setMaximizable(true);
			setLocation(250, 50);
			setSize(1424, 564);
			setIconifiable(true);
			getContentPane().setLayout(null);

			compiladoItem = new SolicitacaoItem();
			compiladoItemDAO = new SolicitacaoItemDAO();

			JPanel panelOcorrenciaData = new JPanel();
			panelOcorrenciaData.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Selecione uma Compilação", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			panelOcorrenciaData.setBounds(10, 5, 346, 77);
			panelOcorrenciaData.setLayout(null);
			getContentPane().add(panelOcorrenciaData);

			comboboxCompilado = new JComboBox<SolicitacaoModel>();
			comboboxCompilado.setBounds(23, 23, 301, 43);
			panelOcorrenciaData.add(comboboxCompilado);
			

			jTableOcorrencias = new JTable();
			jTableOcorrencias.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			jTableOcorrencias.setFillsViewportHeight(true);
			jTableOcorrencias.setModel(new SolicitacaoItemTableModel());
			jTableOcorrencias.getColumnModel().getColumn(0).setMinWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(0).setMaxWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(1).setMinWidth(0);
			jTableOcorrencias.getColumnModel().getColumn(1).setMaxWidth(0);

			jTableOcorrencias.getColumnModel().getColumn(5).setMinWidth(120);
			jTableOcorrencias.getColumnModel().getColumn(5).setMaxWidth(120);
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
			jPanelLancamento.setBounds(10, 85, 347, 354);
			jPanelLancamento.add(panelInfoLancamento);
			getContentPane().add(jPanelLancamento);
			panelInfoLancamento.setLayout(null);

			comboBoxUnidade = new JComboBox<HospitalModel>();
			comboBoxUnidade.setBounds(22, 123, 240, 43);
			AutoCompleteDecorator.decorate(comboBoxUnidade);
			panelInfoLancamento.add(comboBoxUnidade);

			JButton buttonAddHospital = new JButton(Icone.novo("btAdic2.gif"));
			buttonAddHospital.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					acaoAdicionarUnidade();
				}
			});
			buttonAddHospital.setBounds(272, 121, 56, 45);
			panelInfoLancamento.add(buttonAddHospital);

			JLabel labelConta_1 = new JLabel("Unidade:");
			labelConta_1.setBounds(22, 102, 240, 14);
			panelInfoLancamento.add(labelConta_1);

			comboboxDocumento = new JComboBox<CategoriaModel>();
			comboboxDocumento.setBounds(22, 42, 240, 43);
			AutoCompleteDecorator.decorate(comboboxDocumento);
			panelInfoLancamento.add(comboboxDocumento);

			JButton buttonAddEspecialidade = new JButton(Icone.novo("btAdic2.gif"));
			buttonAddEspecialidade.setBounds(272, 42, 56, 45);
			panelInfoLancamento.add(buttonAddEspecialidade);
			buttonAddEspecialidade.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAdicionarDocumento();
				}
			});

			lblEspecialidade = new JLabel("Documento:");
			lblEspecialidade.setBounds(22, 21, 240, 14);
			panelInfoLancamento.add(lblEspecialidade);

			 spinnerQuantidade = new JSpinner();
			spinnerQuantidade.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spinnerQuantidade.setFont(new Font("Tahoma", Font.BOLD, 11));
			spinnerQuantidade.setBounds(22, 285, 240, 43);
			panelInfoLancamento.add(spinnerQuantidade);

			JLabel labelConta_1_1 = new JLabel("Equipamento:");
			labelConta_1_1.setBounds(22, 180, 240, 14);
			panelInfoLancamento.add(labelConta_1_1);

			comboBoxEquipamento = new JComboBox<DepartamentoModel>();
			comboBoxEquipamento.setBounds(22, 201, 240, 43);
			AutoCompleteDecorator.decorate(comboBoxEquipamento);
			panelInfoLancamento.add(comboBoxEquipamento);

			JButton buttonAddHospital_1 = new JButton(Icone.novo("btAdic2.gif"));
			buttonAddHospital_1.setBounds(272, 199, 56, 45);
			panelInfoLancamento.add(buttonAddHospital_1);
			buttonAddHospital_1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					acaoAdicionarEquipamento();
					
				}
			});

			JLabel labelConta_1_1_1 = new JLabel("Quantidade:");
			labelConta_1_1_1.setBounds(22, 266, 240, 14);
			panelInfoLancamento.add(labelConta_1_1_1);
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
			panel_4.setBounds(366, 5, 1029, 518);
			BorderLayout bl_panel_4 = new BorderLayout();
			bl_panel_4.setVgap(5);
			panel_4.setLayout(bl_panel_4);

			buttonExcluir = new JButton(Icone.novo("btExcluir.png"));
			buttonExcluir.setText("Excluir ");

			buttonEditar = new JButton(Icone.novo("btEditar.png"));
			buttonEditar.setText("Editar");

			imageIconCancelar = Icone.novo("btCancelarOP.gif");
			imageIconEditar = Icone.novo("btEditar.gif");

			btSair = new JButton(Icone.novo("btSair.png"));
			btSair.setText("Sair");
			btSair.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					setVisible(false);
					dispose();

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
			
			 compiladoFiltroPanel = new SolicitacaoItemFiltroPanel() {

				@Override
				public void acaoPesquisar( ) {
					
					//acaoLancamentoAtualizar();
					setListaDeOcorrencias(getListaDeOcorrencias());
				}
				
			}; 
			JToggleButton btConsultar = new JToggleButton(Icone.novo("btPesquisa.png"), false);
			btConsultar.setText("Pesquisar");
			btConsultar.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (btConsultar.isSelected()) {
                        panel_5.add(compiladoFiltroPanel, BorderLayout.NORTH);
                    } else {
                        panel_5.remove(compiladoFiltroPanel);
                    }

                    repaint();
                    validate();

					
				}
			});
			
		   

			JPanel panelBarraDeFerramentas = new JPanel();
			panelBarraDeFerramentas.setBorder(new LineBorder(Color.LIGHT_GRAY, 1, true));
			panelBarraDeFerramentas.setLayout(new MigLayout("", "[150px][150px][150px][150px][450px][150px]", "[50px]"));
			panelBarraDeFerramentas.add(buttonExcluir, "cell 0 0,grow");
			panelBarraDeFerramentas.add(buttonEditar, "cell 1 0,grow");
			panelBarraDeFerramentas.add(btConsultar, "cell 2 0,grow");
			panelBarraDeFerramentas.add(btSair, "cell 5 0,grow");
			panel_4.add(panelBarraDeFerramentas, BorderLayout.NORTH);

			getContentPane().add(panel_4);

			panel_5 = new JPanel();
			panel_5.setBounds(1077, 546, 554, 212);
			panel_5.setLayout(new BorderLayout(0, 0));
			panel_5.add(scrollPaneLancamentos);
			
			footerTable = new JTable();
			footerTable.setFillsViewportHeight(true);
			footerTable.setRowSelectionAllowed(false);
			footerTable.setColumnSelectionAllowed(false);
			footerTable.setTableHeader(null);
			footerTable.setRowHeight(24);
			String[] colunasTableSaldo = new String[] { "" };
			footerTable.setModel(new DefaultTableModel(new Object[][] {
			}, colunasTableSaldo) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}

			});


			jscrollPaneFooter = new JScrollPane(footerTable);
			jscrollPaneFooter.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
			jscrollPaneFooter.setPreferredSize(new Dimension(-1, 35));
			panel_5.add(jscrollPaneFooter,BorderLayout.SOUTH);
			
			panel_4.add(panel_5, BorderLayout.CENTER);

			buttonAddMovimentacao = new JButton(Icone.novo("chamado_concluido_16x16.png"));
			buttonAddMovimentacao.setFont(new Font("Tahoma", Font.BOLD, 15));
			buttonAddMovimentacao.setBounds(11, 450, 345, 70);
			getContentPane().add(buttonAddMovimentacao);
			buttonAddMovimentacao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ocorrenciaAdicionar();
				}
			});
			buttonAddMovimentacao.setText("Adicionar Lançamento");

			// jTableOcorrencias.getColumnModel().getColumn(6).setMinWidth(0);
			// jTableOcorrencias.getColumnModel().getColumn(6).setMaxWidth(0);
			// }
			try {
				Conexao.Executar(new Comando() {

					@Override
					public void execute(Session session) throws Exception {

						listaDeCompilados = new Solicitacao().obterTodos(SolicitacaoModel.class);
						for (SolicitacaoModel compiladoModel : listaDeCompilados) {
							comboboxCompilado.addItem(compiladoModel);
						}
						if (listaDeCompilados == null || listaDeCompilados.size() == 0) {
							throw new Exception("Nenhum compilado cadastrado!");
						}

						List<DepartamentoModel> listaDeEquipamentos = new Departamento().obterTodos(DepartamentoModel.class);
						for (DepartamentoModel equipamentoModel : listaDeEquipamentos) {
							comboBoxEquipamento.addItem(equipamentoModel);
						}

						listaDeUnidade = new Hospital().obterTodos(HospitalModel.class);
						for (HospitalModel hospitalModel : listaDeUnidade) {
							comboBoxUnidade.addItem(hospitalModel);
						}

						List<CategoriaModel> listaDeDocumento = new Categoria().obterTodos(CategoriaModel.class);
						for (CategoriaModel documentoModel : listaDeDocumento) {
							comboboxDocumento.addItem(documentoModel);
						}

						setModel(factoryCompiladoItem());
						comboboxCompilado.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
							acaoLancamentoAtualizar();
								
							}
						});
					}
				});
				acaoLancamentoAtualizar();
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(this, e1.getLocalizedMessage(), "ERRO ", JOptionPane.ERROR_MESSAGE);

				throw e1;
			}
		}

		protected void acaoAdicionarUnidade() {
			try {
				HospitalListInternalFrame unidadeInternalFrame = new HospitalListInternalFrame() {

					@Override
					public void acaoSalvar() {

						try {
							Long id = null;
							Conexao.Executar(new Comando() {

								@Override
								public void execute(Session session) throws Exception {
									HospitalModel model = getFormCadPanel().getModel();
									HospitalModel modelAux = negocio.salvar(model);
									comboBoxUnidade.addItem(modelAux);
									comboBoxUnidade.setSelectedItem(modelAux);

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
				unidadeInternalFrame.eventoBotaoIncluir();
				unidadeInternalFrame.setVisible(true);
				unidadeInternalFrame.setModoForm();
				unidadeInternalFrame.setTitle("Cadastro de Unidade");
				unidadeInternalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						unidadeInternalFrame.setVisible(false);
						unidadeInternalFrame.dispose();

					}
				});
				PrincipalDesktop.getjDesktopPane().add(unidadeInternalFrame, 0);
				unidadeInternalFrame.setSelected(true);
				PrincipalDesktop.getjDesktopPane().setSelectedFrame(unidadeInternalFrame);
				// clienteListInternalFrame.setModoForm();
				HospitalFormCadPanel clienteFormCadPanel = (HospitalFormCadPanel) unidadeInternalFrame.getFormCadPanel();
				clienteFormCadPanel.getTitledBorder().setTitle("Informações de Unidade");
				clienteFormCadPanel.getTextFieldDescricao().requestFocus();
				clienteFormCadPanel.getTextFieldDescricao().selectAll();

			} catch (Exception excecao) {
				JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

			}
		}
		
		protected void acaoAdicionarEquipamento() {
			try {
				DepartamentoListInternalFrame equipamentoInternalFrame = new DepartamentoListInternalFrame() {

					@Override
					public void acaoSalvar() {

						try {
							Long id = null;
							Conexao.Executar(new Comando() {

								@Override
								public void execute(Session session) throws Exception {
									DepartamentoModel model = getFormCadPanel().getModel();
									DepartamentoModel modelAux = negocio.salvar(model);
									comboBoxEquipamento.addItem(modelAux);
									comboBoxEquipamento.setSelectedItem(modelAux);

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
				equipamentoInternalFrame.eventoBotaoIncluir();
				equipamentoInternalFrame.setVisible(true);
				equipamentoInternalFrame.setModoForm();
				equipamentoInternalFrame.setTitle("Cadastro de Equipamento");
				equipamentoInternalFrame.buttonBarComponent.btCancelar.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						equipamentoInternalFrame.setVisible(false);
						equipamentoInternalFrame.dispose();

					}
				});
				PrincipalDesktop.getjDesktopPane().add(equipamentoInternalFrame, 0);
				equipamentoInternalFrame.setSelected(true);
				PrincipalDesktop.getjDesktopPane().setSelectedFrame(equipamentoInternalFrame);
				// clienteListInternalFrame.setModoForm();
				DepartamentoFormCadPanel clienteFormCadPanel = (DepartamentoFormCadPanel) equipamentoInternalFrame.getFormCadPanel();
				clienteFormCadPanel.getTitledBorder().setTitle("Informações de Equipamento");
				clienteFormCadPanel.getTextFieldDescricao().requestFocus();
				clienteFormCadPanel.getTextFieldDescricao().selectAll();

			} catch (Exception excecao) {
				JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

			}
		}

		protected void acaoAdicionarDocumento() {
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
									comboboxDocumento.addItem(modelAux);
									comboboxDocumento.setSelectedItem(modelAux);

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
				internalFrame.setTitle("Cadastro de Documento");
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
				
				GrupoFormCadPanel clienteFormCadPanel = (GrupoFormCadPanel) internalFrame.getFormCadPanel();
				clienteFormCadPanel.getTitledBorder().setTitle("Informações de Documento");
				clienteFormCadPanel.getTextFieldDescricao().requestFocus();
				clienteFormCadPanel.getTextFieldDescricao().selectAll();

			} catch (Exception excecao) {
				JOptionPane.showInternalMessageDialog(PrincipalDesktop.getjDesktopPane(), excecao.getMessage() + " " + excecao.getLocalizedMessage());

			}
		}

		private SolicitacaoItemModel factoryCompiladoItem() {
			SolicitacaoItemModel model = new SolicitacaoItemModel();
			return model;
		}
		
		private SolicitacaoItemModel factoryCompiladoItem(SolicitacaoItemModel itemModel) {
			SolicitacaoItemModel model = new SolicitacaoItemModel();
			model.setSolicitacao(itemModel.getSolicitacao());
			model.setDocumento(itemModel.getDocumento());
			model.setUnidade(itemModel.getUnidade());
			return model;
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

		protected void acaoOcorrenciaEditar() {
			if (getModelSelecionado() == null) {
				JOptionPane.showMessageDialog(null, "Selecione um registro");
			} else {
				if (compiladoItemModel.getId() == null) {
					setModel(getModelSelecionado());
				} else {
					setModel(factoryCompiladoItem());
					acaoLancamentoAtualizar();
				}
			}
		}

		private void ocorrenciaExcluir(SolicitacaoItemModel modelSelecionado) {
			try {
				Conexao.Executar(new Comando() {
					@Override
					public void execute(Session session) throws Exception {
						modelSelecionado.setUsuarioDelete(PrincipalDesktop.getUsarioLogado());
						compiladoItem.excluir(modelSelecionado);
					}
				});
				acaoLancamentoAtualizar();
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
						SolicitacaoItemModel model = getModel();
						compiladoItem.merge(model);
					}
				});
				acaoLancamentoAtualizar();
				setModel(factoryCompiladoItem(compiladoItemModel));
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(SolicitacaoItemInternalFrame.this, e1.getLocalizedMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
			}
		}

		private void acaoLancamentoAtualizar() {
			if (obtendoOcorrencias == false) {
				obtendoOcorrencias = true;
				try {
					Conexao.Executar(new Comando() {
						@Override
						public void execute(Session session) throws Exception {
							List<SolicitacaoItemModel> lista = compiladoItemDAO.obterPorSolicitacao((SolicitacaoModel) comboboxCompilado.getSelectedItem());
							setListaDeOcorrencias(lista);

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			obtendoOcorrencias = false;
		}

		private SolicitacaoItemModel getModelSelecionado() {
			if (jTableOcorrencias.getSelectedRow() == -1) {
				return null;
			} else {
				DefaultTableModel defaultTableModel = (DefaultTableModel) jTableOcorrencias.getModel();
				String id = defaultTableModel.getValueAt(jTableOcorrencias.getSelectedRow(), 0).toString().trim();

				SolicitacaoItemModel modelSelecionado = getModelPorID(id);
				return modelSelecionado;
			}
		}

		private SolicitacaoItemModel getModelPorID(String id) {
			SolicitacaoItemModel modelSelecionado = null;
			for (SolicitacaoItemModel model : getListaDeOcorrencias()) {
				if (model.getId().toString().equals(id)) {
					modelSelecionado = model;
					break;
				}
			}
			return modelSelecionado;
		}

		public SolicitacaoItemModel getModel() {

			compiladoItemModel.setUnidade((HospitalModel) comboBoxUnidade.getSelectedItem());
			compiladoItemModel.setDocumento((CategoriaModel) comboboxDocumento.getSelectedItem());
			compiladoItemModel.setEquipamento((DepartamentoModel) comboBoxEquipamento.getSelectedItem());
			 compiladoItemModel.setSolicitacao((SolicitacaoModel) comboboxCompilado.getSelectedItem());
			 compiladoItemModel.setQuantidade(Integer.parseInt( spinnerQuantidade.getValue().toString()) );

			return compiladoItemModel;
		}

		public void setModel
		(SolicitacaoItemModel model) {
			this.compiladoItemModel  = model;

			selectComboBoxCategoria(model.getDocumento());
			selectComboBoxUnidade(model.getUnidade());
			selectComboBoxEquipamento(model.getEquipamento());
			spinnerQuantidade.setValue(model.getQuantidade() != null ? model.getQuantidade() : 1);

			if (compiladoItemModel.getId() != null) {
				buttonExcluir.setEnabled(false);
				comboboxCompilado.setEnabled(false);

				jTableOcorrencias.setEnabled(false);
				jTableOcorrencias.getTableHeader().setEnabled(false);

				buttonEditar.setText("Cancelar");
				buttonEditar.setIcon(imageIconCancelar);

				buttonAddMovimentacao.setText("Salvar Alterações");
			} else {
				comboboxCompilado.setEnabled(true);
				buttonExcluir.setEnabled(true);
				jTableOcorrencias.setEnabled(true);
				jTableOcorrencias.getTableHeader().setEnabled(true);

				buttonEditar.setText("Editar");
				buttonEditar.setIcon(imageIconEditar);

				buttonAddMovimentacao.setText("Adicionar Lançamento");

			}
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					comboboxDocumento.requestFocus();
				}
			});

		}

		private void selectComboBoxCategoria(CategoriaModel categoriaModel) {
			if (categoriaModel != null && categoriaModel.getId() != null)
				for (int i = 0; i < comboboxDocumento.getItemCount(); i++) {
					if (comboboxDocumento.getItemAt(i).getId().compareTo(categoriaModel.getId()) == 0) {
						comboboxDocumento.setSelectedIndex(i);
						break;
					}
				}
			else {
				comboboxDocumento.setSelectedItem(null);
			}
		}

		private void selectComboBoxEquipamento(DepartamentoModel equipamentoModel) {
			if (equipamentoModel != null && equipamentoModel.getId() != null)
				for (int i = 0; i < comboBoxEquipamento.getItemCount(); i++) {
					if (comboBoxEquipamento.getItemAt(i).getId().compareTo(equipamentoModel.getId()) == 0) {
						comboBoxEquipamento.setSelectedIndex(i);
						break;
					}
				}
			else {
				comboBoxEquipamento.setSelectedItem(null);
			}
		}

		private void selectComboBoxUnidade(HospitalModel hospitalModel) {
			if (hospitalModel != null && hospitalModel.getId() != null)
				for (int i = 0; i < comboBoxUnidade.getItemCount(); i++) {
					if (comboBoxUnidade.getItemAt(i).getId().compareTo(hospitalModel.getId()) == 0) {
						comboBoxUnidade.setSelectedIndex(i);
						break;
					}
				}
			else {
				comboBoxUnidade.setSelectedItem(null);
			}
		}

		public List<SolicitacaoItemModel> getListaDeOcorrencias() {
			return listaDeItens;
		}

		public void setListaDeOcorrencias(List<SolicitacaoItemModel> listaDeLancamentos) {
			SolicitacaoItemTableModel defaultTableModelOcorrencias = (SolicitacaoItemTableModel) jTableOcorrencias.getModel();
			defaultTableModelOcorrencias.setRowCount(0);
			
			int count = 0; 
			for (SolicitacaoItemModel compiladoItemModel : listaDeLancamentos) {
				boolean filtrar = true; 
				String filtroEquipamentoNome = compiladoFiltroPanel.getFiltroEquipamentoNome().toLowerCase();
				if(filtroEquipamentoNome != null && !filtroEquipamentoNome.isEmpty() ) { 
					filtrar = compiladoItemModel.getEquipamento().getNome().toLowerCase().contains(filtroEquipamentoNome); 
				}
				String filtroUnidadeNome = compiladoFiltroPanel.getFiltroUnidadeNome().toLowerCase();
				if(filtrar && filtroUnidadeNome != null && !filtroUnidadeNome.isEmpty() ) { 
					filtrar = compiladoItemModel.getUnidade().getNome().toLowerCase().contains(filtroUnidadeNome); 
				}
				
				String filtroDocumentoNome = compiladoFiltroPanel.getFiltroDocumentoNome().toLowerCase();
				if(filtrar && filtroDocumentoNome != null && !filtroDocumentoNome.isEmpty() ) { 
					filtrar = compiladoItemModel.getDocumento().getCategoria_nome_portugues().toLowerCase().contains(filtroDocumentoNome); 
				}
				if(filtrar) {
					defaultTableModelOcorrencias.addRow(compiladoItemModel.toRow());
					count  = count + compiladoItemModel.getQuantidade(); 
				}
			}
			
			DefaultTableModel defaultTableModelPosBancariaFooter = (DefaultTableModel) footerTable.getModel();
			defaultTableModelPosBancariaFooter.setRowCount(0);

			String[] row = new String[] { "<HTML><B>TOTAL de EQUIPAMENTOS: " + count + "</B></HTML> " };
			defaultTableModelPosBancariaFooter.addRow(row);

			
			
			this.listaDeItens = listaDeLancamentos;
			

		
		}
	}
