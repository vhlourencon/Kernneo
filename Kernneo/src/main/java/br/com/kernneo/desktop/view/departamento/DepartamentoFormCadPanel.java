package br.com.kernneo.desktop.view.departamento;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class DepartamentoFormCadPanel extends GenericFormCadPanel<DepartamentoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public DepartamentoFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 108);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Departamento", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
    public DepartamentoModel getModel() {
	model.setNome(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(DepartamentoModel model) {

	if (model == null) {
	    model = new DepartamentoModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getNome());

    }
}
