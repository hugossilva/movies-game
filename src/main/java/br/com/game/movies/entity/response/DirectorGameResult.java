package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseGameResult;

public class DirectorGameResult extends ResponseGameResult {
	
	private final Double relevanceMultiplier = 1.5;
	private Integer directorGameRankingPosition;
	
	public Integer getDirectorGameRankingPosition() {
		return directorGameRankingPosition;
	}
	public void setDirectorGameRankingPosition(Integer directorGameRankingPosition) {
		this.directorGameRankingPosition = directorGameRankingPosition;
	}
	public Double getRelevanceMultiplier() {
		return relevanceMultiplier;
	}

}
