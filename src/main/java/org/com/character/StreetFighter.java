package org.com.character;

public class StreetFighter extends Character {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StreetFighter() {
		initilize();
		setAttackingMinPower(2);
		setAttackingMaxPower(4);
	}

	@Override
	public CharacterType getCharacterType() {
		return CharacterType.StreetFighter;
	}

}
