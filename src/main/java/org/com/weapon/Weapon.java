package org.com.weapon;

import java.io.Serializable;

public abstract class Weapon implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected int damageEffect;

	public abstract int getDamageEffect();

	public abstract String getWeaponName();

}
