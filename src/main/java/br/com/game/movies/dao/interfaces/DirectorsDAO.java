package br.com.game.movies.dao.interfaces;

import java.util.List;

import br.com.game.movies.records.DirectorRecord;

public interface DirectorsDAO {
	public List<DirectorRecord> getRandomDirectorsByLimit(List<Integer> directorIds, Integer limit);
	public DirectorRecord getDirectorByMovieID(int movieId);
}
