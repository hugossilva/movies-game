package br.com.game.movies.records;

import java.util.List;

public record MovieRecord(Integer movieId, String title, String startYear, String posterUrl, Double imdbRating, DirectorRecord director, List<GenreRecord> genres) {}
