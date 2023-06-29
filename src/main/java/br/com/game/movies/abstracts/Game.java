package br.com.game.movies.abstracts;

import br.com.game.movies.entity.session.GameSession;

public abstract class Game {
	
	public abstract ResponseNewGame startNewGame();
	public abstract ResponseRound getNextRound(GameSession gameSession);
	public abstract ResponseRoundResult getRoundResult(GameSession gameSession, int idUserAnswer);

}
