package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "convenio")
public class ConvenioModel extends GenericModel {

    private String nome;
   
    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	if (nome == null) {
	    record.setAttribute("nome", "Não tem.");

	} else {
	    record.setAttribute("nome", getNome() );
	}
	
	return record;
    }

}
