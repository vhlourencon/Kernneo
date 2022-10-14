package br.com.kernneo.server.negocio;

import java.util.ArrayList;
import java.util.Date;

import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.server.dao.ContaBancariaDAO;
import br.com.kernneo.server.dao.MovimentacaoDAO;

public class PosicaoFinanceira
    {

        public PosicaoFinanceiraModel obterPosicoesFinanceira(Date dataSelecionada, String orderBy, boolean desc) throws Exception {
            PosicaoFinanceiraModel posicaoFinanceiraModel = new PosicaoFinanceiraModel();

            ArrayList<MovimentacaoModel> listaDeMovimentacao = new MovimentacaoDAO().obterPorData(dataSelecionada, orderBy, desc);
            posicaoFinanceiraModel.setListaDeMovimentacoes(listaDeMovimentacao);
            posicaoFinanceiraModel.setListaDeContasBancarias(new ContaBancariaDAO().obterTodasContasComPoiscao(dataSelecionada));
            return posicaoFinanceiraModel;
        }

    }
