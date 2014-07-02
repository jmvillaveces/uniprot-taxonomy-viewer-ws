package de.mpg.biochem.controller;

import org.springframework.web.bind.annotation.ResponseStatus;

public class BadRequestException extends RuntimeException {
	
	@ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
	public final class ResourceNotFoundException extends RuntimeException {
	}	
}
