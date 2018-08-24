package org.com.character;

import java.io.Serializable;

import org.com.weapon.Weapon;

public abstract class Character implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int health;
	private boolean protectionJacket;
	private int attackingMinPower;
	private int attackingMaxPower;
	private Weapon weapon;
	private String name;
	private int experience;
	private CharacterStatus characterStatus;

	public void initilize() {
		this.health = 100;
		this.experience = 1;
		characterStatus = CharacterStatus.live;
	}

	public void healthImpacted(int damageEffect) {
		this.health = this.health - damageEffect;
	}

	public abstract CharacterType getCharacterType();

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public boolean isProtectionJacket() {
		return protectionJacket;
	}

	public void setProtectionJacket(boolean protectionJacket) {
		this.protectionJacket = protectionJacket;
	}

	public int getAttackingMinPower() {
		return attackingMinPower;
	}

	public void setAttackingMinPower(int attackingMinPower) {
		this.attackingMinPower = attackingMinPower;
	}

	public int getAttackingMaxPower() {
		return attackingMaxPower;
	}

	public void setAttackingMaxPower(int attackingMaxPower) {
		this.attackingMaxPower = attackingMaxPower;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public CharacterStatus getCharacterStatus() {
		return characterStatus;
	}

	public void setCharacterStatus(CharacterStatus characterStatus) {
		this.characterStatus = characterStatus;
	}

	public boolean isDead() {
		return CharacterStatus.dead == this.characterStatus;
	}

}
