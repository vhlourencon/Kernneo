package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CascadeType;

import com.smartgwt.client.data.Record;

@Table
@Entity(name = "conta_bancaria")
public class ContaBancariaModel extends GenericModel
    {

        private String nome;
        @ManyToOne
        @JoinColumn(name = "id_banco")
        private BancoModel banco;

        @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
        @JoinColumn(name = "id_movimentacao_inicial")
        private MovimentacaoModel movimentacaoInicial;

        private BigDecimal chequeEspecial;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public BancoModel getBanco() {
            return banco;
        }

        public void setBanco(BancoModel banco) {
            this.banco = banco;
        }

        public MovimentacaoModel getMovimentacaoInicial() {
            if (movimentacaoInicial == null) {
                movimentacaoInicial =new MovimentacaoModel();
                movimentacaoInicial.setValor(BigDecimal.ZERO);
                // getMovimentacaoInicial().setConta(this);
            }

            return movimentacaoInicial;
        }

        public void setMovimentacaoInicial(MovimentacaoModel movimentacaoInicial) {
            this.movimentacaoInicial = movimentacaoInicial;
        }

        public BigDecimal getChequeEspecial() {
            if(chequeEspecial == null) { 
                chequeEspecial = BigDecimal.ZERO;
            }
            if (chequeEspecial != null) {
                chequeEspecial = chequeEspecial.setScale(2, RoundingMode.HALF_EVEN);
            }
            return chequeEspecial;
        }

        public void setChequeEspecial(BigDecimal chequeEspecial) {
            if (chequeEspecial != null) {
                chequeEspecial = chequeEspecial.setScale(2, RoundingMode.HALF_EVEN);
            }
            this.chequeEspecial = chequeEspecial;
        }

        @Override
        public Record toRecord() {
            Record record = new Record();

            return record;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return getNome();
        }

    }
