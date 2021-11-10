package br.com.kernneo.desktop.view.departamento;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Departamento;

public class DepartamentoListInternalFrame extends GenericListInternalFrame<Departamento, DepartamentoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DepartamentoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new DepartamentoFormCadPanel());
	setNegocio(new Departamento());
	setModel(new DepartamentoModel());
	setPagina(new GenericPagina<DepartamentoModel>());
	setColunasDaTabela(new String[] { "cod" , "Nome" });
	setGenericFiltroPanel(new DepartamentoFiltroPanel(), new DepartamentoModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	table.getColumnModel().getColumn(0).setMaxWidth(40);
	
	
	
	//acaoIrPrimeiraPagina();

    }

    @Override
    public String[] modelToRow(DepartamentoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
