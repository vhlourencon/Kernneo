package br.com.kernneo.server;

import org.hibernate.Session;

public interface Comando {

	public void execute(Session session) throws Exception;

}