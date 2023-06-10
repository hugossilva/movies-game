package br.com.game.movies.dao.interfaces;



import br.com.game.movies.records.UserRecord;



public interface UserDAO {
	
	public UserRecord getUserInfo(String username, String password);

}
