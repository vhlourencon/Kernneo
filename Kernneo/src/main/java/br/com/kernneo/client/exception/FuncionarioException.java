package br.com.kernneo.client.exception;

import java.io.Serializable;


public class FuncionarioException extends Exception  implements Serializable {

	public FuncionarioException() {
		super();
	}
	
	public FuncionarioException(String msg) {
		super(msg);
	}
	
}
