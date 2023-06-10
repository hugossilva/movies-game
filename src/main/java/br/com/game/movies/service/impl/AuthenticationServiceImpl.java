package br.com.game.movies.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.dao.interfaces.UserDAO;
import br.com.game.movies.entity.GameSession;
import br.com.game.movies.entity.UserInfo;
import br.com.game.movies.enums.AttributesNamesEnum;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.records.UserRecord;
import br.com.game.movies.service.interfaces.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserDAO userDao;
	
	public ResponseEntity<String> authenticateUser(UserInfo userInfo, HttpServletRequest request) {
		ResponseEntity<String> response = null;		
				
		response = this.checkIfUserIsAlreadyLogedIn(request);
		
		UserRecord user = this.getUsersInfoFromDataBase(userInfo);
		
		response = this.checkIfUserIsValid(user);		
		
		this.processAuthentication(response, request, user);
		
		return response;		
	}
	
	public ResponseEntity<String> logoutUser(HttpServletRequest request) {		
		request.getSession().invalidate();		
		return new ResponseEntity<String>("Deslogado com sucesso.", HttpStatus.OK);		
	}
	
	private ResponseEntity<String> checkIfUserIsAlreadyLogedIn(HttpServletRequest request) {
		Boolean login = (Boolean) request.getSession().getAttribute("LOGIN");
		if(login != null) {			
			return new ResponseEntity<String>(MessagesConstants.USER_ALREADY_LOGED_IN, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
		return null;
	}
	
	private ResponseEntity<String> checkIfUserIsValid(UserRecord user) {
		if(user == null) {
			return new ResponseEntity<String>(MessagesConstants.USER_IS_INVALID, HttpStatus.UNAUTHORIZED);			
		}		
		return null;
	}
	
	private void processAuthentication(ResponseEntity<String> response, HttpServletRequest request, UserRecord user) {
		if(response == null) {
			GameSession gameSession = this.getDefaultGameSession(request.getSession().getId());
			response = new ResponseEntity<String>("Login Efetuado com sucesso.", HttpStatus.OK);
			
			request.getSession().setAttribute(AttributesNamesEnum.USER_ROLE.getValue(), user.role());
			request.getSession().setAttribute(AttributesNamesEnum.LOGIN.getValue(), true);
			request.getSession().setAttribute(AttributesNamesEnum.GAME_SESSION.getValue(), gameSession);	
		}		
	}
	
	private GameSession getDefaultGameSession(String sessionId) {
		GameSession gameSession = new GameSession();
		gameSession.setSessionId(sessionId);
		gameSession.setCorrectAnswers(0);
		gameSession.setRoundNumber(0);
		gameSession.setMaxRounds(0);
		gameSession.setGameType(GameTypeEnum.NONE);
		gameSession.setNextRound(true);
		return gameSession;
	}
	
	private UserRecord getUsersInfoFromDataBase(UserInfo userInfo) {
		String username = userInfo.getUsername().toLowerCase().trim();
		String password = userInfo.getPassword().trim();
		return userDao.getUserInfo(username, password);		
	}
}
