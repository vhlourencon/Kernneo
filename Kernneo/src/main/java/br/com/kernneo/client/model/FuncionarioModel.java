package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "funcionario")
public class FuncionarioModel extends GenericModel {

    private Long codigo;
    private String nome;

    @OneToOne(mappedBy = "funcionario")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private EnderecoFuncionarioModel enderecoFuncionario;

    @OneToOne(mappedBy = "funcionario")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private FuncionarioRHModel funcionarioRH;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private CargoModel cargo;

    public boolean ativo;
    private String senha;
    private String cpf;
    private String rg;

    public Long getCodigo() {
	return codigo;
    }

    public void setCodigo(Long codigo) {
	this.codigo = codigo;
    }

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

    public boolean isAtivo() {
	return ativo;
    }

    public void setAtivo(boolean ativo) {
	this.ativo = ativo;
    }

    public CargoModel getCargo() {
        return cargo;
    }

    public void setCargo(CargoModel cargo) {
        this.cargo = cargo;
    }

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

    public EnderecoFuncionarioModel getEnderecoFuncionario() {
	if(enderecoFuncionario == null)
	    enderecoFuncionario = new EnderecoFuncionarioModel();
        return enderecoFuncionario;
    }

    public void setEnderecoFuncionario(EnderecoFuncionarioModel enderecoFuncionario) {
        this.enderecoFuncionario = enderecoFuncionario;
    }

    public FuncionarioRHModel getFuncionarioRH() {
	return funcionarioRH;
    }

    public void setFuncionarioRH(FuncionarioRHModel funcionarioRH) {
	this.funcionarioRH = funcionarioRH;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("nome", getNome() );
	record.setAttribute("cargo", getCargo().getDescricao() );
	record.setAttribute("celular", enderecoFuncionario.getCelular() );

	record.setAttribute("bairro", enderecoFuncionario.getBairro().getNome());

	if(enderecoFuncionario == null){
	    record.setAttribute("endereco_email", "Não tem.");
	} else {
	    record.setAttribute("endereco_email", enderecoFuncionario.getEmail());
	}
	return record;
    }

}
