package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.enums.GameTypeEnum;

public class DirectorGame extends ResponseNewGame {
	
	public DirectorGame() {
		this.setGameType(GameTypeEnum.DIRECTORS_GAME);
	}

}
