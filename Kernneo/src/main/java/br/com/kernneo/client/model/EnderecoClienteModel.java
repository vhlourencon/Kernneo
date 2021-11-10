package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "endereco_cliente")
public class EnderecoClienteModel extends EnderecoModel {

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    public ClienteModel getCliente() {
	return cliente;
    }

    public void setCliente(ClienteModel cliente) {
	this.cliente = cliente;
    }

    @Override
    public Record toRecord() {
	Record record = super.toRecord();
	
	record.setAttribute("id", getId());

	if (cliente == null) {
	    record.setAttribute("cliente", "Não tem.");

	} else {
	    record.setAttribute("cliente", cliente.getId());
	}
	return record;
    }

    @Override
    public String toString() {
	String endereco = "";
	if (getLogradouro() != null) {
	    endereco += getLogradouro() + ", ";
	}

	if (getNumero() != null) {
	    endereco += getNumero() + ", ";
	}

	if (getComplemento() != null) {
	    endereco += getComplemento() + ", ";
	}

	if (getBairro() != null) {
	    endereco += getBairro().getNome() + ", ";
	    endereco += getBairro().getCidade().getNome() + " - ";
	    endereco += getBairro().getCidade().getEstado().getSigla() + ", ";
	}

//	if (getCep() != null) {
//	    endereco += getCep() + ", ";
//	   
//	    
//	}
	
	int tamanho = 0; 
	if (endereco.length() >=2 ) { 
	    tamanho = 2; 
	}
	    
	endereco = endereco.substring(0, endereco.length()- tamanho);

	return endereco;
    }

}
