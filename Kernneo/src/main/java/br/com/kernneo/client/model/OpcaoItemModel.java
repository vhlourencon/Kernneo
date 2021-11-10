package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.kernneo.client.types.OpcaoTypes;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "opcao_item")
public class OpcaoItemModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_opcao")
	private OpcaoModel opcao;

	@ManyToOne
	@JoinColumn(name = "id_item")
	private ItemModel item;

	private OpcaoTypes tipo = OpcaoTypes.observacao;
	private String observacao;

	public void setAlternativaSelecionada(OpcaoItemAlternativaModel opcaoAlternativaModel, boolean seleciona) {

		if (getTipo() == OpcaoTypes.unica_escolha) {
			if (seleciona) {
				for (OpcaoItemAlternativaModel opcaoAlternativaAux : getListaDeAlternativaDoItem()) {
					opcaoAlternativaAux.setSelecionada(false);
				}
				opcaoAlternativaModel.setSelecionada(true);
			} else {
				opcaoAlternativaModel.setSelecionada(false);
			}
		} else {

			opcaoAlternativaModel.setSelecionada(seleciona);

		}

	}

	@Transient
	private ArrayList<OpcaoItemAlternativaModel> listaDeAlternativaDoItem;

	public OpcaoModel getOpcao() {
		return opcao;
	}

	public void setOpcao(OpcaoModel opcao) {
		this.opcao = opcao;
		if (opcao != null) {
			setTipo(opcao.getTipo());
		}

	}

	public OpcaoTypes getTipo() {
		return tipo;
	}

	public void setTipo(OpcaoTypes tipo) {
		this.tipo = tipo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public ItemModel getItem() {
		return item;
	}

	public void setItem(ItemModel item) {
		this.item = item;
	}

	public ArrayList<OpcaoItemAlternativaModel> getListaDeAlternativaDoItem() {
		if (listaDeAlternativaDoItem == null) {
			listaDeAlternativaDoItem = new ArrayList<OpcaoItemAlternativaModel>();

			if (getOpcao() != null) {
				for (OpcaoAlternativaModel opcaoAlternativaModel : opcao.getListaDeAlternativa()) {

					OpcaoItemAlternativaModel opcaoItemAlternativaModel = new OpcaoItemAlternativaModel();
					opcaoItemAlternativaModel.setAlternativa(opcaoAlternativaModel);
					opcaoItemAlternativaModel.setOpcaoItem(this);
					opcaoItemAlternativaModel.setSelecionada(opcaoAlternativaModel.isSelecionada());
					opcaoItemAlternativaModel.setValor(opcaoAlternativaModel.getValor());
					opcaoItemAlternativaModel.setQuantidade(opcaoAlternativaModel.getQuantidade());

					listaDeAlternativaDoItem.add(opcaoItemAlternativaModel);
				}
			}

		}
		return listaDeAlternativaDoItem;
	}

	public void setListaDeAlternativaDoItem(ArrayList<OpcaoItemAlternativaModel> listaDeAlternativaDoItem) {
		this.listaDeAlternativaDoItem = listaDeAlternativaDoItem;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());

		return record;
	}

}
