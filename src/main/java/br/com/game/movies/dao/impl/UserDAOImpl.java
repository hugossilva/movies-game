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
import br.com.game.movies.dao.interfaces.UserDAO;
import br.com.game.movies.records.UserRecord;


@Repository
public class UserDAOImpl implements UserDAO {
	
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	
	public UserRecord getUserInfo(String username, String password) {
		MapSqlParameterSource parameters = this.getQueryParameters(username, password);
		return jdbcNamedParameter.query(QueriesConstants.QUERY_USERS, parameters, this.getResultExtractor());	
	}
	
	private MapSqlParameterSource getQueryParameters(String username, String password) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("username", username);
		parameters.addValue("password", password);
		return parameters;
	}
	
	
	private ResultSetExtractor<UserRecord> getResultExtractor() {
		return new ResultSetExtractor<UserRecord>() {
			public UserRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {					
					return new UserRecord(rs.getInt("USER_ID"), rs.getString("USERNAME"), rs.getString("PASSWORD"), rs.getString("ROLE_NAME"));
				}
				return null;
			}			
		};		
	}
}
