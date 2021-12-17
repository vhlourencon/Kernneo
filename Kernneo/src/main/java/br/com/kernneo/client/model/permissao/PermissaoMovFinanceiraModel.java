package br.com.kernneo.client.model.permissao;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.CidadeModel;
import br.com.kernneo.client.model.ComposicaoModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "permissao_mov_financeira")
public class PermissaoMovFinanceiraModel extends GenericModel {

	@OneToOne
	@JoinColumn(name = "id_funcionario")
	private FuncionarioModel funcionario;

	private boolean deleteUsuarioLancamentoPendente;
	private boolean deleteUsuarioLancamentoFeito;
	private boolean deleteOutrosLancamentoPendente;
	private boolean deleteOutrosLancamentoFeito;

	public FuncionarioModel getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioModel funcionario) {
		this.funcionario = funcionario;
	}
	
	

	public boolean isDeleteUsuarioLancamentoPendente() {
		return deleteUsuarioLancamentoPendente;
	}

	public void setDeleteUsuarioLancamentoPendente(boolean deleteUsuarioLancamentoPendente) {
		this.deleteUsuarioLancamentoPendente = deleteUsuarioLancamentoPendente;
	}

	public boolean isDeleteUsuarioLancamentoFeito() {
		return deleteUsuarioLancamentoFeito;
	}

	public void setDeleteUsuarioLancamentoFeito(boolean deleteUsuarioLancamentoFeito) {
		this.deleteUsuarioLancamentoFeito = deleteUsuarioLancamentoFeito;
	}

	public boolean isDeleteOutrosLancamentoPendente() {
		return deleteOutrosLancamentoPendente;
	}

	public void setDeleteOutrosLancamentoPendente(boolean deleteOutrosLancamentoPendente) {
		this.deleteOutrosLancamentoPendente = deleteOutrosLancamentoPendente;
	}

	public boolean isDeleteOutrosLancamentoFeito() {
		return deleteOutrosLancamentoFeito;
	}

	public void setDeleteOutrosLancamentoFeito(boolean deleteOutrosLancamentoFeito) {
		this.deleteOutrosLancamentoFeito = deleteOutrosLancamentoFeito;
	}

	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

}
