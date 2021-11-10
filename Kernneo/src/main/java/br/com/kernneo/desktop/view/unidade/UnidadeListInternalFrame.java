package br.com.kernneo.desktop.view.unidade;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.UnidadeModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Unidade;

public class UnidadeListInternalFrame extends GenericListInternalFrame<Unidade, UnidadeModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public UnidadeListInternalFrame() {
    	
	setLocation(250, 50);
	setSize(564, 526);
	setVisible(true);
	setResizable(true);
	setClosable(true);

	setMaximizable(true);
	
	
	setFormCadPanel(new UnidadeFormCadPanel());
	setNegocio(new Unidade());
	setModel(new UnidadeModel());
	setPagina(new GenericPagina<UnidadeModel>());
	setColunasDaTabela(new String[] { "cod", "Descrição" });
	
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	
	//acaoIrPrimeiraPagina();

    }

    @Override
    public String[] modelToRow(UnidadeModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
