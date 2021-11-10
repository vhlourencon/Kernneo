package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "opcao_item_alternativa")
public class OpcaoItemAlternativaModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_opcao_item")
	private OpcaoItemModel opcaoItem;

	@ManyToOne
	@JoinColumn(name = "id_alternativa")
	private OpcaoAlternativaModel alternativa;

	private boolean selecionada;
	private BigDecimal valor; 
	private Double quantidade; 
	

	public boolean isSelecionada() {
		return selecionada;
	}

	public void setSelecionada(boolean selecionada) {
		this.selecionada = selecionada;
	}

	public OpcaoItemModel getOpcaoItem() {
		return opcaoItem;
	}

	public void setOpcaoItem(OpcaoItemModel opcaoItem) {
		this.opcaoItem = opcaoItem;
	}

	public OpcaoAlternativaModel getAlternativa() {
		return alternativa;
	}

	public void setAlternativa(OpcaoAlternativaModel alternativa) {
		this.alternativa = alternativa;
		setSelecionada(alternativa.isSelecionada());
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());

		record.setAttribute("selecionada", isSelecionada());

		return record;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

}
