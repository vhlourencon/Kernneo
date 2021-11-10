package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class ObservacaoException extends Exception  implements IsSerializable {

	public ObservacaoException() {
		super();
	}
	
	public ObservacaoException(String msg) {
		super(msg);
	}
	
}
