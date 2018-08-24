package org.com.weapon;

public class Sword extends Weapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int damageEffect;

	public Sword() {

		this.damageEffect = 2;
	}

	public String getWeaponName() {
		return "Sword";
	}

	public int getDamageEffect() {
		return damageEffect;
	}

}
