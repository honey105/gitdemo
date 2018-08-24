package org.com.character;

public class Knight extends Character {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Knight() {
		initilize();
		setAttackingMinPower(4);
		setAttackingMaxPower(7);
	}

	@Override
	public CharacterType getCharacterType() {
		return CharacterType.Knight;
	}

}
