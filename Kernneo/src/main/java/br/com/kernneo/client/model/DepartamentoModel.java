package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "departamento")
public class DepartamentoModel extends GenericModel {

    private String nome;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }
    

    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return getNome() != null ? getNome().toUpperCase() : "";
	}

	@Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
