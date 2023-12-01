package br.com.kernneo.desktop.view.departamento;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class DepartamentoFiltroPanel extends GenericFiltroPanel<DepartamentoModel> {

    private JTextField textField;

    public DepartamentoFiltroPanel() {
	buttonOk.setSize(77, 28);
	buttonOk.setLocation(312, 37);

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 80));

	JLabel lblNewLabel_1 = new JLabel("Nome:");
	lblNewLabel_1.setBounds(10, 24, 101, 14);
	add(lblNewLabel_1);

	textField = new JTextField();
	textField.setBounds(10, 41, 292, 30);
	add(textField);
	textField.setColumns(10);
    }

    @Override
    public DepartamentoModel getModelFiltro() {
	if (modelFiltro == null) {
	    setModelFiltro(new DepartamentoModel());
	    getModelFiltro().setNome(null);
	}

	modelFiltro.setNome(textField.getText());

	return modelFiltro;
    }

}
