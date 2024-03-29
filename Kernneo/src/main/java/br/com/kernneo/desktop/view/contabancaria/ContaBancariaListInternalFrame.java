package br.com.kernneo.desktop.view.contabancaria;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.ContaBancaria;

public class ContaBancariaListInternalFrame extends GenericListInternalFrame<ContaBancaria, ContaBancariaModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ContaBancariaListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new ContaBancariaFormCadPanel());
	setNegocio(new ContaBancaria());
	setModel(new ContaBancariaModel());
	setPagina(new GenericPagina<ContaBancariaModel>());
	setColunasDaTabela(new String[] { "cod" , "Descrição", "Saldo Inicial" , "Cheque Especial"});
	setGenericFiltroPanel(new ContaBancariaFiltroPanel(), new ContaBancariaModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
	setTitle("Cadastro de Contas Bancárias");
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	

	

    }

    @Override
    public String[] modelToRow(ContaBancariaModel model) {
	String id = String.valueOf(model.getId());
	String descricao = model.getNome();
	String saldoInicial = model.currencyFormat(model.getSaldoInicial());
	String chequeEspecial = model.currencyFormat(model.getChequeEspecial());
	
	String[] row = new String[] { id, descricao, saldoInicial, chequeEspecial };
	return row;

    }

}
