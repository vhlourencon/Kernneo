package br.com.kernneo.desktop.view.regulacao;

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
import org.jdesktop.swingx.event.TableColumnModelExtListener;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.client.utils.StringUtils;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.negocio.Departamento;
import br.com.kernneo.server.negocio.Movimentacao;
import br.com.kernneo.server.negocio.Regulacao;
import net.miginfocom.swing.MigLayout;
import net.sf.jasperreports.engine.JRException;

import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Cursor;

public class RegulacaoListInternalFrame extends GenericListInternalFrame<Regulacao, RegulacaoModel>
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JScrollPane jscrollPaneFooter;
		private JTable footerTable;

		private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		public RegulacaoListInternalFrame() {
			table.setModel(new RegulacaoTableModel());

			table.getColumnModel().getColumn(0).setResizable(true);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			table.getColumnModel().getColumn(0).setMaxWidth(0);
			table.getColumnModel().getColumn(0).setMinWidth(0);
			
			 table.getColumnModel().getColumn(3).setHeaderValue("Data Hora");
			 
			table.getColumnModel().getColumn(7).setMaxWidth(100);
			table.getColumnModel().getColumn(7).setMinWidth(100);
			
			table.getColumnModel().getColumn(8).setMaxWidth(100);
			table.getColumnModel().getColumn(8).setMinWidth(100);
			
			table.getColumnModel().getColumn(2).setMaxWidth(150);
			table.getColumnModel().getColumn(2).setMinWidth(150);
			
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			dtcr.setHorizontalAlignment(SwingConstants.LEFT);
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

				setNegocio(new Regulacao());
				setModel(new RegulacaoModel());
				setPagina(new GenericPagina<RegulacaoModel>());
				setGenericFiltroPanel(new RegulacaoFiltroPanel(), new RegulacaoModel(), true);
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
		public void setPagina(GenericPagina<RegulacaoModel> pagina) {
			super.setPagina(pagina);
			DefaultTableModel defaultTableModelPosBancariaFooter = (DefaultTableModel) footerTable.getModel();
			defaultTableModelPosBancariaFooter.setRowCount(0);

			BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
			//for (MovimentacaoModel model : pagina.getListaRegistros()) {
				//bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
			//}
			int qtdRegistros = 0; 
			if(pagina != null && pagina.getListaRegistros() != null) { 
				qtdRegistros = pagina.getListaRegistros().size();
			}
			String[] row = new String[] { "<HTML><B>TOTAL de REGULAÇÕES: " + qtdRegistros + "</B></HTML> " };
			defaultTableModelPosBancariaFooter.addRow(row);

			table.getColumnModel().getColumn(0).setMinWidth(0);
			table.getColumnModel().getColumn(0).setMaxWidth(0);

			// footerTable.getColumnModel().getColumn(0).setMinWidth(0);
			// footerTable.getColumnModel().getColumn(0).setMaxWidth(0);

		}
		
		private BigDecimal getTotal() { 
			BigDecimal bigDecimalSaldo = new BigDecimal(BigInteger.ZERO);
			for (RegulacaoModel model : getPagina().getListaRegistros()) {
			//	bigDecimalSaldo = bigDecimalSaldo.add(model.getValor());
			}
			return bigDecimalSaldo;
		}

		@Override
		public Object[] modelToRow(RegulacaoModel model) {
			Object[] row = model.toRow();
			row[3]  = formatter.format(model.getDataHora());
			return row;

		}

		
	}
