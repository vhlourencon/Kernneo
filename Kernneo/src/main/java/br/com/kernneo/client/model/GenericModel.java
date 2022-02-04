package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.data.Record;

@MappedSuperclass
public abstract class GenericModel implements IsSerializable {

	@Id
	@Column(nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario_delete")
	private FuncionarioModel usuarioDelete;

	@ManyToOne
	@JoinColumn(name = "id_usuario_save")
	private FuncionarioModel usuarioSave;

	@ManyToOne
	@JoinColumn(name = "id_usuario_edit")
	private FuncionarioModel usuarioEdit;

	public FuncionarioModel getUsuarioDelete() {
		return usuarioDelete;
	}

	public void setUsuarioDelete(FuncionarioModel usuarioDelete) {
		this.usuarioDelete = usuarioDelete;
	}

	public FuncionarioModel getUsuarioSave() {
		return usuarioSave;
	}

	public void setUsuarioSave(FuncionarioModel usuarioSave) {
		this.usuarioSave = usuarioSave;
	}

	public FuncionarioModel getUsuarioEdit() {
		return usuarioEdit;
	}

	public void setUsuarioEdit(FuncionarioModel usuarioEdit) {
		this.usuarioEdit = usuarioEdit;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private boolean deletado;

	public boolean isDeletado() {
		return deletado;
	}

	public void setDeletado(boolean deletado) {
		this.deletado = deletado;
	}

	public BigDecimal getValorEmBigDecimal(String valor) {

		if (valor != null && valor.trim().equals("") == false) {
			valor = valor.replaceAll(",", ".");

			BigDecimal bigDecimalValor = new BigDecimal(Double.valueOf(valor));
			bigDecimalValor = bigDecimalValor.setScale(2, BigDecimal.ROUND_HALF_EVEN);

			return bigDecimalValor;
		} else {
			return null;
		}

	}
	

    public String currencyFormat(BigDecimal n) {
        return NumberFormat.getCurrencyInstance().format(n);
    }

	public abstract Record toRecord();

}
