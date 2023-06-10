package br.com.game.movies.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.game.movies.abstracts.ResponseNewGame;
import br.com.game.movies.entity.response.CardGame;
import br.com.game.movies.entity.response.DirectorGame;
import br.com.game.movies.enums.GameTypeEnum;
import br.com.game.movies.service.interfaces.GameService;
import jakarta.annotation.PostConstruct;

@Service
public class GameServiceFactory {
	
    @Autowired
    private List<GameService> gamesService;
    
    private static final Map<GameTypeEnum, GameService> myServiceCache = new HashMap<>();
    
    
    @PostConstruct
    public void initMyServiceCache() {
        for(GameService service : gamesService) {
            myServiceCache.put(service.getGameType(), service);
        }
    }    
    
    public GameService getService(GameTypeEnum type) {
    	GameService service = myServiceCache.get(type);
        if(service == null) throw new RuntimeException("Unknown service type: " + type.getValue());
        return service;
    }
    
    public ResponseNewGame getNewGameClassByGameType(GameTypeEnum type) {    	
    	switch(type) {
    	case CARD_GAME: 
    		return new CardGame();    		
		case DIRECTORS_GAME:
			return new DirectorGame();
		default:
			return null;
    	}
    }
}
