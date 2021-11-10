package br.com.kernneo.desktop.view.unidade;

import javax.swing.JLabel;
import javax.swing.JTextField;

import br.com.kernneo.client.model.UnidadeModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class UnidadeFormCadPanel extends GenericFormCadPanel<UnidadeModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public UnidadeFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(462, 82);
	setLayout(null);
	
	JLabel labelDescricao = new JLabel("Nome:");
	labelDescricao.setBounds(10, 30, 68, 14);
	add(labelDescricao);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(99, 27, 353, 20);
	add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

    }

    @Override
    public UnidadeModel getModel() {
	model.setDescricao(textFieldDescricao.getText());

	

	return model;
    }

    @Override
    public void setModel(UnidadeModel model) {

	if (model == null) {
	    model = new UnidadeModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getDescricao());

    }

}
