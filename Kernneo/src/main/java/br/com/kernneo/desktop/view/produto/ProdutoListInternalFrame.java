package br.com.kernneo.desktop.view.produto;

import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.exception.CategoriaException;
import br.com.kernneo.client.exception.UnidadeException;
import br.com.kernneo.client.model.ProdutoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.DecimalFormattedField;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Produto;

public class ProdutoListInternalFrame extends GenericListInternalFrame<Produto, ProdutoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    DecimalFormat decimalFormat = new DecimalFormat(DecimalFormattedField.REAL);

    public ProdutoListInternalFrame() throws CategoriaException, UnidadeException {

	
	
	
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	setPaginada(true);
	setFormCadPanel(new ProdutoFormCadPanel());
	setNegocio(new Produto());
	setModel(new ProdutoModel());
	setPagina(new GenericPagina<ProdutoModel>());
	setColunasDaTabela(new String[] { "id", "Código" , "Descrição", "Grupo" , "Sub Grupo" , "Preço de Custo" , " Preço de Venda"});
	setGenericFiltroPanel(new ProdutoFiltroPanel(), new ProdutoModel(),  true);
	
	table.getColumnModel().getColumn(1).setMinWidth(40);
	table.getColumnModel().getColumn(2).setMinWidth(300);
	table.getColumnModel().getColumn(3).setMinWidth(90);
	table.getColumnModel().getColumn(4).setMinWidth(90);
	table.getColumnModel().getColumn(5).setMinWidth(100);
	table.getColumnModel().getColumn(6).setMinWidth(100);
	
	setSize(770, 600);
	

    }

   

    @Override
    public String[] modelToRow(ProdutoModel model) {
	String id = String.valueOf(model.getId());
	String codigo = String.valueOf(model.getCodigo());
	String descricao = model.getDescricao();
	String grupo = "-- SEM GRUPO -- ";
	//if (model.getSubGrupo() != null) {
	  //  grupo = model.getSubGrupo().getGrupo().getDescricao();
	//}
	String subGrupo = " -- SEM SUBGRUPO --"; 
	//if ( model.getSubGrupo() != null)  { 
	 //   subGrupo = model.getSubGrupo().getDescricao(); 
	//}
	

	
	String precoDeCusto = decimalFormat.format(model.getPrecoDeVenda()); 
	String precoDeVenda =  decimalFormat.format(model.getPrecoDeCusto()); 

	String[] row = new String[] { id, codigo ,  descricao, grupo , subGrupo, precoDeCusto, precoDeVenda };
	return row;

    }
}
