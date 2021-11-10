package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "subgrupo")
public class SubGrupoModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private CategoriaModel grupo;

    private String descricao;

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public CategoriaModel getGrupo() {
	return grupo;
    }

    public void setGrupo(CategoriaModel grupo) {
	this.grupo = grupo;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
