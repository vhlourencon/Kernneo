package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import br.com.kernneo.client.types.CFOP;
import br.com.kernneo.client.types.CRT;
import br.com.kernneo.client.types.CalculoFracaoType;
import br.com.kernneo.client.types.NFETipoAmbiente;
import br.com.kernneo.client.types.NFETipoEmissao;
import br.com.kernneo.client.types.PedidoDocumentoType;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "empresa")
public class EmpresaModel extends GenericModel {

	private String logo;

	private String razaoSocial;
	private String fantasia;
	private String cnpj;
	private String ie;
	private String im;
	private String responsavel;
	private String cpf;
	private String responsavelTelefone;
	private String email;
	private String site;

	private String logradouro;
	private String complemento;
	private String numero;

	private String chaveDeAcesso;

	private String emailNomeExibicao;
	private String emailEmail;
	private String emailLogin;
	private String emailSenha;
	private String emailPOP;
	private String emailSMTP;
	private boolean emailRequerAutenticacao;

	@Enumerated(EnumType.STRING)
	private CalculoFracaoType tipoDeCalculoFracionado;

	/*
	 * NFCE Config
	 */
	@ManyToOne
	@JoinColumn(name = "id_nfce_icms_csosn_config")
	@Cascade(CascadeType.ALL)
	private IcmsCSOSNModel nfceIcmsCsosnConfig;

	@ManyToOne
	@JoinColumn(name = "id_nfce_icms_cst_config")
	@Cascade(CascadeType.ALL)
	private IcmsCSTModel nfceIcmsCstConfig;

	@Enumerated(EnumType.STRING)
	private CFOP nfceCfop;

	@ManyToOne
	@JoinColumn(name = "id_usuario_fechamento_caixa")
	private UsuarioModel emailUsuarioFechamentoCaixa;

	@ManyToOne
	@JoinColumn(name = "id_usuario_estoque_minimo")
	private UsuarioModel emailUsuarioEstoqueMinimo;

	private boolean configMostraTelaDeProdutosAposConsultaDaMesa = true;
	private boolean configMostraTelaDeProdutoExtendida = true;
	private boolean configTxDeServicoCobrarPorBairro = false;

	@Enumerated(EnumType.STRING)
	private PedidoDocumentoType pedidoDocumentoPadraoType;
	private boolean permirAlterarDocumentoPadrao;

	private boolean permiteUsuarioRealizarPedidoSemCobrar;

	@ManyToOne
	@JoinColumn(name = "id_bairro")
	private BairroModel bairro;

	private String cep;
	private String telefone1;
	private String telefone2;
	private String celular;
	private String fax;

	private String cnae;
	private String cfopPadrao;

	private CRT codigoDeRegimeTributario;
	private Long nfceSerieHomologacao;
	private Long nfceNumeroHomologacao;
	private Long nfceSerieProducao;
	private Long nfceNumeroProducao;
	private String nfceCSCToken;
	private String nfceIdToken;

	@Enumerated(EnumType.STRING)
	private NFETipoEmissao nfceTipoEmissao;

	@Enumerated(EnumType.STRING)
	private NFETipoAmbiente nfeTipoAmbiente;

	private BigDecimal taxaDeServico;
	private BigDecimal taxaDeEntrega;
	
	
	private int ultimaAtualicacao; 
	
	
	
	
	

    public int getUltimaAtualicacao() {
        return ultimaAtualicacao;
    }

    public void setUltimaAtualicacao(int ultimaAtualicacao) {
        this.ultimaAtualicacao = ultimaAtualicacao;
    }

    public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNfceCSCToken() {
		return nfceCSCToken;
	}

