package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.types.NFCeSituacao;
import br.com.kernneo.client.types.NFETipoAmbiente;
import br.com.kernneo.client.types.NFETipoEmissao;
import br.com.kernneo.client.utils.StringUtils;

import com.smartgwt.client.data.Record;

@Entity
@Table(name = "nfce")
public class NFCeModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private ClienteModel cliente;

    @ManyToOne
    @JoinColumn(name = "id_empresa_emitente")
    private EmpresaModel empresaEmitente;

    @ManyToOne
    @JoinColumn(name = "id_mesa")
    private MesaModel mesa;

    private String versao = "3.10";
    private String path;

    private Long numero;
    private Long serie;

    private int cStat;
    private String xMotivo;
    private String chaveNFCe;
    private Date dataHoraEmissao;

    private BigDecimal vBC = BigDecimal.ZERO;
    private BigDecimal vICMS = BigDecimal.ZERO;
    private BigDecimal vBCST = BigDecimal.ZERO;
    private BigDecimal vST = BigDecimal.ZERO;
    private BigDecimal vProd = BigDecimal.ZERO;
    private BigDecimal vFrete = BigDecimal.ZERO;
    private BigDecimal vSeg = BigDecimal.ZERO;
    private BigDecimal vDesc = BigDecimal.ZERO;
    private BigDecimal vII = BigDecimal.ZERO;
    private BigDecimal vIPI = BigDecimal.ZERO;
    private BigDecimal vPIS = BigDecimal.ZERO;
    private BigDecimal vCOFINS = BigDecimal.ZERO;
    private BigDecimal vOutro = BigDecimal.ZERO;
    private BigDecimal vNF = BigDecimal.ZERO;
    private BigDecimal vTotTrib = BigDecimal.ZERO;
    private BigDecimal vICMSDeson = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    private NFETipoEmissao nfeTipoEmissao;

    @Enumerated(EnumType.STRING)
    private NFETipoAmbiente nfeTipoAmbiente;

    @Enumerated(EnumType.STRING)
    private NFCeSituacao nfCeSituacao = NFCeSituacao.nao_emitida;

    @Override
    public Record toRecord() {

	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("dataHoraEmissaoStr", getDataHoraEmissaoString());
	record.setAttribute("dataHoraEmissao", getDataHoraEmissao());
	record.setAttribute("situacao", getNfCeSituacao());
	record.setAttribute("serie", getSerie());
	record.setAttribute("numero", getNumero());
	record.setAttribute("cStat", getcStat());
	record.setAttribute("xMotivo", getxMotivo());

	if (getCliente() != null) {
		record.setAttribute("clienteNome", getCliente().getNome());

	} else {
		record.setAttribute("clienteNome", " NÃO IDENTIFICADO");

	}
	if (getPath() != null) {
		String nfceArquivoXMLString = getPath();
		int indexDaUltimaBarra = nfceArquivoXMLString.lastIndexOf('\\');

		if (indexDaUltimaBarra != -1) {
			indexDaUltimaBarra = indexDaUltimaBarra + 1;
			nfceArquivoXMLString = nfceArquivoXMLString.substring(indexDaUltimaBarra, nfceArquivoXMLString.length());
			record.setAttribute("path", nfceArquivoXMLString);
		}
	}


	return record;
    }

    public String getPath() {
	return path;
    }

    public void setPath(String path) {
	this.path = path;
    }

    public ClienteModel getCliente() {
	return cliente;
    }

    public void setCliente(ClienteModel cliente) {
	this.cliente = cliente;
    }

    public EmpresaModel getEmpresaEmitente() {
	return empresaEmitente;
    }

    public void setEmpresaEmitente(EmpresaModel empresaEmitente) {
	this.empresaEmitente = empresaEmitente;
    }

    public String getVersao() {
	return versao;
    }

    public void setVersao(String versao) {
	this.versao = versao;
    }

    public Long getNumero() {
	return numero;
    }

    public void setNumero(Long numero) {
	this.numero = numero;
    }

    public Long getSerie() {
	return serie;
    }

    public void setSerie(Long serie) {
	this.serie = serie;
    }

    public MesaModel getMesa() {
	return mesa;
    }

    public void setMesa(MesaModel mesa) {
	this.mesa = mesa;
    }

    public NFETipoEmissao getNfeTipoEmissao() {
	return nfeTipoEmissao;
    }

    public void setNfeTipoEmissao(NFETipoEmissao nfeTipoEmissao) {
	this.nfeTipoEmissao = nfeTipoEmissao;
    }

    public Date getDataHoraEmissao() {
	return dataHoraEmissao;
    }
    
    public String getDataHoraEmissaoString() { 
    	
    	String dataHora = ""; 
    	if ( getDataHoraEmissao() != null ) { 
    		dataHora = StringUtils.getDataHoraFormatada(getDataHoraEmissao());
    	}
    	
    	
    	return dataHora;
    }

    public NFETipoAmbiente getNfeTipoAmbiente() {
	return nfeTipoAmbiente;
    }

    public void setNfeTipoAmbiente(NFETipoAmbiente nfeTipoAmbiente) {
	this.nfeTipoAmbiente = nfeTipoAmbiente;
    }

    public String getNumeroFormatado(int numero) {

	String numString = "";
	if (numero < 10) {
	    numString = "00" + numero;
	} else if (numero >= 10 && numero < 100) {
	    numString = "0" + numero;
	} else if (numero >= 100) {
	    numString = String.valueOf(numero);
	}

	return numString;
    }

    public String removeCaracteres(String texto) {
	String out = texto.replace('(', ' ').replace(')', ' ');
	return out;
    }

    public int getcStat() {
	return cStat;
    }

    public void setcStat(int cStat) {
	this.cStat = cStat;
    }

    public String getxMotivo() {
	return xMotivo;
    }

    public void setxMotivo(String xMotivo) {
	this.xMotivo = xMotivo;
    }

    public void setDataHoraEmissao(Date dataHoraEmissao) {
	this.dataHoraEmissao = dataHoraEmissao;
    }

    public NFCeSituacao getNfCeSituacao() {
	return nfCeSituacao;
    }

    public void setNfCeSituacao(NFCeSituacao nfCeSituacao) {
	this.nfCeSituacao = nfCeSituacao;
    }

    public String getChaveNFCe() {
	return chaveNFCe;
    }

    public void setChaveNFCe(String chaveNFCe) {
	this.chaveNFCe = chaveNFCe;
    }

    public BigDecimal getvBC() {
	return vBC;
    }

    public void setvBC(BigDecimal vBC) {
	this.vBC = vBC;
    }

    public BigDecimal getvICMS() {
	return vICMS;
    }

    public void setvICMS(BigDecimal vICMS) {
	this.vICMS = vICMS;
    }

    public BigDecimal getvBCST() {
	return vBCST;
    }

    public void setvBCST(BigDecimal vBCST) {
	this.vBCST = vBCST;
    }

    public BigDecimal getvST() {
	return vST;
    }

    public void setvST(BigDecimal vST) {
	this.vST = vST;
    }

    public BigDecimal getvProd() {
	return vProd;
    }

    public void setvProd(BigDecimal vProd) {
	this.vProd = vProd;
    }

    public BigDecimal getvFrete() {
	return vFrete;
    }

    public void setvFrete(BigDecimal vFrete) {
	this.vFrete = vFrete;
    }

    public BigDecimal getvSeg() {
	return vSeg;
    }

    public void setvSeg(BigDecimal vSeg) {
	this.vSeg = vSeg;
    }

    public BigDecimal getvDesc() {
	return vDesc;
    }

    public void setvDesc(BigDecimal vDesc) {
	this.vDesc = vDesc;
    }

    public BigDecimal getvII() {
	return vII;
    }

    public void setvII(BigDecimal vII) {
	this.vII = vII;
    }

    public BigDecimal getvIPI() {
	return vIPI;
    }

    public void setvIPI(BigDecimal vIPI) {
	this.vIPI = vIPI;
    }

    public BigDecimal getvPIS() {
	return vPIS;
    }

    public void setvPIS(BigDecimal vPIS) {
	this.vPIS = vPIS;
    }

    public BigDecimal getvCOFINS() {
	return vCOFINS;
    }

    public void setvCOFINS(BigDecimal vCOFINS) {
	this.vCOFINS = vCOFINS;
    }

    public BigDecimal getvOutro() {
	return vOutro;
    }

    public void setvOutro(BigDecimal vOutro) {
	this.vOutro = vOutro;
    }

    public BigDecimal getvNF() {
	return vNF;
    }

    public void setvNF(BigDecimal vNF) {
	this.vNF = vNF;
    }

    public BigDecimal getvTotTrib() {
	return vTotTrib;
    }

    public void setvTotTrib(BigDecimal vTotTrib) {
	this.vTotTrib = vTotTrib;
    }

    public BigDecimal getvICMSDeson() {
	return vICMSDeson;
    }

    public void setvICMSDeson(BigDecimal vICMSDeson) {
	this.vICMSDeson = vICMSDeson;
    }

}
