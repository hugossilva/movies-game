package br.com.game.movies.dao.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
import br.com.game.movies.records.RankingPointsRecord;
import br.com.game.movies.utils.DaoUtils;

@Repository
public class RankingPointsDAOImpl implements RankingPointsDAO {
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	@Autowired
	protected NamedParameterJdbcTemplate jdbcNamedParameter;
	
	@Override
	public void insertRankingPoints(RankingPointsRecord rankingPointsRecord) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		parameters.addValue("user_id", rankingPointsRecord.userId());
		parameters.addValue("game_type", rankingPointsRecord.gameType());
		parameters.addValue("points", rankingPointsRecord.points());
		
		this.jdbcNamedParameter.update(QueriesConstants.INSERT_RANKING_POINTS, parameters);
	}
	
	@Override
	public List<RankingPointsRecord> getGeneralRanking() {		
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_GENERAL_RANKING, this.getResultSetFromRanking());
	}
	
	@Override
	public List<RankingPointsRecord> getRankingByGameType(GameTypeEnum gameType) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("game_type", gameType.name());
		return this.jdbcNamedParameter.query(QueriesConstants.QUERY_RANKING_BY_GAME_TYPE, parameters, this.getResultSetFromRanking());
	}
	
	private ResultSetExtractor<List<RankingPointsRecord>> getResultSetFromRanking() {		
		return new ResultSetExtractor<List<RankingPointsRecord>>() {			
			public List<RankingPointsRecord> extractData(ResultSet rs) throws SQLException, DataAccessException {				
				List<RankingPointsRecord> rankings = new ArrayList<RankingPointsRecord>();
				while(rs.next()) {
					String gameType = DaoUtils.hasColumn(rs, "GAME_TYPE") ? rs.getString("GAME_TYPE") : "";
					RankingPointsRecord ranking = new RankingPointsRecord(rs.getInt("RANKING_POINTS_ID"), rs.getInt("USER_ID"), rs.getString("USERNAME"), gameType, rs.getDouble("POINTS"));								
					rankings.add(ranking);					
				}
				return rankings;
			}
		};
	}
}
