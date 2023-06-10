package br.com.game.movies.entity.response;

import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.entity.Movie;
import br.com.game.movies.enums.GameTypeEnum;

public class RoundCardGame extends ResponseRound {	
	
	private Movie card1;
	private Movie card2;
	
	public RoundCardGame(GameTypeEnum gameType, int round, int maxRounds) {
		super(gameType, round, maxRounds);
	}
	
	public Movie getCard1() {
		return card1;
	}
	public void setCard1(Movie card1) {
		this.card1 = card1;
	}
	public Movie getCard2() {
		return card2;
	}
	public void setCard2(Movie card2) {
		this.card2 = card2;
	}
}