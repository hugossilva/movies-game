package br.com.game.movies.service.interfaces;

import org.springframework.http.ResponseEntity;

import br.com.game.movies.entity.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {
	public ResponseEntity<String> authenticateUser(UserInfo userInfo, HttpServletRequest request);
}
