package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.enums.GameTypeEnum;

public class CardGame extends ResponseNewGame {	
	public CardGame() {		
		this.setGameType(GameTypeEnum.CARD_GAME);
	}
}
