package br.com.kernneo.client.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "cidades")
public class CidadeModel extends GenericModel {

    @Column(name="codigo_ibge")
    private Integer codigoIbge;
    private Integer populacao_2010;
    private Double densidade_demo;
    private String gentilico; 
    private Double area; 
    private String nome;

    @ManyToOne
    @JoinColumn(name = "estado_id")
    private EstadoModel estado;
    
    

    public Integer getPopulacao_2010() {
		return populacao_2010;
	}

	public void setPopulacao_2010(Integer populacao_2010) {
		this.populacao_2010 = populacao_2010;
	}

	public Double getDensidade_demo() {
		return densidade_demo;
	}

	public void setDensidade_demo(Double densidade_demo) {
		this.densidade_demo = densidade_demo;
	}

	public String getGentilico() {
		return gentilico;
	}

	public void setGentilico(String gentilico) {
		this.gentilico = gentilico;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getNome() {
	return nome;
    }

    public void setNome(String nome) {
	this.nome = nome;
    }

    public EstadoModel getEstado() {
	return estado;
    }

    public void setEstado(EstadoModel estado) {
	this.estado = estado;
    }

    @Override
    public Record toRecord() {
	Record record = new Record();
	record.setAttribute("id", getId());
	record.setAttribute("nome", getNome());
	record.setAttribute("codigoIbge", getCodigoIbge());

	if (estado != null) {

	    record.setAttribute("estadoNome", getEstado().getNome());
	    record.setAttribute("estadoSigla", getEstado().getSigla());
	}
	return record;
    }

    public Integer getCodigoIbge() {
	return codigoIbge;
    }

    public void setCodigoIbge(Integer codigoIbge) {
	this.codigoIbge = codigoIbge;
    }

}
