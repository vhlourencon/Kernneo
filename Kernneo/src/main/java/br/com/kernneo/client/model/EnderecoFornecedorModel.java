package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "endereco_fornecedor")
public class EnderecoFornecedorModel extends EnderecoModel {

    @ManyToOne
    @JoinColumn(name = "id_fornecedor")
    private FornecedorModel fornecedor;

    public FornecedorModel getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(FornecedorModel fornecedor) {
        this.fornecedor = fornecedor;
    }

    @Override
    public Record toRecord() {
	Record record = super.toRecord();
	record.setAttribute("id", getId());
	
	if (fornecedor == null) {
	    record.setAttribute("fornecedor", "Não tem.");

	} else {
	    record.setAttribute("fornecedor", fornecedor.getId() );
	}
	return record;
    }

}
