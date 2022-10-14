package br.com.kernneo.server.dao;

import java.util.ArrayList;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jdatepicker.DateModel;

import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.server.ConnectFactory;

public class MovimentacaoDAO extends GenericDAO<MovimentacaoModel>
    {

        public ArrayList<MovimentacaoModel> obterPorData(Date dataSelecionada, String orderBy, boolean desc) {

            String sqlMovimentacoes = "select p FROM  " + MovimentacaoModel.class.getCanonicalName() + " p WHERE " + "p.deletado = :deletado " + "AND dataHora is not null " + "AND date(dataHora)=date(:dataHora) " ;

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

            ArrayList<MovimentacaoModel> lista = (ArrayList<MovimentacaoModel>) select.getResultList();
            return lista;
        }

        public ArrayList<MovimentacaoModel> obterTodasMovimentacoesInicias() {

            String sql = "select m FROM br.com.kernneo.client.model.MovimentacaoModel m where deletado=:deletado ";
            Session session = ConnectFactory.getSession();
            Query select = session.createQuery(sql);
            select.setParameter("deletado", false);

            return (ArrayList<MovimentacaoModel>) select.getResultList();
        }

    }
