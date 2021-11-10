package br.com.kernneo.desktop.view.contabancaria;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class ContaBancariaFormCadPanel extends GenericFormCadPanel<ContaBancariaModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public ContaBancariaFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 108);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de ContaBancaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 516, 80);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(10, 38, 46, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(54, 35, 438, 20);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

    }

    @Override
    public ContaBancariaModel getModel() {
	model.setDescricao(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(ContaBancariaModel model) {

	if (model == null) {
	    model = new ContaBancariaModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getDescricao());

    }
}