	public void setNfceCSCToken(String nfceCSCToken) {
		this.nfceCSCToken = nfceCSCToken;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getIe() {
		return ie;
	}

	public void setIe(String ie) {
		this.ie = ie;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public BairroModel getBairro() {
		return bairro;
	}

	public void setBairro(BairroModel bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getResponsavelTelefone() {
		return responsavelTelefone;
	}

	public void setResponsavelTelefone(String responsavelTelefone) {
		this.responsavelTelefone = responsavelTelefone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCnae() {
		return cnae;
	}

	public void setCnae(String cnae) {
		this.cnae = cnae;
	}

	public String getCfopPadrao() {
		return cfopPadrao;
	}

	public void setCfopPadrao(String cfopPadrao) {
		this.cfopPadrao = cfopPadrao;
	}

	@Enumerated(EnumType.ORDINAL)
	public CRT getCodigoDeRegimeTributario() {
		return codigoDeRegimeTributario;
	}

	public void setCodigoDeRegimeTributario(CRT codigoDeRegimeTributario) {
		this.codigoDeRegimeTributario = codigoDeRegimeTributario;
	}

	public BigDecimal getTaxaDeServico() {
		return taxaDeServico;
	}

	public void setTaxaDeServico(BigDecimal taxaDeServico) {
		this.taxaDeServico = taxaDeServico;
	}

	public String getEmailNomeExibicao() {
		return emailNomeExibicao;
	}

	public void setEmailNomeExibicao(String emailNomeExibicao) {
		this.emailNomeExibicao = emailNomeExibicao;
	}

	public String getEmailEmail() {
		return emailEmail;
	}

	public void setEmailEmail(String emailEmail) {
		this.emailEmail = emailEmail;
	}

	public String getEmailLogin() {
		return emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}

	public String getEmailSenha() {
		return emailSenha;
	}

	public void setEmailSenha(String emailSenha) {
		this.emailSenha = emailSenha;
	}

	public String getEmailPOP() {
		return emailPOP;
	}

	public void setEmailPOP(String emailPOP) {
		this.emailPOP = emailPOP;
	}

	public String getEmailSMTP() {
		return emailSMTP;
	}

	public void setEmailSMTP(String emailSMTP) {
		this.emailSMTP = emailSMTP;
	}

	public boolean isEmailRequerAutenticacao() {
		return emailRequerAutenticacao;
	}

	public void setEmailRequerAutenticacao(boolean emailRequerAutenticacao) {
		this.emailRequerAutenticacao = emailRequerAutenticacao;
	}

	public UsuarioModel getEmailUsuarioFechamentoCaixa() {
		return emailUsuarioFechamentoCaixa;
	}

	public void setEmailUsuarioFechamentoCaixa(UsuarioModel emailUsuarioFechamentoCaixa) {
		this.emailUsuarioFechamentoCaixa = emailUsuarioFechamentoCaixa;
	}

	public UsuarioModel getEmailUsuarioEstoqueMinimo() {
		return emailUsuarioEstoqueMinimo;
	}

	public void setEmailUsuarioEstoqueMinimo(UsuarioModel emailUsuarioEstoqueMinimo) {
		this.emailUsuarioEstoqueMinimo = emailUsuarioEstoqueMinimo;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (razaoSocial == null) {
			record.setAttribute("razaoSocial", "NAO TEM");
		} else {
			record.setAttribute("razaoSocial", getRazaoSocial());
		}
		record.setAttribute("cnpj", getCnpj());
		record.setAttribute("fantasia", getFantasia());
		record.setAttribute("ie", getIe());
		record.setAttribute("im", getIm());
		record.setAttribute("responsavel", getResponsavel());
		record.setAttribute("telefone1", getTelefone1());
		record.setAttribute("celular", getCelular());

		return record;
	}

	public BigDecimal getTaxaDeEntrega() {
		return taxaDeEntrega;
	}

	public void setTaxaDeEntrega(BigDecimal taxaDeEntrega) {
		this.taxaDeEntrega = taxaDeEntrega;
	}

	public String getChaveDeAcesso() {
		return chaveDeAcesso;
	}

	public void setChaveDeAcesso(String chaveDeAcesso) {
		this.chaveDeAcesso = chaveDeAcesso;
	}

	public boolean isConfigMostraTelaDeProdutosAposConsultaDaMesa() {
		return configMostraTelaDeProdutosAposConsultaDaMesa;
	}

	public void setConfigMostraTelaDeProdutosAposConsultaDaMesa(boolean configMostraTelaDeProdutosAposConsultaDaMesa) {
		this.configMostraTelaDeProdutosAposConsultaDaMesa = configMostraTelaDeProdutosAposConsultaDaMesa;
	}

	public boolean isConfigMostraTelaDeProdutoExtendida() {
		return configMostraTelaDeProdutoExtendida;
	}

	public void setConfigMostraTelaDeProdutoExtendida(boolean configMostraTelaDeProdutoExtendida) {
		this.configMostraTelaDeProdutoExtendida = configMostraTelaDeProdutoExtendida;
	}

	public Long getNfceSerieHomologacao() {
		return nfceSerieHomologacao;
	}

	public void setNfceSerieHomologacao(Long nfceSerieHomologacao) {
		this.nfceSerieHomologacao = nfceSerieHomologacao;
	}

	public Long getNfceNumeroHomologacao() {
		return nfceNumeroHomologacao;
	}

	public void setNfceNumeroHomologacao(Long nfceNumeroHomologacao) {
		this.nfceNumeroHomologacao = nfceNumeroHomologacao;
	}

	public Long getNfceSerieProducao() {
		return nfceSerieProducao;
	}

	public void setNfceSerieProducao(Long nfceSerieProducao) {
		this.nfceSerieProducao = nfceSerieProducao;
	}

	public Long getNfceNumeroProducao() {
		return nfceNumeroProducao;
	}

	public void setNfceNumeroProducao(Long nfceNumeroProducao) {
		this.nfceNumeroProducao = nfceNumeroProducao;
	}

	public boolean isPermirAlterarDocumentoPadrao() {
		return permirAlterarDocumentoPadrao;
	}

	public void setPermirAlterarDocumentoPadrao(boolean permirAlterarDocumentoPadrao) {
		this.permirAlterarDocumentoPadrao = permirAlterarDocumentoPadrao;
	}

	public CalculoFracaoType getTipoDeCalculoFracionado() {
		if (tipoDeCalculoFracionado == null) {
			tipoDeCalculoFracionado = CalculoFracaoType.fracionado;
		}
		return tipoDeCalculoFracionado;
	}

	public void setTipoDeCalculoFracionado(CalculoFracaoType tipoDeCalculoFracionado) {
		this.tipoDeCalculoFracionado = tipoDeCalculoFracionado;
	}

	public PedidoDocumentoType getPedidoDocumentoPadraoType() {
		return pedidoDocumentoPadraoType;
	}

	public void setPedidoDocumentoPadraoType(PedidoDocumentoType pedidoDocumentoPadraoType) {
		this.pedidoDocumentoPadraoType = pedidoDocumentoPadraoType;
	}

	public boolean isPermiteUsuarioRealizarPedidoSemCobrar() {
		return permiteUsuarioRealizarPedidoSemCobrar;
	}

	public void setPermiteUsuarioRealizarPedidoSemCobrar(boolean permiteUsuarioRealizarPedidoSemCobrar) {
		this.permiteUsuarioRealizarPedidoSemCobrar = permiteUsuarioRealizarPedidoSemCobrar;
	}

	public NFETipoEmissao getNfceTipoEmissao() {
		if (nfceTipoEmissao == null) {
			nfceTipoEmissao = NFETipoEmissao._9_CONTINGENCIA_OFF_LINE;
		}
		return nfceTipoEmissao;
	}

	public void setNfceTipoEmissao(NFETipoEmissao nfceTipoEmissao) {
		this.nfceTipoEmissao = nfceTipoEmissao;
	}

	public IcmsCSOSNModel getNfceIcmsCsosnConfig() {
		if (nfceIcmsCsosnConfig == null) {
			nfceIcmsCsosnConfig = new IcmsCSOSNModel();
			this.setNfceIcmsCsosnConfig(nfceIcmsCsosnConfig);
		}

		return nfceIcmsCsosnConfig;
	}

	public void setNfceIcmsCsosnConfig(IcmsCSOSNModel nfceIcmsCsosnConfig) {
		this.nfceIcmsCsosnConfig = nfceIcmsCsosnConfig;
	}

	public IcmsCSTModel getNfceIcmsCstConfig() {
		if (nfceIcmsCstConfig == null) {
			nfceIcmsCstConfig = new IcmsCSTModel();
			this.setNfceIcmsCstConfig(nfceIcmsCstConfig);
		}

		return nfceIcmsCstConfig;
	}

	public void setNfceIcmsCstConfig(IcmsCSTModel nfceIcmsCstConfig) {
		this.nfceIcmsCstConfig = nfceIcmsCstConfig;
	}

	public CFOP getNfceCfop() {
		return nfceCfop;
	}

	public void setNfceCfop(CFOP nfceCfop) {
		this.nfceCfop = nfceCfop;
	}

	public boolean isConfigTxDeServicoCobrarPorBairro() {
		return configTxDeServicoCobrarPorBairro;
	}

	public NFETipoAmbiente getNfeTipoAmbiente() {
		return nfeTipoAmbiente;
	}

	public void setNfeTipoAmbiente(NFETipoAmbiente nfeTipoAmbiente) {
		this.nfeTipoAmbiente = nfeTipoAmbiente;
	}

	public void setConfigTxDeServicoCobrarPorBairro(boolean configTxDeServicoCobrarPorBairro) {
		this.configTxDeServicoCobrarPorBairro = configTxDeServicoCobrarPorBairro;
	}

	public String getNfceIdToken() {
		return nfceIdToken;
	}

	public void setNfceIdToken(String nfceIdToken) {
		this.nfceIdToken = nfceIdToken;
	}

}
