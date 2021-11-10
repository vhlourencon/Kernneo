package br.com.kernneo.client.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "produto_cofins")
public class COFINSModel extends GenericModel {

    @OneToOne
    @JoinColumn(name = "id_produto")
    private ProdutoModel produto;

   

    @Override
    public Record toRecord() {
	// TODO Auto-generated method stub
	return null;
    }

}
