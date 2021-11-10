package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.smartgwt.client.data.Record;

public class SpedNotaModel extends GenericModel {
    
    private SpedC010Model spdC010;
    private ArrayList<SpedC170Model> listaDeC170;
    private String ind_oper;
    private String ind_emit;
    private String cod_part;
    private String cod_mod;
    private String cod_sit;
    private String serie;
    private String num_doc;
    private String chv_nfe;
    private String dt_doc;
    private String dt_e_s;
    private BigDecimal vl_doc;
    private String ind_pgto;
    private BigDecimal vl_desc;
    private BigDecimal vl_abat_nt;
    private BigDecimal vl_merc;
    private String ind_frt;
    private BigDecimal vl_frt;
    private BigDecimal vl_seg;
    private BigDecimal vl_out_da;
    private BigDecimal vl_bc_icms;
    private BigDecimal vl_icms;
    private BigDecimal vl_bc_icms_st;
    private BigDecimal vl_icms_st;
    private BigDecimal vl_ipi;
    private BigDecimal vl_pis;
    private BigDecimal vl_cofins;
    private BigDecimal vl_pis_st;
    private BigDecimal vl_cofins_st;
    
    public SpedNotaModel() { 
	
    }

    public SpedNotaModel(String[] linha) {
	setInd_oper(linha[2]);
	setInd_emit(linha[3]);
	setCod_part(linha[4]);
	setCod_mod(linha[5]);
	setCod_sit(linha[6]);
	setSerie(linha[7]);
	setNum_doc(linha[8]);
	setChv_nfe(linha[9]);
	setDt_doc(linha[10]);
	setDt_e_s(linha[11]);

	String vl_docStr = linha[12];
	if (vl_docStr != null && vl_docStr.trim().equals("") == false) {
	    vl_docStr = vl_docStr.replaceAll(",", ".");

	    BigDecimal bigDecimalVlDoc = new BigDecimal(Double.valueOf(vl_docStr));
	    bigDecimalVlDoc = bigDecimalVlDoc.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	    setVl_doc(bigDecimalVlDoc);

	}

	setInd_pgto(linha[13]);

	String vl_descStr = linha[14];
	if (vl_descStr != null && vl_descStr.trim().equals("") == false) {
	    vl_descStr = vl_descStr.replaceAll(",", ".");

	    BigDecimal bigDecimalDesc = new BigDecimal(Double.valueOf(vl_descStr));
	    bigDecimalDesc = bigDecimalDesc.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_desc(bigDecimalDesc);
	}

	String vl_abat_ntStr = linha[15];
	if (vl_abat_ntStr != null && vl_abat_ntStr.trim().equals("") == false) {
	    vl_abat_ntStr = vl_abat_ntStr.replaceAll(",", ".");

	    BigDecimal bigDecimalVlAbat = new BigDecimal(Double.valueOf(vl_abat_ntStr));
	    bigDecimalVlAbat = bigDecimalVlAbat.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_abat_nt(bigDecimalVlAbat);
	}

	String vl_mercStr = linha[16];
	if (vl_mercStr != null && vl_mercStr.trim().equals("") == false) {
	    vl_mercStr = vl_mercStr.replaceAll(",", ".");

	    BigDecimal bigDecimalVlMerc = new BigDecimal(Double.valueOf(vl_mercStr));
	    bigDecimalVlMerc = bigDecimalVlMerc.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_merc(bigDecimalVlMerc);
	}

	setInd_frt(linha[17]);

	String vl_frtStr = linha[18];
	if (vl_frtStr != null && vl_frtStr.trim().equals("") == false) {
	    vl_frtStr = vl_frtStr.replaceAll(",", ".");

	    BigDecimal bigDecimalVlFrt = new BigDecimal(Double.valueOf(vl_frtStr));
	    bigDecimalVlFrt = bigDecimalVlFrt.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_frt(bigDecimalVlFrt);
	}

	String vl_segStr = linha[19];
	if (vl_segStr != null && vl_segStr.trim().equals("") == false) {
	    vl_segStr = vl_segStr.replaceAll(",", ".");

	    BigDecimal bigDecimalSeg = new BigDecimal(Double.valueOf(vl_segStr));
	    bigDecimalSeg = bigDecimalSeg.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_seg(bigDecimalSeg);
	}

	String vl_out_da = linha[20];
	if (vl_out_da != null && vl_out_da.trim().equals("") == false) {
	    vl_out_da = vl_out_da.replaceAll(",", ".");

	    BigDecimal bigDecimalOutDa = new BigDecimal(Double.valueOf(vl_frtStr));
	    bigDecimalOutDa = bigDecimalOutDa.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_out_da(new BigDecimal(Double.valueOf(vl_out_da)));
	}

	String vl_bc_icmsStr = linha[21];
	if (vl_bc_icmsStr != null && vl_bc_icmsStr.trim().equals("") == false) {
	    vl_bc_icmsStr = vl_bc_icmsStr.replaceAll(",", ".");

	    BigDecimal bigDecimaBcICMS = new BigDecimal(Double.valueOf(vl_bc_icmsStr));
	    bigDecimaBcICMS = bigDecimaBcICMS.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_bc_icms(bigDecimaBcICMS);
	}

	String vl_icmsStr = linha[22];
	if (vl_icmsStr != null && vl_icmsStr.trim().equals("") == false) {
	    vl_icmsStr = vl_icmsStr.replaceAll(",", ".");
	    setVl_icms(new BigDecimal(Double.valueOf(vl_icmsStr)));
	}

	String vl_bc_ICMSstStr = linha[23];
	if (vl_bc_ICMSstStr != null && vl_bc_ICMSstStr.trim().equals("") == false) {
	    vl_bc_ICMSstStr = vl_bc_ICMSstStr.replaceAll(",", ".");

	    BigDecimal bigDecimalBcICMSst = new BigDecimal(Double.valueOf(vl_bc_ICMSstStr));
	    bigDecimalBcICMSst = bigDecimalBcICMSst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_bc_icms_st(bigDecimalBcICMSst);
	}

	String vl_icmsStStr = linha[24];
	if (vl_icmsStStr != null && vl_icmsStStr.trim().equals("") == false) {
	    vl_icmsStStr = vl_icmsStStr.replaceAll(",", ".");

	    BigDecimal bigDecimalICMSst = new BigDecimal(Double.valueOf(vl_icmsStStr));
	    bigDecimalICMSst = bigDecimalICMSst.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_icms_st(bigDecimalICMSst);
	}

	String vl_ipiStr = linha[25];
	if (vl_ipiStr != null && vl_ipiStr.trim().equals("") == false) {
	    vl_ipiStr = vl_ipiStr.replaceAll(",", ".");

	    BigDecimal bigDecimalIPI = new BigDecimal(Double.valueOf(vl_ipiStr));
	    bigDecimalIPI = bigDecimalIPI.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_ipi(bigDecimalIPI);

	}

	String vl_pisStr = linha[26];
	if (vl_pisStr != null && vl_pisStr.trim().equals("") == false) {
	    vl_pisStr = vl_pisStr.replaceAll(",", ".");

	    BigDecimal bigDecimalPis = new BigDecimal(Double.valueOf(vl_pisStr));
	    bigDecimalPis = bigDecimalPis.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_pis(bigDecimalPis);
	}

	String vl_cofinsStr = linha[27];
	if (vl_cofinsStr != null && vl_cofinsStr.trim().equals("") == false) {
	    vl_cofinsStr = vl_cofinsStr.replaceAll(",", ".");

	    BigDecimal bigDecimalCofins = new BigDecimal(Double.valueOf(vl_cofinsStr));
	    bigDecimalCofins = bigDecimalCofins.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	    setVl_cofins(bigDecimalCofins);
	}

	String vl_pisStStr = linha[28];
	if (vl_pisStStr != null && vl_pisStStr.trim().equals("") == false) {
	    vl_pisStStr = vl_pisStStr.replaceAll(",", ".");
	    
	    BigDecimal bigDecimalPisST = new BigDecimal(Double.valueOf(vl_pisStStr));
	    bigDecimalPisST = bigDecimalPisST.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	    
	    setVl_pis_st(bigDecimalPisST);
	}

	String vl_cofinsStStr = linha[29];
	if (vl_cofinsStStr != null && vl_cofinsStStr.trim().equals("") == false) {
	    vl_cofinsStStr = vl_cofinsStStr.replaceAll(",", ".");
	    
	    BigDecimal bigDecimalCofinsST = new BigDecimal(Double.valueOf(vl_cofinsStStr));
	    bigDecimalCofinsST = bigDecimalCofinsST.setScale(2, BigDecimal.ROUND_HALF_EVEN);
	    
	   
	    setVl_cofins_st(bigDecimalCofinsST);
	}

    }
    
