package br.com.kernneo.desktop.view.subgrupo;

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

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.SubGrupoException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.PaginacaoButtonBarComponent;
import br.com.kernneo.server.negocio.Categoria;
import br.com.kernneo.server.negocio.SubGrupo;

public abstract class SubGrupoFiltroFrame extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public JTable table;

    public PaginacaoButtonBarComponent paginacaoButtonBarComponent;

    private JScrollPane jScrollPaneTable;

    public JPanel panelCenter;
    private JTextField textField;

    private ArrayList<SubGrupoModel> listaDeSubGrupos;
    private JLabel lblEntercSeleciona;
    private JLabel lblGrupo;
    private JComboBox comboBox;

    private ArrayList<CategoriaModel> listaDeGrupos;

    public SubGrupoFiltroFrame() throws CategoriaException, SubGrupoException {
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
	textField.setBounds(158, 42, 293, 20);
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

	JLabel lblNewLabel_1 = new JLabel("Descrição:");
	lblNewLabel_1.setBounds(158, 25, 116, 14);
	panel.add(lblNewLabel_1);

	lblGrupo = new JLabel("Grupo:");
	lblGrupo.setBounds(10, 25, 46, 14);
	panel.add(lblGrupo);

	comboBox = new JComboBox();
	comboBox.addItem("--");
	listaDeGrupos = new Categoria().obterTodos(CategoriaModel.class);
	for (CategoriaModel grupoModel : listaDeGrupos) {
	    comboBox.addItem(new ComboItem(grupoModel.getDescricao(), grupoModel.getId().toString()));

	}

	comboBox.setBounds(10, 42, 138, 20);
	comboBox.addActionListener(new ActionListener() {

	    public void actionPerformed(ActionEvent e) {
		acaoFiltrar();

	    }
	});

	panel.add(comboBox);

	setColunasDaTabela(new String[] { "id", "Descrição", "Grupo" });
	listaDeSubGrupos = new SubGrupo().obterTodos(SubGrupoModel.class);
	setListaDeSubGrupos(listaDeSubGrupos);

	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);

	table.getColumnModel().getColumn(2).setMinWidth(150);
	table.getColumnModel().getColumn(2).setMaxWidth(150);

	table.addMouseListener(new MouseListener() {

	    public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2) {
		    getSubGrupoSelecionados(getModelSelecionado());
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
		getSubGrupoSelecionados(getModelSelecionado());
		setModal(false);
		dispose();

	    }

	});

	SwingUtilities.invokeLater(new Runnable() {

	    public void run() {
		textField.requestFocus();
	    }
	});

    }

    protected void acaoFiltrar() {

	String nome = textField.getText();
	String idGrupoSelecionado = null;

	CategoriaModel grupoModelSelecionado = getGrupoSelecionado();
	if (grupoModelSelecionado != null) {
	    idGrupoSelecionado = grupoModelSelecionado.getId().toString();
	}

	ArrayList<SubGrupoModel> listaAux = new ArrayList<SubGrupoModel>();
	for (SubGrupoModel model : listaDeSubGrupos) {
	    if (model.getDescricao().toLowerCase().contains(nome.toLowerCase())) {
		if (idGrupoSelecionado != null) {
		    if (model.getGrupo().getId().toString().equals(idGrupoSelecionado)) {
			listaAux.add(model);
		    }

		} else {
		    listaAux.add(model);
		}

	    }
	}

	setListaDeSubGrupos(listaAux);
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

    public abstract void getSubGrupoSelecionados(SubGrupoModel subGrupo);

    private CategoriaModel getGrupoSelecionado() {
	if (comboBox.getSelectedIndex() == 0) {
	    return null;
	} else {
	    Object item = comboBox.getSelectedItem();
	    String value = ((ComboItem) item).getValue();

	    CategoriaModel model = null;

	    for (CategoriaModel grupoModel : listaDeGrupos) {
		if (grupoModel.getId().toString().trim().equals(value)) {
		    model = grupoModel;
		    break;
		}
	    }
	    return model;
	}

    }

    private SubGrupoModel getModelSelecionado() {

	if (table.getSelectedRow() == -1) {

	    return null;

	} else {
	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

	    SubGrupoModel modelSelecionado = null;
	    for (SubGrupoModel model : listaDeSubGrupos) {

		if (model.getId().toString().equals(id)) {
		    modelSelecionado = model;
		    break;
		}
	    }
	    return modelSelecionado;
	}
    }

    public String[] modelToRow(SubGrupoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	String taxaDeEntrega = model.getGrupo().getDescricao();

	String[] row = new String[] { id, descricao, taxaDeEntrega };
	return row;
    }

    public void setListaDeSubGrupos(ArrayList<SubGrupoModel> listaDeSubGrupos) {

	limpaTabela();

	for (SubGrupoModel model : listaDeSubGrupos) {

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
