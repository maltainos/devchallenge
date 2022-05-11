package com.ibi.challenge.ws.exception.resource;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	@Getter
	private String recurso;
	
	public ResourceNotFoundException(String mensagem, String recurso) {
		super(mensagem);
		this.recurso = recurso;
	}

}