    public void addC170(final SpedC170Model spedC170) {
	spedC170.setSpedNota(this);
  	getListaDeC170().add(spedC170);

      }

    @Override
    public Record toRecord() {
	Record record = new Record(); 
	record.setAttribute("serie", getSerie());
	record.setAttribute("numero", getNum_doc());
	record.setAttribute("chv_nfe", getChv_nfe());
	record.setAttribute("vl_doc", getVl_doc());
	record.setAttribute("vl_pis", getVl_pis());
	record.setAttribute("vl_cofins", getVl_cofins());
	
	return record;
    }

    public String getInd_oper() {
	return ind_oper;
    }

    public void setInd_oper(String ind_oper) {
	this.ind_oper = ind_oper;
    }

    public String getInd_emit() {
	return ind_emit;
    }

    public void setInd_emit(String ind_emit) {
	this.ind_emit = ind_emit;
    }

    public String getCod_part() {
	return cod_part;
    }

    public void setCod_part(String cod_part) {
	this.cod_part = cod_part;
    }

    public String getCod_mod() {
	return cod_mod;
    }

    public void setCod_mod(String cod_mod) {
	this.cod_mod = cod_mod;
    }

    public String getCod_sit() {
	return cod_sit;
    }

    public void setCod_sit(String cod_sit) {
	this.cod_sit = cod_sit;
    }

