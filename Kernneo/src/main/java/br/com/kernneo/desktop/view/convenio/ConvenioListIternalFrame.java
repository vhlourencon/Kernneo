package br.com.kernneo.desktop.view.convenio;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ConvenioModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Convenio;

public class ConvenioListIternalFrame extends GenericListInternalFrame<Convenio, ConvenioModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ConvenioListIternalFrame() {
    	
	
	
	
	setFormCadPanel(new ConvenioFormCadPanel());
	setNegocio(new Convenio());
	setModel(new ConvenioModel());
	setPagina(new GenericPagina<ConvenioModel>());
	setColunasDaTabela(new String[] { "cod" , "Nome" });
	setGenericFiltroPanel(new ConvenioFiltroPanel(),   new ConvenioModel() , true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(ConvenioModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
