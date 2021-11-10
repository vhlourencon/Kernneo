package br.com.kernneo.client.model.seguranca;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "perfil")
public class PerfilModel extends GenericModel {

    private String descricao;
    private boolean status;
    
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }
}
