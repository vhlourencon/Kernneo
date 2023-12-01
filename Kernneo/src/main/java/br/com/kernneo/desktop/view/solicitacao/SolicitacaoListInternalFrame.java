package br.com.kernneo.desktop.view.solicitacao;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.SolicitacaoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Solicitacao;

public class SolicitacaoListInternalFrame extends GenericListInternalFrame<Solicitacao, SolicitacaoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SolicitacaoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new SolicitacaoFormCadPanel());
	setNegocio(new Solicitacao());
	setModel(new SolicitacaoModel());
	setPagina(new GenericPagina<SolicitacaoModel>());
	setColunasDaTabela(new String[] { "cod" , "Nome" });
	setGenericFiltroPanel(new SolicitacaoFiltroPanel(), new SolicitacaoModel(),  false);
	setTitle("Cadastro de Compilado");
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	setModoLista();
	
	

	

    }

    @Override
    public String[] modelToRow(SolicitacaoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	

	String[] row = new String[] { id, descricao };
	return row;

    }

}
