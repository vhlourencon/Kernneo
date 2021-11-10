package br.com.kernneo.desktop.view.cartao;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CartaoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class CartaoFormCadPanel extends GenericFormCadPanel<CartaoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public CartaoFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 108);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Cartao", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
    public CartaoModel getModel() {
	model.setDescricao(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(CartaoModel model) {

	if (model == null) {
	    model = new CartaoModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getDescricao());

    }
}
