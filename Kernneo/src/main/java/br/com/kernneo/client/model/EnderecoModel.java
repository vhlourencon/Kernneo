package br.com.kernneo.client.model;


import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import com.smartgwt.client.data.Record;

@MappedSuperclass
public class EnderecoModel extends GenericModel {

    private String logradouro;
    private String cep;
    private String complemento;
    //private boolean enderecoPrincipal;

    @ManyToOne
    private BairroModel bairro;
    private String numero;

  

    private String telefone;
    private String celular;
    private String email;
    private String site;
    
    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
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

//    public boolean isEnderecoPrincipal() {
//        return enderecoPrincipal;
//    }
//
//    public void setEnderecoPrincipal(boolean enderecoPrincipal) {
//        this.enderecoPrincipal = enderecoPrincipal;
//    }
    

    public BairroModel getBairro() {
	return bairro;
    }

    public void setBairro(BairroModel bairro) {
	this.bairro = bairro;
    }

    public String getNumero() {
	return numero;
    }

    public void setNumero(String numero) {
	this.numero = numero;
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
	
	//record.setAttribute("principal", isEnderecoPrincipal());
	if (logradouro == null) {
	    record.setAttribute("logradouro", "Não tem.");

	} else {
	    record.setAttribute("logradouro", getLogradouro() );
	}
	
	if (cep == null) {
	    record.setAttribute("cep", "Não tem.");

	} else {
	    record.setAttribute("cep", getCep() );
	}
	
	if (complemento == null) {
	    record.setAttribute("complemento", "Não tem.");

	} else {
	    record.setAttribute("complemento", getComplemento());
	}
	
	if (bairro == null) {
	    record.setAttribute("bairro", "Não tem.");
	    record.setAttribute("bairroNome", "Não tem.");
	    
	    record.setAttribute("cidade", "Não tem.");
	    record.setAttribute("cidadeNome", "Não tem.");
	    
	    record.setAttribute("estadoSigla", "Não tem.");
	    record.setAttribute("estado", "Não tem.");
	    
	} else {
	    record.setAttribute("bairro", bairro.getId());
	    record.setAttribute("bairroNome", bairro.getNome());
	    
	    record.setAttribute("estado", bairro.getCidade().getEstado().getId());
	    record.setAttribute("estadoSigla", bairro.getCidade().getEstado().getSigla());
	    
	    record.setAttribute("cidade", bairro.getCidade().getId());
	    record.setAttribute("cidadeNome", bairro.getCidade().getNome());
	}
	
	if (numero == null) {
	    record.setAttribute("numero", "Não tem.");

	} else {
	    record.setAttribute("numero", getNumero());
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
	
	return record;
    }

}
