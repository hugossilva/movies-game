package br.com.game.movies.service.interfaces;

import org.springframework.http.ResponseEntity;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.abstracts.ResponseRoundResult;
import br.com.game.movies.entity.request.NewGameRequest;
import br.com.game.movies.entity.request.RoundResultRequest;
import br.com.game.movies.entity.response.ResponseBody;
import jakarta.servlet.http.HttpServletRequest;

public interface GamesService {
	public ResponseEntity<ResponseBody<ResponseNewGame>> startNewGame(NewGameRequest newGameRequest, HttpServletRequest request);
	public ResponseEntity<ResponseBody<ResponseRound>> nextRoundGame(HttpServletRequest request);
	public ResponseEntity<ResponseBody<ResponseRoundResult>> getRoundResult(RoundResultRequest roundResult,HttpServletRequest request);
	public ResponseEntity<ResponseBody<ResponseGameResult>> getGameResult(HttpServletRequest request);
}
