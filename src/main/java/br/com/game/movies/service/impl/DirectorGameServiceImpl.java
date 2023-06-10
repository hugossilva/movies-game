package br.com.game.movies.service.impl;

import org.springframework.stereotype.Service;

import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.response.CardGameResult;
import br.com.game.movies.entity.response.RoundResultCardGame;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.service.interfaces.GameService;

@Service
public class DirectorGameServiceImpl implements GameService {
	
	@Override
	public GameTypeEnum getGameType() {
		return GameTypeEnum.DIRECTORS_GAME;
	}

	@Override
	public ResponseNewGame startNewGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseRound getNextRound(GameSession gameSession) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoundResultCardGame getRoundResult(GameSession gameSession, int idUserAnswer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CardGameResult getGameResult(GameSession gameSession) {
		// TODO Auto-generated method stub
		return null;
	}



}
