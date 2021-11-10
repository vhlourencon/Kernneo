package br.com.kernneo.client.exception;

import com.google.gwt.user.client.rpc.IsSerializable;

@SuppressWarnings("serial")
public class TicketException extends Exception  implements IsSerializable {

	public TicketException() {
		super();
	}
	
	public TicketException(String msg) {
		super(msg);
	}
	
}
