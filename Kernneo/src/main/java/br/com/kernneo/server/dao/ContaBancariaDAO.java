package br.com.kernneo.server.dao;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.kernneo.client.exception.ContaBancariaException;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.negocio.ContaBancaria;

public class ContaBancariaDAO extends GenericDAO<ContaBancariaModel>
    {

        public ArrayList<ContaBancariaModel> obterTodasContasComPoiscao(Date dataSelecionada) throws Exception {
            ArrayList<ContaBancariaModel> listaDeContas = new ContaBancaria().obterTodos(ContaBancariaModel.class);
            if (listaDeContas != null) {
                Session session = ConnectFactory.getSession();
                Query select = session.createQuery("select  new " + PosicaoBancariaModel.class.getCanonicalName() + "(sum(valor),conta)   FROM " + MovimentacaoModel.class.getCanonicalName() + " p WHERE " + "p.deletado = :deletado " + "AND dataHora is not null " + "AND date(dataHora)<date(:dataHora) AND executado=:executado group by id_conta");
                select.setParameter("deletado", false);
                select.setParameter("dataHora", dataSelecionada);
                select.setParameter("executado", true);

                ArrayList<PosicaoBancariaModel> listaDePosicao = (ArrayList<PosicaoBancariaModel>) select.getResultList();
                if (listaDePosicao != null) {
                    for (ContaBancariaModel contaBancariaModel : listaDeContas) {
                        for (PosicaoBancariaModel posicaoBancariaModel : listaDePosicao) {
                            if (posicaoBancariaModel.getContaBancariaSelecionada().getId().compareTo(contaBancariaModel.getId()) == 0) {
                                contaBancariaModel.setPosicaoAux(posicaoBancariaModel);
                            }
                        }
                    }
                }
            }
            return listaDeContas;
        }

      

    }
