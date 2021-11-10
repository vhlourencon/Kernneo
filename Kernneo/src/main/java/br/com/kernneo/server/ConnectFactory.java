package br.com.kernneo.server;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// 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
public class ConnectFactory {
	private static SessionFactory sessionFactory = null;
	
	static {
		try {
			sessionFactory = getSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static SessionFactory getSessionFactory()  {
		if (sessionFactory == null) {
			SessionFactory sessionFactory = new Configuration().configure("br/com/kernneo/server/Hibernate.cfg.xml").buildSessionFactory();
			sessionFactory.openSession();
			return sessionFactory;
		}
		return sessionFactory;
	}

	/*
	 * public static SessionFactory getSessionFactory() { if (sessionFactory ==
	 * null) { Configuration configuration = new Configuration();
	 * configuration.configure("Hibernate.cfg.xml"); serviceRegistry = new
	 * StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
	 * .build(); sessionFactory =
	 * configuration.buildSessionFactory(serviceRegistry);
	 * sessionFactory.openSession(); return sessionFactory; } return sessionFactory;
	 * }
	 */

	public static Session getSession()  {
		  return getSessionFactory().getCurrentSession();
	
	}

}