    public String getSerie() {
	return serie;
    }

    public void setSerie(String serie) {
	this.serie = serie;
    }

    public String getNum_doc() {
	return num_doc;
    }

    public void setNum_doc(String num_doc) {
	this.num_doc = num_doc;
    }

    public String getChv_nfe() {
	return chv_nfe;
    }

    public void setChv_nfe(String chv_nfe) {
	this.chv_nfe = chv_nfe;
    }

    public String getDt_doc() {
	return dt_doc;
    }

    public void setDt_doc(String dt_doc) {
	this.dt_doc = dt_doc;
    }

    public String getDt_e_s() {
	return dt_e_s;
    }

    public void setDt_e_s(String dt_e_s) {
	this.dt_e_s = dt_e_s;
    }

    public BigDecimal getVl_doc() {
	return vl_doc;
    }

    public void setVl_doc(BigDecimal vl_doc) {
	this.vl_doc = vl_doc;
    }

    public String getInd_pgto() {
	return ind_pgto;
    }

    public void setInd_pgto(String ind_pgto) {
	this.ind_pgto = ind_pgto;
    }

    public BigDecimal getVl_desc() {
	return vl_desc;
    }

    public void setVl_desc(BigDecimal vl_desc) {
	this.vl_desc = vl_desc;
    }

    public BigDecimal getVl_abat_nt() {
	return vl_abat_nt;
    }

    public void setVl_abat_nt(BigDecimal vl_abat_nt) {
	this.vl_abat_nt = vl_abat_nt;
    }

    public BigDecimal getVl_merc() {
	return vl_merc;
    }

    public void setVl_merc(BigDecimal vl_merc) {
	this.vl_merc = vl_merc;
    }

    public String getInd_frt() {
	return ind_frt;
    }

    public void setInd_frt(String ind_frt) {
	this.ind_frt = ind_frt;
    }

    public BigDecimal getVl_frt() {
	return vl_frt;
    }

    public void setVl_frt(BigDecimal vl_frt) {
	this.vl_frt = vl_frt;
    }

    public BigDecimal getVl_seg() {
	return vl_seg;
    }

    public void setVl_seg(BigDecimal vl_seg) {
	this.vl_seg = vl_seg;
    }

    public BigDecimal getVl_out_da() {
	return vl_out_da;
    }

    public void setVl_out_da(BigDecimal vl_out_da) {
	this.vl_out_da = vl_out_da;
    }

    public BigDecimal getVl_icms() {
	return vl_icms;
    }

    public void setVl_icms(BigDecimal vl_icms) {
	this.vl_icms = vl_icms;
    }

    public BigDecimal getVl_bc_icms() {
	return vl_bc_icms;
    }

    public void setVl_bc_icms(BigDecimal vl_bc_icms) {
	this.vl_bc_icms = vl_bc_icms;
    }

    public BigDecimal getVl_ipi() {
	return vl_ipi;
    }

    public void setVl_ipi(BigDecimal vl_ipi) {
	this.vl_ipi = vl_ipi;
    }

    public BigDecimal getVl_pis() {
	return vl_pis;
    }

    public void setVl_pis(BigDecimal vl_pis) {
	this.vl_pis = vl_pis;
    }

    public BigDecimal getVl_cofins() {
	return vl_cofins;
    }

    public void setVl_cofins(BigDecimal vl_cofins) {
	this.vl_cofins = vl_cofins;
    }

    public BigDecimal getVl_pis_st() {
	return vl_pis_st;
    }

    public void setVl_pis_st(BigDecimal vl_pis_st) {
	this.vl_pis_st = vl_pis_st;
    }

    public BigDecimal getVl_cofins_st() {
	return vl_cofins_st;
    }

    public void setVl_cofins_st(BigDecimal vl_cofins_st) {
	this.vl_cofins_st = vl_cofins_st;
    }

    public BigDecimal getVl_bc_icms_st() {
	return vl_bc_icms_st;
    }

    public void setVl_bc_icms_st(BigDecimal vl_bc_icms_st) {
	this.vl_bc_icms_st = vl_bc_icms_st;
    }

    public BigDecimal getVl_icms_st() {
	return vl_icms_st;
    }

    public void setVl_icms_st(BigDecimal vl_icms_st) {
	this.vl_icms_st = vl_icms_st;
    }

    public SpedC010Model getSpdC010() {
	return spdC010;
    }

    public void setSpdC010(SpedC010Model spdC010) {
	this.spdC010 = spdC010;
    }

    public ArrayList<SpedC170Model> getListaDeC170() {
	if ( listaDeC170 == null ) { 
	    listaDeC170 = new ArrayList<SpedC170Model>();
	}
	return listaDeC170;
    }

    public void setListaDeC170(ArrayList<SpedC170Model> listaDeC170) {
	this.listaDeC170 = listaDeC170;
    }

}
