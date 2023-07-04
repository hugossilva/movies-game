package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.dao.interfaces.RankingPointsDAO;
import br.com.game.movies.entity.RankingPoint;
import br.com.game.movies.enums.AttributesNamesEnum;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.RankingRecord;
import br.com.game.movies.service.interfaces.RankingService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class RankingServiceImpl implements RankingService {
	
	@Autowired
	private RankingPointsDAO rankingPointsDao;
	
	@Override
	public void saveRankingByGameResult(ResponseGameResult gameResult) {
		RankingRecord rankingPoints = this.gameResultToRankingPointsRecord(gameResult);
		this.rankingPointsDao.insertRankingPoints(rankingPoints);
	}
	
	@Override
	public List<RankingPoint> getGenralRanking() {
		List<RankingRecord> rankingRecords = this.rankingPointsDao.getGeneralRanking();
		return this.rankingPointRecordsToRankingPoint(rankingRecords);
	}
	
	@Override
	public List<RankingPoint> getRankingByGameType(GameTypeEnum gameType, HttpServletRequest request) {
		Integer userId = (Integer) request.getSession().getAttribute(AttributesNamesEnum.USER_ID.getValue()); 
		List<RankingRecord> rankingRecords = this.rankingPointsDao.getRankingByGameType(gameType, userId);
		return this.rankingPointRecordsToRankingPoint(rankingRecords);
	}
	
	private List<RankingPoint> rankingPointRecordsToRankingPoint(List<RankingRecord> rankingRecords) {
		List<RankingPoint> rankings = new ArrayList<RankingPoint>();
		
		for(int i = 0; i < rankingRecords.size(); i++) {
			RankingRecord rankingRecord = rankingRecords.get(i);
			RankingPoint ranking = new RankingPoint();
			
			ranking.setUserId(rankingRecord.userId());
			ranking.setGameType(rankingRecord.gameType());
			ranking.setPoints(rankingRecord.points());
			ranking.setPosition(rankingRecord.position());
			ranking.setUserName(rankingRecord.username());
			rankings.add(ranking);
		}
		
		return rankings;
	}
	
	private RankingRecord gameResultToRankingPointsRecord(ResponseGameResult gameResult) {		
		return new RankingRecord(0, gameResult.getUserId(), "", gameResult.getGameType().name(), gameResult.getTotalPointsScored());		
	}
	

}
