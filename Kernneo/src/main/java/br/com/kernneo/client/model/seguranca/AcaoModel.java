package br.com.kernneo.client.model.seguranca;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "acao")
public class AcaoModel extends GenericModel {

    private String descricao;
    //private ModuloModel modulo;
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }
    
//    public ModuloModel getModulo() {
//        return modulo;
//    }
//    
//    public void setModulo(ModuloModel modulo) {
//        this.modulo = modulo;
//    }
}
