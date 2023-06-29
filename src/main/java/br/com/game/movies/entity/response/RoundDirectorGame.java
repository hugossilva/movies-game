package br.com.game.movies.entity.response;

import java.util.ArrayList;
import java.util.List;

import br.com.game.movies.abstracts.ResponseRound;
import br.com.game.movies.entity.Director;
import br.com.game.movies.enums.GameTypeEnum;

public class RoundDirectorGame extends ResponseRound {
	
	private Integer movieId;
	private String movieTitle;
	private String posterUrl;
	
	private List<Director> directors;

	public RoundDirectorGame(GameTypeEnum gameType, int round, int maxRounds) {
		super(gameType, round, maxRounds);
		this.directors = new ArrayList<Director>();
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

	public List<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(List<Director> directors) {
		this.directors = directors;
	}

}
