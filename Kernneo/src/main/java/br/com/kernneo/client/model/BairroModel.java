package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.types.MesaTipo;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "bairro")
public class BairroModel extends GenericModel {

	private String nome;

	@ManyToOne
	@JoinColumn(name = "id_cidade")
	private CidadeModel cidade;

	private BigDecimal taxaDeEntrega = BigDecimal.ZERO;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (getTaxaDeEntrega() != null) {
			record.setAttribute("taxaDeEntrega", StringUtils.getDoubleFormatadoEmDinheiro(getTaxaDeEntrega()));
		} else {
			record.setAttribute("taxaDeEntrega", StringUtils.getDoubleFormatadoEmDinheiro(BigDecimal.ZERO));
		}

		if (nome == null) {
			record.setAttribute("nome", "Não tem.");

		} else {
			record.setAttribute("nome", getNome());
		}

		if (cidade == null) {

		} else {
			record.setAttribute("cidadeID", cidade.getId());
			record.setAttribute("cidadeNome", cidade.getNome());

			if (cidade.getEstado() != null) {
				record.setAttribute("estadoId", cidade.getEstado().getId());
				record.setAttribute("estadoNome", cidade.getEstado().getNome());
				record.setAttribute("estadoSigla", cidade.getEstado().getSigla());

			}
		}

		return record;
	}

	public CidadeModel getCidade() {
		return cidade;
	}

	public void setCidade(CidadeModel cidade) {
		this.cidade = cidade;
	}

	public BigDecimal getTaxaDeEntrega() {

		if (taxaDeEntrega == null) {
			taxaDeEntrega = BigDecimal.ZERO;
		}
		taxaDeEntrega = taxaDeEntrega.setScale(2, BigDecimal.ROUND_HALF_EVEN);

		return taxaDeEntrega;

	}

	public void setTaxaDeEntrega(BigDecimal taxaDeEntrega) {
		this.taxaDeEntrega = taxaDeEntrega;
	}

}
