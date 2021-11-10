package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class OpcaoProdutoException extends Exception  implements IsSerializable {

	public OpcaoProdutoException() {
		super();
	}
	
	public OpcaoProdutoException(String msg) {
		super(msg);
	}
	
}
