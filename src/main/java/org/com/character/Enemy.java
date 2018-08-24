package org.com.character;

public class Enemy extends Character {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Enemy() {
		initilize();
	}

	@Override
	public CharacterType getCharacterType() {
		return CharacterType.Enemy;
	}

}
