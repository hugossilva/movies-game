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
import br.com.game.movies.dao.interfaces.GenreDAO;
import br.com.game.movies.records.GenreRecord;

@Repository
public class GenresDAOImpl implements GenreDAO {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	public List<GenreRecord> getMovieGenresByIdMovie(int movieId) {
		MapSqlParameterSource parameters = this.getQueryParameters(movieId);		
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_GENRES_BY_MOVIE_ID, parameters, this.getResultExtractor());		
	}
	
	private MapSqlParameterSource getQueryParameters(int movieId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("movie_id", movieId);		
		return parameters;
	}
	
	private ResultSetExtractor<List<GenreRecord>> getResultExtractor() {
		return new ResultSetExtractor<List<GenreRecord>>() {
			List<GenreRecord> genres = new ArrayList<GenreRecord>();
			public List<GenreRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {				
				while(rs.next()) {
					GenreRecord genre = new GenreRecord(rs.getInt("GENRE_ID"), rs.getString("GENRE"));
					genres.add(genre);
				}
				return genres;
			}
		};
	}	
}
