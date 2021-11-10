package br.com.kernneo.desktop.view.fornecedor;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import br.com.kernneo.client.model.FornecedorModel;
import br.com.kernneo.client.utils.GenericPagina;
import br.com.kernneo.desktop.view.widget.GenericListInternalFrame;
import br.com.kernneo.server.negocio.Fornecedor;

public class FornecedorListInternalFrame extends GenericListInternalFrame<Fornecedor, FornecedorModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public FornecedorListInternalFrame() {

	setSize(780, 540);

	getjScrollPaneTable().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	setFormCadPanel(new FornecedorFormCadPanel());
	setNegocio(new Fornecedor());
	setModel(new FornecedorModel());
	setPagina(new GenericPagina<FornecedorModel>());
	setPaginada(true);
	setColunasDaTabela(new String[] { "Código", "Abreviatura", "Nome Fantasia", "Razao Social", "Tipo", "Endereço", "Numero",  "Cidade", "CEP", "Fax", "Fone1", "Fone2", "CNPJ", "IE", "CPF", "RG", "Site", "Email" });

	
	

	table.getColumnModel().getColumn(1).setMinWidth(220);
	table.getColumnModel().getColumn(2).setMinWidth(220);
	table.getColumnModel().getColumn(3).setMinWidth(150);
	table.getColumnModel().getColumn(4).setMinWidth(80);
	table.getColumnModel().getColumn(5).setMinWidth(240);
	table.getColumnModel().getColumn(6).setMinWidth(120);
	table.getColumnModel().getColumn(7).setMinWidth(80);
	table.getColumnModel().getColumn(8).setMinWidth(130);
	table.getColumnModel().getColumn(9).setMinWidth(80);
	table.getColumnModel().getColumn(10).setMinWidth(80);
	table.getColumnModel().getColumn(11).setMinWidth(80);
	table.getColumnModel().getColumn(12).setMinWidth(80);
	table.getColumnModel().getColumn(13).setMinWidth(80);
	table.getColumnModel().getColumn(14).setMinWidth(80);
	table.getColumnModel().getColumn(15).setMinWidth(150);
	table.getColumnModel().getColumn(16).setMinWidth(200);
	table.getColumnModel().getColumn(17).setMinWidth(200);

    }

    @Override
    public String[] modelToRow(FornecedorModel model) {

	String id = String.valueOf(model.getId());
//	String abreviatura = model.getAbreviatura();
	String nomeFantasia = model.getFantasia();
	String razao = model.getRazaoSocial();
	String tipo = "";
	
//
//	String numero = model.getNumero();
//	String cidade = "";
//	if (model.getCidade() != null) {
//	    cidade = model.getCidade().getNome() + " - " + model.getCidade().getEstado().getSigla();
//	}
//
//	String cep = model.getCep();
//	String fax = model.getFax();
//	String fone1 = model.getFone1();
//	String fone2 = model.getFone2();
//	String cnpj = model.getCnpj();
//	String ie = model.getIe();
//	String cpf = model.getCpf();
//	String rg = model.getRg();
//	String site = model.getSite();
//	String email = model.getEmail();
//	
//	

	//String[] row = new String[] { id, abreviatura, nomeFantasia, razao, tipo, endereco, numero, cidade, cep, fax, fone1, fone2, cnpj, ie, cpf, rg, site, email };
	String[] row = new String[]{};
	return row;

    }

}
