package br.com.game.movies.entity.request;

import br.com.game.movies.enums.GameTypeEnum;

public class GameRequest {
	
	private GameTypeEnum gameType;

	public GameTypeEnum getGameType() {
		return gameType;
	}

	public void setGameType(GameTypeEnum gameType) {
		this.gameType = gameType;
	}

}
