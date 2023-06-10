package br.com.game.movies.abstracts;

import br.com.game.movies.enums.GameTypeEnum;

public abstract class ResponseGameResult {
	
	private GameTypeEnum gameType;
	private Integer numberOfCorrectAnswers;
	private Integer numberOfRoundsPlayed;
	private Integer numberOfTotalRounds;	
	private Double totalPointsScored;
	private Integer generalRankingPosition;
	
	public GameTypeEnum getGameType() {
		return gameType;
	}
	public void setGameType(GameTypeEnum gameType) {
		this.gameType = gameType;
	}
	public Integer getNumberOfCorrectAnswers() {
		return numberOfCorrectAnswers;
	}
	public void setNumberOfCorrectAnswers(Integer numberOfCorrectAnswers) {
		this.numberOfCorrectAnswers = numberOfCorrectAnswers;
	}
	public Integer getNumberOfRoundsPlayed() {
		return numberOfRoundsPlayed;
	}
	public void setNumberOfRoundsPlayed(Integer numberOfRoundsPlayed) {
		this.numberOfRoundsPlayed = numberOfRoundsPlayed;
	}
	public Double getTotalPointsScored() {
		return totalPointsScored;
	}
	public void setTotalPointsScored(Double totalPointsScored) {
		this.totalPointsScored = totalPointsScored;
	}
	public Integer getNumberOfTotalRounds() {
		return numberOfTotalRounds;
	}
	public void setNumberOfTotalRounds(Integer numberOfTotalRounds) {
		this.numberOfTotalRounds = numberOfTotalRounds;
	}
	public Integer getGeneralRankingPosition() {
		return generalRankingPosition;
	}
	public void setGeneralRankingPosition(Integer generalRankingPosition) {
		this.generalRankingPosition = generalRankingPosition;
	}
}