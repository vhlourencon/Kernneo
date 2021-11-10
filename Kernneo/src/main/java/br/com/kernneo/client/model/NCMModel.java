package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.widgets.tree.TreeNode;

@Table
@Entity(name = "ncm")
public class NCMModel extends GenericModel {

    @ManyToOne
    @JoinColumn(name = "id_ncm_pai")
    private NCMModel ncmPai;

    private String codigo;
    private String descricao;
    private Double tec;

    public String getCodigo() {
	return codigo;
    }

    public void setCodigo(String codigoNCM) {
	this.codigo = codigoNCM;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("codigo", getCodigo());
	
	
	record.setAttribute("codigoCompleto", getCododigoCompleto());
	record.setAttribute("descricao", getDescricao());
	record.setAttribute("tec", getTec());

	return record;
    }
    
    public String getCododigoCompleto() {
	String codigoCompleto = getCodigo(); 
	NCMModel ncmPai = getNcmPai();
	while (ncmPai != null) {
	    codigoCompleto = ncmPai.getCodigo() + "." +  codigoCompleto;
	    ncmPai = ncmPai.getNcmPai();
	}
	

	
	return codigoCompleto;
    }


    public TreeNode toTreeNode() {
	TreeNode treeNode = new TreeNode();

	if (getNcmPai() == null) {
	    treeNode.setParentID("null");
	} else {
	    treeNode.setParentID(getNcmPai().getId().toString());
	}

	treeNode.setID(getId().toString());
	treeNode.setTitle(getCodigo());
	treeNode.setIsFolder(true);
	treeNode.setAttribute("codigo", getCodigo());
	treeNode.setAttribute("descricao", getDescricao());
	treeNode.setAttribute("tec", getTec());
	
	
	treeNode.setAttribute("codigoCompleto", getCododigoCompleto());

	return treeNode;
    }

    public NCMModel getNcmPai() {
	return ncmPai;
    }

    public void setNcmPai(NCMModel ncmPai) {
	this.ncmPai = ncmPai;
    }

    public Double getTec() {
	return tec;
    }

    public void setTec(Double tec) {
	this.tec = tec;
    }

}
