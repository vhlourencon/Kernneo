package br.com.kernneo.client.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "cliente")
public class ClienteModel extends GenericModel {

    @Transient
    private ArrayList<EnderecoClienteModel> listaEndereco;

    @Transient
    private ArrayList<MesaRecebimentoModel> listaDeContasApagar;

    private String nome;
    private String cpf;
    private String rg;
    private String celular;
    private String telefone;
    private String email;

    public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public String getCpf() {
	return cpf;
    }

    public void setCpf(String cpf) {
	this.cpf = cpf;
    }

    public String getRg() {
	return rg;
    }

    public void setRg(String rg) {
	this.rg = rg;
    }

    public String getCelular() {
	return celular;
    }

    public void setCelular(String celular) {
	this.celular = celular;
    }

    public String getTelefone() {
	return telefone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public void setTelefone(String telefone) {
	this.telefone = telefone;
    }

    public ArrayList<EnderecoClienteModel> getListaEndereco() {
	if (listaEndereco == null) {
	    listaEndereco = new ArrayList<EnderecoClienteModel>();
	}
	return listaEndereco;
    }

    public void setListaEndereco(ArrayList<EnderecoClienteModel> listaEndereco) {
	this.listaEndereco = listaEndereco;
    }

    public ArrayList<MesaRecebimentoModel> getListaDeContasApagar() {
	if (listaDeContasApagar == null) {
	    listaDeContasApagar = new ArrayList<MesaRecebimentoModel>();
	}
	return listaDeContasApagar;
    }

    public void setListaDeContasApagar(ArrayList<MesaRecebimentoModel> listaDeContasApagar) {
	this.listaDeContasApagar = listaDeContasApagar;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("nome", getNome());
	record.setAttribute("cpf", getCpf());
	record.setAttribute("rg", getRg());
	record.setAttribute("email", email);
	record.setAttribute("celular", celular);
	record.setAttribute("telefone", telefone);

	return record;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return getNome();
    }

}
