package com.ibi.challenge.ws.exception.resource;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MensagemException {

	private String recurso;
	private String mensagem;
	private HttpStatus status;
	private String exception;
	private LocalDateTime timestamp;
	
}
