package br.com.kernneo.client.model;

import java.math.BigDecimal;

import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

import br.com.kernneo.client.types.ModBC;
import br.com.kernneo.client.types.ModBCST;
import br.com.kernneo.client.types.MotivoDesICMS;

import com.smartgwt.client.data.Record;

@MappedSuperclass
public class IcmsModel extends GenericModel {

    @OneToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;
    
    
  
 
  
   
    private ModBC modBC;
    private BigDecimal pRedBC = BigDecimal.ZERO;
    private BigDecimal vBC = BigDecimal.ZERO;

    private BigDecimal pICMS = BigDecimal.ZERO;
    private BigDecimal vICMS = BigDecimal.ZERO;

    private ModBCST modBCST;

    private BigDecimal pMVAST = BigDecimal.ZERO;
    private BigDecimal pRedBCST = BigDecimal.ZERO;
    private BigDecimal vBCST = BigDecimal.ZERO;
    private BigDecimal pICMSST = BigDecimal.ZERO;
    private BigDecimal vICMSST = BigDecimal.ZERO;
    private BigDecimal vBCSTRet = BigDecimal.ZERO;
    private BigDecimal vICMSSTRet = BigDecimal.ZERO;
    private BigDecimal vBCSTDest = BigDecimal.ZERO;
    private BigDecimal vICMSSTDest = BigDecimal.ZERO;

    private MotivoDesICMS motivoDesICMS;
    private BigDecimal pBCOp = BigDecimal.ZERO;
    private String UFST;
    private BigDecimal pCredSN = BigDecimal.ZERO;
    private BigDecimal vCredICMSSN = BigDecimal.ZERO;

    private BigDecimal vICMSDeson = BigDecimal.ZERO;
    private BigDecimal vICMSOp = BigDecimal.ZERO;
    private BigDecimal pDif = BigDecimal.ZERO;
    private BigDecimal vICMSDif = BigDecimal.ZERO;

    

    public ProdutoModel getProduto() {
	return produto;
    }

    public void setProduto(ProdutoModel produto) {
	this.produto = produto;
    }

  

  



    

    public ModBC getModBC() {
	return modBC;
    }

    public void setModBC(ModBC modBC) {
	this.modBC = modBC;
    }

    public BigDecimal getpRedBC() {
	return pRedBC;
    }

    public void setpRedBC(BigDecimal pRedBC) {
	this.pRedBC = pRedBC;
    }

    public BigDecimal getvBC() {
	return vBC;
    }

    public void setvBC(BigDecimal vBC) {
	this.vBC = vBC;
    }

    public BigDecimal getpICMS() {
	return pICMS;
    }

    public void setpICMS(BigDecimal pICMS) {
	this.pICMS = pICMS;
    }

    public BigDecimal getvICMS() {
	return vICMS;
    }

    public void setvICMS(BigDecimal vICMS) {
	this.vICMS = vICMS;
    }

    public ModBCST getModBCST() {
	return modBCST;
    }

    public void setModBCST(ModBCST modBCST) {
	this.modBCST = modBCST;
    }

    public BigDecimal getpMVAST() {
	return pMVAST;
    }

    public void setpMVAST(BigDecimal pMVAST) {
	this.pMVAST = pMVAST;
    }

    public BigDecimal getpRedBCST() {
	return pRedBCST;
    }

    public void setpRedBCST(BigDecimal pRedBCST) {
	this.pRedBCST = pRedBCST;
    }

    public BigDecimal getvBCST() {
	return vBCST;
    }

    public void setvBCST(BigDecimal vBCST) {
	this.vBCST = vBCST;
    }

    public BigDecimal getpICMSST() {
	return pICMSST;
    }

    public void setpICMSST(BigDecimal pICMSST) {
	this.pICMSST = pICMSST;
    }

    public BigDecimal getvICMSST() {
	return vICMSST;
    }

    public void setvICMSST(BigDecimal vICMSST) {
	this.vICMSST = vICMSST;
    }

    public BigDecimal getvBCSTRet() {
	return vBCSTRet;
    }

    public void setvBCSTRet(BigDecimal vBCSTRet) {
	this.vBCSTRet = vBCSTRet;
    }

    public BigDecimal getvICMSSTRet() {
	return vICMSSTRet;
    }

    public void setvICMSSTRet(BigDecimal vICMSSTRet) {
	this.vICMSSTRet = vICMSSTRet;
    }

    public BigDecimal getvBCSTDest() {
	return vBCSTDest;
    }

    public void setvBCSTDest(BigDecimal vBCSTDest) {
	this.vBCSTDest = vBCSTDest;
    }

    public BigDecimal getvICMSSTDest() {
	return vICMSSTDest;
    }

    public void setvICMSSTDest(BigDecimal vICMSSTDest) {
	this.vICMSSTDest = vICMSSTDest;
    }

    public MotivoDesICMS getMotivoDesICMS() {
	return motivoDesICMS;
    }

    public void setMotivoDesICMS(MotivoDesICMS motivoDesICMS) {
	this.motivoDesICMS = motivoDesICMS;
    }

    public BigDecimal getpBCOp() {
	return pBCOp;
    }

    public void setpBCOp(BigDecimal pBCOp) {
	this.pBCOp = pBCOp;
    }

    public String getUFST() {
	return UFST;
    }

    public void setUFST(String uFST) {
	UFST = uFST;
    }

    public BigDecimal getpCredSN() {
	return pCredSN;
    }

    public void setpCredSN(BigDecimal pCredSN) {
	this.pCredSN = pCredSN;
    }

    public BigDecimal getvCredICMSSN() {
	return vCredICMSSN;
    }

    public void setvCredICMSSN(BigDecimal vCredICMSSN) {
	this.vCredICMSSN = vCredICMSSN;
    }

    public BigDecimal getvICMSDeson() {
	return vICMSDeson;
    }

    public void setvICMSDeson(BigDecimal vICMSDeson) {
	this.vICMSDeson = vICMSDeson;
    }

    public BigDecimal getvICMSOp() {
	return vICMSOp;
    }

    public void setvICMSOp(BigDecimal vICMSOp) {
	this.vICMSOp = vICMSOp;
    }

    public BigDecimal getpDif() {
	return pDif;
    }

    public void setpDif(BigDecimal pDif) {
	this.pDif = pDif;
    }

    public BigDecimal getvICMSDif() {
	return vICMSDif;
    }

    public void setvICMSDif(BigDecimal vICMSDif) {
	this.vICMSDif = vICMSDif;
    }

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
