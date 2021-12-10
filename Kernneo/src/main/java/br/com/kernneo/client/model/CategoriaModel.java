package br.com.kernneo.client.model;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "categoria")
public class CategoriaModel extends GenericModel {

	private String descricao;

	private boolean ativo;

	@Column(name = "categorias_img")
	private String imagem;

	private String categoria_nome_portugues;
	private String categoria_nome_ingles;
	private String categoria_nome_espanhol;

	private String flag;

	@Transient
	private ArrayList<ProdutoModel> listaDeProdutos = new ArrayList<ProdutoModel>();

	@Transient
	private ArrayList<OpcaoCategoriaModel> listaDeOpcaoDaCategoria;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getCategoria_nome_portugues() {
		return categoria_nome_portugues;
	}

	public void setCategoria_nome_portugues(String categoria_nome_portugues) {
		this.categoria_nome_portugues = categoria_nome_portugues;
	}

	public String getCategoria_nome_ingles() {
		return categoria_nome_ingles;
	}

	public void setCategoria_nome_ingles(String categoria_nome_ingles) {
		this.categoria_nome_ingles = categoria_nome_ingles;
	}

	public String getCategoria_nome_espanhol() {
		return categoria_nome_espanhol;
	}

	public void setCategoria_nome_espanhol(String categoria_nome_espanhol) {
		this.categoria_nome_espanhol = categoria_nome_espanhol;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("categoria_nome_portugues", categoria_nome_portugues);
		record.setAttribute("imagem", imagem);
		record.setAttribute("ativo", ativo);
		if (isAtivo()) {
			record.setAttribute("ativoString", "Ativa");
		} else {
			record.setAttribute("ativoString", "Inativa");
		}
		return record;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public OpcaoCategoriaModel addOpcaoDaCategoria(OpcaoModel opcaoModel) {
		OpcaoCategoriaModel opcaoCategoriaModel = new OpcaoCategoriaModel();
		opcaoCategoriaModel.setOpcao(opcaoModel);
		opcaoCategoriaModel.setCategoria(this);

		getListaDeOpcaoDaCategoria().add(opcaoCategoriaModel);

		return opcaoCategoriaModel;
	}

	public ArrayList<ProdutoModel> getListaDeProdutos() {
		if (listaDeProdutos == null) {
			listaDeProdutos = new ArrayList<ProdutoModel>();
		}
		return listaDeProdutos;
	}

	public void setListaDeProdutos(ArrayList<ProdutoModel> listaDeProdutos) {
		this.listaDeProdutos = listaDeProdutos;
	}

	public ArrayList<OpcaoCategoriaModel> getListaDeOpcaoDaCategoria() {
		if (listaDeOpcaoDaCategoria == null) {
			listaDeOpcaoDaCategoria = new ArrayList<OpcaoCategoriaModel>();
		}
		return listaDeOpcaoDaCategoria;
	}

	public void setListaDeOpcaoDaCategoria(ArrayList<OpcaoCategoriaModel> listaDeOpcaoDaCategoria) {
		this.listaDeOpcaoDaCategoria = listaDeOpcaoDaCategoria;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getCategoria_nome_portugues();
	}

}
