package br.com.kernneo.client.caravana.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.model.GenericModel;

@Table
@Entity(name = "caravana_evento_catparticipante")
public class EventoCategoriaPartModel extends GenericModel {

	@ManyToOne
	@JoinColumn(name = "id_evento")
	private CaravanaEventoModel evento;

	@ManyToOne
	@JoinColumn(name = "id_local")
	private CaravanaCatParticipanteModel categoriaParticipanteModel;
	
	private Long limiteDeParticipantes; 
	
	@Column( columnDefinition = "boolean default true", nullable = false)
	private boolean permitidoCadastrarNovo=true;
	
	@Column( columnDefinition = "boolean default true", nullable = false)
	private boolean permiteSemPreCadastro=true;

	public CaravanaEventoModel getEvento() {
		return evento;
	}

	public void setEvento(CaravanaEventoModel evento) {
		this.evento = evento;
	}

	public CaravanaCatParticipanteModel getCategoriaParticipanteModel() {
		return categoriaParticipanteModel;
	}

	public void setCategoriaParticipanteModel(CaravanaCatParticipanteModel categoriaParticipanteModel) {
		this.categoriaParticipanteModel = categoriaParticipanteModel;
	}

	@Override
	public Record toRecord() {
		Record record = new Record();
		record.setAttribute("id", getId());
		if (getCategoriaParticipanteModel() != null) {
			record.setAttribute("categoriaParticipanteNome", getCategoriaParticipanteModel().getNome());
		}

		return record;
	}

	public Long getLimiteDeParticipantes() {
		return limiteDeParticipantes;
	}

	public void setLimiteDeParticipantes(Long limiteDeParticipantes) {
		this.limiteDeParticipantes = limiteDeParticipantes;
	}

	public boolean isPermitidoCadastrarNovo() {
		return permitidoCadastrarNovo;
	}

	public void setPermitidoCadastrarNovo(boolean permitidoCadastrarNovo) {
		this.permitidoCadastrarNovo = permitidoCadastrarNovo;
	}

	public boolean isPermiteSemPreCadastro() {
		return permiteSemPreCadastro;
	}

	public void setPermiteSemPreCadastro(boolean permiteSemPreCadastro) {
		this.permiteSemPreCadastro = permiteSemPreCadastro;
	}

	

}
