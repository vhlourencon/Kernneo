package br.com.kernneo.desktop.view.bairro;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class BairroFormCadPanel extends GenericFormCadPanel<BairroModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private DecimalFormattedField formattedTextField;

    private static final Locale BRAZIL = new Locale("pt", "BR");
    private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
    public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00", REAL);

    public BairroFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(545, 277);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de CFOP", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 525, 141);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(10, 24, 46, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(10, 41, 505, 22);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

	JLabel labelTaxaDeEntraga = new JLabel("Taxa de Entrega:");
	labelTaxaDeEntraga.setBounds(10, 82, 138, 14);
	panel.add(labelTaxaDeEntraga);

	DecimalFormat dFormat = new DecimalFormat("#,###,###.00");
	NumberFormatter Formatter = new NumberFormatter(dFormat);
	Formatter.setFormat(dFormat);
	Formatter.setAllowsInvalid(false);

	formattedTextField = new DecimalFormattedField(DecimalFormattedField.REAL);
	formattedTextField.setFormatterFactory(new DefaultFormatterFactory(Formatter));

	formattedTextField.setBounds(10, 101, 138, 23);
	panel.add(formattedTextField);

    }

    @Override
    public BairroModel getModel() {
	model.setNome(textFieldDescricao.getText());

	double valor = (double) formattedTextField.getDoubleValue();
	//model.setTaxaDeEntrega(valor);
	return model;
    }

    @Override
    public void setModel(BairroModel model) {

	if (model == null) {
	    model = new BairroModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getNome());
	

    }
}
