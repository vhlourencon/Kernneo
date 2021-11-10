package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "cfop")
public class CFOPModel extends GenericModel {

    private String aplicacao;
    private String descricao;
    private String cfop; 

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("aplicacao", getAplicacao());
	record.setAttribute("descricao", getDescricao());

	return record;
    }

    public String getAplicacao() {
	return aplicacao;
    }

    public void setAplicacao(String aplicacao) {
	this.aplicacao = aplicacao;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String getCfop() {
        return cfop;
    }

    public void setCfop(String cfop) {
        this.cfop = cfop;
    }

   

}
