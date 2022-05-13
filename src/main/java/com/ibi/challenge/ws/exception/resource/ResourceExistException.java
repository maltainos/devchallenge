package com.ibi.challenge.ws.exception.resource;

import lombok.Getter;
import lombok.Setter;

public class ResourceExistException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String mensagem;
	
	public ResourceExistException(String mensagem) {
		super(mensagem);
	}
}
