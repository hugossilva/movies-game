package br.com.game.movies.dao.interfaces;

import br.com.game.movies.records.DirectorRecord;

public interface DirectorsDAO {
	public DirectorRecord getDirectorByMovieID(int movieId);
}
