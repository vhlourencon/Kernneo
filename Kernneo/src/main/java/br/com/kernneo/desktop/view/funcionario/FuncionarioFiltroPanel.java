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
	buttonOk.setSize(77, 30);
	buttonOk.setLocation(444, 48);

	setBorder(new TitledBorder(null, "Filtrar por ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	setLayout(null);
	setPreferredSize(new Dimension(545, 103));

	JLabel jLabelNome = new JLabel("Nome:");
	jLabelNome.setBounds(26, 32, 101, 14);
	add(jLabelNome);

	textField = new JTextField();
	textField.setBounds(26, 48, 410, 30);
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
