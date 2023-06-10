package br.com.game.movies.dao.interfaces;

import java.util.List;

import br.com.game.movies.records.GenreRecord;

public interface GenreDAO {
	
	public List<GenreRecord> getMovieGenresByIdMovie(int movieId);

}
