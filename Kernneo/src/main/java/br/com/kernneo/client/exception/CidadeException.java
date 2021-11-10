package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class CidadeException extends Exception   implements IsSerializable{

	public CidadeException() {
		super();
	}
	
	public CidadeException(String msg) {
		super(msg);
	}
	
}
