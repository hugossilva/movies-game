package br.com.game.movies.abstracts;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.game.movies.enums.GameTypeEnum;

public abstract class ResponseRound {
	
	private GameTypeEnum gameType;
	private String message;
	private int round;
	private int maxRounds;
	@JsonIgnore
	private int idCorrectAnswer;

	public ResponseRound(GameTypeEnum gameType, int round, int maxRounds) {
		this.gameType = gameType;
		this.round = round;
		this.maxRounds = maxRounds;
	}
	
	public GameTypeEnum getGameType() {
		return gameType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getRound() {
		return round;
	}	
	public int getMaxRounds() {
		return maxRounds;
	}	
	public int getIdCorrectAnswer() {
		return idCorrectAnswer;
	}

	public void setIdCorrectAnswer(int idCorrectAnswer) {
		this.idCorrectAnswer = idCorrectAnswer;
	}
}