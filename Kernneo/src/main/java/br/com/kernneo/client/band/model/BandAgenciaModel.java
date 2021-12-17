package br.com.kernneo.client.band.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "band_agencia")
public class BandAgenciaModel extends GenericModel {

	private String nome;
	private String endereco;
	private String teste;
	private String cep;
	private String bairro;
	private String cidade;
	private String uf;
	
	

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("endereco", getEndereco());
		record.setAttribute("cep", getCep());
		record.setAttribute("bairro", getBairro());
		record.setAttribute("cidade", getCidade());
		record.setAttribute("uf", getUf());

		return record;
	}

}
