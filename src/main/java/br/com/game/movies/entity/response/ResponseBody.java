package br.com.game.movies.entity.response;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {
	
	private Integer statusCode;
	private String message;
	private T body;
	
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}

}
