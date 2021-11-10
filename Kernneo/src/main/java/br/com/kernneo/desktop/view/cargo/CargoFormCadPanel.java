package br.com.kernneo.desktop.view.cargo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CargoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class CargoFormCadPanel extends GenericFormCadPanel<CargoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public CargoFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 108);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Cargo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 516, 80);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Descrição:");
	labelNome.setBounds(10, 38, 66, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(86, 35, 406, 20);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

    }

    @Override
    public CargoModel getModel() {
	model.setDescricao(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(CargoModel model) {

	if (model == null) {
	    model = new CargoModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getDescricao());

    }
}
