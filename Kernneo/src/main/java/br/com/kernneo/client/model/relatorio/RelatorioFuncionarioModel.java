package br.com.kernneo.client.model.relatorio;

import java.util.ArrayList;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.kernneo.client.model.ConvenioModel;
import br.com.kernneo.client.model.EnderecoClienteModel;
import br.com.kernneo.client.model.EnderecoFuncionarioModel;
import br.com.kernneo.client.model.FluxoDeCaixaModel;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RelatorioFuncionarioModel implements IsSerializable {

    private String nome;
    private String email;
    private String contato;
    private String cidade;
    private String estado;
    
    private ConvenioModel convenio;

    private EnderecoFuncionarioModel funcionarioEndereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public EnderecoFuncionarioModel getFuncionarioEndereco() {
        return funcionarioEndereco;
    }

    public void setFuncionarioEndereco(EnderecoFuncionarioModel funcionarioEndereco) {
        this.funcionarioEndereco = funcionarioEndereco;
    }

}
