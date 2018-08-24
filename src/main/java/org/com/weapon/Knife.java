package org.com.weapon;

public class Knife extends Weapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Knife() {
		this.damageEffect = 1;
	}

	public String getWeaponName() {
		return "Knife";
	}

	public int getDamageEffect() {
		return damageEffect;
	}

}
