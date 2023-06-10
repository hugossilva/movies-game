package br.com.game.movies.dao.interfaces;

import java.util.List;

import br.com.game.movies.records.MovieRecord;

public interface MovieDAO {
	
	public List<MovieRecord> getRandomMoviesByLimit(List<Integer> movieIds, Integer limit);
	public List<MovieRecord> getMovies();	

}
