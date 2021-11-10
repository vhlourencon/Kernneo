package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "observacao")
public class ObservacaoModel extends GenericModel {

   

	private String descricao;

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	
	if (descricao == null) {
	    record.setAttribute("descricao", "Não tem.");

	} else {
	    record.setAttribute("descricao", getDescricao());
	}

	return record;
    }

}
