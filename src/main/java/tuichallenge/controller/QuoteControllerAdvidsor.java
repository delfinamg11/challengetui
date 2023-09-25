package tuichallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;
import tuichallenge.error.ApiError;
import tuichallenge.exception.ElementNotFoundException;

@RestControllerAdvice
@Slf4j
public class QuoteControllerAdvidsor {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ElementNotFoundException.class)
	public ApiError handleNotFoundException(ElementNotFoundException ex) {
		log.error("Element not found", ex);
		return new ApiError(HttpStatus.NOT_FOUND.toString(), ex.getMessage());
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(RuntimeException.class)
	public ApiError handleRunTimeException(RuntimeException ex) {
		log.error("Internal error", ex);
		return new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "There was an error processing your request");
	}
}
