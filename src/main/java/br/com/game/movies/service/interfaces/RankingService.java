package br.com.game.movies.service.interfaces;

import java.util.List;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.entity.RankingPoint;
import br.com.game.movies.enums.GameTypeEnum;

public interface RankingService {
	
	public void saveRankingByGameResult(ResponseGameResult gameResult);
	public List<RankingPoint> getGenralRanking();
	public List<RankingPoint> getRankingByGameType(GameTypeEnum gameType);

}
