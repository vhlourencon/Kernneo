package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "fornecedor")
public class FornecedorModel extends GenericModel {

    private String razaoSocial;
    private String fantasia;
    private String cnpj;
    private String ie;
    private String logradouro;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_bairro")
    private BairroModel bairro;
    private String cep;

    private String contatoNome;
    private String contatoTelefone;
    private String contatoEmail;
    private String contatoCelular;

    public String getRazaoSocial() {
	return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
	this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
	return fantasia;
    }

    public void setFantasia(String fantasia) {
	this.fantasia = fantasia;
    }

    public String getCnpj() {
	return cnpj;
    }

    public void setCnpj(String cnpj) {
	this.cnpj = cnpj;
    }

    public String getIe() {
	return ie;
    }

    public void setIe(String ie) {
	this.ie = ie;
    }

    public String getLogradouro() {
	return logradouro;
    }

    public void setLogradouro(String logradouro) {
	this.logradouro = logradouro;
    }

    public String getNumero() {
	return numero;
    }

    public void setNumero(String numero) {
	this.numero = numero;
    }

    public BairroModel getBairro() {
	return bairro;
    }

    public void setBairro(BairroModel bairro) {
	this.bairro = bairro;
    }

    public String getCep() {
	return cep;
    }

    public void setCep(String cep) {
	this.cep = cep;
    }

    public String getContatoNome() {
	return contatoNome;
    }

    public void setContatoNome(String contatoNome) {
	this.contatoNome = contatoNome;
    }

    public String getContatoTelefone() {
	return contatoTelefone;
    }

    public void setContatoTelefone(String contatoTelefone) {
	this.contatoTelefone = contatoTelefone;
    }

    public String getContatoEmail() {
	return contatoEmail;
    }

    public void setContatoEmail(String contatoEmail) {
	this.contatoEmail = contatoEmail;
    }

    public String getContatoCelular() {
	return contatoCelular;
    }

    public void setContatoCelular(String contatoCelular) {
	this.contatoCelular = contatoCelular;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("contatoEmail", getContatoEmail());
	record.setAttribute("contatoCelular", getContatoCelular());
	record.setAttribute("contatoNome", getContatoNome());
	record.setAttribute("contatoTelefone", getContatoTelefone());

	record.setAttribute("razaoSocial", getRazaoSocial());
	record.setAttribute("fantasia", getFantasia());
	record.setAttribute("cnpj", getCnpj());
	record.setAttribute("ie", getIe());
	record.setAttribute("logradouro", getLogradouro());
	record.setAttribute("numero", getNumero());
	record.setAttribute("cep", getCep());

	String rua = getLogradouro();
	if (rua == null) {
	    rua = "";
	}

	String numero = getNumero();
	if (numero == null) {
	    numero = "";
	}

	String cep = getCep();
	if (cep == null) {
	    cep = "";
	}

	String bairro = "";
	String cidade = "";
	String uf = "";

	if (getBairro() != null) {
	    record.setAttribute("bairroId", getBairro().getId());
	    record.setAttribute("bairroNome", getBairro().getNome());
	    record.setAttribute("bairroCidadeNome", getBairro().getCidade().getNome());
	    record.setAttribute("bairroCidadeEstadoSiglra", getBairro().getCidade().getEstado().getSigla());

	    bairro = getBairro().getNome();
	    cidade = getBairro().getCidade().getNome();
	    uf = getBairro().getCidade().getEstado().getSigla();

	}

	String enderecoCompleto = "Rua: " + logradouro + ", N " + numero + ", " + bairro + ", " + bairro + ", " + cidade + " - " + uf;
	record.setAttribute("enderecoCompleto", enderecoCompleto);

	return record;
    }

}
