package br.com.kernneo.desktop.view.subgrupo;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.model.SubGrupoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.ComboItem;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.SubGrupo;

public class SubGrupoListInternalFrame extends GenericListInternalFrame<SubGrupo, SubGrupoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JPanel panelFiltro;

    private SubGrupoFormCadPanel subGrupoFormCadPanel;

    private JComboBox comboBox;

    public SubGrupoListInternalFrame() throws CategoriaException {

	setMaximizable(true);

	subGrupoFormCadPanel = new SubGrupoFormCadPanel();
	setFormCadPanel(subGrupoFormCadPanel);
	setNegocio(new SubGrupo());
	//setFiltroModel(new SubGrupoModel());
	
	setModel(new SubGrupoModel());
	setPagina(new GenericPagina<SubGrupoModel>());
	setColunasDaTabela(new String[] { "cod", "Descrição", "Grupo" });
	setGenericFiltroPanel(new SubGrupoFiltroPanel(), new SubGrupoModel(), true);

	
	

	
	

    }

    public CategoriaModel getGrupoSelecionado() {
	if (comboBox.getSelectedIndex() == 0) {
	    return null;
	} else {
	    CategoriaModel model = null;
	    Object item = comboBox.getSelectedItem();
	    String value = ((ComboItem) item).getValue();

	    for (CategoriaModel grupoModel : subGrupoFormCadPanel.getListaDeGrupos()) {
		if (grupoModel.getId().toString().trim().equals(value)) {
		    model = grupoModel;
		    break;
		}
	    }
	    return model;
	}
    }

   

    @Override
    public String[] modelToRow(SubGrupoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	String grupo = "-- SEM GRUPO -- ";
	if (model.getGrupo() != null) {
	    grupo = model.getGrupo().getDescricao();
	}

	String[] row = new String[] { id, descricao, grupo };
	return row;

    }
}
