package br.com.kernneo.desktop.view.entregaequipamento;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.sun.xml.internal.rngom.digested.DInterleavePattern;

import br.com.kernneo.client.model.DepartamentoModel;
import br.com.kernneo.client.model.EntregaEquipamentoModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Departamento;
import br.com.kernneo.server.negocio.EntregaEquipamento;

public class EntregaEquipamentoListInternalFrame extends GenericListInternalFrame<EntregaEquipamento, EntregaEquipamentoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public EntregaEquipamentoListInternalFrame() {
    	
	
	
	
	setFormCadPanel(new EntregaEquipamentoFormCadPanel());
	setNegocio(new EntregaEquipamento());
	setModel(new EntregaEquipamentoModel());
	setPagina(new GenericPagina<EntregaEquipamentoModel>());
	setColunasDaTabela(new String[] { "cod" , "Nome" });
	//setGenericFiltroPanel(new DepartamentoFiltroPanel(), new DepartamentoModel(),  true);
	
	table.getColumnModel().getColumn(0).setResizable(true);
	
    
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	
	
	table.getColumnModel().getColumn(0).setMaxWidth(40);
	setPreferredSize(new Dimension(800, 600));
	
	
	//acaoIrPrimeiraPagina();

    }

    @Override
    public String[] modelToRow(EntregaEquipamentoModel model) {
	String id = String.valueOf(model.getId());
	//String descricao = model.getNome();
	

	String[] row = new String[] { id, "" };
	return row;

    }

}
