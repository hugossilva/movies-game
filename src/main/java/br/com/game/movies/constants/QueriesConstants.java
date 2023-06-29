package br.com.game.movies.constants;

public final class QueriesConstants {
	
	public static final String QUERY_DIRECTORS = " SELECT D.DIRECTOR_ID, D.DIRECTOR FROM DIRECTORS D "
			+ " JOIN MOVIES M ON M.DIRECTOR_ID = D.DIRECTOR_ID "
			+ "	AND M.MOVIE_ID = :movie_id ";
	
	public static final String QUERY_GENRES_BY_MOVIE_ID = 	" SELECT G.GENRE_ID, G.GENRE FROM GENRES G "
			+ " JOIN GENRE_MOVIES GM ON G.GENRE_ID = GM.GENRE_ID " 
			+ " WHERE GM.MOVIE_ID = :movie_id ";
	
	public static final String QUERY_MOVIES = "SELECT MOVIE_ID, TITLE, START_YEAR, DIRECTOR_ID, POSTER_URL, IMDB_RATING FROM MOVIES";
	
	public static final String QUERY_RANDOM_MOVIES = "SELECT "
			+ "MOVIE_ID, "
			+ "TITLE, "
			+ "START_YEAR, "
			+ "DIRECTOR_ID, "
			+ "POSTER_URL, "
			+ "IMDB_RATING "
			+ "FROM MOVIES "
			+ "WHERE MOVIE_ID NOT IN (:movie_ids) "
			+ "ORDER BY RAND() LIMIT :limit";
	
	public static final String QUERY_RANDOM_DIRECTORS = "SELECT "
			+ "DIRECTOR_ID, "			
			+ "DIRECTOR "			
			+ "FROM DIRECTORS "
			+ "WHERE DIRECTOR_ID NOT IN (:director_ids) "
			+ "ORDER BY RAND() LIMIT :limit";
	
	public static final String QUERY_USERS = "SELECT U.USER_ID, U.USERNAME, U.PASSWORD, R.ROLE_NAME FROM USERS U "
			+ " JOIN ROLES R "
			+ " ON R.ROLE_ID = U.ROLE_ID "
			+ " WHERE U.USERNAME = :username "
			+ " AND U.PASSWORD = :password ";
}
