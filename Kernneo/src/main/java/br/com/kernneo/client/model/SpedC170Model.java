package br.com.kernneo.client.model;

import java.math.BigDecimal;

import com.smartgwt.client.data.Record;

public class SpedC170Model extends GenericModel {

    private SpedNotaModel spedNota;
    private String num_item;
    private String cod_item;
    private String descr_com;
    private String qtd;
    private String unid;
    private BigDecimal vl_item;
    private BigDecimal vl_desc;
    private String ind_mov;
    private String cst_icms;
    private String cfop;
    private String cod_nat;
    private BigDecimal vl_bc_icms;
    private BigDecimal aliq_icms;
    private BigDecimal vl_icms;
    private BigDecimal vl_bc_icms_st;
    private BigDecimal aliq_st;
    private BigDecimal vl_icms_st;
    private String ind_apur;
    private String cst_ipi;
    private String cod_enq;
    private BigDecimal vl_bc_ipi;
    private BigDecimal aliq_ipi;
    private BigDecimal vl_ipi;
    private String cst_pis;
    private BigDecimal vl_bc_pis;
    private BigDecimal aliq_pis;
    private BigDecimal quant_bc_pis;
    private BigDecimal aliq_pis_quant;
    private BigDecimal vl_pis;
    private String cst_cofins;
    private BigDecimal vl_bc_cofins;
    private BigDecimal aliq_cofins;
    private BigDecimal quant_bc_cofins;
    private BigDecimal aliq_cofins_quant;
    private BigDecimal vl_cofins;
    private String cod_cta;

    public SpedC170Model() {

    }

    public SpedC170Model(String[] linha) {
	setNum_item(linha[2]);
	setCod_item(linha[3]);
	setDescr_com(linha[4]);
	setQtd(linha[5]);
	setUnid(linha[6]);
	setVl_item(getValorEmBigDecimal(linha[7]));
	setVl_desc(getValorEmBigDecimal(linha[8]));
	setInd_mov(linha[9]);
	setCst_icms(linha[10]);
	setCfop(linha[11]);
	setCod_nat(linha[12]);
	setVl_bc_icms(getValorEmBigDecimal(linha[13]));
	setAliq_icms(getValorEmBigDecimal(linha[14]));
	setVl_icms(getValorEmBigDecimal(linha[15]));
	setVl_bc_icms_st(getValorEmBigDecimal(linha[16]));
	setAliq_st(getValorEmBigDecimal(linha[17]));
	setVl_icms_st(getValorEmBigDecimal(linha[18]));
	setInd_apur(linha[19]);
	setCst_ipi(linha[20]);
	setCod_enq(linha[21]);
	setVl_bc_ipi(getValorEmBigDecimal(linha[22]));
	setAliq_ipi(getValorEmBigDecimal(linha[23]));
	setVl_ipi(getValorEmBigDecimal(linha[24]));
	setCst_pis(linha[25]);
	setVl_bc_pis(getValorEmBigDecimal(linha[26]));
	setAliq_pis(getValorEmBigDecimal(linha[27]));
	setQuant_bc_pis(getValorEmBigDecimal(linha[28]));
	setAliq_pis_quant(getValorEmBigDecimal(linha[29]));
	setVl_pis(getValorEmBigDecimal(linha[30]));
	setCst_cofins(linha[31]);
	setVl_bc_cofins(getValorEmBigDecimal(linha[32]));
	setAliq_cofins(getValorEmBigDecimal(linha[33]));
	setQuant_bc_cofins(getValorEmBigDecimal(linha[34]));
	setAliq_cofins_quant(getValorEmBigDecimal(linha[35]));
	setVl_cofins(getValorEmBigDecimal(linha[36]));
	setCod_cta(linha[37]);
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("num_item", getNum_item());
	record.setAttribute("cod_item", getCod_item());
	record.setAttribute("descr_compl", getDescr_com());
	record.setAttribute("qtd", getQtd());
	record.setAttribute("unid", getUnid());
	record.setAttribute("vl_item", getVl_cofins());
	record.setAttribute("vl_desc", getVl_desc());
	record.setAttribute("ind_mov", getInd_mov());
	record.setAttribute("cst_icms", getCst_icms());
	record.setAttribute("cfop", getCfop());

	

	return record;
    }

    public SpedNotaModel getSpedNota() {
	return spedNota;
    }

    public void setSpedNota(SpedNotaModel spedNota) {
	this.spedNota = spedNota;
    }

    public String getNum_item() {
	return num_item;
    }

    public void setNum_item(String num_item) {
	this.num_item = num_item;
    }

    public String getCod_item() {
	return cod_item;
    }

    public void setCod_item(String cod_item) {
	this.cod_item = cod_item;
    }

    public String getDescr_com() {
	return descr_com;
    }

    public void setDescr_com(String descr_com) {
	this.descr_com = descr_com;
    }

    public String getQtd() {
	return qtd;
    }

    public void setQtd(String qtd) {
	this.qtd = qtd;
    }

    public String getUnid() {
	return unid;
    }

    public void setUnid(String unid) {
	this.unid = unid;
    }

    public BigDecimal getVl_item() {
	return vl_item;
    }

    public void setVl_item(BigDecimal vl_item) {
	this.vl_item = vl_item;
    }

    public BigDecimal getVl_desc() {
	return vl_desc;
    }

    public void setVl_desc(BigDecimal vl_desc) {
	this.vl_desc = vl_desc;
    }

    public String getInd_mov() {
	return ind_mov;
    }

    public void setInd_mov(String ind_mov) {
	this.ind_mov = ind_mov;
    }

