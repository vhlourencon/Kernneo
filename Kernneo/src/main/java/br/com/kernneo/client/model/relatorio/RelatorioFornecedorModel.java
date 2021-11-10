package br.com.kernneo.client.model.relatorio;

import java.util.ArrayList;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.kernneo.client.model.ConvenioModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.model.EnderecoFornecedorModel;
import br.com.kernneo.client.model.FluxoDeCaixaModel;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RelatorioFornecedorModel implements IsSerializable {

    private String fantasia;
    private String cnpj;
    private String email;
    private String contato;
    private String cidade;
    private String estado;
    
    private ConvenioModel convenio;

    private EnderecoFornecedorModel fornecedorEndereco;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public ConvenioModel getConvenio() {
        return convenio;
    }

    public void setConvenio(ConvenioModel convenio) {
        this.convenio = convenio;
    }

    public EnderecoFornecedorModel getFornecedorEndereco() {
        return fornecedorEndereco;
    }

    public void setFornecedorEndereco(EnderecoFornecedorModel fornecedorEndereco) {
        this.fornecedorEndereco = fornecedorEndereco;
    }
    
}
