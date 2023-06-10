package br.com.game.movies.entity;

import br.com.game.movies.enums.GameTypeEnum;

public class GameSession {
	
	private String sessionId;
	private GameTypeEnum gameType;
	private int roundNumber;
	private int maxRounds;
	private int idRoundCorrectAnswer;
	private int correctAnswers;
	private boolean nextRound;
	private String reasonNextRound;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public GameTypeEnum getGameType() {
		return gameType;
	}
	public void setGameType(GameTypeEnum gameType) {
		this.gameType = gameType;
	}
	public int getRoundNumber() {
		return roundNumber;
	}
	public void setRoundNumber(int roundNumber) {
		this.roundNumber = roundNumber;
	}
	public int getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(int correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public boolean isNextRound() {
		return nextRound;
	}
	public void setNextRound(boolean nextRound) {
		this.nextRound = nextRound;
	}
	public String getReasonNextRound() {
		return reasonNextRound;
	}
	public void setReasonNextRound(String reasonNextRound) {
		this.reasonNextRound = reasonNextRound;
	}
	public int getMaxRounds() {
		return maxRounds;
	}
	public void setMaxRounds(int maxRounds) {
		this.maxRounds = maxRounds;
	}
	public int getIdRoundCorrectAnswer() {
		return idRoundCorrectAnswer;
	}
	public void setIdRoundCorrectAnswer(int idRoundCorrectAnswer) {
		this.idRoundCorrectAnswer = idRoundCorrectAnswer;
	}
}
