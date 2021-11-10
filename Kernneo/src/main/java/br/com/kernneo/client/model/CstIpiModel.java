package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "cst_ipi")
public class CstIpiModel extends GenericModel {

    private String codigo;
    private String descricao;
    
    
    
    
    public String getCodigo() {
        return codigo;
    }




    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }




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
	record.setAttribute("codigo", codigo);
	record.setAttribute("descricao" , descricao);
	return record;
    }
    
    
    
   
}
