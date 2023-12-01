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
@Entity(name = "solicitacao_item")
public class SolicitacaoItemModel extends GenericModel
	{

		
		@ManyToOne
		@JoinColumn(name = "id_solicitacao")
		private SolicitacaoModel solicitacao;

		@ManyToOne
		@JoinColumn(name = "id_equipamento")
		private DepartamentoModel equipamento;

		@ManyToOne
		@JoinColumn(name = "id_unidade")
		private HospitalModel unidade;

		@ManyToOne
		@JoinColumn(name = "id_documento")
		private CategoriaModel documento;

		
		private Integer quantidade; 
		

		
		
		
		
		

		public DepartamentoModel getEquipamento() {
			return equipamento;
		}

		public void setEquipamento(DepartamentoModel equipamento) {
			this.equipamento = equipamento;
		}

		public HospitalModel getUnidade() {
			return unidade;
		}

		public void setUnidade(HospitalModel unidade) {
			this.unidade = unidade;
		}

		public CategoriaModel getDocumento() {
			return documento;
		}

		public void setDocumento(CategoriaModel documento) {
			this.documento = documento;
		}

		public Integer getQuantidade() {
			return quantidade;
		}

		public void setQuantidade(Integer quantidade) {
			this.quantidade = quantidade;
		}
		
		

		
		
		

	

		public SolicitacaoModel getSolicitacao() {
			return solicitacao;
		}

		public void setSolicitacao(SolicitacaoModel solicitacao) {
			this.solicitacao = solicitacao;
		}

		@Override
		public Record toRecord() {
			Record record = new Record();
			return record;
		}
		
		public Object[] toRow( ) {
			String documento = getDocumento() != null ? getDocumento().getCategoria_nome_portugues().toUpperCase() : "";
			String equipamento = getEquipamento() != null ? getEquipamento().getNome().toUpperCase() : "";
			String unidade = getUnidade() != null ? getUnidade().getNome().toUpperCase() : "";
			String quantidade = getQuantidade() != null ? getQuantidade().toString() : "0";
			String compilado = getSolicitacao() != null ? getSolicitacao().getNome().toUpperCase() : "";
			
			return new Object[] { getId(),compilado, documento, unidade, equipamento, quantidade};
		}

	}
