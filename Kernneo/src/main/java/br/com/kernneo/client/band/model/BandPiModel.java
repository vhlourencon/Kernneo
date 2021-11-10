package br.com.kernneo.client.band.model;

import java.awt.image.BandedSampleModel;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.kernneo.client.model.GenericModel;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "band_pi")
public class BandPiModel extends GenericModel {

	private String codigo;

	@ManyToOne
	@JoinColumn(name = "id_band_empresa")
	private BandEmpresaModel empresa;

	@ManyToOne
	@JoinColumn(name = "id_band_agencia")
	private BandAgenciaModel agencia;

	private String rp;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public BandEmpresaModel getEmpresa() {
		return empresa;
	}

	public void setEmpresa(BandEmpresaModel empresa) {
		this.empresa = empresa;
	}

	public BandAgenciaModel getAgencia() {
		return agencia;
	}

	public void setAgencia(BandAgenciaModel agencia) {
		this.agencia = agencia;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		record.setAttribute("codigo", getCodigo());
		record.setAttribute("rp", getRp());

		if (getAgencia() != null) {
			record.setAttribute("agenciaNome", getAgencia().getNome());
		}

		if (getEmpresa() != null) {
			record.setAttribute("empresaNome", getEmpresa().getNome());
		}

		return record;
	}

	public String getRp() {
		return rp;
	}

	public void setRp(String rp) {
		this.rp = rp;
	}

}
