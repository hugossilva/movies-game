package br.com.game.movies.utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import br.com.game.movies.entity.Director;
import br.com.game.movies.entity.Genre;
import br.com.game.movies.entity.Movie;
import br.com.game.movies.records.DirectorRecord;
import br.com.game.movies.records.GenreRecord;
import br.com.game.movies.records.MovieRecord;

public class DaoUtils {
	
	public static Movie fillMovieFromDTO(MovieRecord movieDTO) {
		Movie movie = new Movie();
		
		movie.setMovieId(movieDTO.movieId());
		movie.setMovieTitle(movieDTO.title());
		movie.setPosterUrl(movieDTO.posterUrl());
		movie.setImdbRating(movieDTO.imdbRating());
		movie.setDirector(fillDirectorFromDTO(movieDTO.director()));
		movie.setGenres(fillGenresFromDTO(movieDTO.genres()));
		
		return movie;
	}
	
	public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
	    ResultSetMetaData rsmd = rs.getMetaData();
	    int columns = rsmd.getColumnCount();
	    for (int x = 1; x <= columns; x++) {
	        if (columnName.equals(rsmd.getColumnName(x))) {
	            return true;
	        }
	    }
	    return false;
	}
	
	private static Director fillDirectorFromDTO(DirectorRecord directorDTO) {
		Director director = new Director();
		director.setDirectorId(directorDTO.directorId());
		director.setName(directorDTO.director());
		return director;
	}	
	
	private static List<Genre> fillGenresFromDTO(List<GenreRecord> genreRecords) {		
		return genreRecords.stream().map(genreRecord -> {
			Genre genre = new Genre();
			genre.setGenreId(genreRecord.genreId());
			genre.setGenre(genreRecord.genre());
			return genre;
		}).collect(Collectors.toList());
	}
}
