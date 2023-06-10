package br.com.game.movies.service.interfaces;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.response.RoundResultCardGame;
import br.com.game.movies.enums.GameTypeEnum;

public interface GameService {
	public GameTypeEnum getGameType();
	public ResponseNewGame startNewGame();	
	public ResponseRound getNextRound(GameSession gameSession);
	public RoundResultCardGame getRoundResult(GameSession gameSession,  int idUserAnswer);
	public ResponseGameResult getGameResult(GameSession gameSession);
}
