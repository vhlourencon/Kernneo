package br.com.kernneo.desktop.view.solicitacao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;

import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.SolicitacaoItemModel;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.dao.SolicitacaoItemDAO;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Solicitacao;
import br.com.kernneo.server.negocio.SolicitacaoItem;
import br.com.kernneo.server.negocio.Estado;

public class SolicitacaoGeralFrame extends JInternalFrame
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public JTable table;

		public PaginacaoButtonBarComponent paginacaoButtonBarComponent;

		private JScrollPane jScrollPaneTable;

		public JPanel panelCenter;
		private JLabel lblNewLabel;
		private JTextField textField;

		private ArrayList<CidadeModel> listaDeCidades;

		private JComboBox<SolicitacaoModel> comboBoxCompilado;
		private JLabel lblEntercSeleciona;

		private SolicitacaoItemDAO compiladoItemDAO;

		private HashMap<Long, SolicitacaoItemModel> mapDosCompilados;

		protected List<SolicitacaoItemModel> lista;

		public SolicitacaoGeralFrame() throws Exception {
			setRootPaneCheckingEnabled(false);
			setResizable(true);
			setClosable(true);
			setVisible(true);
			setMaximizable(true);
			setLocation(250, 50);
			setSize(608, 526);
			setPreferredSize(new Dimension(608, 526));

			setIconifiable(true);

			getContentPane().setLayout(new BorderLayout(0, 0));

			compiladoItemDAO = new SolicitacaoItemDAO();

			JPanel panelSouth = new JPanel();
			getContentPane().add(panelSouth, BorderLayout.SOUTH);

			lblEntercSeleciona = new JLabel("Enter/\"Clique duplo\" para  SELECIONAR");
			panelSouth.add(lblEntercSeleciona);

			table = new JTable();
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
			table.setFillsViewportHeight(true);

			jScrollPaneTable = new JScrollPane(table);

			panelCenter = new JPanel();
			panelCenter.setLayout(new BorderLayout());

			panelCenter.add(getjScrollPaneTable());

			getContentPane().add(panelCenter, BorderLayout.CENTER);
			JPanel panel = new JPanel();
			panel.setLayout(null);
			panel.setPreferredSize(new Dimension(100, 100));

			panel.setBorder(new TitledBorder(null, "Filtrar Por", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			getContentPane().add(panel, BorderLayout.NORTH);

			textField = new JTextField();
			textField.setBounds(228, 41, 293, 30);
			panel.add(textField);
			textField.setColumns(10);
			textField.addKeyListener(new KeyListener() {

				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				public void keyReleased(KeyEvent e) {
					 acaoFiltrar();

				}

				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});

			lblNewLabel = new JLabel("Compilado:");
			lblNewLabel.setBounds(10, 24, 103, 14);
			panel.add(lblNewLabel);

			comboBoxCompilado = new JComboBox<SolicitacaoModel>();
			comboBoxCompilado.setBounds(10, 41, 208, 30);
			panel.add(comboBoxCompilado);

			JLabel lblNewLabel_1 = new JLabel("Equipamento:");
			lblNewLabel_1.setBounds(228, 24, 120, 14);
			panel.add(lblNewLabel_1);

			setColunasDaTabela(new String[] { "Equipamento", "Quantidade" });

			table.getColumnModel().getColumn(1).setMinWidth(80);
			table.getColumnModel().getColumn(1).setMaxWidth(80);
			table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");

			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					textField.requestFocus();
				}
			});
			Conexao.Executar(new Comando() {

				@Override
				public void execute(Session session) throws Exception {
					List<SolicitacaoModel> lista = new Solicitacao().obterTodos(SolicitacaoModel.class);
					for (SolicitacaoModel compiladoModel : lista) {
						comboBoxCompilado.addItem(compiladoModel);
					}
					comboBoxCompilado.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							acaoAtualizarLista();
							acaoFiltrar();
						}
					});

				}
			});
			acaoAtualizarLista();
			acaoFiltrar();

		}

		private void acaoAtualizarLista() {
			try {
				Conexao.Executar(new Comando() {

					@Override
					public void execute(Session session) throws Exception {
						lista = compiladoItemDAO.obterPorSolicitacao((SolicitacaoModel) comboBoxCompilado.getSelectedItem());
					}
				});
				mapDosCompilados = new HashMap<Long, SolicitacaoItemModel>();
				for (SolicitacaoItemModel compiladoItemModel : lista) {
					Long idEquipamento = compiladoItemModel.getEquipamento().getId();
					SolicitacaoItemModel modelAux = mapDosCompilados.get(idEquipamento);
					if (modelAux != null) {
						Integer total = (modelAux.getQuantidade() + compiladoItemModel.getQuantidade());
						modelAux.setQuantidade(total);
					} else {
						modelAux = compiladoItemModel;
					}
					mapDosCompilados.put(idEquipamento, modelAux);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		protected void acaoFiltrar() {
			limpaTabela();
			for (SolicitacaoItemModel compiladoItemModel : mapDosCompilados.values()) {
				boolean filtrar = false;
				String textoParaBusca = textField.getText();

				if (textoParaBusca != null && !textoParaBusca.trim().equals("")) {
					textoParaBusca = textoParaBusca.toLowerCase(); 
					if (compiladoItemModel.getEquipamento().getNome().toLowerCase().contains(textoParaBusca)) {
						filtrar = true;
					}
				} else {
					filtrar = true;
				}
				if (filtrar) {
					DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
					defaultTableModel.addRow(modelToRow(compiladoItemModel));
				}
			}

		}

		public JPanel getPanelCenter() {
			return panelCenter;
		}

		public void setPanelCenter(JPanel panelCenter) {
			this.panelCenter = panelCenter;
		}

		public void setColunasDaTabela(String[] colunas) {
			table.setModel(new DefaultTableModel(new Object[][] {

			}, colunas) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			});

		}

		public String[] modelToRow(SolicitacaoItemModel model) {
			String equipamento = "";
			String quantidade = "";
			if (model != null && model.getEquipamento() != null) {
				equipamento = model.getEquipamento().getNome();
			}
			if (model != null && model.getQuantidade() != null) {
				quantidade = String.valueOf(model.getQuantidade());
			}
			// String codigoIBGE = model.getCodigo_ibge().toString();

			String[] row = new String[] { equipamento, quantidade };
			return row;
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
	}
