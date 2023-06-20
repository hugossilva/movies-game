package br.com.game.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.abstracts.ResponseRoundResult;
import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.request.NewGameRequest;
import br.com.game.movies.entity.request.RoundResultRequest;
import br.com.game.movies.entity.response.ResponseBody;
import br.com.game.movies.enums.AttributesNamesEnum;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.factory.GameDetailsFactory;
import br.com.game.movies.factory.GameServiceFactory;
import br.com.game.movies.service.interfaces.GameService;
import br.com.game.movies.service.interfaces.GamesService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class GamesServiceImpl implements GamesService {	
	
	@Autowired
	private GameServiceFactory gameServiceFactory;
	
	@Autowired
	private GameDetailsFactory gameDetailFactory;
	
	
	@Override
	public ResponseEntity<ResponseBody<ResponseNewGame>> startNewGame(NewGameRequest newGameRequest, HttpServletRequest request) {
		ResponseBody<ResponseNewGame> response;
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());	
		response = this.checkIfUserIsLoggedIn(currentGame);
		response = this.checkIfUserAlreadyStatedAGame(currentGame);
		response = this.checkIfGameCanBeStarted(newGameRequest, request, response);
		return new ResponseEntity<ResponseBody<ResponseNewGame>>(response, HttpStatus.resolve(response.getStatusCode()));
	}
	
	@Override
	public ResponseEntity<ResponseBody<ResponseRound>> nextRoundGame(HttpServletRequest request) {
		ResponseBody<ResponseRound> response;
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());		
		response = this.checkIfUserIsLoggedIn(currentGame);
		response = this.checkIfNotReachMaxRounds(currentGame, response);
		response = this.checkIfNextRoundIsAvailable(currentGame, response);
		response = this.getNextRoundInfo(currentGame, response, request);		
		return new ResponseEntity<ResponseBody<ResponseRound>>(response, HttpStatus.resolve(response.getStatusCode()));
	}
	
	
	@Override
	public ResponseEntity<ResponseBody<ResponseRoundResult>> getRoundResult(RoundResultRequest resultRequest,HttpServletRequest request) {		
		ResponseBody<ResponseRoundResult> response;
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());
		response = this.checkIfUserIsLoggedIn(currentGame);
		response = this.checkIfRoundIsActive(response, currentGame);
		response = this.getCorrectAnswerForRound(resultRequest, response,request);		
		return new ResponseEntity<ResponseBody<ResponseRoundResult>>(response, HttpStatus.resolve(response.getStatusCode()));		
	}
	
	@Override
	public ResponseEntity<ResponseBody<ResponseGameResult>> getGameResult(HttpServletRequest request) {
		ResponseBody<ResponseGameResult> response;
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());
		response = this.checkIfUserIsLoggedIn(currentGame);
		response = this.checkIfUserHasAGameToFinish(currentGame, response);
		response = this.finishGameAndGetResult(response, request);		
		return new ResponseEntity<ResponseBody<ResponseGameResult>>(response, HttpStatus.resolve(response.getStatusCode()));		
	}
	
	private ResponseBody<ResponseGameResult> finishGameAndGetResult(ResponseBody<ResponseGameResult> response, HttpServletRequest request) {
		if(response == null) {
			response = new ResponseBody<ResponseGameResult>();
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Jogo Finalizado com sucesso.");
			GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());
			GameService gameService = gameServiceFactory.getService(currentGame.getGameType());			
			
			ResponseGameResult gameResult = gameService.getGameResult(currentGame);
			
			response.setBody(gameResult);
		
			request.getSession().setAttribute(AttributesNamesEnum.GAME_SESSION.getValue(), getDefaultGameSession(request.getSession().getId()));
		}
		
		return response;
	}
	
	private GameSession getDefaultGameSession(String sessionId) {
		GameSession gameSession = new GameSession();
		gameSession.setSessionId(sessionId);
		gameSession.setCorrectAnswers(0);
		gameSession.setRoundNumber(0);
		gameSession.setMaxRounds(0);
		gameSession.setGameType(GameTypeEnum.NONE);
		gameSession.setNextRound(true);
		return gameSession;
	}
	
	private ResponseBody<ResponseGameResult> checkIfUserHasAGameToFinish(GameSession currentGame, ResponseBody<ResponseGameResult> response) {
		if(response == null && (currentGame == null || currentGame.getGameType().equals(GameTypeEnum.NONE))) {
			response = new ResponseBody<ResponseGameResult>();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(MessagesConstants.NONE_GAME_TO_FINISH);
		}		
		return response;
	}
	
	private ResponseBody<ResponseRoundResult> getCorrectAnswerForRound(RoundResultRequest resultRequest, ResponseBody<ResponseRoundResult> response, HttpServletRequest request) {
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());
		if(response == null && !currentGame.isNextRound()) {			
			response = new ResponseBody<ResponseRoundResult>();
			response.setStatusCode(HttpStatus.OK.value());
			GameService gameService = gameServiceFactory.getService(currentGame.getGameType());
			ResponseRoundResult roundResult = gameService.getRoundResult(currentGame, resultRequest.getIdAnswer());	
			
			currentGame.setNextRound(true);
			currentGame.setReasonNextRound("");
			
			if(roundResult.isCorrectAnswer()) {
				currentGame.setCorrectAnswers(currentGame.getCorrectAnswers() + 1);
			}
			
			roundResult.setNumberOfCorrectAnswers(currentGame.getCorrectAnswers());
			
			response.setMessage(roundResult.getMessage());
			response.setBody(roundResult);
			
			request.getSession().setAttribute(AttributesNamesEnum.GAME_SESSION.getValue(), currentGame);
			
		}		
		return response;		
	}
	
	private ResponseBody<ResponseRoundResult> checkIfRoundIsActive(ResponseBody<ResponseRoundResult> response, GameSession currentGame) {
		if(response == null && currentGame.isNextRound()) {
			response = new ResponseBody<ResponseRoundResult>();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(MessagesConstants.NONE_ACTIVED_ROUND);
		}	
		
		return response;		
	}
	
	private <T> ResponseBody<T> checkIfUserIsLoggedIn(GameSession currentGame) {
		ResponseBody<T> response = new ResponseBody<T>();
		if(currentGame == null) {
			response.setStatusCode(HttpStatus.UNAUTHORIZED.value());
			response.setMessage(MessagesConstants.USER_NOT_LOGED_IN);			
			return response;
		}
		return null;		
	}
	
	private ResponseBody<ResponseRound> checkIfNextRoundIsAvailable(GameSession currentGame, ResponseBody<ResponseRound> response) {
		if(response == null && currentGame != null && !currentGame.isNextRound()) {
			response = new ResponseBody<ResponseRound>();
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setMessage(currentGame.getReasonNextRound());	
		}		
		return response;
	}
	
	private ResponseBody<ResponseRound> checkIfNotReachMaxRounds(GameSession currentGame, ResponseBody<ResponseRound> response) {
		if(response == null && currentGame != null && !currentGame.isNextRound()) {
			if((currentGame.getRoundNumber() + 1) > currentGame.getMaxRounds()) {
				response = new ResponseBody<ResponseRound>();
				response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
				response.setMessage(MessagesConstants.MAX_ROUND_REACH);	
			}
		}		
		return response;		
	}
	
	private ResponseBody<ResponseRound> getNextRoundInfo(GameSession currentGame, ResponseBody<ResponseRound> response, HttpServletRequest request) {
		if(response == null) {
			response = new ResponseBody<ResponseRound>();
			currentGame.setRoundNumber(currentGame.getRoundNumber() + 1);
			currentGame.setNextRound(false);
			currentGame.setReasonNextRound(MessagesConstants.ACTIVE_ROUND);			
			
			GameService gameService = gameServiceFactory.getService(currentGame.getGameType());
			ResponseRound round = gameService.getNextRound(currentGame);
			
			currentGame.setIdRoundCorrectAnswer(round.getIdCorrectAnswer());
			
			request.getSession().setAttribute(AttributesNamesEnum.GAME_SESSION.getValue(), currentGame);
			
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage(MessagesConstants.NEW_ROUND);
			response.setBody(round);
		}		
		
		return response;
	}
	
	private ResponseBody<ResponseNewGame> checkIfUserAlreadyStatedAGame(GameSession currentGame) {		
		if(currentGame != null && !currentGame.getGameType().equals(GameTypeEnum.NONE)) {
			ResponseBody<ResponseNewGame> response = new ResponseBody<ResponseNewGame>();
			response.setMessage(MessagesConstants.GAME_ALREADY_STARTED);
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return response;
		}
		return null;		
	}
	
	private ResponseBody<ResponseNewGame> checkIfGameCanBeStarted(NewGameRequest newGameRequest, HttpServletRequest request, ResponseBody<ResponseNewGame> response) {
		GameSession currentGame = (GameSession) request.getSession().getAttribute(AttributesNamesEnum.GAME_SESSION.getValue());
		if(response == null && currentGame != null && currentGame.getGameType().equals(GameTypeEnum.NONE)) {
			response = new ResponseBody<ResponseNewGame>();
			response.setStatusCode(HttpStatus.OK.value());
			ResponseNewGame newGame = this.startNewGameByGameType(newGameRequest);			
			this.putNewGameInfoInCurrentGame(newGameRequest, currentGame);
			request.getSession().setAttribute(AttributesNamesEnum.GAME_SESSION.getValue(), currentGame);
			this.fillNewGameInfoByGameType(newGameRequest, newGame, response);
			return response;
		}
		
		return response;
	}
	
	private ResponseNewGame startNewGameByGameType(NewGameRequest newGameRequest) {
		GameService gameService = gameServiceFactory.getService(newGameRequest.getGameType());
		return gameService.startNewGame();
	}
	
	private void putNewGameInfoInCurrentGame(NewGameRequest newGameRequest, GameSession currentGame) {
		currentGame.setGameType(newGameRequest.getGameType());
	}
	
	private void fillNewGameInfoByGameType(NewGameRequest newGameRequest, ResponseNewGame newGame, ResponseBody<ResponseNewGame> response) {
		newGame.setGameStarted(true);
		newGame.setNextUrl(gameDetailFactory.getNextUrlByGameType(newGameRequest.getGameType()));
		response.setMessage(gameDetailFactory.getNewGameMessageByGameType(newGameRequest.getGameType()));
		response.setBody(newGame);
	}
}
