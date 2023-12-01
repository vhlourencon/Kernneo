package br.com.kernneo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

// 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
public class PropertiesFactory
	{
		private static Properties props = null;
		private static String propsKeyServerHostDB = "hibernate.connection.url";

		static {
			try {
				props = propertiesFactory();
			} catch (Throwable ex) {
				System.err.println("Initial SessionFactory creation failed." + ex);
				throw new ExceptionInInitializerError(ex);
			}
		}

		private static Properties propertiesFactory() throws IOException {
			if (props == null) {

				props = new Properties();

				File fileConfig = new File("config.properties");
				if (!fileConfig.exists()) {
					OutputStream output = new FileOutputStream("config.properties");
					props.put(PropertiesFactory.propsKeyServerHostDB, "127.0.0.1");
					props.store(output, null);
					output.close();
				}
				FileInputStream file = new FileInputStream("config.properties");
				props.load(file);
				return props;
			}
			return props;
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

		public static Object getPropertiesConfig(String key) throws IOException {
			return propertiesFactory().get(key);
		}

		public static void putPropertiesConfig(String key, String value) throws IOException {
			OutputStream output = new FileOutputStream("config.properties");
			propertiesFactory().put(key, value);
			propertiesFactory().store(output, null);
			output.close();
		}

		private static Properties getProp() throws IOException {

			Properties props = new Properties();

			File fileConfig = new File("config.properties");
			if (fileConfig.exists() == false) {
				/// fileConfig.createNewFile();

				OutputStream output = new FileOutputStream("config.properties");
				props.put(PropertiesFactory.propsKeyServerHostDB, "127.0.0.1");
				props.store(output, null);
				output.close();
				// throw new Exception("Configure o arquivo de propriedades!");
			}

			FileInputStream file = new FileInputStream("config.properties");
			props.load(file);
			return props;

		}

	}