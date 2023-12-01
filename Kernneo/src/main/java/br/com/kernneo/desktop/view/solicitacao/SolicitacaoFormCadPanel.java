package br.com.kernneo.desktop.view.solicitacao;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;

public class SolicitacaoFormCadPanel extends GenericFormCadPanel<SolicitacaoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
	private TitledBorder titledBorder;

    public SolicitacaoFormCadPanel() {
	initialize();

    }

    private void initialize() {

	setSize(536, 108);
	setLayout(null);

	 titledBorder = new TitledBorder(null, "Informações do Compilado", TitledBorder.LEADING, TitledBorder.TOP, null, null);

	JPanel panel = new JPanel();
	panel.setBorder(titledBorder);
	panel.setBounds(10, 11, 516, 80);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Nome:");
	labelNome.setBounds(10, 38, 38, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(54, 30, 438, 30);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

    }

    @Override
    public SolicitacaoModel getModel() {
	model.setNome(textFieldDescricao.getText());
	return model;
    }

    @Override
    public void setModel(SolicitacaoModel model) {

	if (model == null) {
	    model = new SolicitacaoModel();
	}
	
	

	super.setModel(model);
	textFieldDescricao.setText(model.getNome());
    }

    public JTextField getTextFieldDescricao() {
        return textFieldDescricao;
    }

    public void setTextFieldDescricao(JTextField textFieldDescricao) {
        this.textFieldDescricao = textFieldDescricao;
    }

	public TitledBorder getTitledBorder() {
		return titledBorder;
	}

	public void setTitledBorder(TitledBorder titledBorder) {
		this.titledBorder = titledBorder;
	}
    
    
}
