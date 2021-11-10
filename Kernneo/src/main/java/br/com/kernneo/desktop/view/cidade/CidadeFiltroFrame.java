package br.com.kernneo.desktop.view.cidade;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComboBox;
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

import br.com.kernneo.client.exception.CidadeException;
import br.com.kernneo.client.exception.EstadoException;
import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.EstadoModel;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import br.com.kernneo.server.negocio.Cidade;
import br.com.kernneo.server.negocio.Estado;

public abstract class CidadeFiltroFrame extends JDialog {

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

    private JComboBox comboBoxUF;
    private JLabel lblEntercSeleciona;

    public CidadeFiltroFrame() throws EstadoException, CidadeException {
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
		textField.setBounds(83, 41, 293, 20);
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

	lblNewLabel = new JLabel("UF:");
	lblNewLabel.setBounds(10, 24, 46, 14);
	panel.add(lblNewLabel);

	comboBoxUF = new JComboBox();
	comboBoxUF.setBounds(10, 41, 63, 20);
	panel.add(comboBoxUF);

	comboBoxUF.addItem("--");
	ArrayList<EstadoModel> listaDeEstados = new Estado().obterTodos(EstadoModel.class);
	for (EstadoModel estadoModel : listaDeEstados) {
	    comboBoxUF.addItem(estadoModel.getSigla());
	}
	comboBoxUF.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		acaoFiltrar();

	    }
	});

	JLabel lblNewLabel_1 = new JLabel("Nome:");
	lblNewLabel_1.setBounds(83, 24, 46, 14);
	panel.add(lblNewLabel_1);

	setColunasDaTabela(new String[] { "id", "Nome da Cidade", "UF", "Código IBGE" });
	listaDeCidades = new Cidade().obterTodos(CidadeModel.class);
	setListaDeCidades(listaDeCidades);

	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);

	table.getColumnModel().getColumn(2).setMinWidth(40);
	table.getColumnModel().getColumn(2).setMaxWidth(40);

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
	String uf = "";
	if (comboBoxUF.getSelectedIndex() > 0) {
	    uf = comboBoxUF.getSelectedItem().toString();
	}

	String nomeCidade = textField.getText();
	System.out.println(nomeCidade);

	ArrayList<CidadeModel> listaAux = new ArrayList<CidadeModel>();
	for (CidadeModel cidadeModel : listaDeCidades) {
	    if (cidadeModel.getEstado().getSigla().contains(uf) && cidadeModel.getNome().toLowerCase().contains(nomeCidade.toLowerCase())) {
		listaAux.add(cidadeModel);
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

    public abstract void getCidadeSelecionada(CidadeModel cidade);

    private CidadeModel getModelSelecionado() {

	if (table.getSelectedRow() == -1) {

	    return null;

	} else {
	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

	    CidadeModel modelSelecionado = null;
	    for (CidadeModel model : listaDeCidades) {

		if (model.getId().toString().equals(id)) {
		    modelSelecionado = model;
		    break;
		}
	    }
	    return modelSelecionado;
	}
    }

    public String[] modelToRow(CidadeModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	String uf = model.getEstado().getSigla();
	//String codigoIBGE = model.getCodigo_ibge().toString();

	String[] row = new String[] { id, descricao, uf };
	return row;
    }

    public void setListaDeCidades(ArrayList<CidadeModel> listaDeCidades) {

	limpaTabela();

	for (CidadeModel model : listaDeCidades) {

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
