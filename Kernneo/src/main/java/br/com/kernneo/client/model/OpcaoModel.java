package br.com.kernneo.client.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.com.kernneo.client.types.OpcaoTypes;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "opcao")
public class OpcaoModel extends GenericModel {

	private String nome;

	@Enumerated(EnumType.STRING)
	private OpcaoTypes tipo = OpcaoTypes.unica_escolha;

	@javax.persistence.Transient
	private ArrayList<OpcaoAlternativaModel> listaDeAlternativa;

	public void addAlternativa(OpcaoAlternativaModel opcaoAlternativaModel) {

		opcaoAlternativaModel.setOpcao(this);

		getListaDeAlternativa().add(opcaoAlternativaModel);
	}

	public void setAlternativaSelecionada(OpcaoAlternativaModel opcaoAlternativaModel, boolean seleciona) {

		if (getTipo() == OpcaoTypes.unica_escolha) {
			if (seleciona) {
				for (OpcaoAlternativaModel opcaoAlternativaAux : getListaDeAlternativa()) {
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

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public OpcaoTypes getTipo() {

		return tipo;
	}

	public void setTipo(OpcaoTypes tipo) {

		this.tipo = tipo;
		
	}

	public ArrayList<OpcaoAlternativaModel> getListaDeAlternativa() {
		if (listaDeAlternativa == null) {
			listaDeAlternativa = new ArrayList<OpcaoAlternativaModel>();
		}
		return listaDeAlternativa;
	}

	public void setListaDeAlternativa(ArrayList<OpcaoAlternativaModel> listaDeAlternativa) {
		this.listaDeAlternativa = listaDeAlternativa;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", getNome());
		record.setAttribute("tipo", getTipo());

		return record;
	}

}
