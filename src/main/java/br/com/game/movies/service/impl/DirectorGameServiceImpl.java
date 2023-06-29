package br.com.game.movies.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.game.movies.constants.GamesConstants;
import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.constants.UrlConstants;
import br.com.game.movies.dao.interfaces.DirectorsDAO;
import br.com.game.movies.dao.interfaces.MovieDAO;
import br.com.game.movies.entity.Director;
import br.com.game.movies.entity.response.DirectorGame;
import br.com.game.movies.entity.response.DirectorGameResult;
import br.com.game.movies.entity.response.RoundDirectorGame;
import br.com.game.movies.entity.response.RoundResultDirectorGame;
import br.com.game.movies.entity.session.DirectorGameSession;
import br.com.game.movies.entity.session.GameSession;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.DirectorRecord;
import br.com.game.movies.records.MovieRecord;
import br.com.game.movies.service.interfaces.GameService;
import br.com.game.movies.utils.ServicesUtils;

@Service
public class DirectorGameServiceImpl implements GameService {
	
	@Autowired
	private MovieDAO movieDao;
	
	@Autowired
	private DirectorsDAO directorDao;
	
	@Override
	public GameSession getGameSession(String sessionId) {
		DirectorGameSession session = new DirectorGameSession();
		session.setCorrectAnswers(0);
		session.setGameType(GameTypeEnum.DIRECTORS_GAME);
		session.setMaxRounds(GamesConstants.NUMBER_MAX_ROUNDS_DIRECTOR_GAME);
		session.setNextRound(true);
		session.setRoundNumber(0);
		session.setSessionId(sessionId);
		session.setMoviesId(new ArrayList<Integer>());
		session.setLastIdsDirectorsUsed(new ArrayList<Integer>());
		return session;
	}
	
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
		DirectorGameSession session = (DirectorGameSession) gameSession;
		
		RoundDirectorGame nextRound = new RoundDirectorGame(gameSession.getGameType(), gameSession.getRoundNumber(), gameSession.getMaxRounds());		
		
		List<MovieRecord> movies = this.movieDao.getRandomMoviesByLimit(session.getMoviesId(), GamesConstants.NUMBER_OF_MOVIES_FOR_DIRECTOR_GAME);		
		
		this.addUsedIdsToList(movies, session, GamesConstants.NUMBER_OF_MOVIES_FOR_DIRECTOR_GAME);
		
		MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, 0);
		List<Integer> lastUsedIds = new ArrayList<Integer>();
		
		lastUsedIds.add(movie.director().directorId());
		
		List<DirectorRecord> directors = directorDao.getRandomDirectorsByLimit(lastUsedIds, 3);		
		
		
		this.setMovieDataIntoResponseRound(movie, nextRound);
		
		DirectorRecord randomDirector1 = ServicesUtils.getValueFromListByIndex(directors, 0);
		DirectorRecord randomDirector2 = ServicesUtils.getValueFromListByIndex(directors, 1);
		DirectorRecord randomDirector3 = ServicesUtils.getValueFromListByIndex(directors, 2);
		
		nextRound.getDirectors().add(this.recordDirectorToDirector(randomDirector1));
		nextRound.getDirectors().add(this.recordDirectorToDirector(randomDirector2));
		nextRound.getDirectors().add(this.recordDirectorToDirector(randomDirector3));
		
		lastUsedIds.add(randomDirector1.directorId());
		lastUsedIds.add(randomDirector2.directorId());
		lastUsedIds.add(randomDirector3.directorId());
		
		session.setLastIdsDirectorsUsed(lastUsedIds);
		
		return nextRound;
	}

	@Override
	public RoundResultDirectorGame getRoundResult(GameSession gameSession, int idUserAnswer) {
		RoundResultDirectorGame roundResult = new RoundResultDirectorGame();		
		roundResult.setIdCorrectAnswer(gameSession.getIdRoundCorrectAnswer());		
		roundResult.setMessage(MessagesConstants.INCORRECT_ANSWER);
		roundResult.setCorrectAnswer(false);
		
		if(gameSession.getIdRoundCorrectAnswer() == idUserAnswer) {
			roundResult.setMessage(MessagesConstants.CORRECT_ANSWER);
			roundResult.setCorrectAnswer(true);
		}		
		
		return roundResult;
	}

	@Override
	public DirectorGameResult getGameResult(GameSession gameSession) {
		DirectorGameResult gameResult = new DirectorGameResult();
		
		gameResult.setGameType(GameTypeEnum.DIRECTORS_GAME);
		gameResult.setNumberOfCorrectAnswers(gameSession.getCorrectAnswers());
		gameResult.setNumberOfRoundsPlayed(gameSession.getRoundNumber());
		gameResult.setNumberOfTotalRounds(gameSession.getMaxRounds());
		gameResult.setTotalPointsScored(gameResult.getRelevanceMultiplier() * gameSession.getCorrectAnswers());
		
		return gameResult;
	}
	
	private void setMovieDataIntoResponseRound(MovieRecord movie, RoundDirectorGame nextRound) {
		if(movie != null) {
			nextRound.setMovieId(movie.movieId());
			nextRound.setMovieTitle(movie.title());
			nextRound.setPosterUrl(movie.posterUrl());
			nextRound.setIdCorrectAnswer(movie.director().directorId());
			nextRound.getDirectors().add(this.recordDirectorToDirector(movie.director()));
		}
	}
	
	private Director recordDirectorToDirector(DirectorRecord directorRecord) {
		Director director = new Director();
		director.setDirectorId(directorRecord.directorId());
		director.setName(directorRecord.director());
		return director;
	}

	
	private void addUsedIdsToList(List<MovieRecord> movies, DirectorGameSession session, Integer idsToAdd) {		
		for(int i = 0; i < idsToAdd; i++) {
			MovieRecord movie = ServicesUtils.getValueFromListByIndex(movies, i);
			session.getMoviesId().add(movie.movieId());
		}		
	}

}