    public String getCst_icms() {
	return cst_icms;
    }

    public void setCst_icms(String cst_icms) {
	this.cst_icms = cst_icms;
    }

    public String getCfop() {
	return cfop;
    }

    public void setCfop(String cfop) {
	this.cfop = cfop;
    }

    public String getCod_nat() {
	return cod_nat;
    }

    public void setCod_nat(String cod_nat) {
	this.cod_nat = cod_nat;
    }

    public BigDecimal getVl_bc_icms() {
	return vl_bc_icms;
    }

    public void setVl_bc_icms(BigDecimal vl_bc_icms) {
	this.vl_bc_icms = vl_bc_icms;
    }

    public BigDecimal getAliq_icms() {
	return aliq_icms;
    }

    public void setAliq_icms(BigDecimal aliq_icms) {
	this.aliq_icms = aliq_icms;
    }

    public BigDecimal getVl_icms() {
	return vl_icms;
    }

    public void setVl_icms(BigDecimal vl_icms) {
	this.vl_icms = vl_icms;
    }

    public BigDecimal getVl_bc_icms_st() {
	return vl_bc_icms_st;
    }

    public void setVl_bc_icms_st(BigDecimal vl_bc_icms_st) {
	this.vl_bc_icms_st = vl_bc_icms_st;
    }

    public BigDecimal getAliq_st() {
	return aliq_st;
    }

    public void setAliq_st(BigDecimal aliq_st) {
	this.aliq_st = aliq_st;
    }

    public BigDecimal getVl_icms_st() {
	return vl_icms_st;
    }

    public void setVl_icms_st(BigDecimal vl_icms_st) {
	this.vl_icms_st = vl_icms_st;
    }

    public String getInd_apur() {
	return ind_apur;
    }

    public void setInd_apur(String ind_apur) {
	this.ind_apur = ind_apur;
    }

    public String getCst_ipi() {
	return cst_ipi;
    }

    public void setCst_ipi(String cst_ipi) {
	this.cst_ipi = cst_ipi;
    }

    public String getCod_enq() {
	return cod_enq;
    }

    public void setCod_enq(String cod_enq) {
	this.cod_enq = cod_enq;
    }

    public BigDecimal getVl_bc_ipi() {
	return vl_bc_ipi;
    }

    public void setVl_bc_ipi(BigDecimal vl_bc_ipi) {
	this.vl_bc_ipi = vl_bc_ipi;
    }

    public BigDecimal getAliq_ipi() {
	return aliq_ipi;
    }

    public void setAliq_ipi(BigDecimal aliq_ipi) {
	this.aliq_ipi = aliq_ipi;
    }

    public BigDecimal getVl_ipi() {
	return vl_ipi;
    }

    public void setVl_ipi(BigDecimal vl_ipi) {
	this.vl_ipi = vl_ipi;
    }

    public String getCst_pis() {
	return cst_pis;
    }

    public void setCst_pis(String cst_pis) {
	this.cst_pis = cst_pis;
    }

    public BigDecimal getVl_bc_pis() {
	return vl_bc_pis;
    }

    public void setVl_bc_pis(BigDecimal vl_bc_pis) {
	this.vl_bc_pis = vl_bc_pis;
    }

    public BigDecimal getAliq_pis() {
	return aliq_pis;
    }

    public void setAliq_pis(BigDecimal aliq_pis) {
	this.aliq_pis = aliq_pis;
    }

    public BigDecimal getQuant_bc_pis() {
	return quant_bc_pis;
    }

    public void setQuant_bc_pis(BigDecimal quant_bc_pis) {
	this.quant_bc_pis = quant_bc_pis;
    }

    public BigDecimal getAliq_pis_quant() {
	return aliq_pis_quant;
    }

    public void setAliq_pis_quant(BigDecimal aliq_pis_quant) {
	this.aliq_pis_quant = aliq_pis_quant;
    }

    public BigDecimal getVl_pis() {
	return vl_pis;
    }

    public void setVl_pis(BigDecimal vl_pis) {
	this.vl_pis = vl_pis;
    }

    public String getCst_cofins() {
	return cst_cofins;
    }

    public void setCst_cofins(String cst_cofins) {
	this.cst_cofins = cst_cofins;
    }

    public BigDecimal getVl_bc_cofins() {
	return vl_bc_cofins;
    }

    public void setVl_bc_cofins(BigDecimal vl_bc_cofins) {
	this.vl_bc_cofins = vl_bc_cofins;
    }

    public BigDecimal getAliq_cofins() {
	return aliq_cofins;
    }

    public void setAliq_cofins(BigDecimal aliq_cofins) {
	this.aliq_cofins = aliq_cofins;
    }

    public BigDecimal getQuant_bc_cofins() {
	return quant_bc_cofins;
    }

    public void setQuant_bc_cofins(BigDecimal quant_bc_cofins) {
	this.quant_bc_cofins = quant_bc_cofins;
    }

    public BigDecimal getAliq_cofins_quant() {
	return aliq_cofins_quant;
    }

    public void setAliq_cofins_quant(BigDecimal aliq_cofins_quant) {
	this.aliq_cofins_quant = aliq_cofins_quant;
    }

    public BigDecimal getVl_cofins() {
	return vl_cofins;
    }

    public void setVl_cofins(BigDecimal vl_cofins) {
	this.vl_cofins = vl_cofins;
    }

    public String getCod_cta() {
	return cod_cta;
    }

    public void setCod_cta(String cod_cta) {
	this.cod_cta = cod_cta;
    }

}
