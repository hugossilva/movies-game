package br.com.game.movies.enums;

public enum AttributesNamesEnum {
	
	LOGIN("LOGIN"),
	USER_ROLE("USER_ROLE"),
	GAME_SESSION("GAME_SESSION"),
	USED_CARD_GAME_COMBINATION("USED_CG_COMBINATION"),
	USER_NAME("USER_NAME"),
	USER_ID("USER_ID");
	
	private String attributesNames;
	
	AttributesNamesEnum(String attributesNames) {
		this.attributesNames = attributesNames;
	}
	
	public String getValue() {
		return this.attributesNames;
	}

}
