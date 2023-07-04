package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseGameResult;

public class CardGameResult extends ResponseGameResult {
	
	private final Double relevanceMultiplier = 1.0;
	private Integer cardGameRankingPosition;	
	
	public void setTotalPointsScored(Integer totalPointsScored) {
		this.setTotalPointsScored(this.relevanceMultiplier * totalPointsScored);
	}
	
	public Integer getCardGameRankingPosition() {
		return cardGameRankingPosition;
	}
	public void setCardGameRankingPosition(Integer cardGameRankingPosition) {
		this.cardGameRankingPosition = cardGameRankingPosition;
	}
	public Double getRelevanceMultiplier() {
		return relevanceMultiplier;
	}
}
