package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseRoundResult;

public class RoundResultCardGame extends ResponseRoundResult {
	
	private Integer correctMovieId;

	public Integer getCorrectMovieId() {
		return correctMovieId;
	}

	public void setCorrectMovieId(Integer correctMovieId) {
		this.correctMovieId = correctMovieId;
	}
}
