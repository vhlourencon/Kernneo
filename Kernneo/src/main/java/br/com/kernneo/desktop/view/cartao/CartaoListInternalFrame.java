package br.com.kernneo.desktop.view.cartao;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.CartaoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cartao;

public class CartaoListInternalFrame extends GenericListInternalFrame<Cartao, CartaoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CartaoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new CartaoFormCadPanel());
	setNegocio(new Cartao());
	setModel(new CartaoModel());
	setPagina(new GenericPagina<CartaoModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição" });
	setGenericFiltroPanel(new CartaoFiltroPanel(), new CartaoModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	
	
	

    }

    @Override
    public String[] modelToRow(CartaoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
