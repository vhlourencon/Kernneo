import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import br.com.kernneo.client.band.model.BandProgramaModel;
import br.com.kernneo.client.exception.ClienteException;
import br.com.kernneo.client.model.ClienteModel;
import br.com.kernneo.client.model.ContaBancariaModel;
import br.com.kernneo.client.model.FuncionarioModel;
import br.com.kernneo.client.model.MovimentacaoModel;
import br.com.kernneo.client.model.PosicaoBancariaModel;
import br.com.kernneo.client.model.PosicaoFinanceiraModel;
import br.com.kernneo.client.types.MovimentacaoFinanceiraTypes;
import br.com.kernneo.server.Comando;
import br.com.kernneo.server.Conexao;
import br.com.kernneo.server.ConnectFactory;
import br.com.kernneo.server.dao.FuncionarioDAO;
import br.com.kernneo.server.dao.PosicaoBancariaDAO;
import br.com.kernneo.server.negocio.Cliente;
import br.com.kernneo.server.negocio.ContaBancaria;
import br.com.kernneo.server.negocio.PosicaoFinanceira;

public class Teste {
	
	private static boolean isPasswordCorrect(char[] input) {
	    boolean isCorrect = true;
	    char[] correctPassword =  { '1','2','3'};

	    if (input.length != correctPassword.length) {
	        isCorrect = false;
	    } else {
	        isCorrect = Arrays.equals (input, correctPassword);
	    }

	    //Zero out the password.
	    Arrays.fill(correctPassword,'0');

	    return isCorrect;
	}

	public static void main(String[] args) throws Exception {
		

		Conexao.Executar(new Comando() {
			
			@Override
			public void execute(Session session) throws Exception {
				FuncionarioModel funcionarioModel =  new FuncionarioDAO().obterPorLogin("ivo");
				System.out.println(funcionarioModel.getSenha().toCharArray());
				System.out.println( "123".toCharArray() );
				 char[] correctPassword =  new String("123").toCharArray() ;
				 System.out.println(isPasswordCorrect(funcionarioModel.getSenha().toCharArray()));
			}
		});
//			
//		Conexao.Executar(new Comando() {
//			
//			@Override
//			public void execute(Session session) throws Exception {
//				/*
//				 * CRÉDITOS 
//				 */
//				Query select = session.createSQLQuery("select sum(valor)  FROM movimentacao " +
//						" p WHERE "
//						+ "p.deletado = :deletado "
//						+ "AND "
//						+ "dataHora is not null "
//						+ "AND tipo=:tipo "
//						+ "AND date(dataHora)<date(:dataHora) "
//						+ "AND id_conta=:id_conta"
//						);
//				select.setParameter("deletado", false);
//				select.setParameter("dataHora",new Date());
//				select.setParameter("tipo", MovimentacaoFinanceiraTypes.credito.toString());
//				select.setParameter("id_conta",24);
//				
//				Double credito =  (Double) select.uniqueResult();
//				if(credito == null ) { 
//					credito = 0.0; 
//				}
//				
//				
//				/*
//				 * DÉBITOS
//				 */
//				select.setParameter("deletado", false);
//				select.setParameter("dataHora",new Date());
//				select.setParameter("tipo", MovimentacaoFinanceiraTypes.debito.toString());
//				select.setParameter("id_conta",24);
//				Double debito =  (Double) select.uniqueResult();
//				if(debito == null) { 
//					debito = 0.0 ; 
//				}
//				
//				
//				
//				
//				BigDecimal creditoBigDecimal = new BigDecimal(credito);
//				creditoBigDecimal = creditoBigDecimal.setScale(2,RoundingMode.HALF_EVEN);
//				creditoBigDecimal.add(new BigDecimal(credito));
//				
//				
//				BigDecimal debitoBigDecimal = new BigDecimal(debito);
//				debitoBigDecimal = debitoBigDecimal.setScale(2,RoundingMode.HALF_EVEN);
//				
//				BigDecimal bigDecimalSaldo = BigDecimal.ZERO; 
//				bigDecimalSaldo = bigDecimalSaldo.setScale(2,RoundingMode.HALF_EVEN);
//				bigDecimalSaldo = bigDecimalSaldo.add(creditoBigDecimal);
//				bigDecimalSaldo = bigDecimalSaldo.subtract(debitoBigDecimal);
//				
//				double saldo = credito - debito; 
//				
//				
//				
//				System.out.println("Saldo: " + saldo);
//				System.out.println("Saldo: " + bigDecimalSaldo);
//				
//				
//				select = session.createQuery("select p FROM  "+  MovimentacaoModel.class.getCanonicalName() +
//						" p WHERE "
//						+ "p.deletado = :deletado "
//						+ "AND "
//						+ "dataHora is not null "
//						+ "AND date(dataHora)=date(:dataHora) AND id_conta=:id_conta"
//						);
//				select.setParameter("deletado", false);
//				select.setParameter("dataHora",new Date());
//				select.setParameter("id_conta", 24L);
//				
//				List<MovimentacaoModel> lista = select.getResultList();
//				for (MovimentacaoModel movimentacaoModel : lista) {
//					
//				}
//				System.out.println(lista.size());
//				
//				
//			}
//		});

	}
}
