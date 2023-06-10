package br.com.game.movies.abstracts;

public abstract class ResponseRoundResult {
	
	private String message;
	private Integer numberOfCorrectAnswers;
	private Integer idCorrectAnswer;
	private boolean isCorrectAnswer;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}
	public void setNumberOfCorrectAnswers(Integer numberOfCorrectAnswers) {
		this.numberOfCorrectAnswers = numberOfCorrectAnswers;
	}
	public boolean isCorrectAnswer() {
		return isCorrectAnswer;
	}
	public void setCorrectAnswer(boolean isCorrectAnswer) {
		this.isCorrectAnswer = isCorrectAnswer;
	}
	public Integer getIdCorrectAnswer() {
		return idCorrectAnswer;
	}
	public void setIdCorrectAnswer(Integer idCorrectAnswer) {
		this.idCorrectAnswer = idCorrectAnswer;
	}

}
