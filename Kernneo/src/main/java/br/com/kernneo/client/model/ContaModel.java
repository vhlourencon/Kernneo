package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

import br.com.kernneo.client.types.ContaType;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "conta_receber")
public class ContaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_cliente")
	private ClienteModel cliente;

	@ManyToOne
	@JoinColumn(name = "id_fornecedor")
	private FornecedorModel fornecedor;

	private boolean clienteSelecionado = true;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataHora;
	private BigDecimal valor;
	private String obs;
	private boolean quitado;

	@Enumerated(EnumType.STRING)
	private ContaType tipo;

	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dataHoraQuitado;

	@ManyToOne
	@JoinColumn(name = "id_usuario_quitador")
	private UsuarioModel usuarioQuitador;

	public ClienteModel getCliente() {
		return cliente;
	}

	public void setCliente(ClienteModel cliente) {
		this.cliente = cliente;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public Date getDataHoraQuitado() {
		return dataHoraQuitado;
	}

	public void setDataHoraQuitado(Date dataHoraQuitado) {
		this.dataHoraQuitado = dataHoraQuitado;
	}

	public ContaType getTipo() {
		return tipo;
	}

	public void setTipo(ContaType tipo) {
		this.tipo = tipo;
	}

	public UsuarioModel getUsuarioQuitador() {
		return usuarioQuitador;
	}

	public void setUsuarioQuitador(UsuarioModel usuarioQuitador) {
		this.usuarioQuitador = usuarioQuitador;
	}

	public FornecedorModel getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(FornecedorModel fornecedor) {
		this.fornecedor = fornecedor;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (getValor() != null) {
			record.setAttribute("valor", StringUtils.getDoubleFormatadoEmDinheiro(getValor()));
			record.setAttribute("valorDouble", getValor());
		}

		record.setAttribute("quitado", quitado);
		record.setAttribute("dataHora", StringUtils.getDataHoraFormatada(getDataHora()));
		record.setAttribute("dataHoraQuitado", StringUtils.getDataHoraFormatada(getDataHoraQuitado()));
		record.setAttribute("obs", getObs());
		if (getCliente() != null) {
			record.setAttribute("clienteNome", getCliente().getNome());
			record.setAttribute("clienteId", getCliente().getId());
		}

		return record;
	}

	public boolean isClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(boolean clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

}
