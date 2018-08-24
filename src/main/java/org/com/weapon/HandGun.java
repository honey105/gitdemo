package org.com.weapon;

public class HandGun extends Weapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HandGun() {
		this.damageEffect = 3;
	}

	public String getWeaponName() {
		return "HandGun";
	}

	public int getDamageEffect() {
		return damageEffect;
	}

}
