package br.com.kernneo.client.model;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.smartgwt.client.data.Record;

import br.com.kernneo.client.utils.StringUtils;

@Table
@Entity(name = "regulacao")
public class RegulacaoModel extends GenericModel
	{

		@ManyToOne
		@JoinColumn(name = "id_funcionario")
		private FuncionarioModel funcionario;

		@ManyToOne
		@JoinColumn(name = "id_cidade")
		private CidadeModel cidade;

		@ManyToOne
		@JoinColumn(name = "id_hospital")
		private HospitalModel hospital;

		@ManyToOne
		@JoinColumn(name = "id_especialidade")
		private CategoriaModel especialidade;

		@Temporal(TemporalType.TIMESTAMP)
		private Date dataHora;
		private String codigo;
		private String observacao;

		@Column(columnDefinition = "BOOLEAN default FALSE")
		private boolean liminar;

		@javax.persistence.Transient
		private Boolean filtroLiminar;
		@javax.persistence.Transient
		private Date filtroDataHoraFinal;
		@javax.persistence.Transient
		private Boolean filtroResolvido;
		
		@Column(columnDefinition = "BOOLEAN default FALSE")
		private boolean resolvido=false;

		public FuncionarioModel getFuncionario() {
			return funcionario;
		}

		public void setFuncionario(FuncionarioModel funcionario) {
			this.funcionario = funcionario;
		}

		public Date getDataHora() {
			return dataHora;
		}

		public void setDataHora(Date dataHora) {
			this.dataHora = dataHora;
		}

		public CidadeModel getCidade() {
			return cidade;
		}

		public void setCidade(CidadeModel cidade) {
			this.cidade = cidade;
		}

		public HospitalModel getHospital() {
			return hospital;
		}

		public void setHospital(HospitalModel hospital) {
			this.hospital = hospital;
		}

		public CategoriaModel getEspecialidade() {
			return especialidade;
		}

		public void setEspecialidade(CategoriaModel especialidade) {
			this.especialidade = especialidade;
		}

		public String getCodigo() {
			return codigo;
		}

		public void setCodigo(String codigo) {
			this.codigo = codigo;
		}

		public String getObservacao() {
			return observacao;
		}

		public void setObservacao(String observacao) {
			this.observacao = observacao;
		}

		public boolean isLiminar() {
			return liminar;
		}

		public void setLiminar(boolean liminar) {
			this.liminar = liminar;
		}

		
		
		public Boolean getFiltroLiminar() {
			return filtroLiminar;
		}

		public void setFiltroLiminar(Boolean filtroLiminar) {
			this.filtroLiminar = filtroLiminar;
		}

		public Date getFiltroDataHoraFinal() {
			return filtroDataHoraFinal;
		}

		public void setFiltroDataHoraFinal(Date filtroDataHoraFinal) {
			this.filtroDataHoraFinal = filtroDataHoraFinal;
		}
		

		public boolean isResolvido() {
			return resolvido;
		}

		public void setResolvido(boolean resolvido) {
			this.resolvido = resolvido;
		}
		
		

		public Boolean getFiltroResolvido() {
			return filtroResolvido;
		}

		public void setFiltroResolvido(Boolean filtroResolvido) {
			this.filtroResolvido = filtroResolvido;
		}

		@Override
		public Record toRecord() {
			Record record = new Record();
			return record;
		}
		
		public Object[] toRow( ) {
			String cidade = getCidade() != null ? getCidade().getNome().toUpperCase() : "";
			String especialide = getEspecialidade() != null ? getEspecialidade().getCategoria_nome_portugues().toUpperCase() : "";
			String localDeAtendimento = getHospital() != null ? getHospital().getNome().toUpperCase() : "";
			String regulador = getUsuarioSave() != null ? getUsuarioSave().toString() : "";
			return new Object[] { getId(),regulador, getCodigo(),StringUtils.getHoraFormat(getDataHora()), cidade, especialide, localDeAtendimento,  isLiminar(), isResolvido() };
		}

	}
