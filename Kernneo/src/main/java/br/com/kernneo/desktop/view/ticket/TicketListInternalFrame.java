package br.com.kernneo.desktop.view.ticket;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.TicketModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Ticket;

public class TicketListInternalFrame extends GenericListInternalFrame<Ticket, TicketModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TicketListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new TicketFormCadPanel());
	setNegocio(new Ticket());
	setModel(new TicketModel());
	setPagina(new GenericPagina<TicketModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição" });
	setGenericFiltroPanel(new TicketFiltroPanel(), new TicketModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(TicketModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
