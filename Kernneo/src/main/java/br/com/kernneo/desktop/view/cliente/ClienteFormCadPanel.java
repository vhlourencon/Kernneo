package br.com.kernneo.desktop.view.cliente;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import net.miginfocom.swing.MigLayout;

public class ClienteFormCadPanel extends GenericFormCadPanel<ClienteModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;

    public ClienteFormCadPanel() {
	initialize();

    }

    private void initialize() {

	//setSize(379, 100);
    	setLayout(new MigLayout("", "", "[50px]"));

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 430, 102);
	add(panel, "");
	panel.setLayout(new MigLayout());

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(10, 31, 46, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(10, 48, 300, 30);

	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(50);

    }

    @Override
    public ClienteModel getModel() {
	model.setNome(textFieldDescricao.getText());

	return model;
    }

    @Override
    public void setModel(ClienteModel model) {

	if (model == null) {
	    model = new ClienteModel();
	}

	super.setModel(model);
	textFieldDescricao.setText(model.getNome());

    }
}
