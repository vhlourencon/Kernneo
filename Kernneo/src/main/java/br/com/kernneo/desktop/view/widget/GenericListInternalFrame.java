package br.com.kernneo.desktop.view.widget;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.client.model.GenericModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Negocio;

public class GenericListInternalFrame<NEGOCIO extends Negocio, GENERICMODEL extends GenericModel>
		extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected NEGOCIO negocio;
	protected GENERICMODEL model;
	protected GENERICMODEL filtroModel;
	private GenericFormCadPanel<GENERICMODEL> formCadPanel;
	private GenericFiltroPanel<GENERICMODEL> genericFiltroPanel;
	private GenericPagina<GENERICMODEL> pagina;
	private boolean isPaginada;

	public JTable table;
	public ButtonBarComponent buttonBarComponent;

	public PaginacaoButtonBarComponent paginacaoButtonBarComponent;

	private JScrollPane jScrollPaneTable;

	public JPanel panelCenter;

	public GenericListInternalFrame() {
		setRootPaneCheckingEnabled(false);
		setResizable(true);
		getContentPane().setLayout(new BorderLayout(0, 0));
		setResizable(false);
		setClosable(true);
		setVisible(true);
		setMaximizable(true);
		setLocation(250, 50);
		setSize(560, 526);
		setIconifiable(true);

		buttonBarComponent = new ButtonBarComponent();
		buttonBarComponent.btIncluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				eventoBotaoIncluir();

			}
		});

		buttonBarComponent.btCancelar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				setModoLista();

			}
		});

		buttonBarComponent.btSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if (getFormCadPanel().getModel().getId() == null) {
					acaoSalvar();
				} else {
					acaoEditar();
				}

			}
		});

		buttonBarComponent.btExcluir.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GENERICMODEL modelSelecionado = getModelSelecionado();
				if (modelSelecionado == null) {
					JOptionPane.showMessageDialog(null, "Selecione um registro");
				} else {
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja excluir?", "Warning", dialogButton);
					if (dialogResult == JOptionPane.YES_OPTION) {
						acaoExcluir(modelSelecionado);
					}

				}
			}
		});

		buttonBarComponent.btAlterar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				GENERICMODEL modelSelecionado = getModelSelecionado();
				if (modelSelecionado == null) {
					JOptionPane.showMessageDialog(null, "Selecione um registro");
				} else {
					getFormCadPanel().setModel(modelSelecionado);
					setModoForm();
				}

			}
		});

		buttonBarComponent.btSair.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				hide();

			}
		});

		buttonBarComponent.btConsultar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (buttonBarComponent.btConsultar.isSelected()) {

					getPanelCenter().add(getGenericFiltroPanel(), BorderLayout.NORTH);
				} else {
					getPanelCenter().remove(getGenericFiltroPanel());
				}

				repaint();
				validate();

			}
		});

		table = new JTable();
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		table.setFillsViewportHeight(true);

		jScrollPaneTable = new JScrollPane(table);

		panelCenter = new JPanel();
		panelCenter.setLayout(new BorderLayout());

		paginacaoButtonBarComponent = new PaginacaoButtonBarComponent();
		if (isPaginada == true) {
			getContentPane().add(paginacaoButtonBarComponent, BorderLayout.SOUTH);
		}

		paginacaoButtonBarComponent.btProx.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				acaoIrProximaPagina();
			}
		});

		paginacaoButtonBarComponent.btUlt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				acaoIrUltimaPagina();
			}
		});

		paginacaoButtonBarComponent.btPrim.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				acaoIrPrimeiraPagina();
			}
		});

		paginacaoButtonBarComponent.btAnt.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				acaoIrPaginaAnterior();
			}
		});

		acaoAddComponentes();
		setModoLista();

	}

	public void eventoBotaoIncluir() {
		getFormCadPanel().setModel(null);
		setModoForm();

	}

	public void acaoEsconderPrimeiraColuna() {
		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);

	}

	public void acaoAddComponentes() {

		panelCenter.add(getjScrollPaneTable());

		getContentPane().add(panelCenter, BorderLayout.CENTER);
		getContentPane().add(buttonBarComponent, BorderLayout.NORTH);

	}

	protected void acaoExcluir(GENERICMODEL model) {
		try {
			Conexao.Executar(new Comando() {

				@Override
				public void execute(Session session) throws Exception {
					negocio.excluir(model);
					if (isPaginada() == false) {
						getPagina().getListaRegistros().remove((GENERICMODEL) model);
						acaoAtualizarPagina();
					} else {
						if (getPagina().getResto() == 0) {
							getPagina().setPaginaAtual(getPagina().getUltimaPagina());
							acaoIrProximaPagina();
						} else {
							acaoIrUltimaPagina();
						}
					}

				}
			});
			JOptionPane.showMessageDialog(this, "Excluido com sucesso!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}

	}

	protected void acaoEditar() {
		try {

			Conexao.Executar(new Comando() {

				@Override
				public void execute(Session session) throws Exception {
					daoEditar(getFormCadPanel().getModel());
				

				}
			});
			acaoAtualizarPagina();
			setModoLista();
			JOptionPane.showMessageDialog(this, "Alterado com sucesso!");

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public void eventoAcaoEditar(GENERICMODEL model) {

	}

	public void eventoAcaoSalvar(GENERICMODEL model) {

	}

	public void daoSalvar(GENERICMODEL model) throws Exception {
		negocio.salvar(model);
	}

	public void daoEditar(GENERICMODEL model) throws Exception {
		negocio.alterar(model);
	}

	public void acaoObterTodos() {
		try {
			Conexao.Executar(new Comando() {

				@Override
				public void execute(Session session) throws Exception {
					ArrayList<GENERICMODEL> lista = negocio
							.obterTodosComFiltro(getGenericFiltroPanel().getModelFiltro());
					getPagina().setListaRegistros(lista);
					acaoAtualizarPagina();

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public void acaoSalvar() {

		try {
			Conexao.Executar(new Comando() {

				@Override
				public void execute(Session session) throws Exception {
					GenericModel model = getFormCadPanel().getModel();
					daoSalvar((GENERICMODEL) model);
					
				}
			});
			if (isPaginada() == false) {
				getPagina().getListaRegistros().add((GENERICMODEL) model);
				acaoAtualizarPagina();
			} else {
				if (getPagina().getResto() == 0) {
					getPagina().setPaginaAtual(getPagina().getUltimaPagina());
					acaoIrProximaPagina();
				} else {
					acaoIrUltimaPagina();
				}
			}				
			setModoLista();
			JOptionPane.showMessageDialog(this, "Inserido com sucesso!");
			eventoAcaoSalvar((GENERICMODEL) model);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}

	}

	public void acaoIrPrimeiraPagina() {
		try {
			Conexao.Executar(new Comando() {
				
				@Override
				public void execute(Session session) throws Exception {
					getPagina().setPaginaAtual(0);
					setPagina(negocio.obterPaginaPosterior(getPagina(), getFiltroModel()));
					
				}
			});
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public void acaoIrProximaPagina() {
		try {
			Conexao.Executar(new Comando() {
				
				@Override
				public void execute(Session session) throws Exception {
					paginacaoButtonBarComponent.liberarComponentes(false);
					setPagina(negocio.obterPaginaPosterior(getPagina(), getFiltroModel()));
				}
			});
		

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public void acaoIrPaginaAnterior() {
		try {
			Conexao.Executar(new Comando() {
				
				@Override
				public void execute(Session session) throws Exception {
					paginacaoButtonBarComponent.liberarComponentes(false);
					setPagina(negocio.obterPaginaAnterior(getPagina(), getFiltroModel()));

					
				}
			});
			
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public void acaoIrUltimaPagina() {
		try {
			Conexao.Executar(new Comando() {
				
				@Override
				public void execute(Session session) throws Exception {
					paginacaoButtonBarComponent.liberarComponentes(false);
					getPagina().setPaginaAtual(getPagina().getUltimaPagina() - 1);
					setPagina(negocio.obterPaginaPosterior(getPagina(), getFiltroModel()));

					
				}
			});
		
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public void acaoAtualizarPagina() {
		try {
			Conexao.Executar(new Comando() {
				
				@Override
				public void execute(Session session) throws Exception {
					paginacaoButtonBarComponent.liberarComponentes(false);

					if (isPaginada) {
						getPagina().setPaginaAtual(getPagina().getPaginaAtual() - 1);
						setPagina(negocio.obterPaginaPosterior(getPagina(), getModel()));
					} else {
						setPagina(getPagina());
					}
					
				}
			});
			

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();

		}
	}

	public JPanel getPanelCenter() {
		return panelCenter;
	}

	public void setPanelCenter(JPanel panelCenter) {
		this.panelCenter = panelCenter;
	}

	public void setModoForm() {

		buttonBarComponent.removeAll();
		buttonBarComponent.add(buttonBarComponent.btSalvar);
		buttonBarComponent.add(buttonBarComponent.btCancelar);

		remove(panelCenter);
		remove(paginacaoButtonBarComponent);

		getContentPane().add(getFormCadPanel(), BorderLayout.CENTER);

		repaint();
		buttonBarComponent.repaint();
		getContentPane().repaint();
		validate();

	}

	public void setModoLista() {
		buttonBarComponent.removeAll();

		buttonBarComponent.add(buttonBarComponent.btIncluir);
		buttonBarComponent.add(buttonBarComponent.btAlterar);
		buttonBarComponent.add(buttonBarComponent.btExcluir);
		buttonBarComponent.add(buttonBarComponent.btConsultar);
		buttonBarComponent.add(buttonBarComponent.btSair);

		if (getFormCadPanel() != null) {
			remove(getFormCadPanel());
		}

		getContentPane().add(getPanelCenter(), BorderLayout.CENTER);
		if (isPaginada == true) {
			getContentPane().add(paginacaoButtonBarComponent, BorderLayout.SOUTH);
		}

		repaint();
		buttonBarComponent.repaint();
		getContentPane().repaint();
		validate();

	}

	public void setColunasDaTabela(String[] colunas) {
		table.setModel(new DefaultTableModel(new Object[][] {

		}, colunas) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.setDefaultRenderer(String.class, dtcr);

		acaoEsconderPrimeiraColuna();

	}

	public GenericFormCadPanel<GENERICMODEL> getFormCadPanel() {
		return formCadPanel;
	}

	public void setFormCadPanel(GenericFormCadPanel<GENERICMODEL> formCadPanel) {
		this.formCadPanel = formCadPanel;

	}

	public NEGOCIO getNegocio() {
		return negocio;
	}

	public void setNegocio(NEGOCIO negocio) {
		this.negocio = negocio;
	}

	public String[] modelToRow(GENERICMODEL model) {
		return null;
	}

	public GENERICMODEL getModel() {
		return model;
	}

	public void setModel(GENERICMODEL model) {
		this.model = model;
	}

	public GenericPagina<GENERICMODEL> getPagina() {
		return pagina;
	}

	public void setPagina(GenericPagina<GENERICMODEL> pagina) {
		this.pagina = pagina;

		limpaTabela();

		paginacaoButtonBarComponent.getLabelQuantidadeDePaginas().setText(String.valueOf(pagina.getQtdePaginas()));

		if (pagina.getQtdePaginas() == 0) {
			paginacaoButtonBarComponent.liberarComponentes(false);
			paginacaoButtonBarComponent.getTextField().setText("0");
		} else {
			paginacaoButtonBarComponent.getTextField().setText(String.valueOf(pagina.getPaginaAtual()));
			if (pagina.getQtdePaginas() == 1) {
				paginacaoButtonBarComponent.liberarComponentes(false);

			} else {
				if (pagina.getPaginaAtual() == 1) {

					paginacaoButtonBarComponent.btPrim.setEnabled(false);
					paginacaoButtonBarComponent.btProx.setEnabled(true);
					paginacaoButtonBarComponent.btUlt.setEnabled(true);
					paginacaoButtonBarComponent.btAnt.setEnabled(false);
					paginacaoButtonBarComponent.getTextField().setEnabled(true);

				} else if (pagina.getPaginaAtual() < pagina.getQtdePaginas()) {
					paginacaoButtonBarComponent.liberarComponentes(true);
				} else {
					paginacaoButtonBarComponent.btAnt.setEnabled(true);
					paginacaoButtonBarComponent.btPrim.setEnabled(true);
					paginacaoButtonBarComponent.getTextField().setEnabled(true);

				}
			}
		}

		for (GENERICMODEL model : pagina.getListaRegistros()) {
			DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
			defaultTableModel.addRow(modelToRow(model));
		}

		if (getPagina().getListaRegistros().size() == 0) {
			// getTabela().setEmptyMessage("<br><IMG SRC =\"" +
			// GWT.getHostPageBaseURL() +
			// "imagens/iconeListaVazia.png\" center title=\"A lista est\u00E1
			// vazia!\"><br>Lista vazia<br><br>");
			// getTabela().redraw();
		}
		// System.out.println(pagina.getQtdePaginas());

	}

	private GENERICMODEL getModelSelecionado() {

		if (table.getSelectedRow() == -1) {

			return null;

		} else {
			DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
			String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

			GENERICMODEL modelSelecionado = null;
			for (GENERICMODEL model : getPagina().getListaRegistros()) {
				if (model.getId().toString().equals(id)) {
					modelSelecionado = model;
					break;
				}
			}
			return modelSelecionado;
		}
	}

	public JScrollPane getjScrollPaneTable() {
		return jScrollPaneTable;
	}

	public void setjScrollPaneTable(JScrollPane jScrollPaneTable) {
		this.jScrollPaneTable = jScrollPaneTable;
	}

	private void limpaTabela() {

		DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
		defaultTableModel.setRowCount(0);
	}

	public boolean isPaginada() {
		return isPaginada;
	}

	public void setPaginada(boolean isPaginada) {
		this.isPaginada = isPaginada;

		if (isPaginada()) {
			getContentPane().add(paginacaoButtonBarComponent, BorderLayout.SOUTH);
		}
	}

	public GenericFiltroPanel<GENERICMODEL> getGenericFiltroPanel() {
		return genericFiltroPanel;
	}

	public void setGenericFiltroPanel(GenericFiltroPanel<GENERICMODEL> genericFiltroPanel, GENERICMODEL filtroModel,
			boolean selecionado) {
		this.genericFiltroPanel = genericFiltroPanel;

		if (genericFiltroPanel == null) {
			buttonBarComponent.btConsultar.setEnabled(false);
			setFiltroModel(filtroModel);
		} else {
			genericFiltroPanel.setModelFiltro(filtroModel);
			if (selecionado) {
				buttonBarComponent.btConsultar.setSelected(true);
				getPanelCenter().add(getGenericFiltroPanel(), BorderLayout.NORTH);
			}
			getGenericFiltroPanel().buttonOk.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					if (isPaginada()) {
						acaoIrPrimeiraPagina();
					} else {
						acaoObterTodos();
					}

				}
			});
		}

		if (isPaginada()) {
			acaoIrPrimeiraPagina();
		} else {
			acaoObterTodos();
		}
	}

	public GENERICMODEL getFiltroModel() {

		if (genericFiltroPanel == null) {
			return filtroModel;
		} else {
			return genericFiltroPanel.getModelFiltro();
		}

	}

	public void setFiltroModel(GENERICMODEL filtroModel) {
		this.filtroModel = filtroModel;
	};

}
