package br.com.game.movies.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.game.movies.entity.RankingPoint;
import br.com.game.movies.entity.request.GameRequest;
import br.com.game.movies.service.interfaces.RankingService;

@RestController
@CrossOrigin
@RequestMapping(value = "/", produces = "application/json")
public class RankingController {
	
	@Autowired
	private RankingService rankingService;
	
	@PostMapping("/general-ranking")
	public List<RankingPoint> getGeneralRanking() {
		return this.rankingService.getGenralRanking();
	}
	
	@PostMapping("/ranking-by-game")
	public List<RankingPoint> getRankingByGameType(@RequestBody GameRequest gameRequest) {
		return this.rankingService.getRankingByGameType(gameRequest.getGameType());
	}

}
