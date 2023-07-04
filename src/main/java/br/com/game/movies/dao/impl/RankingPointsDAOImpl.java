package br.com.game.movies.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.game.movies.constants.QueriesConstants;
import br.com.game.movies.dao.interfaces.RankingPointsDAO;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.RankingRecord;
import br.com.game.movies.utils.DaoUtils;

@Repository
public class RankingPointsDAOImpl implements RankingPointsDAO {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	@Override
	public void insertRankingPoints(RankingRecord rankingRecord) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("user_id", rankingRecord.userId());
		parameters.addValue("game_type", rankingRecord.gameType());
		parameters.addValue("points", rankingRecord.points());
		
		this.jdbcNamedParameter.update(QueriesConstants.INSERT_RANKING_POINTS, parameters);
	}
	
	@Override
	public List<RankingRecord> getGeneralRanking() {		
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_GENERAL_RANKING, this.getResultSetFromRanking());
	}
	
	public List<RankingRecord> getGeneralRankingByGameType(GameTypeEnum gameType) {
		String query = this.getQueryByGameType(gameType);
		return this.jdbcNamedParameter.query(query, this.getResultSetFromRanking());		
	}
	
	@Override
	public List<RankingRecord> getRankingByGameType(GameTypeEnum gameType, Integer userId) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();		
		parameters.addValue("user_id", userId);		
		String query = this.getQueryByGameType(gameType);		
		return this.jdbcNamedParameter.query(query, parameters, this.getResultSetFromRanking());
	}
	
	private String getQueryByGameType(GameTypeEnum gameType) {
		switch(gameType) {
			case CARD_GAME: {
				return QueriesConstants.GENERAL_RANKING_VIEW_CARD_GAME;
			}
			case DIRECTORS_GAME: {
				return QueriesConstants.GENERAL_RANKING_VIEW_DIRECTORS_GAME;
			}
			default: {			
				return "";
			}
		}		
	}
	
	private ResultSetExtractor<RankingRecord> getSingleResultRankingByType() {
		return new ResultSetExtractor<RankingRecord>() {
			@Override
			public RankingRecord extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					String gameType = DaoUtils.hasColumn(rs, "GAME_TYPE") ? rs.getString("GAME_TYPE") : "";
					return new RankingRecord(rs.getInt("POSITION"), rs.getInt("USER_ID"), rs.getString("USERNAME"), gameType, rs.getDouble("POINTS"));
				}
				return null;
			}
		};		
	}
	
	
	
	private ResultSetExtractor<List<RankingRecord>> getResultSetFromRanking() {		
		return new ResultSetExtractor<List<RankingRecord>>() {			
			public List<RankingRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {				
				List<RankingRecord> rankings = new ArrayList<RankingRecord>();
				while(rs.next()) {
					String gameType = DaoUtils.hasColumn(rs, "GAME_TYPE") ? rs.getString("GAME_TYPE") : "";
					RankingRecord ranking = new RankingRecord(rs.getInt("POSITION"), rs.getInt("USER_ID"), rs.getString("USERNAME"), gameType, rs.getDouble("POINTS"));								
					rankings.add(ranking);					
				}
				return rankings;
			}
		};
	}
}
