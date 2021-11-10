package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "usuario")
public class UsuarioModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    EmpresaModel empresa;

    private Long codigo;
    private String nome;
    private String login;
    private String senha;
    private String email;

    @Transient
    private String confirmaSenha;
    private String foto;
    private boolean administrador;

    private String senhaPDV;

    private boolean acessoPDV;
    private boolean acessoSistema;

    public String getSenha() {
	return senha;
    }

    public void setSenha(String senha) {
	this.senha = senha;
    }

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

    public Long getCodigo() {
	return codigo;
    }

    public void setCodigo(Long codigo) {
	this.codigo = codigo;
    }

    public boolean isAcessoPDV() {
	return acessoPDV;
    }

    public void setAcessoPDV(boolean acessoPDV) {
	this.acessoPDV = acessoPDV;
    }

    public boolean isAcessoSistema() {
	return acessoSistema;
    }

    public void setAcessoSistema(boolean acessoSistema) {
	this.acessoSistema = acessoSistema;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("codigo", codigo);
	record.setAttribute("nome", nome);
	record.setAttribute("senha", senha);
	record.setAttribute("foto", foto);
	record.setAttribute("administrador", isAdministrador());
	record.setAttribute("acessoAoSistema", isAcessoSistema());
	record.setAttribute("acessoAoPDV", isAcessoPDV());
	record.setAttribute("login", login);

	return record;
    }

    public boolean isAdministrador() {
	return administrador;
    }

    public void setAdministrador(boolean administrador) {
	this.administrador = administrador;
    }

    public String getFoto() {
	return foto;
    }

    public void setFoto(String foto) {
	this.foto = foto;
    }

    public String getSenhaPDV() {
	return senhaPDV;
    }

    public void setSenhaPDV(String senhaPDV) {
	this.senhaPDV = senhaPDV;
    }

    public String getConfirmaSenha() {
	return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
	this.confirmaSenha = confirmaSenha;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public EmpresaModel getEmpresa() {
	return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
	this.empresa = empresa;
    }

}
