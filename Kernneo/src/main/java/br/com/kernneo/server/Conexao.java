\package br.com.kernneo.server;

import org.hibernate.Session;

public  interface Conexao  {
	

	
	public static void Executar(Comando comando) throws Exception {
		Session session = ConnectFactory.getSession();
		try {
		    session.beginTransaction();
		    comando.execute(session);
		    session.getTransaction().commit();
		} catch (Exception e) {
		    session.getTransaction().rollback();
		    e.printStackTrace();
		    throw e;
		} finally {
		    if (session != null && session.isOpen()) {
			 session.close();
		    }
		}
		
	}
	
	
    

	

}
