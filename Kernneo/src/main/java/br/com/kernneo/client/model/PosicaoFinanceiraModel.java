package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;

public class PosicaoFinanceiraModel
    {

        private Date dataSelecionada;
        private ArrayList<ContaBancariaModel> listaDeContasBancarias;
        private ArrayList<MovimentacaoModel>  listaDeMovimentacoes;
        
        
        

        public BigDecimal getSaldoInicial() {
            BigDecimal bigDecimalSaldoInicial = new BigDecimal(0.0);
            for (ContaBancariaModel contaBancariaModel : getListaDeContasBancarias()) {
                bigDecimalSaldoInicial = bigDecimalSaldoInicial.add(contaBancariaModel.getSaldoInicial());
            }
            return bigDecimalSaldoInicial;
        }

        public BigDecimal getSaldoInicialComPosicaoBancaria() {
            BigDecimal bigDecimalSaldoInicial = new BigDecimal(0.0);
            for (ContaBancariaModel contaBancariaModel : getListaDeContasBancarias()) {
                bigDecimalSaldoInicial = bigDecimalSaldoInicial.add(contaBancariaModel.getPosicaoAux().getSaldo()).add(contaBancariaModel.getSaldoInicial());
            }
            return bigDecimalSaldoInicial;
        }
        
        public BigDecimal getSaldoAcumuladoMensal() {
            BigDecimal bigDecimalSaldoInicial = new BigDecimal(0.0);
            for (ContaBancariaModel contaBancariaModel : getListaDeContasBancarias()) {
                bigDecimalSaldoInicial = bigDecimalSaldoInicial.add(contaBancariaModel.getPosicaoAux().getSaldoAcumuladoMensal()).add(contaBancariaModel.getSaldoInicial());
            }
            return bigDecimalSaldoInicial;
        }
        
        public BigDecimal getSaldoAcumuladoTotal() {
            BigDecimal bigDecimalSaldoInicial = new BigDecimal(0.0);
            for (ContaBancariaModel contaBancariaModel : getListaDeContasBancarias()) {
                bigDecimalSaldoInicial = bigDecimalSaldoInicial.add(contaBancariaModel.getPosicaoAux().getSaldoAcumuladoTotal()).add(contaBancariaModel.getSaldoInicial());
            }
            return bigDecimalSaldoInicial;
        }
        
        

        public BigDecimal getSaldoFinalExecutado() {
            BigDecimal bigDecimalSaldoFinal = getSaldoInicialComPosicaoBancaria();   
            
            for (MovimentacaoModel movimentacaoModel : getListaDeMovimentacoes()) {
                if(movimentacaoModel.isExecutado()) {
                bigDecimalSaldoFinal = bigDecimalSaldoFinal.add(movimentacaoModel.getValor());
                }
            }
            return bigDecimalSaldoFinal;
        }

        public Date getDataSelecionada() {
            return dataSelecionada;
        }

        public void setDataSelecionada(Date dataSelecionada) {
            this.dataSelecionada = dataSelecionada;
        }

        public ArrayList<ContaBancariaModel> getListaDeContasBancarias() {
            if (listaDeContasBancarias == null) {
                listaDeContasBancarias = new ArrayList<ContaBancariaModel>();
            }
            return listaDeContasBancarias;
        }

        public void setListaDeContasBancarias(ArrayList<ContaBancariaModel> listaDeContasBancarias) {
            this.listaDeContasBancarias = listaDeContasBancarias;
        }

        public ArrayList<MovimentacaoModel> getListaDeMovimentacoes() {
            return listaDeMovimentacoes;
        }

        public void setListaDeMovimentacoes(ArrayList<MovimentacaoModel> listaDeMovimentacoes) {
            this.listaDeMovimentacoes = listaDeMovimentacoes;
        }
        
        
        
        
        

    }
