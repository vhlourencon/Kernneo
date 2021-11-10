package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.kernneo.client.types.CST;

@Table
@Entity(name = "produto_icms_cst")
public class IcmsCSTModel extends IcmsModel {

    private CST cst;

    public CST getCst() {
	return cst;
    }

    public void setCst(CST cst) {
	this.cst = cst;
    }

}
