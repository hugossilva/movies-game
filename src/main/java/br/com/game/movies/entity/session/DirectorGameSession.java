package br.com.game.movies.entity.session;

import java.util.List;

public class DirectorGameSession extends GameSession {
	
	List<Integer> moviesId;
	List<Integer> lastIdsDirectorsUsed;	

	public List<Integer> getMoviesId() {
		return moviesId;
	}
	public void setMoviesId(List<Integer> moviesId) {
		this.moviesId = moviesId;
	}
	public List<Integer> getLastIdsDirectorsUsed() {
		return lastIdsDirectorsUsed;
	}
	public void setLastIdsDirectorsUsed(List<Integer> lastIdsDirectorsUsed) {
		this.lastIdsDirectorsUsed = lastIdsDirectorsUsed;
	}

}
