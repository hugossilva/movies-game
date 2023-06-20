package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import br.com.game.movies.comparators.MoviesComparator;
import br.com.game.movies.constants.GamesConstants;
import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.constants.UrlConstants;
import br.com.game.movies.dao.interfaces.MovieDAO;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.response.CardGame;
import br.com.game.movies.entity.response.CardGameResult;
import br.com.game.movies.entity.response.RoundCardGame;
import br.com.game.movies.entity.response.RoundResultCardGame;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.MovieRecord;
import br.com.game.movies.service.interfaces.GameService;
import br.com.game.movies.utils.DaoUtils;
import br.com.game.movies.utils.ServicesUtils;

@Service
public class CardGameServiceImpl implements GameService  {
	
	@Autowired
	@Qualifier("CardGameSession")
	private Map<String, List<Integer>> mapSession;
	
	@Autowired
	private MovieDAO movieDao;
	
	@Override
	public GameTypeEnum getGameType() {
		return GameTypeEnum.CARD_GAME;
	}
	
	@Override
	public CardGame startNewGame() {
		CardGame newCardGame = new CardGame();
		newCardGame.setMessage(MessagesConstants.START_NEW_DIRECTORS_GAME);
		newCardGame.setNextUrl(UrlConstants.NEXT_ROUND);
		newCardGame.setMaxNumberOfRounds(GamesConstants.NUMBER_MAX_ROUNDS_CARD_GAME);		
		return newCardGame;
	}
	
	@Override
	public RoundCardGame getNextRound(GameSession gameSession) {
		RoundCardGame response = new RoundCardGame(GameTypeEnum.CARD_GAME, gameSession.getRoundNumber(), GamesConstants.NUMBER_MAX_ROUNDS_CARD_GAME);
		
		List<Integer> usedIds = this.getUsedIds(gameSession.getSessionId());
		
		List<MovieRecord> movies = this.movieDao.getRandomMoviesByLimit(usedIds, GamesConstants.NUMBER_OF_MOVIES_FOR_CARD_GAME);
		
		this.addUsedIdsToList(movies, usedIds, GamesConstants.NUMBER_OF_MOVIES_FOR_CARD_GAME);
		
		mapSession.put(gameSession.getSessionId(), usedIds);
		
		this.addCardsToResponse(movies, response);
		
		MovieRecord correctAnswer = this.getCorrectAnswerFromMovies(movies);
		
		response.setIdCorrectAnswer(correctAnswer.movieId());
		
		return response;
	}
	
	@Override
	public RoundResultCardGame getRoundResult(GameSession gameSession,  int idUserAnswer) {
		RoundResultCardGame roundResult = new RoundResultCardGame();
		
		roundResult.setIdCorrectAnswer(gameSession.getIdRoundCorrectAnswer());
		roundResult.setCorrectMovieId(gameSession.getIdRoundCorrectAnswer());
		roundResult.setMessage(MessagesConstants.INCORRECT_ANSWER_CARD_GAME);
		roundResult.setCorrectAnswer(false);
		
		if(gameSession.getIdRoundCorrectAnswer() == idUserAnswer) {
			roundResult.setMessage(MessagesConstants.CORRECT_ANSWER_CARD_GAME);
			roundResult.setCorrectAnswer(true);
		}		
		
		return roundResult;
	}
	
	@Override
	public CardGameResult getGameResult(GameSession gameSession) {
		CardGameResult gameResult = new CardGameResult();
		
		gameResult.setGameType(GameTypeEnum.CARD_GAME);
		gameResult.setNumberOfCorrectAnswers(gameSession.getCorrectAnswers());
		gameResult.setNumberOfRoundsPlayed(gameSession.getRoundNumber());
		gameResult.setNumberOfTotalRounds(gameSession.getMaxRounds());
		gameResult.setTotalPointsScored(gameResult.getRelevanceMultiplier() * gameSession.getCorrectAnswers());		
		
		return gameResult;
	}
	
	private MovieRecord getCorrectAnswerFromMovies(List<MovieRecord> movies) {
		MoviesComparator movieComparator = new MoviesComparator();
		return movies.stream().max(movieComparator).get();
	}
	
	private List<Integer> getUsedIds(String sessionId) {
		List<Integer> usedIds = mapSession.get(sessionId);
		
		if(usedIds == null) {
			return new ArrayList<Integer>();
		}
		
		return usedIds;
	}
	
	private void addCardsToResponse(List<MovieRecord> movies, RoundCardGame response) {
		MovieRecord movie1 = ServicesUtils.getValueFromListByIndex(movies, 0);
		MovieRecord movie2 = ServicesUtils.getValueFromListByIndex(movies, 1);
		
		response.setCard1(DaoUtils.fillMovieFromDTO(movie1));
		response.setCard2(DaoUtils.fillMovieFromDTO(movie2));		
	}
	
	private void addUsedIdsToList(List<MovieRecord> movies, List<Integer> usedIds, Integer idsToAdd) {		
		for(int i = 0; i < idsToAdd; i++) {
			MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, i);
			usedIds.add(movie.movieId());
		}		
	}


}