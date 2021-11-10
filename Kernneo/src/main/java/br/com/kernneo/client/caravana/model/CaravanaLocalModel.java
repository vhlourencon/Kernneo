package br.com.kernneo.client.caravana.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_local")
public class CaravanaLocalModel extends GenericModel {

	private String nome;

	/*
	 * Endereço
	 */
	private String enderecoBairro;
	private String enderecoCep;
	private String enderecoLogradouro;
	private String enderecoNumero;

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private CidadeModel enderecoCidade;

	/*
	 * Contato
	 */
	private String contatoNome;
	private String contatoFone;
	private String contatoEmail;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getContatoNome() {
		return contatoNome;
	}

	public void setContatoNome(String contatoNome) {
		this.contatoNome = contatoNome;
	}

	public String getContatoFone() {
		return contatoFone;
	}

	public void setContatoFone(String contatoFone) {
		this.contatoFone = contatoFone;
	}

	public String getContatoEmail() {
		return contatoEmail;
	}

	public void setContatoEmail(String contatoEmail) {
		this.contatoEmail = contatoEmail;
	}

	public String getEnderecoBairro() {
		return enderecoBairro;
	}

	public void setEnderecoBairro(String enderecoBairro) {
		this.enderecoBairro = enderecoBairro;
	}

	public String getEnderecoCep() {
		return enderecoCep;
	}

	public void setEnderecoCep(String enderecoCep) {
		this.enderecoCep = enderecoCep;
	}

	public String getEnderecoLogradouro() {
		return enderecoLogradouro;
	}

	public void setEnderecoLogradouro(String enderecoLogradouro) {
		this.enderecoLogradouro = enderecoLogradouro;
	}

	public String getEnderecoNumero() {
		return enderecoNumero;
	}

	public void setEnderecoNumero(String enderecoNumero) {
		this.enderecoNumero = enderecoNumero;
	}

	public CidadeModel getEnderecoCidade() {
		return enderecoCidade;
	}

	public void setEnderecoCidade(CidadeModel enderecoCidade) {
		this.enderecoCidade = enderecoCidade;
	}

	public String getEnderecoCompleto() {
		String aux = "";

		aux += getEnderecoLogradouro() + ", ";
		aux += getEnderecoNumero() + ", ";
		aux += getEnderecoBairro() + ", ";

		if (getEnderecoCidade() != null) {
			aux += getEnderecoCidade().getNome();
			if (getEnderecoCidade().getEstado() != null) {
				aux += " - " + getEnderecoCidade().getEstado().getSigla();
			}
		}

		return aux;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());

		record.setAttribute("contatoNome", getContatoNome());
		record.setAttribute("contatoContatoEmail", getContatoEmail());
		record.setAttribute("contatoFone", getContatoFone());
		record.setAttribute("enderecoLogradouro", getEnderecoLogradouro());
		record.setAttribute("enderecoNumero", getEnderecoNumero());
		record.setAttribute("enderecoBairro", getEnderecoBairro());
		record.setAttribute("enderecoCep", getEnderecoCep());

		if (getEnderecoCidade() != null) {
			record.setAttribute("enderecoCidadeNome", getEnderecoCidade().getNome());

			if (getEnderecoCidade().getEstado() != null) {
				record.setAttribute("enderecoCidadeEstoSigla", getEnderecoCidade().getEstado().getSigla());
			}

		}

		record.setAttribute("enderecoCompleto", getEnderecoCompleto());

		return record;
	}

}
