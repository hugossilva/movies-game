package br.com.game.movies.factory;

import org.springframework.stereotype.Service;

import br.com.game.movies.constants.MessagesConstants;
import br.com.game.movies.constants.UrlConstants;
import br.com.game.movies.enums.GameTypeEnum;

@Service
public class GameDetailsFactory {
	
	public String getNewGameMessageByGameType(GameTypeEnum type) {
		switch(type) {
			case CARD_GAME: {
				return MessagesConstants.START_NEW_CARD_GAME;
			}
			case DIRECTORS_GAME: {
				return MessagesConstants.START_NEW_DIRECTORS_GAME;
			}
			default :{
				return MessagesConstants.NONE_GAME;
			}			
		}		
	}
	
	public String getNextUrlByGameType(GameTypeEnum type) {
		switch(type) {
			case CARD_GAME: {
				return UrlConstants.CARD_GAME_PAGE;
			}
			case DIRECTORS_GAME: {
				return UrlConstants.DIRECTOR_GAME_PAGE;
			}
			default :{
				return UrlConstants.NONE_GAME_PAGE;
			}			
		}
			
	}

}
