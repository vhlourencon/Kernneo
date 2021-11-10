package br.com.kernneo.client.caravana.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.caravana.types.CaravanaUsuarioPerfilTypes;
import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_usuario")
public class CaravanaUsuarioModel extends GenericModel {

	private String nome;
	private String login;
	private String senha;
	private String email;
	private String telefone;
	
	
	@Transient
	private String confirmaSenha;
	private boolean ativo;

	@Enumerated(EnumType.STRING)
	private CaravanaUsuarioPerfilTypes perfil;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public CaravanaUsuarioPerfilTypes getPerfil() {
		return perfil;
	}

	public void setPerfil(CaravanaUsuarioPerfilTypes perfil) {
		this.perfil = perfil;
	}

	@Override
	public Record toRecord() {

		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("login", getLogin());
		record.setAttribute("senha", getSenha());
		record.setAttribute("email", getEmail());
		record.setAttribute("telefone", getTelefone());
		record.setAttribute("perfil", getPerfil());
		record.setAttribute("ativo", isAtivo());

		return record;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

}
