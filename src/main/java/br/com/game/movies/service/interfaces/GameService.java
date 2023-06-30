package br.com.game.movies.service.interfaces;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.abstracts.ResponseRoundResult;
import br.com.game.movies.entity.session.GameSession;
import br.com.game.movies.enums.GameTypeEnum;

public interface GameService {
	public GameSession getGameSession(String sessionId, Integer userId);
	public GameTypeEnum getGameType();
	public ResponseNewGame startNewGame();	
	public ResponseRound getNextRound(GameSession gameSession);
	public ResponseRoundResult getRoundResult(GameSession gameSession,  int idUserAnswer);
	public ResponseGameResult getGameResult(GameSession gameSession);
}
