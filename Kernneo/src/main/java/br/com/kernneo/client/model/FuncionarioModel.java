package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.permissao.PermissaoMovFinanceiraModel;
import br.com.kernneo.client.types.Modulo;

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

	@OneToOne(mappedBy = "funcionario")
	@Cascade(CascadeType.ALL)
	private PermissaoMovFinanceiraModel permissaoMovFinanceiraModel;

	@ManyToOne
	@JoinColumn(name = "id_cargo")
	private CargoModel cargo;
	
	@Enumerated(EnumType.STRING)
	private Modulo moduloDeAcesso = Modulo.ZECA;

	public boolean ativo;
	private String login;
	private String senha;

	@Transient
	private String senhaTemp;

	@Transient
	private String confirmaSenhaTemp;

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

	public String getSenhaTemp() {
		return senhaTemp;
	}

	public void setSenhaTemp(String senhaTemp) {
		this.senhaTemp = senhaTemp;
	}

	public String getConfirmaSenhaTemp() {
		return confirmaSenhaTemp;
	}

	public void setConfirmaSenhaTemp(String confirmaSenhaTemp) {
		this.confirmaSenhaTemp = confirmaSenhaTemp;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public EnderecoFuncionarioModel getEnderecoFuncionario() {
		if (enderecoFuncionario == null)
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

	public PermissaoMovFinanceiraModel getPermissaoMovFinanceiraModel() {
		if (permissaoMovFinanceiraModel == null) {
			permissaoMovFinanceiraModel = new PermissaoMovFinanceiraModel();
			permissaoMovFinanceiraModel.setFuncionario(this);
			this.setPermissaoMovFinanceiraModel(permissaoMovFinanceiraModel);
		}
		return permissaoMovFinanceiraModel;
	}

	public void setPermissaoMovFinanceiraModel(PermissaoMovFinanceiraModel permissaoMovFinanceiraModel) {
		this.permissaoMovFinanceiraModel = permissaoMovFinanceiraModel;
	}
	
	

	

	public Modulo getModuloDeAcesso() {
		return moduloDeAcesso;
	}

	public void setModuloDeAcesso(Modulo moduloDeAcesso) {
		this.moduloDeAcesso = moduloDeAcesso;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("cargo", getCargo().getDescricao());
		record.setAttribute("celular", enderecoFuncionario.getCelular());

		record.setAttribute("bairro", enderecoFuncionario.getBairro().getNome());

		if (enderecoFuncionario == null) {
			record.setAttribute("endereco_email", "Não tem.");
		} else {
			record.setAttribute("endereco_email", enderecoFuncionario.getEmail());
		}
		return record;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return  getNome() != null ? getNome() : "";
	}
	
	

}
