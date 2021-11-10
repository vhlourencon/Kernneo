package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "funcionario_rh")
public class FuncionarioRHModel extends GenericModel {

    @OneToOne
    @JoinColumn(name = "id_funcionario")
    private FuncionarioModel funcionario;

    @ManyToOne
    private CargoModel cargo;

    public FuncionarioModel getFuncionario() {
	return funcionario;
    }

    public void setFuncionario(FuncionarioModel funcionario) {
	this.funcionario = funcionario;
    }

    public CargoModel getCargo() {
	return cargo;
    }

    public void setCargo(CargoModel cargo) {
	this.cargo = cargo;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
