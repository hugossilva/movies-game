package br.com.game.movies.dao.interfaces;

import java.util.List;

import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.RankingPointsRecord;

public interface RankingPointsDAO {
	
	public void insertRankingPoints(RankingPointsRecord rankingPointsRecord);
	public List<RankingPointsRecord> getGeneralRanking();
	public List<RankingPointsRecord> getRankingByGameType(GameTypeEnum gameType);

}
