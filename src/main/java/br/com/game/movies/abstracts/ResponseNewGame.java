package br.com.game.movies.abstracts;

import br.com.game.movies.enums.GameTypeEnum;

public abstract class ResponseNewGame {
	
	private boolean gameStarted;
	private GameTypeEnum gameType;
	private String message;
	private String nextUrl;
	private int round;
	private int maxNumberOfRounds;
	
	public boolean isGameStarted() {
		return gameStarted;
	}
	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}
	public GameTypeEnum getGameType() {
		return gameType;
	}
	public void setGameType(GameTypeEnum gameType) {
		this.gameType = gameType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNextUrl() {
		return nextUrl;
	}
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}
	public int getMaxNumberOfRounds() {
		return maxNumberOfRounds;
	}
	public void setMaxNumberOfRounds(int maxNumberOfRounds) {
		this.maxNumberOfRounds = maxNumberOfRounds;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
}
