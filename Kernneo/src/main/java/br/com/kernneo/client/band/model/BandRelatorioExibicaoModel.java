package br.com.kernneo.client.band.model;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;


@Table
@Entity(name = "relatorio_exibicao")
public class BandRelatorioExibicaoModel extends GenericModel {
	
	@ManyToOne
	@JoinColumn(name = "id_band_pi")
	private BandPiModel bandPi; 

	private Date dataInicial;
	private Date dataFinal;
	private String codigoPi;
	
	
	
	
	
	@Transient
	private ArrayList<BandExibicaoModel> listaDeExibidos=new ArrayList<BandExibicaoModel>(); 

	
	
	
	

	
	
	

	public BandPiModel getBandPi() {
		return bandPi;
	}









	public void setBandPi(BandPiModel bandPi) {
		this.bandPi = bandPi;
	}









	public Date getDataInicial() {
		return dataInicial;
	}









	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}









	public Date getDataFinal() {
		return dataFinal;
	}









	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}









	public String getCodigoPi() {
		return codigoPi;
	}









	public void setCodigoPi(String codigoPi) {
		this.codigoPi = codigoPi;
	}









	public ArrayList<BandExibicaoModel> getListaDeExibidos() {
		return listaDeExibidos;
	}









	public void setListaDeExibidos(ArrayList<BandExibicaoModel> listaDeExibidos) {
		this.listaDeExibidos = listaDeExibidos;
	}









	@Override
	public Record toRecord() {
		// TODO Auto-generated method stub
		return null;
	}

}
