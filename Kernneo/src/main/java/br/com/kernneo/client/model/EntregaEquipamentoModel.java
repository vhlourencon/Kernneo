package br.com.kernneo.client.model;

import java.beans.Transient;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.smartgwt.client.data.Record;

@Table
@Entity(name = "entrega_equipamento")
public class EntregaEquipamentoModel extends GenericModel
	{

		


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
		private Date data; 
		private String observacao; 
		
		

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
		

		public Date getData() {
			return data;
		}

		public void setData(Date data) {
			this.data = data;
		}

		public String getObservacao() {
			return observacao;
		}

		public void setObservacao(String observacao) {
			this.observacao = observacao;
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
			//String compilado = getCompilado() != null ? getCompilado().getNome().toUpperCase() : "";
			
			return new Object[] { getId(),"", documento, unidade, equipamento, quantidade};
		}

	}
