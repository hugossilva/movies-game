package br.com.game.movies.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.game.movies.constants.QueriesConstants;
import br.com.game.movies.dao.interfaces.DirectorsDAO;
import br.com.game.movies.dao.interfaces.GenreDAO;
import br.com.game.movies.dao.interfaces.MovieDAO;
import br.com.game.movies.records.MovieRecord;

@Repository
public class MovieDAOImpl implements MovieDAO {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	@Autowired
	protected DirectorsDAO directorDAO;
	
	@Autowired
	protected GenreDAO genreDAO;
	
	public List<MovieRecord> getRandomMoviesByLimit(List<Integer> movieIds, Integer limit) {
		MapSqlParameterSource parameters = this.getQueryParameters(movieIds, limit);		
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_RANDOM_MOVIES, parameters, this.getResultSetFromMoviesQuery(this.directorDAO, this.genreDAO));
	}
	
	public List<MovieRecord> getMovies() {		
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_MOVIES, this.getResultSetFromMoviesQuery(this.directorDAO, this.genreDAO));
	}
	
	private MapSqlParameterSource getQueryParameters(List<Integer> movieIds, Integer limit) {		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("movie_ids", movieIds);
		parameters.addValue("limit", limit);
		return parameters;
	}	
	
	private ResultSetExtractor<List<MovieRecord>> getResultSetFromMoviesQuery(DirectorsDAO directorDAO, GenreDAO genreDAO) {		
		return new ResultSetExtractor<List<MovieRecord>>() {			
			public List<MovieRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {				
				List<MovieRecord> movies = new ArrayList<MovieRecord>();
				while(rs.next()) {
					Integer movieId = rs.getInt("MOVIE_ID");
					MovieRecord movie = new MovieRecord(movieId, rs.getString("TITLE"), rs.getString("START_YEAR"), rs.getString("POSTER_URL"), rs.getDouble("IMDB_RATING"),directorDAO.getDirectorByMovieID(movieId), genreDAO.getMovieGenresByIdMovie(movieId));			
					movies.add(movie);					
				}
				return movies;
			}
		};
	}

}
