package br.com.kernneo.desktop.view.grupo;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.CategoriaModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Categoria;

public class GrupoListInternalFrame extends GenericListInternalFrame<Categoria, CategoriaModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public GrupoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new GrupoFormCadPanel());
	setNegocio(new Categoria());
	setModel(new CategoriaModel());
	setPagina(new GenericPagina<CategoriaModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição" });
	setGenericFiltroPanel(new GrupoFiltroPanel(), new CategoriaModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(CategoriaModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
