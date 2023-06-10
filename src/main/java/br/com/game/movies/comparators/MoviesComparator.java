package br.com.game.movies.comparators;

import java.util.Comparator;

import br.com.game.movies.records.MovieRecord;

public class MoviesComparator implements Comparator<MovieRecord> {

	@Override
	public int compare(MovieRecord o1, MovieRecord o2) {
		if(o1.imdbRating() > o2.imdbRating()) {
			return 1;
		} 
		
		
		if(o1.imdbRating() < o2.imdbRating()) {
			return -1;
		}
		
		return 0;
	}

}
