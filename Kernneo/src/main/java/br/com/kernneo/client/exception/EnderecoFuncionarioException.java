package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class EnderecoFuncionarioException extends Exception  implements IsSerializable {

	public EnderecoFuncionarioException() {
		super();
	}
	
	public EnderecoFuncionarioException(String msg) {
		super(msg);
	}
	
}
