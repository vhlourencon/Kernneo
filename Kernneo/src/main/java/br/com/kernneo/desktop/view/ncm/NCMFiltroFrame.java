package br.com.kernneo.desktop.view.ncm;

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

import br.com.kernneo.client.exception.NCMException;
import br.com.kernneo.client.model.NCMModel;
import br.com.kernneo.server.negocio.NCM;

public abstract class NCMFiltroFrame extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public JTable table;

    
    private JScrollPane jScrollPaneTable;

    public JPanel panelCenter;
    private JTextField textField;

    private ArrayList<NCMModel> listaDeNCM;
    private JLabel lblEntercSeleciona;
    private JLabel lblNcm;
    private JTextField textFieldNCM;

    public NCMFiltroFrame() throws NCMException {
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
		textField.setBounds(106, 41, 293, 23);
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
	
	JLabel lblNewLabel_1 = new JLabel("Nome:");
	lblNewLabel_1.setBounds(106, 24, 46, 14);
	panel.add(lblNewLabel_1);
	
	lblNcm = new JLabel("NCM:");
	lblNcm.setBounds(10, 24, 46, 14);
	panel.add(lblNcm);
	
	textFieldNCM = new JTextField();
	textFieldNCM.setBounds(10, 41, 86, 23);
	textFieldNCM.addKeyListener(new KeyListener() {
	    
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
	panel.add(textFieldNCM);
	textFieldNCM.setColumns(10);

	setColunasDaTabela(new String[] { "id", "NCM ", "Descrição" });
	listaDeNCM = new NCM().obterTodos(NCMModel.class);
	setListNCM(listaDeNCM);

	table.getColumnModel().getColumn(0).setMinWidth(0);
	table.getColumnModel().getColumn(0).setMaxWidth(0);

	table.getColumnModel().getColumn(1).setMinWidth(80);
	table.getColumnModel().getColumn(1).setMaxWidth(80);

	table.addMouseListener(new MouseListener() {

	     public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	    }

	    public void mousePressed(MouseEvent e) {
		if (e.getClickCount() == 2) {
		    getNCMSelecionado(getModelSelecionado());
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
		getNCMSelecionado(getModelSelecionado());
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
	String ncm = textFieldNCM.getText();
	
	ArrayList<NCMModel> listaAux = new ArrayList<NCMModel>();
	for (NCMModel model : listaDeNCM) {
//	    if ( model.getNcm().toLowerCase().contains(ncm.toLowerCase()) &&  model.getNome().toLowerCase().contains(nome.toLowerCase())) {
//		listaAux.add(model);
//	    }
	}

	setListNCM(listaAux);
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

    public abstract void getNCMSelecionado(NCMModel ncm);

    private NCMModel getModelSelecionado() {

	if (table.getSelectedRow() == -1) {

	    return null;

	} else {
	    DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
	    String id = defaultTableModel.getValueAt(table.getSelectedRow(), 0).toString().trim();

	    NCMModel modelSelecionado = null;
	    for (NCMModel model :  listaDeNCM) {

		if (model.getId().toString().equals(id)) {
		    modelSelecionado = model;
		    break;
		}
	    }
	    return modelSelecionado;
	}
    }

    public String[] modelToRow(NCMModel model) {
	String id = String.valueOf(model.getId());
//	String nome = model.getNome();
//	String ncm = model.getNcm(); 
	
	

	String[] row = new String[] { id};
	return row;
    }

    public void setListNCM(ArrayList<NCMModel> listaDeNCM) {

	limpaTabela();

	for (NCMModel model : listaDeNCM) {

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
