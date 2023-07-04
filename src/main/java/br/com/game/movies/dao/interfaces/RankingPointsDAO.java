package br.com.game.movies.dao.interfaces;

import java.util.List;

import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.RankingRecord;

public interface RankingPointsDAO {
	
	public void insertRankingPoints(RankingRecord rankingPointsRecord);
	public List<RankingRecord> getGeneralRanking();
	public List<RankingRecord> getRankingByGameType(GameTypeEnum gameType, Integer userId);

}
