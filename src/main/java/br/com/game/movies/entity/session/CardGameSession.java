package br.com.game.movies.entity.session;

import java.util.List;

public class CardGameSession extends GameSession {
	
	List<Integer> moviesId;

	public List<Integer> getMoviesId() {
		return moviesId;
	}

	public void setMoviesId(List<Integer> moviesId) {
		this.moviesId = moviesId;
	}

}
