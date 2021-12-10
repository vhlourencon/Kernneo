package br.com.kernneo.desktop.view.funcionario;

import java.text.DecimalFormat;

import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Funcionario;

public class FuncionarioListInternalFrame extends GenericListInternalFrame<Funcionario, FuncionarioModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    DecimalFormat decimalFormat = new DecimalFormat(DecimalFormattedField.REAL);

    public FuncionarioListInternalFrame() {

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	setPaginada(true);
	setFormCadPanel(new FuncionarioFormCadPanel());
	setNegocio(new Funcionario());
	setModel(new FuncionarioModel());
	setPagina(new GenericPagina<FuncionarioModel>());
	setColunasDaTabela(new String[] { "id", "Login", "Nome"});
	setGenericFiltroPanel(new FuncionarioFiltroPanel(), new FuncionarioModel(), true);

	table.getColumnModel().getColumn(1).setMinWidth(40);
	table.getColumnModel().getColumn(2).setMinWidth(300);
	
	
	setSize(689, 700);

    }

    @Override
    public String[] modelToRow(FuncionarioModel model) {
	String id = String.valueOf(model.getId());
	String codigo = model.getLogin();
	String nome = model.getNome();
	

	String[] row = new String[] { id, codigo, nome };
	return row;

    }
}
