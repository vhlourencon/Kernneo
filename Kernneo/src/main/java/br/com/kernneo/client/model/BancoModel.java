package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "banco")
public class BancoModel extends GenericModel {

    private String codigoBanco;
    private String nome;
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	
	if (nome == null) {
	    record.setAttribute("nome", "Não tem.");
	} else {
	    record.setAttribute("nome", getNome());
	}
	if (codigoBanco == null) {
	    record.setAttribute("codigoBanco", "Não tem.");
	} else {
	    record.setAttribute("codigoBanco", getCodigoBanco());
	}

	return record;
    }

}
