package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.smartgwt.client.data.Record;
import com.smartgwt.client.docs.MobileDevelopment;

import br.com.kernneo.client.types.FormaDePagamento;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.client.types.MovimentacaoRecorrenciaTypes;
import br.com.kernneo.client.types.MovimentacaoTypes;

@Table
@Entity(name = "movimentacao")
public class MovimentacaoModel extends GenericModel
    {

        @ManyToOne
        @JoinColumn(name = "id_movimentacao_pai")
        private MovimentacaoModel movimentacaoPai;

        @ManyToOne
        @JoinColumn(name = "id_categoria")
        private CategoriaModel categoria;

        @ManyToOne
        @JoinColumn(name = "id_conta")
        private ContaBancariaModel conta;

        @ManyToOne
        @JoinColumn(name = "id_fornecedor")
        private FornecedorModel fornecedor;

        @ManyToOne
        @JoinColumn(name = "id_cliente")
        private ClienteModel cliente;

        private BigDecimal valor;

        @Temporal(TemporalType.DATE)
        private Date dataHora;
        @Temporal(TemporalType.DATE)
        private Date dataHoraExecutado;
        private String descricao;

       
        private int recorrenciaQuantidade=1;
        private int recorrenciaParcela =1; 
        @Enumerated(EnumType.STRING)
        private MovimentacaoRecorrenciaTypes recorrenciaTipo;
        
        
       

        public Date getDataHoraExecutado() {
            return dataHoraExecutado;
        }

        public void setDataHoraExecutado(Date dataHoraExecutado) {
            this.dataHoraExecutado = dataHoraExecutado;
        }

        private boolean contaMovimentacaoInicial;

        private boolean executado;
        
        @Transient
        private Boolean filtroExecutado;

        @Enumerated(EnumType.STRING)
        private MovimentacaoFinanceiraTypes tipo;

        public MovimentacaoModel getMovimentacaoPai() {
            return movimentacaoPai;
        }

        public void setMovimentacaoPai(MovimentacaoModel movimentacaoPai) {
            this.movimentacaoPai = movimentacaoPai;
        }

        public CategoriaModel getCategoria() {
            return categoria;
        }

        public void setCategoria(CategoriaModel categoria) {
            this.categoria = categoria;
        }

        public ContaBancariaModel getConta() {
            return conta;
        }

        public void setConta(ContaBancariaModel conta) {
            this.conta = conta;
        }

        public FornecedorModel getFornecedor() {
            return fornecedor;
        }

        public void setFornecedor(FornecedorModel fornecedor) {
            this.fornecedor = fornecedor;
        }

        public BigDecimal getValor() {
            if (valor != null) {
                valor = valor.setScale(2, RoundingMode.HALF_EVEN);
            }

            return valor;
        }

        public void setValor(BigDecimal valor) {
            if (valor != null) {
                valor = valor.setScale(2, RoundingMode.HALF_EVEN);
            }
            this.valor = valor;
        }

        public Date getDataHora() {
            return dataHora;
        }

        public void setDataHora(Date dataHora) {
            this.dataHora = dataHora;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        

        

        public MovimentacaoFinanceiraTypes getTipo() {
            return tipo;
        }

        public void setTipo(MovimentacaoFinanceiraTypes tipo) {
            this.tipo = tipo;
        }

        @Override
        public Record toRecord() {
            // TODO Auto-generated method stub
            return null;
        }

        public boolean isContaMovimentacaoInicial() {
            return contaMovimentacaoInicial;
        }

        public ClienteModel getCliente() {
            return cliente;
        }

        public void setCliente(ClienteModel cliente) {
            this.cliente = cliente;
        }

        public void setContaMovimentacaoInicial(boolean contaMovimentacaoInicial) {
            this.contaMovimentacaoInicial = contaMovimentacaoInicial;
        }

        public boolean isExecutado() {
            return executado;
        }

        public void setExecutado(boolean executado) {
            this.executado = executado;
        }

        public Boolean getFiltroExecutado() {
            return filtroExecutado;
        }

        public void setFiltroExecutado(Boolean filtroExecutado) {
            this.filtroExecutado = filtroExecutado;
        }

		public int getRecorrenciaQuantidade() {
			return recorrenciaQuantidade;
		}

		public void setRecorrenciaQuantidade(int recorrenciaQuantidade) {
			this.recorrenciaQuantidade = recorrenciaQuantidade;
		}

		public MovimentacaoRecorrenciaTypes getRecorrenciaTipo() {
			return recorrenciaTipo;
		}

		public void setRecorrenciaTipo(MovimentacaoRecorrenciaTypes recorrenciaTipo) {
			this.recorrenciaTipo = recorrenciaTipo;
		}

		public int getRecorrenciaParcela() {
			return recorrenciaParcela;
		}

		public void setRecorrenciaParcela(int recorrenciaParcela) {
			this.recorrenciaParcela = recorrenciaParcela;
		}

		
     
		
		
        

    }
