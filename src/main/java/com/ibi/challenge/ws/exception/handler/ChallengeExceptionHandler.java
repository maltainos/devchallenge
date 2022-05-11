package com.ibi.challenge.ws.exception.handler;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ibi.challenge.ws.exception.resource.MensagemException;
import com.ibi.challenge.ws.exception.resource.ResourceNotFoundException;

@ControllerAdvice
public class ChallengeExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler({ResourceNotFoundException.class})
	public ResponseEntity<MensagemException> handleResourceNotFoundException(ResourceNotFoundException ex){
		
		MensagemException msg = new MensagemException();
		msg.setRecurso(ex.getRecurso());
		msg.setMensagem(messageSource.getMessage("resource.not-found",null, LocaleContextHolder.getLocale()));
		msg.setException(ex.getMessage());
		msg.setTimestamp(LocalDateTime.now());
		msg.setStatus(HttpStatus.NOT_FOUND);
		
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(msg);
	}

}
