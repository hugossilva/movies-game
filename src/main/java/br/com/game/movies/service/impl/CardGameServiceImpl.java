package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.game.movies.comparators.MoviesComparator;
import br.com.game.movies.constants.GamesConstants;
import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.constants.UrlConstants;
import br.com.game.movies.dao.interfaces.MovieDAO;
import br.com.game.movies.entity.response.CardGame;
import br.com.game.movies.entity.response.CardGameResult;
import br.com.game.movies.entity.response.RoundCardGame;
import br.com.game.movies.entity.response.RoundResultCardGame;
import br.com.game.movies.entity.session.CardGameSession;
import br.com.game.movies.entity.session.GameSession;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.MovieRecord;
import br.com.game.movies.service.interfaces.GameService;
import br.com.game.movies.service.interfaces.RankingService;
import br.com.game.movies.utils.DaoUtils;
import br.com.game.movies.utils.ServicesUtils;

@Service
public class CardGameServiceImpl implements GameService  {
	
	@Autowired
	private MovieDAO movieDao;
	
	@Autowired
	private RankingService rankingService;
	
	@Override
	public GameTypeEnum getGameType() {
		return GameTypeEnum.CARD_GAME;
	}
	
	@Override
	public GameSession getGameSession(String sessionId, Integer userId) {
		CardGameSession session = new CardGameSession();
		session.setCorrectAnswers(0);
		session.setGameType(GameTypeEnum.CARD_GAME);
		session.setMaxRounds(GamesConstants.NUMBER_MAX_ROUNDS_CARD_GAME);
		session.setNextRound(true);
		session.setRoundNumber(0);
		session.setSessionId(sessionId);
		session.setMoviesId(new ArrayList<Integer>());
		session.setUserId(userId);
		return session;
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
		CardGameSession cardGameSession = (CardGameSession) gameSession;
		
		RoundCardGame response = new RoundCardGame(GameTypeEnum.CARD_GAME, cardGameSession.getRoundNumber(), GamesConstants.NUMBER_MAX_ROUNDS_CARD_GAME);		
		
		List<MovieRecord> movies = this.movieDao.getRandomMoviesByLimit(cardGameSession.getMoviesId(), GamesConstants.NUMBER_OF_MOVIES_FOR_CARD_GAME);
		
		this.addUsedIdsToList(movies, cardGameSession, GamesConstants.NUMBER_OF_MOVIES_FOR_CARD_GAME);				
		
		this.addCardsToResponse(movies, response);
		
		MovieRecord correctAnswer = this.getCorrectAnswerFromMovies(movies);
		
		response.setIdCorrectAnswer(correctAnswer.movieId());
		
		return response;
	}
	
	@Override
	public RoundResultCardGame getRoundResult(GameSession gameInfo,  int idUserAnswer) {
		RoundResultCardGame roundResult = new RoundResultCardGame();
		
		roundResult.setIdCorrectAnswer(gameInfo.getIdRoundCorrectAnswer());
		roundResult.setCorrectMovieId(gameInfo.getIdRoundCorrectAnswer());
		roundResult.setMessage(MessagesConstants.INCORRECT_ANSWER);
		roundResult.setCorrectAnswer(false);
		
		if(gameInfo.getIdRoundCorrectAnswer() == idUserAnswer) {
			roundResult.setMessage(MessagesConstants.CORRECT_ANSWER);
			roundResult.setCorrectAnswer(true);
		}		
		
		return roundResult;
	}
	
	@Override
	public CardGameResult getGameResult(GameSession gameInfo) {
		CardGameResult gameResult = new CardGameResult();
		
		gameResult.setUserId(gameInfo.getUserId());
		gameResult.setGameType(GameTypeEnum.CARD_GAME);
		gameResult.setNumberOfCorrectAnswers(gameInfo.getCorrectAnswers());
		gameResult.setNumberOfRoundsPlayed(gameInfo.getRoundNumber());
		gameResult.setNumberOfTotalRounds(gameInfo.getMaxRounds());
		gameResult.setTotalPointsScored(gameResult.getRelevanceMultiplier() * gameInfo.getCorrectAnswers());
		
		this.rankingService.saveRankingByGameResult(gameResult);
		
		return gameResult;
	}
	
	private MovieRecord getCorrectAnswerFromMovies(List<MovieRecord> movies) {
		MoviesComparator movieComparator = new MoviesComparator();
		return movies.stream().max(movieComparator).get();
	}
	
	private void addCardsToResponse(List<MovieRecord> movies, RoundCardGame response) {
		MovieRecord movie1 = ServicesUtils.getValueFromListByIndex(movies, 0);
		MovieRecord movie2 = ServicesUtils.getValueFromListByIndex(movies, 1);
		
		response.setCard1(DaoUtils.fillMovieFromDTO(movie1));
		response.setCard2(DaoUtils.fillMovieFromDTO(movie2));		
	}
	
	private void addUsedIdsToList(List<MovieRecord> movies, CardGameSession gameSession, Integer idsToAdd) {		
		for(int i = 0; i < idsToAdd; i++) {
			MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, i);
			gameSession.getMoviesId().add(movie.movieId());
		}		
	}




}