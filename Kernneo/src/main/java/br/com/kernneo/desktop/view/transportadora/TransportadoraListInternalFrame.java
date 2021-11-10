package br.com.kernneo.desktop.view.transportadora;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.TransportadoraModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Transportadora;

public class TransportadoraListInternalFrame extends GenericListInternalFrame<Transportadora, TransportadoraModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TransportadoraListInternalFrame() {

	setFormCadPanel(new TransportadoraFormCadPanel());
	setNegocio(new Transportadora());
	setModel(new TransportadoraModel());
	setPagina(new GenericPagina<TransportadoraModel>());
	setColunasDaTabela(new String[] { "cod", "Nome", "CNPJ", "I.E", "Enrereço", "Cidade - UF", "Telefone", "Celular", "Email", "Site" });
	setGenericFiltroPanel(new TransportadoraFiltroPanel(), new TransportadoraModel(),  true);

	
	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.getColumnModel().getColumn(1).setMinWidth(220);
	table.getColumnModel().getColumn(2).setMinWidth(120);
	table.getColumnModel().getColumn(3).setMinWidth(120);
	table.getColumnModel().getColumn(4).setMinWidth(250);
	table.getColumnModel().getColumn(5).setMinWidth(200);
	table.getColumnModel().getColumn(6).setMinWidth(100);
	table.getColumnModel().getColumn(7).setMinWidth(100);
	table.getColumnModel().getColumn(8).setMinWidth(200);
	table.getColumnModel().getColumn(9).setMinWidth(300);

    }

    @Override
    public String[] modelToRow(TransportadoraModel model) {
	String id = String.valueOf(model.getId());
	String nome = model.getNomeFantasia();
	String cnpj = model.getCnpj();
	String ie = model.getIe();
	String endereco = model.getLogradouro();
	String cidade = "";
	if (model.getCidade() != null) {
	    cidade = model.getCidade().getNome() + " - " + model.getCidade().getEstado().getSigla();

	}
	String telefone = model.getTelefone();
	String celular = model.getCelular();
	String email = model.getEmail(); 
	String site = model.getSite()	;

	String[] row = new String[] { id, nome, cnpj, ie, endereco, cidade, telefone, celular , email , site};
	return row;

    }

}
