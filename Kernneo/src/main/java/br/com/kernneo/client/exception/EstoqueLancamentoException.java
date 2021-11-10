package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EstoqueLancamentoException extends Exception implements IsSerializable {

	public EstoqueLancamentoException() {
		super();
	}
	
	public EstoqueLancamentoException(String msg) {
		super(msg);
	}
	
	
	
}
