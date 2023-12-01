package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class SolicitacaoException extends Exception  implements IsSerializable {

	public SolicitacaoException() {
		super();
	}
	
	public SolicitacaoException(String msg) {
		super(msg);
	}
	
}
