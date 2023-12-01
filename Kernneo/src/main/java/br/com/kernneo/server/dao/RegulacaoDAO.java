package br.com.kernneo.server.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.jdatepicker.DateModel;
import org.jfree.data.time.Hour;

import br.com.kernneo.client.model.BairroModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.OcorrenciaModel;
import br.com.kernneo.client.model.RegulacaoModel;
import br.com.kernneo.client.model.UsuarioModel;
import br.com.kernneo.server.ConnectFactory;

public class RegulacaoDAO extends GenericDAO<RegulacaoModel>
    {

		
		public void resolverRegulacoesAnteriores(RegulacaoModel model) { 
			String sql = "update " + RegulacaoModel.class.getCanonicalName() +"  s set s.resolvido=1 where s.codigo=:codigo";
			
			
			
			  Session session = ConnectFactory.getSession();
			  Query select = session.createQuery(sql);
	        
	          select.setParameter("codigo", model.getCodigo());
	          
	          select.executeUpdate(); 
		}
        public List<RegulacaoModel> obterPorDataEusuario(Date dataSelecionada,FuncionarioModel funcionarioModel,  String orderBy, boolean desc) {

            String sqlMovimentacoes = "select p FROM  " + RegulacaoModel.class.getCanonicalName() 
            		+ " p WHERE " + "p.deletado = :deletado " 
            		+ "AND dataHora is not null " 
            		+ "AND date(dataHora)=date(:dataHora) "
            		;

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

            return (ArrayList<RegulacaoModel>) select.getResultList();

        }
        
        public List<RegulacaoModel> obterPendente( String orderBy, boolean desc) {

            String sqlMovimentacoes = "select p FROM  " + RegulacaoModel.class.getCanonicalName() 
            		+ " p WHERE " + "p.deletado = :deletado " 
            		+ "AND dataHora is not null " 
            		+ "AND resolvido=:resolvido " 
            		+ "AND UNIX_TIMESTAMP(dataHora)<=UNIX_TIMESTAMP(:dataHora) ";
            		

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



            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int hora = calendar.get(Calendar.HOUR_OF_DAY);
            if(hora >= 7 && hora < 19) { 
            	calendar.set(Calendar.HOUR_OF_DAY, 7);
            } else if( hora >= 19) { 
            	calendar.set(Calendar.HOUR_OF_DAY, 19);
            } else { 
            	calendar.add( Calendar.HOUR, ((5 + hora)  * (-1)));
            }
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            
            Session session = ConnectFactory.getSession();
            Query select = session.createQuery(sqlMovimentacoes);
            select.setParameter("deletado", false);
            select.setParameter("dataHora", calendar.getTime());
            select.setParameter("resolvido", false);

            
            

            return (ArrayList<RegulacaoModel>) select.getResultList();

        }
        
        public RegulacaoModel obterPorCodigo(String codigo)  {
        	Session session = ConnectFactory.getSession();

        	String sql = "from " + RegulacaoModel.class.getCanonicalName() + " where codigo=:codigo and deletado=:deletado";

        	Query select = session.createQuery(sql);
        	select.setParameter("codigo", codigo);
        	select.setParameter("deletado", false);

        	RegulacaoModel usuarioModel = (RegulacaoModel) select.uniqueResult();

        	return usuarioModel;

            }

      

    }
