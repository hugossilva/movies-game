package br.com.game.movies.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.game.movies.entity.UserInfo;
import br.com.game.movies.service.interfaces.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/", produces = "application/json")
public class LoginContoller {
	
	@Autowired
	private AuthenticationService authentication;	
	
	@PostMapping("/logar")
	public ResponseEntity<String> Login(@RequestBody UserInfo userInfo, HttpServletRequest request) {		
		return this.authentication.authenticateUser(userInfo, request);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return new ResponseEntity<String>("Deslogado com sucesso.", HttpStatus.OK);	
	}	
}
