package org.com.weapon;

public class Rifle extends Weapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rifle() {

		this.damageEffect = 5;
	}

	public String getWeaponName() {
		return "Rifle";
	}

	public int getDamageEffect() {
		return damageEffect;
	}

}
