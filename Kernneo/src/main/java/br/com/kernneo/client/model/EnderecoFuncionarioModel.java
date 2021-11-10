package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "endereco_funcionario")
public class EnderecoFuncionarioModel extends EnderecoModel {

    @ManyToOne
    @JoinColumn(name = "id_funcionario")
    private FuncionarioModel funcionario;
    
    public FuncionarioModel getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioModel funcionario) {
        this.funcionario = funcionario;
    }
    
    @Override
    public Record toRecord() {
	Record record = super.toRecord();
	record.setAttribute("id", getId());
	
	if (funcionario == null) {
	    record.setAttribute("funcionario", "Não tem.");

	} else {
	    record.setAttribute("funcionario", funcionario.getId() );
	}
	return record;
    }

}
