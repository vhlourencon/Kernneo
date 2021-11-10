package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "transportadora")
public class TransportadoraModel extends GenericModel {

    private String nomeFantasia;

    private String cnpj;
    private String ie;
    private String logradouro;
    private String numero;
    private String cep;
    private String complemento;
    
    @ManyToOne
    @JoinColumn(name = "id_estado")
    private EstadoModel estado;

    @ManyToOne
    @JoinColumn(name = "id_cidade")
    private CidadeModel cidade;

    @ManyToOne
    @JoinColumn(name = "id_bairro")
    private BairroModel bairro;

    private String responsavel;
    private String telefone;
    private String celular;
    private String email;
    private String site;

    public String getNomeFantasia() {
	return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
	this.nomeFantasia = nomeFantasia;
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
    
    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public BairroModel getBairro() {
	return bairro;
    }

    public void setBairro(BairroModel bairro) {
	this.bairro = bairro;
    }

    public CidadeModel getCidade() {
	return cidade;
    }

    public void setCidade(CidadeModel cidade) {
	this.cidade = cidade;
    }

    public EstadoModel getEstado() {
	return estado;
    }

    public void setEstado(EstadoModel estado) {
	this.estado = estado;
    }

    public String getResponsavel() {
	return responsavel;
    }

    public void setResponsavel(String responsavel) {
	this.responsavel = responsavel;
    }

    public String getTelefone() {
	return telefone;
    }

    public void setTelefone(String telefone) {
	this.telefone = telefone;
    }

    public String getCelular() {
	return celular;
    }

    public void setCelular(String celular) {
	this.celular = celular;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getSite() {
	return site;
    }

    public void setSite(String site) {
	this.site = site;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());

	record.setAttribute("cnpj", getCnpj());
	record.setAttribute("nomeFantasia", getNomeFantasia());
	record.setAttribute("ie", getIe());
	record.setAttribute("logradouro", getLogradouro());
	record.setAttribute("numero", getNumero());
	record.setAttribute("complemento", getComplemento());
	record.setAttribute("numero", getCep());

	if (responsavel == null) {
	    record.setAttribute("responsavel", "Não tem.");
	} else {
	    record.setAttribute("responsavel", getResponsavel());
	}
	if (telefone == null) {
	    record.setAttribute("telefone", "Não tem.");
	} else {
	    record.setAttribute("telefone", getTelefone());
	}
	if (celular == null) {
	    record.setAttribute("celular", "Não tem.");
	} else {
	    record.setAttribute("celular", getCelular());
	}
	if (email == null) {
	    record.setAttribute("email", "Não tem.");
	} else {
	    record.setAttribute("email", getEmail());
	}
	if (site == null) {
	    record.setAttribute("site", "Não tem.");
	} else {
	    record.setAttribute("site", getSite());
	}
	if (estado == null) {
	    record.setAttribute("estado", "Não tem");
	} else {
	    record.setAttribute("estado", estado.getNome());
	}
	if (cidade == null) {
	    record.setAttribute("cidade", "Não tem");
	} else {
	    record.setAttribute("cidade", cidade.getNome());
	}
	if (bairro == null) {
	    record.setAttribute("bairro", "Não tem");
	} else {
	    record.setAttribute("bairro", bairro.getNome());
	}

	return record;
    }

}
