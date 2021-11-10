package br.com.kernneo.desktop.view.fornecedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
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

import br.com.kernneo.client.exception.FornecedorException;
import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import br.com.kernneo.server.negocio.Fornecedor;

public abstract class FornecedorFiltroDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public JTable table;

    public PaginacaoButtonBarComponent paginacaoButtonBarComponent;

    private JScrollPane jScrollPaneTable;

    public JPanel panelCenter;
    private JTextField textFieldRazaoSocial;

    private ArrayList<FornecedorModel> lista;
    private JLabel lblEntercSeleciona;
    private JTextField textFieldNomeFantasia;
    private JTextField textFieldAbreviatura;

    private JComboBox comboBoxTipo;

    public FornecedorFiltroDialog() throws FornecedorException {
	setRootPaneCheckingEnabled(false);
	setResizable(true);
	getContentPane().setLayout(new BorderLayout(0, 0));
	setModal(true);
	setPreferredSize(new Dimension(250, 420));
	setSize(916, 420);

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

	textFieldRazaoSocial = new JTextField();
	textFieldRazaoSocial.setBounds(10, 41, 180, 23);
	panel.add(textFieldRazaoSocial);
	textFieldRazaoSocial.setColumns(10);
	textFieldRazaoSocial.addKeyListener(new KeyAdapter() {
	    public void keyReleased(KeyEvent e)  {
		acaoFiltrar();
	    }
	});
	
	

	JLabel lblNewLabel_1 = new JLabel("Razão Social:");
	lblNewLabel_1.setBounds(10, 24, 95, 14);
	panel.add(lblNewLabel_1);

	JLabel lblFantasia = new JLabel("Fantasia:");
	lblFantasia.setBounds(200, 24, 95, 14);
	panel.add(lblFantasia);

	textFieldNomeFantasia = new JTextField();
	textFieldNomeFantasia.setColumns(10);
	textFieldNomeFantasia.setBounds(200, 41, 180, 23);
	textFieldNomeFantasia.addKeyListener(new KeyAdapter() {
	    public void keyReleased(KeyEvent e)  {
		acaoFiltrar();
	    }
	});
	panel.add(textFieldNomeFantasia);

	JLabel label = new JLabel("Tipo:");
	label.setBounds(580, 27, 46, 14);
	panel.add(label);

	comboBoxTipo = new JComboBox();
	comboBoxTipo.setModel(new DefaultComboBoxModel(new String[] { "TODOS", "Fisica ", "Juridica" }));
	comboBoxTipo.setSelectedIndex(0);
	comboBoxTipo.setBounds(580, 41, 97, 23);
	comboBoxTipo.addActionListener(new ActionListener() {
	    
	    public void actionPerformed(ActionEvent e) {
		acaoFiltrar();
	    }
	});
	panel.add(comboBoxTipo);

	JLabel lblAbreviatura = new JLabel("Abreviatura:");
	lblAbreviatura.setBounds(390, 24, 95, 14);
	panel.add(lblAbreviatura);

	textFieldAbreviatura = new JTextField();
	textFieldAbreviatura.setColumns(10);
	textFieldAbreviatura.setBounds(390, 41, 180, 23);
	panel.add(textFieldAbreviatura);
	textFieldAbreviatura.addKeyListener(new KeyAdapter() {
	    public void keyReleased(KeyEvent e)  {
		acaoFiltrar();
	    }
	});

	setColunasDaTabela(new String[] { "id", " RAZÃO SOCIAL ", " Nome fantasia", " Abreviatura", "CNPJ", "CPF", "Tipo " });
	lista = new Fornecedor().obterTodos(FornecedorModel.class);
	setLista(lista);

	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);

	table.getColumnModel().getColumn(1).setMinWidth(150);
	table.getColumnModel().getColumn(2).setMinWidth(150);
	table.getColumnModel().getColumn(6).setMaxWidth(80);

	table.addMouseListener(new MouseListener() {

	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2) {
		    getModelSelecionado(getSelecionado());
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
		getModelSelecionado(getSelecionado());
		setModal(false);
		dispose();

	    }

	});

	SwingUtilities.invokeLater(new Runnable() {

	    public void run() {
		textFieldRazaoSocial.requestFocus();
	    }
	});

    }

    protected void acaoFiltrar() {

	String razaoSocial = textFieldRazaoSocial.getText();
	String nomeFantasia = textFieldNomeFantasia.getText();
	String abreviatura = textFieldAbreviatura.getText();

	ArrayList<FornecedorModel> listaAux = new ArrayList<FornecedorModel>();
	for (FornecedorModel model : lista) {
	    if (((model.getRazaoSocial().toLowerCase().contains(razaoSocial)) && (model.getFantasia().toLowerCase().contains(nomeFantasia)))) {
		if (comboBoxTipo.getSelectedIndex() == 0) {
		    listaAux.add(model);
		} else {
		    if (comboBoxTipo.getSelectedIndex() == 1) {
			
		    } else if (comboBoxTipo.getSelectedIndex() == 2) {
			
		    }
		}
	    }

	}

	setLista(listaAux);
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

    public abstract void getModelSelecionado(FornecedorModel model);

    private FornecedorModel getSelecionado() {

	if (table.getSelectedRow() == -1) {

	    return null;

	} else {
	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

	    FornecedorModel modelSelecionado = null;
	    for (FornecedorModel model : lista) {

		if (model.getId().toString().equals(id)) {
		    modelSelecionado = model;
		    break;
		}
	    }
	    return modelSelecionado;
	}
    }

    public String[] modelToRow(FornecedorModel model) {
	String id = String.valueOf(model.getId());
	String razaoSocial = model.getRazaoSocial();
	String nomeFantasia = model.getFantasia();
//	String abreviatura = model.getAbreviatura();
	String cnpj = model.getCnpj();


	String[] row = new String[] { id, razaoSocial, nomeFantasia, cnpj};
	return row;
    }

    public void setLista(ArrayList<FornecedorModel> listaDeModel) {

	limpaTabela();

	for (FornecedorModel model : listaDeModel) {

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
