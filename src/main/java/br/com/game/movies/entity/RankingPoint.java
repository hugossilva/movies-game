package br.com.game.movies.entity;

public class RankingPoint {
	
	private Integer rankingPointsId;
	private Integer userId;
	private String userName;
	private String gameType;
	private Double points;
	
	public Integer getRankingPointsId() {
		return rankingPointsId;
	}
	public void setRankingPointsId(Integer rankingPointsId) {
		this.rankingPointsId = rankingPointsId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getGameType() {
		return gameType;
	}
	public void setGameType(String gameType) {
		this.gameType = gameType;
	}
	public Double getPoints() {
		return points;
	}
	public void setPoints(Double points) {
		this.points = points;
	}

}
