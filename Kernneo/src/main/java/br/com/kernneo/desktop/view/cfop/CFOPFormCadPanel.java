package br.com.kernneo.desktop.view.cfop;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class CFOPFormCadPanel extends GenericFormCadPanel<CFOPModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private JTextPane textPane;

    public CFOPFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(545, 277);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de CFOP", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 525, 255);
	add(panel);
	panel.setLayout(null);

	JLabel labelCFOP = new JLabel("CFOP:");
	labelCFOP.setBounds(10, 24, 46, 14);
	panel.add(labelCFOP);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(10, 41, 60, 20);
	textFieldDescricao.setSize(60, 20);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

	 textPane = new JTextPane();
	textPane.setBounds(10, 72, 505, 172);
	panel.add(textPane);

    }

    @Override
    public CFOPModel getModel() {
	// model.setCfop(textFieldDescricao.getText());
	 model.setDescricao(textPane.getText());
	return model;
    }

    @Override
    public void setModel(CFOPModel model) {

	if (model == null) {
	    model = new CFOPModel();
	}

	super.setModel(model);

	// textFieldDescricao.setText(model.getCfop());
	 textPane.setText(model.getDescricao());

    }
}
