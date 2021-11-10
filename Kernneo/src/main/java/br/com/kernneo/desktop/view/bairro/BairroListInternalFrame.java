package br.com.kernneo.desktop.view.bairro;

import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Bairro;

public class BairroListInternalFrame extends GenericListInternalFrame<Bairro, BairroModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    DecimalFormat dFormat = new DecimalFormat("#,##0.00");

    public BairroListInternalFrame() {

	
	setTitle("Cadastro de Bairros");
	setPaginada(false);
	setFormCadPanel(new BairroFormCadPanel());
	setNegocio(new Bairro());
	setModel(new BairroModel());
	setPagina(new GenericPagina<BairroModel>());
	setColunasDaTabela(new String[] { "cod", "Nome", "Taxa de Entrega" });
	setGenericFiltroPanel(new BairroFiltroPanel(), new BairroModel(),  true);
	

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

	table.getColumnModel().getColumn(2).setMinWidth(160);
	table.getColumnModel().getColumn(2).setMaxWidth(160);

//	acaoObterTodos();

    }

    @Override
    public String[] modelToRow(BairroModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	String numero = "";

	String[] row = new String[] { id, descricao, numero };
	return row;

    }

}
