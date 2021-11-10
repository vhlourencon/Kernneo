package br.com.kernneo.desktop.view.planocontas;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.PlanoContasModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.PlanoContas;

public class PlanoContasListInternalFrame extends GenericListInternalFrame<PlanoContas, PlanoContasModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PlanoContasListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new PlanoContasFormCadPanel());
	setNegocio(new PlanoContas());
	setModel(new PlanoContasModel());
	setPagina(new GenericPagina<PlanoContasModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição" });
	setGenericFiltroPanel(new PlanoContasFiltroPanel(), new PlanoContasModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(PlanoContasModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
