package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.dao.interfaces.RankingPointsDAO;
import br.com.game.movies.entity.RankingPoint;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.RankingPointsRecord;
import br.com.game.movies.service.interfaces.RankingService;

@Service
public class RankingServiceImpl implements RankingService {
	
	@Autowired
	private RankingPointsDAO rankingPointsDao;
	
	@Override
	public void saveRankingByGameResult(ResponseGameResult gameResult) {
		RankingPointsRecord rankingPoints = this.gameResultToRankingPointsRecord(gameResult);
		this.rankingPointsDao.insertRankingPoints(rankingPoints);
	}
	
	@Override
	public List<RankingPoint> getGenralRanking() {
		List<RankingPointsRecord> rankingRecords = this.rankingPointsDao.getGeneralRanking();
		return this.rankingPointRecordsToRankingPoint(rankingRecords);
	}
	
	@Override
	public List<RankingPoint> getRankingByGameType(GameTypeEnum gameType) {
		List<RankingPointsRecord> rankingRecords = this.rankingPointsDao.getRankingByGameType(gameType);
		return this.rankingPointRecordsToRankingPoint(rankingRecords);
	}
	
	private List<RankingPoint> rankingPointRecordsToRankingPoint(List<RankingPointsRecord> rankingRecords) {
		List<RankingPoint> rankings = new ArrayList<RankingPoint>();
		
		for(int i = 0; i < rankingRecords.size(); i++) {
			RankingPointsRecord rankingRecord = rankingRecords.get(i);
			RankingPoint ranking = new RankingPoint();
			
			ranking.setUserId(rankingRecord.userId());
			ranking.setGameType(rankingRecord.gameType());
			ranking.setPoints(rankingRecord.points());
			ranking.setRankingPointsId(rankingRecord.rankingPointsId());
			ranking.setUserName(rankingRecord.userName());
			rankings.add(ranking);
		}
		
		return rankings;
	}
	
	private RankingPointsRecord gameResultToRankingPointsRecord(ResponseGameResult gameResult) {		
		return new RankingPointsRecord(0, gameResult.getUserId(), "", gameResult.getGameType().name(), gameResult.getTotalPointsScored());		
	}
	

}
