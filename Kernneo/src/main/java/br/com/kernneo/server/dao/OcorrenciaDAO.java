package br.com.kernneo.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jdatepicker.DateModel;

import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.server.ConnectFactory;

public class OcorrenciaDAO extends GenericDAO<OcorrenciaModel>
    {

        public List<OcorrenciaModel> obterPorData(Date dataSelecionada, String orderBy, boolean desc) {

            String sqlMovimentacoes = "select p FROM  " + OcorrenciaModel.class.getCanonicalName() + " p WHERE " + "p.deletado = :deletado " + "AND dataHora is not null " + "AND date(dataHora)=date(:dataHora) ";

            if (orderBy != null) {
                sqlMovimentacoes += " order by " + orderBy;
                if (desc) {
                    sqlMovimentacoes += " desc";
                } else {
                    sqlMovimentacoes += " asc";
                }
            } else {
                sqlMovimentacoes += " order by id asc";
            }

            Session session = ConnectFactory.getSession();

            Query select = session.createQuery(sqlMovimentacoes);
            select.setParameter("deletado", false);
            select.setParameter("dataHora", dataSelecionada);

            return (ArrayList<OcorrenciaModel>) select.getResultList();

        }

      

    }
