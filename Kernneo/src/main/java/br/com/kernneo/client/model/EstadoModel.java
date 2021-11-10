package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "estados")
public class EstadoModel extends GenericModel {

    private String nome;
    private Integer codigoIbge;
    private String sigla;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getSigla() {
	return sigla;
    }

    public void setSigla(String sigla) {
	this.sigla = sigla;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("nome", getNome());
	record.setAttribute("sigla", getSigla());
	record.setAttribute("codigoIgbe", getCodigoIbge());

	return record;
    }

    public Integer getCodigoIbge() {
	return codigoIbge;
    }

    public void setCodigoIbge(Integer codigoIbge) {
	this.codigoIbge = codigoIbge;
    }

}
