package org.com.weapon;

public class BowAndArrow extends Weapon {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BowAndArrow() {

		this.damageEffect = 5;
	}

	public String getWeaponName() {
		return "BowAndArrow";
	}

	public int getDamageEffect() {
		return damageEffect;
	}
}
