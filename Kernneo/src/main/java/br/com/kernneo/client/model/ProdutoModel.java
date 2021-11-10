package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.kernneo.client.types.CFOP;
import br.com.kernneo.client.types.OrigemDaMercadoria;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "produto")
public class ProdutoModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_categoria")
	private CategoriaModel categoria;

	@ManyToOne
	@JoinColumn(name = "id_ncm")
	private NCMModel ncm;

	private String ncmString;

	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private EstoqueModel estoque;

	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private ComposicaoModel composicao;

	/*
	 * Parte Fiscal NFCE
	 */
	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private IcmsCSTModel nfceIcmsCstConfig;

	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private IcmsCSOSNModel nfceIcmsCsosnConfig;

	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private PISModel pis;

	@OneToOne(mappedBy = "produto")
	@Cascade(CascadeType.ALL)
	private COFINSModel cofins;

	@Enumerated(EnumType.STRING)
	private CFOP nfceCFOP;

	@Enumerated(EnumType.STRING)
	private OrigemDaMercadoria origemDaMercadoria = OrigemDaMercadoria.nacional_0;

	private boolean nfceUsaConfigEmpresa = true;
	private boolean opcaoUsaConfigCategoria = true;
	
	@Transient
	private ArrayList<OpcaoProdutoModel> listaDeOpcaoDoProduto; 

	/*
	 * Fim parte fiscal
	 */

	private Long codigo;

	private String descricao;
	private String nome;

	private String nomeIngles;
	private String nomeEspanhol;
	private String descricaoIngles;
	private String descricaoEspanhol;

	private BigDecimal precoDeCusto;
	private BigDecimal precoDeVenda;

	private String codigoDeBarras;
	private String imagem;

	private boolean imprimeCozinha;
	private boolean imprimeCopa1;
	private boolean imprimeCopa2;
	private boolean imprimeCopa3;
	private boolean imprimeBar;
	private boolean imprimeCaixa;

	private String ean;
	private String eanTrib;

	private boolean ativo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public BigDecimal getPrecoDeCusto() {
		return precoDeCusto;
	}

	public void setPrecoDeCusto(BigDecimal precoDeCusto) {
		this.precoDeCusto = precoDeCusto;
	}

	public BigDecimal getPrecoDeVenda() {
		return precoDeVenda;
	}

	public void setPrecoDeVenda(BigDecimal precoDeVenda) {
		this.precoDeVenda = precoDeVenda;
	}

	public NCMModel getNcm() {
		return ncm;
	}

	public void setNcm(NCMModel ncm) {
		this.ncm = ncm;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public IcmsCSTModel getNfceIcmsCstConfig() {
		if (nfceIcmsCstConfig == null) {
			nfceIcmsCstConfig = new IcmsCSTModel();
			nfceIcmsCstConfig.setProduto(this);
			this.setNfceIcmsCstConfig(nfceIcmsCstConfig);
		}
		return nfceIcmsCstConfig;
	}

	public void setNfceIcmsCstConfig(IcmsCSTModel icmsCST) {
		this.nfceIcmsCstConfig = icmsCST;
	}

	public IcmsCSOSNModel getNfceIcmsCsosnConfig() {
		if (nfceIcmsCsosnConfig == null) {
			nfceIcmsCsosnConfig = new IcmsCSOSNModel();
			nfceIcmsCsosnConfig.setProduto(this);
			this.setNfceIcmsCsosnConfig(nfceIcmsCsosnConfig);
		}
		return nfceIcmsCsosnConfig;
	}

	public void setNfceIcmsCsosnConfig(IcmsCSOSNModel icmsCSOSN) {
		this.nfceIcmsCsosnConfig = icmsCSOSN;
	}

	public PISModel getPis() {
		return pis;
	}

	public void setPis(PISModel pis) {
		this.pis = pis;
	}

	public COFINSModel getCofins() {
		return cofins;
	}

	public void setCofins(COFINSModel cofins) {
		this.cofins = cofins;
	}

	@Enumerated(EnumType.STRING)
	public OrigemDaMercadoria getOrigemDaMercadoria() {
		return origemDaMercadoria;
	}

	public void setOrigemDaMercadoria(OrigemDaMercadoria origemDaMercadoria) {
		this.origemDaMercadoria = origemDaMercadoria;
	}
	
	public OpcaoProdutoModel addOpcaoDoProduto(OpcaoModel opcaoModel) {
		OpcaoProdutoModel opcaoProdutoModel = new OpcaoProdutoModel();
		opcaoProdutoModel.setOpcao(opcaoModel);
		opcaoProdutoModel.setProduto(this);
		

		getListaDeOpcaoDoProduto().add(opcaoProdutoModel);

		return opcaoProdutoModel;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("nome", nome);
		record.setAttribute("descricao", descricao);
		record.setAttribute("imagem", imagem);
		record.setAttribute("precoDeVenda", StringUtils.getDoubleFormatadoEmDinheiro(getPrecoDeVenda()));
		record.setAttribute("precoDeCusto", StringUtils.getDoubleFormatadoEmDinheiro(getPrecoDeCusto()));
		record.setAttribute("ativo", ativo);
		record.setAttribute("codigo", codigo);
		if (isAtivo()) {
			record.setAttribute("ativoString", "Ativo");
		} else {
			record.setAttribute("ativoString", "Inativo");
		}

		if (getCategoria() != null) {
			record.setAttribute("categoriaNome", getCategoria().getCategoria_nome_portugues());
		}

		if (getEstoque() != null) {
			record.setAttribute("estoqueSaldoMinimo", getEstoque().getSaldoMinimo());
			record.setAttribute("estoqueSaldo", getEstoque().getSaldo());
		}

		return record;
	}

	public ComposicaoModel getComposicao() {
		if (composicao == null) {
			composicao = new ComposicaoModel();
			composicao.setProduto(this);
			this.setComposicao(composicao);
		}
		return composicao;
	}

	public void setComposicao(ComposicaoModel composicao) {
		this.composicao = composicao;
	}

	public EstoqueModel getEstoque() {
		if (estoque == null) {
			estoque = new EstoqueModel();
			estoque.setProduto(this);
			this.setEstoque(estoque);
		}
		return estoque;

	}

	public void setEstoque(EstoqueModel estoque) {
		this.estoque = estoque;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public CategoriaModel getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaModel categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isImprimeCozinha() {
		return imprimeCozinha;
	}

	public void setImprimeCozinha(boolean imprimeCozinha) {
		this.imprimeCozinha = imprimeCozinha;
	}

	public boolean isImprimeCopa1() {
		return imprimeCopa1;
	}

	public void setImprimeCopa1(boolean imprimeCopa1) {
		this.imprimeCopa1 = imprimeCopa1;
	}

	public boolean isImprimeCopa2() {
		return imprimeCopa2;
	}

	public void setImprimeCopa2(boolean imprimeCopa2) {
		this.imprimeCopa2 = imprimeCopa2;
	}

	public boolean isImprimeCopa3() {
		return imprimeCopa3;
	}

	public void setImprimeCopa3(boolean imprimeCopa3) {
		this.imprimeCopa3 = imprimeCopa3;
	}

	public boolean isImprimeBar() {
		return imprimeBar;
	}

	public void setImprimeBar(boolean imprimeBar) {
		this.imprimeBar = imprimeBar;
	}

	public boolean isImprimeCaixa() {
		return imprimeCaixa;
	}

	public void setImprimeCaixa(boolean imprimeCaixa) {
		this.imprimeCaixa = imprimeCaixa;
	}

	public String getNomeIngles() {
		return nomeIngles;
	}

	public void setNomeIngles(String nomeIngles) {
		this.nomeIngles = nomeIngles;
	}

	public String getNomeEspanhol() {
		return nomeEspanhol;
	}

	public void setNomeEspanhol(String nomeEspanhol) {
		this.nomeEspanhol = nomeEspanhol;
	}

	public String getDescricaoIngles() {
		return descricaoIngles;
	}

	public void setDescricaoIngles(String descricaoIngles) {
		this.descricaoIngles = descricaoIngles;
	}

	public String getDescricaoEspanhol() {
		return descricaoEspanhol;
	}

	public void setDescricaoEspanhol(String descricaoEspanhol) {
		this.descricaoEspanhol = descricaoEspanhol;
	}

	

	public CFOP getNfceCFOP() {
		return nfceCFOP;
	}

	public void setNfceCFOP(CFOP nfceCFOP) {
		this.nfceCFOP = nfceCFOP;
	}

	public String getNcmString() {
		return ncmString;
	}

	public void setNcmString(String ncmString) {
		this.ncmString = ncmString;
	}

	public String getEan() {
		return ean;
	}

	public void setEan(String ean) {
		this.ean = ean;
	}

	public String getEanTrib() {
		return eanTrib;
	}

	public void setEanTrib(String eanTrib) {
		this.eanTrib = eanTrib;
	}

	public boolean isNfceUsaConfigEmpresa() {
		return nfceUsaConfigEmpresa;
	}

	public void setNfceUsaConfigEmpresa(boolean nfceUsaConfigEmpresa) {
		this.nfceUsaConfigEmpresa = nfceUsaConfigEmpresa;
	}

	public boolean isOpcaoUsaConfigCategoria() {
		return opcaoUsaConfigCategoria;
	}

	public void setOpcaoUsaConfigCategoria(boolean opcaoUsaConfigCategoria) {
		this.opcaoUsaConfigCategoria = opcaoUsaConfigCategoria;
	}

	public ArrayList<OpcaoProdutoModel> getListaDeOpcaoDoProduto() {
		if (listaDeOpcaoDoProduto == null) { 
			listaDeOpcaoDoProduto = new ArrayList<OpcaoProdutoModel>();
		}
		return listaDeOpcaoDoProduto;
	}

	public void setListaDeOpcaoDoProduto(ArrayList<OpcaoProdutoModel> listaDeOpcaoDoProduto) {
		this.listaDeOpcaoDoProduto = listaDeOpcaoDoProduto;
	}
	
	

}
