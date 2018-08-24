package org.com.character;

public class Warrior extends Character {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Warrior() {
		initilize();
		setAttackingMinPower(5);
		setAttackingMaxPower(30);
	}

	@Override
	public CharacterType getCharacterType() {
		return CharacterType.Warrior;
	}

}
