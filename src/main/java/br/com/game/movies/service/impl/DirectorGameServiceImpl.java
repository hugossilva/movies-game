package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.game.movies.constants.GamesConstants;
import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.constants.UrlConstants;
import br.com.game.movies.dao.interfaces.MovieDAO;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.response.CardGameResult;
import br.com.game.movies.entity.response.DirectorGame;
import br.com.game.movies.entity.response.RoundDirectorGame;
import br.com.game.movies.entity.response.RoundResultCardGame;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.MovieRecord;
import br.com.game.movies.service.interfaces.GameService;
import br.com.game.movies.utils.ServicesUtils;

@Service
public class DirectorGameServiceImpl implements GameService {
	
	@Autowired
	@Qualifier("CardGameSession")
	private Map<String, List<Integer>> mapSession;
	
	@Autowired
	private MovieDAO movieDao;
	
	@Override
	public GameTypeEnum getGameType() {
		return GameTypeEnum.DIRECTORS_GAME;
	}

	@Override
	public DirectorGame startNewGame() {
		DirectorGame newGame = new DirectorGame();
		newGame.setMessage(MessagesConstants.START_NEW_DIRECTORS_GAME);
		newGame.setNextUrl(UrlConstants.NEXT_ROUND);
		newGame.setMaxNumberOfRounds(GamesConstants.NUMBER_MAX_ROUNDS_DIRECTOR_GAME);		
		return newGame;		
	}

	@Override
	public RoundDirectorGame getNextRound(GameSession gameSession) {
		RoundDirectorGame nextRound = new RoundDirectorGame(gameSession.getGameType(), gameSession.getRoundNumber(), gameSession.getMaxRounds());
		
		List<Integer> usedIds = this.getUsedIds(gameSession.getSessionId());
		
		List<MovieRecord> movies = this.movieDao.getRandomMoviesByLimit(usedIds, GamesConstants.NUMBER_OF_MOVIES_FOR_DIRECTOR_GAME);
		
		MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, 0);
		
		this.setMovieDataIntoResponseRound(movie, nextRound);
		
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
	
	private void setMovieDataIntoResponseRound(MovieRecord movie, RoundDirectorGame nextRound) {
		if(movie != null) {
			nextRound.setMovieId(movie.movieId());
			nextRound.setMovieTitle(movie.title());
			nextRound.setPosterUrl(movie.posterUrl());
			nextRound.setIdCorrectAnswer(movie.director().directorId());
		}
	}
	
	
	private List<Integer> getUsedIds(String sessionId) {
		List<Integer> usedIds = mapSession.get(sessionId);
		
		if(usedIds == null) {
			return new ArrayList<Integer>();
		}
		
		return usedIds;
	}
	
	private void addUsedIdsToList(List<MovieRecord> movies, List<Integer> usedIds, Integer idsToAdd) {		
		for(int i = 0; i < idsToAdd; i++) {
			MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, i);
			usedIds.add(movie.director().directorId());
		}		
	}



}
