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
import br.com.game.movies.records.DirectorRecord;

@Repository
public class DirectorsDAOImpl implements DirectorsDAO {
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	@Override
	public List<DirectorRecord> getRandomDirectorsByLimit(List<Integer> directorIds, Integer limit) {
		MapSqlParameterSource parameters = this.getQueryRandomParameters(directorIds, limit);
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_RANDOM_DIRECTORS, parameters, this.getResultsExtractor());		
	}
	
	public DirectorRecord getDirectorByMovieID(int movieId) {
		MapSqlParameterSource parameters = this.getQueryParameters(movieId);
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_DIRECTORS, parameters, this.getResultExtractor());
	}
	
	private MapSqlParameterSource getQueryRandomParameters(List<Integer> directorIds, Integer limit) {		
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("director_ids", directorIds);
		parameters.addValue("limit", limit);
		return parameters;
	}	
	
	private MapSqlParameterSource getQueryParameters(int movieId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("movie_id", movieId);		
		return parameters;
	}
	
	private ResultSetExtractor<List<DirectorRecord>> getResultsExtractor() {
		return new ResultSetExtractor<List<DirectorRecord>>() {

			@Override
			public List<DirectorRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<DirectorRecord> directorsRecord = new ArrayList<DirectorRecord>();
				while(rs.next()) {
					DirectorRecord director = new DirectorRecord(rs.getInt("DIRECTOR_ID"), rs.getString("DIRECTOR"));
					directorsRecord.add(director);
				}
				return directorsRecord;
			}		
		};
	}
	
	private ResultSetExtractor<DirectorRecord> getResultExtractor() {
		return new ResultSetExtractor<DirectorRecord>() {
			public DirectorRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {					
					return new DirectorRecord(rs.getInt("DIRECTOR_ID"), rs.getString("DIRECTOR"));
				}
				return null;
			}
		};
	}
}