package br.com.kernneo.client.model.seguranca;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "modulo")
public class ModuloModel extends GenericModel {

    private String descricao;
    private String tipo;
    
    
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
