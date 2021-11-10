package br.com.kernneo.desktop.view.subgrupo;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.GenericFormCadPanel;
import br.com.kernneo.server.negocio.Categoria;

public class SubGrupoFormCadPanel extends GenericFormCadPanel<SubGrupoModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JTextField textFieldDescricao;
    private ArrayList<CategoriaModel> listaDeGrupos;
    private JComboBox comboBox;

    public SubGrupoFormCadPanel() throws CategoriaException {
	initialize();

    }

    private void initialize() throws CategoriaException {

	setSize(536, 121);
	setLayout(null);

	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(null, "Informa\u00E7\u00F5es de SubGrupo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	panel.setBounds(10, 11, 516, 97);
	add(panel);
	panel.setLayout(null);

	JLabel labelNome = new JLabel("Descrição:");
	labelNome.setBounds(10, 56, 71, 14);
	panel.add(labelNome);

	textFieldDescricao = new JTextField();
	textFieldDescricao.setBounds(91, 53, 401, 20);
	panel.add(textFieldDescricao);
	textFieldDescricao.setColumns(10);

	comboBox = new JComboBox();
	comboBox.setBounds(91, 22, 401, 20);
	panel.add(comboBox);

	JLabel lblGrupo = new JLabel("Grupo:");
	lblGrupo.setBounds(10, 25, 46, 14);
	panel.add(lblGrupo);

	listaDeGrupos = new Categoria().obterTodos(CategoriaModel.class);
	comboBox.addItem("--Escolha um Item--");
	for (CategoriaModel grupoModel : listaDeGrupos) {
	    comboBox.addItem(new ComboItem(grupoModel.getDescricao(), grupoModel.getId().toString()));

	}

    }

    @Override
    public SubGrupoModel getModel() {
	model.setDescricao(textFieldDescricao.getText());
	model.setGrupo(getGrupoSelecionado());

	return model;
    }

    private CategoriaModel getGrupoSelecionado() {
	if (comboBox.getSelectedIndex() == 0) {
	    return null;
	} else {
	    Object item = comboBox.getSelectedItem();
	    String value = ((ComboItem) item).getValue();

	    CategoriaModel model = null;

	    for (CategoriaModel grupoModel : listaDeGrupos) {
		if (grupoModel.getId().toString().trim().equals(value)) {
		    model = grupoModel;
		    break;
		}
	    }
	    return model;
	}

    }

    @Override
    public void setModel(SubGrupoModel model) {

	if (model == null) {
	    model = new SubGrupoModel();
	}

	super.setModel(model);

	textFieldDescricao.setText(model.getDescricao());
	int posicao = getPosicaoNaLista(model.getGrupo());
	if (posicao == -1) {
	    comboBox.setSelectedIndex(0);
	} else {
	    comboBox.setSelectedIndex(posicao);
	}

    }

    private int getPosicaoNaLista(CategoriaModel model) {
	int posicao = -1;
	int count = 1;
	if (model != null) {
	    for (CategoriaModel grupoModel : listaDeGrupos) {
		if (grupoModel.getId().toString().equals(model.getId().toString())) {

		    posicao = count;
		    break;
		}
		count++;
	    }
	}
	return posicao;
    }

    public ArrayList<CategoriaModel> getListaDeGrupos() {
	return listaDeGrupos;
    }

    public void setListaDeGrupos(ArrayList<CategoriaModel> listaDeGrupos) {
	this.listaDeGrupos = listaDeGrupos;
    }

}
