package br.com.kernneo.desktop.view.cargo;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.CargoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Cargo;

public class CargoListInternalFrame extends GenericListInternalFrame<Cargo, CargoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public CargoListInternalFrame() {

	setFormCadPanel(new CargoFormCadPanel());
	setNegocio(new Cargo());
	setModel(new CargoModel());
	setPagina(new GenericPagina<CargoModel>());
	setColunasDaTabela(new String[] { "cod", "Descrição" });
	setGenericFiltroPanel(new CargoFiltroPanel(), new CargoModel(), true);

	table.getColumnModel().getColumn(0).setResizable(true);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

    }

    @Override
    public String[] modelToRow(CargoModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getDescricao();

	String[] row = new String[] { id, descricao };
	return row;

    }

}
