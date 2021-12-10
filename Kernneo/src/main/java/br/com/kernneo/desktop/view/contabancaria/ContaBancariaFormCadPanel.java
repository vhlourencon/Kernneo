package br.com.kernneo.desktop.view.contabancaria;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import br.com.kernneo.desktop.view.widget.JMoedaRealTextField;
import br.com.kernneo.desktop.view.widget.JMoneyField;

public class ContaBancariaFormCadPanel extends GenericFormCadPanel<ContaBancariaModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private JFormattedTextField textField;

    public ContaBancariaFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 181);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de ContaBancaria", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 516, 159);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(10, 38, 46, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(10, 53, 438, 20);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);
	
	JLabel lblNewLabel = new JLabel("Saldo Inicial:");
	lblNewLabel.setBounds(10, 84, 85, 14);
	panel.add(lblNewLabel);
	
	
		
	 
	DecimalFormat dFormat = new DecimalFormat("#,##0.00");
	NumberFormatter Formatter = new NumberFormatter(dFormat);
	Formatter.setFormat(dFormat);
	Formatter.setAllowsInvalid(false);

	textField = new DecimalFormattedField(DecimalFormattedField.REAL);
	textField.setFormatterFactory(new DefaultFormatterFactory(Formatter));
	textField.setBounds(10, 109, 126, 30);
	panel.add(textField);
	textField.setColumns(10);

    }

    @Override
    public ContaBancariaModel getModel() {
	model.setNome(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(ContaBancariaModel model) {

	if (model == null) {
	    model = new ContaBancariaModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getNome());

    }
}
