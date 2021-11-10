package br.com.kernneo.desktop.view.observacao;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ObservacaoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Observacao;

public class ObservacaoListInternalFrame extends GenericListInternalFrame<Observacao, ObservacaoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ObservacaoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new ObservacaoFormCadPanel());
	setNegocio(new Observacao());
	setModel(new ObservacaoModel());
	setPagina(new GenericPagina<ObservacaoModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição" });
	setGenericFiltroPanel(new ObservacaoFiltroPanel(), new ObservacaoModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(ObservacaoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
