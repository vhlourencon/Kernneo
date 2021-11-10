package br.com.kernneo.desktop.view.cfop;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.CFOPModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.CFOP;

public class CFOPListInternalFrame extends GenericListInternalFrame<CFOP, CFOPModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CFOPListInternalFrame() {

	setLocation(250, 50);
	setSize(564, 526);
	setVisible(true);
	setResizable(true);
	setClosable(true);

	setMaximizable(true);
	setPaginada(false);

	setFormCadPanel(new CFOPFormCadPanel());
	setNegocio(new CFOP());
	setModel(new CFOPModel());
	setPagina(new GenericPagina<CFOPModel>());
	setColunasDaTabela(new String[] { "cod", "CFOP", "Descrição" });
	setGenericFiltroPanel(new CFOPFiltroPanel(), new CFOPModel(), true);

	table.getColumnModel().getColumn(0).setResizable(true);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

	table.getColumnModel().getColumn(1).setMinWidth(100);
	table.getColumnModel().getColumn(1).setMaxWidth(100);

	acaoObterTodos();

    }

    @Override
    public String[] modelToRow(CFOPModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();
	String numero = "";

	String[] row = new String[] { id, numero, descricao };
	return row;

    }

}
