package br.com.kernneo.desktop.view.funcionario;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.desktop.view.widget.GenericFiltroPanel;

public class FuncionarioFiltroPanel extends GenericFiltroPanel<FuncionarioModel> {
    private JTextField textField;

    public FuncionarioFiltroPanel() {
	buttonOk.setSize(77, 28);
	buttonOk.setLocation(238, 34);

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(536, 80));

	JLabel jLabelNome = new JLabel("Nome:");
	jLabelNome.setBounds(10, 21, 101, 14);
	add(jLabelNome);

	textField = new JTextField();
	textField.setBounds(10, 38, 218, 20);
	add(textField);
	textField.setColumns(10);
    }

    @Override
    public FuncionarioModel getModelFiltro() {
	if (modelFiltro == null) {
	    setModelFiltro(new FuncionarioModel());
	    getModelFiltro().setNome(null);
	}

	modelFiltro.setNome(textField.getText());

	return modelFiltro;
    }

}
