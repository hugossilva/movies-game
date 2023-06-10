package br.com.game.movies.enums;

public enum GameTypeEnum {
	
	NONE(0),
	CARD_GAME(1),
	DIRECTORS_GAME(2);
	
	private int gameType;
	
	GameTypeEnum(int gameType) {
		this.gameType = gameType;
	}
	
	public int getValue() {
		return this.gameType;
	}

}
