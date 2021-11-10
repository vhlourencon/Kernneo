package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.kernneo.client.types.CSOSN;

@Table
@Entity(name = "produto_icms_csosn")
public class IcmsCSOSNModel extends IcmsModel {

    private CSOSN csosn;
    
    
 

    public CSOSN getCsosn() {
	return csosn;
    }
    
    
    

    public void setCsosn(CSOSN csosn) {
	this.csosn = csosn;
    }
   
}
