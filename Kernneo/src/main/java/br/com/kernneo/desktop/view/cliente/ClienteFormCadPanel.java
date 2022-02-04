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
    	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de Cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 446, 99);
	add(panel, "cell 0 0");
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(20, 26, 46, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(20, 43, 400, 30);

	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(50);
	
	
	
    }
    
    

    public JTextField getTextFieldDescricao() {
        return textFieldDescricao;
    }

    public void setTextFieldDescricao(JTextField textFieldDescricao) {
        this.textFieldDescricao = textFieldDescricao;
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
	textFieldDescricao.requestFocus();

    }
}
