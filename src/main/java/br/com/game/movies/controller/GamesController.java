package br.com.game.movies.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.game.movies.abstracts.ResponseGameResult;
import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.abstracts.ResponseRoundResult;
import br.com.game.movies.entity.request.GameRequest;
import br.com.game.movies.entity.request.RoundResultRequest;
import br.com.game.movies.entity.response.ResponseBody;
import br.com.game.movies.service.interfaces.GamesService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/", produces = "application/json")
public class GamesController {
	
	@Autowired
	private GamesService games;
	
	
	@PostMapping("/new-game")
	public ResponseEntity<ResponseBody<ResponseNewGame>> Login(@RequestBody GameRequest newGameRequest, HttpServletRequest request) {		
		return games.startNewGame(newGameRequest, request);
	}
	
	@PostMapping("/round-game")
	public ResponseEntity<ResponseBody<ResponseRound>> getRoundForCardGame(HttpServletRequest request) {
		return games.nextRoundGame(request);
	}
	
	@PostMapping("/round-result")
	public ResponseEntity<ResponseBody<ResponseRoundResult>> getRoundResult(@RequestBody RoundResultRequest roundResult, HttpServletRequest request) {
		return games.getRoundResult(roundResult, request);
	}
	
	@GetMapping("/game-result")
	public ResponseEntity<ResponseBody<ResponseGameResult>> getGameResult(HttpServletRequest request) {
		return games.getGameResult(request);
	}	
}

