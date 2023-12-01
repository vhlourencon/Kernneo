package br.com.kernneo.desktop.view.financeiro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Departamento;
import br.com.kernneo.server.negocio.Movimentacao;
import br.com.kernneo.server.negocio.PosicaoFinanceira;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;

import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;

public class MovimentacaoListInternalFrame extends GenericListInternalFrame<Movimentacao, MovimentacaoModel>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JScrollPane jscrollPaneFooter;
		private JTable footerTable;
		private PosicaoFinanceira posicaoFinanceira; 

		private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		public MovimentacaoListInternalFrame() {
			table.setModel(new MovimentacaoListTableModel());
			posicaoFinanceira = new PosicaoFinanceira(); 
			// setFormCadPanel(new DepartamentoFormCadPanel());

			// setColunasDaTabela(new String[] { "id", "Descrição", "Conta", "Tipo",
			// "Valor", "Pago/Recebido", "Data Pago/Recebido" });

			table.getColumnModel().getColumn(0).setResizable(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.CENTER);
			table.setDefaultRenderer(Object.class, dtcr);

			buttonBarComponent.removeAll();
			buttonBarComponent.setLayout(new MigLayout("", "[105px][105px][105px]", "[40px]"));

			buttonBarComponent.add(buttonBarComponent.btConsultar, "cell 0 0,grow");
			buttonBarComponent.add(buttonBarComponent.btImprimir, "cell 1 0,grow");
			buttonBarComponent.add(buttonBarComponent.btSair, "cell 2 0,grow");

			buttonBarComponent.btImprimir.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						 /* CURSOR */
			            setCursor(new Cursor(Cursor.WAIT_CURSOR));
						MovimentacaoFiltroPanel movimentacaoFiltroPanel = (MovimentacaoFiltroPanel) getGenericFiltroPanel(); 
						HashMap<String, Object> args = new HashMap<String, Object>();
						args.put("DATA_INI", getFiltroModel().getDataHora());
						args.put("DATA_FIM", getFiltroModel().getDataHoraExecutado());
						args.put("PARAM_CLIENTE_NOME", ((ClienteModel)movimentacaoFiltroPanel.getComboBoxCliente().getSelectedItem()).getNome());
						args.put("PARAM_TIPO", (movimentacaoFiltroPanel.getComboBoxTipo().getSelectedItem()).toString());
						args.put("PARAM_CATEGORIA", (movimentacaoFiltroPanel.getComboBoxCategoria().getSelectedItem()).toString());
						args.put("PARAM_CONTA", (movimentacaoFiltroPanel.getComboBoxConta().getSelectedItem()).toString());
						args.put("PARAM_RECEBIDOS_PAGOS", (movimentacaoFiltroPanel.getComboBoxRecebidosPagos().getSelectedItem()).toString());
						args.put("VALOR_TOTAL", getTotal());
						
						
						
						//args.put("PARAM_TIPO", getFiltroModel().getDataHoraExecutado());
						///args.put("PARAM_CONTA", getFiltroModel().getDataHora());
						///args.put("PARAM_CATEGORIA", getFiltroModel().getDataHoraExecutado());
					

						new MovimentacaoFinanceiraReport().gerar(args, new ArrayList<MovimentacaoModel>(getPagina().getListaRegistros()));
					} catch (ClassNotFoundException | FileNotFoundException | JRException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} finally  { 
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
					}
				}
			});

			footerTable = new JTable();
			footerTable.setFillsViewportHeight(true);
			// footerTable.setColumnModel(table.getColumnModel());
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

			repaint();
			validate();

			jscrollPaneFooter = new JScrollPane(footerTable);
			jscrollPaneFooter.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
			jscrollPaneFooter.setPreferredSize(new Dimension(-1, 35));

			BorderLayout bl_holdingPanel = new BorderLayout();
			bl_holdingPanel.setVgap(5);
			panelCenter.setLayout(bl_holdingPanel);

			panelCenter.add(getjScrollPaneTable(), BorderLayout.CENTER);
			panelCenter.add(jscrollPaneFooter, BorderLayout.SOUTH);

			// getContentPane().add(panelCenter, BorderLayout.CENTER);
			// getContentPane().add(buttonBarComponent, BorderLayout.NORTH);

			// JPanel holdingPanel = new JPanel(bl_holdingPanel);
			// holdingPanel.add(getjScrollPaneTable(), BorderLayout.CENTER);
			// holdingPanel.add(jscrollPaneFooter, BorderLayout.SOUTH);
			// getPanelCenter().setLayout(bl_holdingPanel);
			// getPanelCenter().removeAll();

			try {

				setNegocio(new Movimentacao());
				setModel(new MovimentacaoModel());
				setPagina(new GenericPagina<MovimentacaoModel>());
				setGenericFiltroPanel(new MovimentacaoFiltroPanel(), new MovimentacaoModel(), true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			repaint();
			buttonBarComponent.repaint();
			getContentPane().repaint();
			validate();
			// acaoIrPrimeiraPagina();
			// setm

		}

		@Override
		public void setPagina(GenericPagina<MovimentacaoModel> pagina) {
			super.setPagina(pagina);
			DefaultTableModel defaultTableModelPosBancariaFooter = (DefaultTableModel) footerTable.getModel();
			defaultTableModelPosBancariaFooter.setRowCount(0);

			BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
			for (MovimentacaoModel model : pagina.getListaRegistros()) {
				bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
			}
			String[] row = new String[] { "<HTML><B>TOTAL: " + StringUtils.currencyFormat(bigDecimalSaldo) + "</B></HTML> " };
			defaultTableModelPosBancariaFooter.addRow(row);

			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);

			// footerTable.getColumnModel().getColumn(0).setMinWidth(0);
			// footerTable.getColumnModel().getColumn(0).setMaxWidth(0);

		}
		
		private BigDecimal getTotal() { 
			BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
			for (MovimentacaoModel model : getPagina().getListaRegistros()) {
				bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
			}
			return bigDecimalSaldo;
		}
		
		

		@Override
		public Object[] modelToRow(MovimentacaoModel movimentacaoModel) {

			String id = String.valueOf(movimentacaoModel.getId());
			String descricao = movimentacaoModel.getDescricao();
			String tipo = "";
			String conta = "";
			String categoria = "";
			String cliente = "";
			if (movimentacaoModel.getConta() != null) {
				conta = movimentacaoModel.getConta().getNome();
			}
			if (movimentacaoModel.getTipo() != null) {
				tipo = movimentacaoModel.getTipo().toString();
			}
			if (movimentacaoModel.getCategoria() != null) {
				categoria = movimentacaoModel.getCategoria().getCategoria_nome_portugues();
			}
			if (movimentacaoModel.getCliente() != null) {
				cliente = movimentacaoModel.getCliente().getNome();
			}
			String dataHoraExecutado = "";
			if (movimentacaoModel.getDataHoraExecutado() != null) {
				dataHoraExecutado = formatter.format(movimentacaoModel.getDataHoraExecutado());
			}

			String dataHora = "";
			if (movimentacaoModel.getDataHora() != null) {
				dataHora = formatter.format(movimentacaoModel.getDataHora());
			}

			Object[] row = new Object[] { id, dataHora, categoria, cliente, conta, tipo, movimentacaoModel.currencyFormat(movimentacaoModel.getValor()), movimentacaoModel.isExecutado(), dataHoraExecutado, descricao };
			return row;

		}

		@Override
		public void acaoObterTodos() {
			 try {
	                Conexao.Executar(new Comando() {

	                    @Override
	                    public void execute(Session session) throws Exception {
	                    	
	                        ArrayList<MovimentacaoModel> lista = negocio.obterTodosComFiltro(getGenericFiltroPanel().getModelFiltro());
	                        getPagina().setListaRegistros(lista);
	                        setPagina(getPagina());

	                    }
	                });

	            } catch (Exception e) {
	                e.printStackTrace();
	                JOptionPane.showMessageDialog(this, e.getMessage());
	            }
		}

		public class MovimentacaoListTableModel extends DefaultTableModel
			{

				/**
				* 
				*/
				private static final long serialVersionUID = 1L;

				public MovimentacaoListTableModel() {
					super(new String[] { "id", "Data", "Categoria", "Cliente", "Conta", "Tipo", "Valor", "Pago/Recebido", "Data Pago/Recebido", "Descricao" }, 0);
				}

				@Override
				public Class<?> getColumnClass(int columnIndex) {
					Class clazz = String.class;
					switch (columnIndex) {

					case 7:
						clazz = Boolean.class;
						break;
					}
					return clazz;
				}

			}

	}
