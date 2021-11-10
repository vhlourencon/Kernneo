package br.com.kernneo.desktop.view.bairro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JDialog;
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

import br.com.kernneo.client.exception.BairroException;
import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import br.com.kernneo.server.negocio.Bairro;

public abstract class BairroFiltroFrame extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public JTable table;

    public PaginacaoButtonBarComponent paginacaoButtonBarComponent;

    private JScrollPane jScrollPaneTable;

    public JPanel panelCenter;
    private JTextField textField;

    private ArrayList<BairroModel> listaDeBairros;
    private JLabel lblEntercSeleciona;

    public BairroFiltroFrame() throws BairroException {
	setRootPaneCheckingEnabled(false);
	setResizable(true);
	getContentPane().setLayout(new BorderLayout(0, 0));
	setModal(true);
	setPreferredSize(new Dimension(250, 420));
	setSize(563, 420);

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
		textField.setBounds(10, 41, 293, 30);
		panel.add(textField);
		textField.setColumns(10);
		textField.setMinimumSize(new Dimension(10, 50)); 
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
	
	JLabel lblNewLabel_1 = new JLabel("Nome:");
	lblNewLabel_1.setBounds(10, 24, 46, 14);
	panel.add(lblNewLabel_1);

	setColunasDaTabela(new String[] { "id", "Nome ", "Taxa  de Entreaga" });
	listaDeBairros = new Bairro().obterTodos(BairroModel.class);
	setListaDeCidades(listaDeBairros);

	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);

	table.getColumnModel().getColumn(2).setMinWidth(80);
	table.getColumnModel().getColumn(2).setMaxWidth(80);

	table.addMouseListener(new MouseListener() {

	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2) {
		    getCidadeSelecionada(getModelSelecionado());
		    setModal(false);
		    dispose();
		}

	    }

	    public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	    }
	});

	table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Enter");
	table.getActionMap().put("Enter", new AbstractAction() {

	    public void actionPerformed(ActionEvent e) {
		getCidadeSelecionada(getModelSelecionado());
		setModal(false);
		dispose();

	    }

	});
	
	SwingUtilities.invokeLater( new Runnable() { 

	    public void run() { 
	            textField.requestFocus(); 
	        } 
	    } );

    }

    protected void acaoFiltrar() {
	

	String nome = textField.getText();
	
	ArrayList<BairroModel> listaAux = new ArrayList<BairroModel>();
	for (BairroModel model : listaDeBairros) {
	    if ( model.getNome().toLowerCase().contains(nome.toLowerCase())) {
		listaAux.add(model);
	    }
	}

	setListaDeCidades(listaAux);
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

    public abstract void getCidadeSelecionada(BairroModel cidade);

    private BairroModel getModelSelecionado() {

	if (table.getSelectedRow() == -1) {

	    return null;

	} else {
	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

	    BairroModel modelSelecionado = null;
	    for (BairroModel model : listaDeBairros) {

		if (model.getId().toString().equals(id)) {
		    modelSelecionado = model;
		    break;
		}
	    }
	    return modelSelecionado;
	}
    }

    public String[] modelToRow(BairroModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	String taxaDeEntrega = "";
	
	

	String[] row = new String[] { id, descricao, taxaDeEntrega };
	return row;
    }

    public void setListaDeCidades(ArrayList<BairroModel> listaDeBairros) {

	limpaTabela();

	for (BairroModel model : listaDeBairros) {

	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    defaultTableModel.addRow(modelToRow(model));
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
}
