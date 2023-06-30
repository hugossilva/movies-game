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
	
	public static final String INSERT_RANKING_POINTS = 
			"INSERT INTO RANKING_POINTS (USER_ID, GAME_TYPE, POINTS) "
			+ "VALUES"
			+ " (:user_id, :game_type, :points) ";
	
	public static final String QUERY_GENERAL_RANKING = 
			"SELECT RP.RANKING_POINTS_ID, RP.USER_ID, U.USERNAME, SUM(RP.POINTS) as POINTS "
			+ "FROM RANKING_POINTS RP "
			+ "JOIN USERS U "
			+ "ON RP.USER_ID = U.USER_ID "
			+ "GROUP BY RP.USER_ID "
			+ "ORDER BY RP.USER_ID "
			+ "ASC";
	
	public static final String QUERY_RANKING_BY_GAME_TYPE = 
			"SELECT RP.USER_ID, U.USERNAME, SUM(RP.POINTS) "
			+ "FROM RANKING_POINTS RP "
			+ "JOIN USERS U "
			+ "ON RP.USER_ID = U.USER_ID "
			+ "WHERE RP.GAME_TYPE = :game_type "
			+ "GROUP BY RP.USER_ID "
			+ "ORDER BY RP.USER_ID "
			+ "ASC";
	
	
}
