package br.com.game.movies.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public DirectorRecord getDirectorByMovieID(int movieId) {
		MapSqlParameterSource parameters = this.getQueryParameters(movieId);
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_DIRECTORS, parameters, this.getResultExtractor());
	}
	
	private MapSqlParameterSource getQueryParameters(int movieId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("movie_id", movieId);		
		return parameters;
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