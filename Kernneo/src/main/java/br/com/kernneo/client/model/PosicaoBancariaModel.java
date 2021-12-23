package br.com.kernneo.client.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;

public class PosicaoBancariaModel
    {

        private Date dataSelecionada;
        private ContaBancariaModel contaBancariaSelecionada;
        private BigDecimal saldo;

        private ArrayList<MovimentacaoModel> listaDeMovimentacao;

        public BigDecimal getCalcSaldoInicial() {
            BigDecimal bigDecimalSaldo = new BigDecimal(0.0);
            if (getContaBancariaSelecionada() != null && getContaBancariaSelecionada().getMovimentacaoInicial() != null) {
                bigDecimalSaldo = bigDecimalSaldo.add(getContaBancariaSelecionada().getMovimentacaoInicial().getValor());
            }
            bigDecimalSaldo = bigDecimalSaldo.add(getSaldo());
            return bigDecimalSaldo;
        }

        public BigDecimal getCalcSaldoFinal() {
            BigDecimal bigDecimalSaldoAux = getCalcSaldoInicial();
            if (getListaDeMovimentacao() != null) {

                for (MovimentacaoModel movimentacaoModel : listaDeMovimentacao) {
                    if (movimentacaoModel.isExecutado()) {
                        bigDecimalSaldoAux = bigDecimalSaldoAux.add(movimentacaoModel.getValor());
                    }
                }

            }
            return bigDecimalSaldoAux;
        }

        public Date getDataSelecionada() {
            return dataSelecionada;
        }

        public void setDataSelecionada(Date dataSelecionada) {
            this.dataSelecionada = dataSelecionada;
        }

        public ArrayList<MovimentacaoModel> getListaDeMovimentacao() {
            return listaDeMovimentacao;
        }

        public void setListaDeMovimentacao(ArrayList<MovimentacaoModel> listaDeMovimentacao) {
            this.listaDeMovimentacao = listaDeMovimentacao;
        }

        public ContaBancariaModel getContaBancariaSelecionada() {
            return contaBancariaSelecionada;
        }

        public void setContaBancariaSelecionada(ContaBancariaModel contaBancariaSelecionada) {
            this.contaBancariaSelecionada = contaBancariaSelecionada;
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public void setSaldo(BigDecimal saldo) {
            this.saldo = saldo;
        }

    }
