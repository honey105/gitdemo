package org.com.game.state;

import java.io.Serializable;
import java.util.List;

import org.com.character.Character;
import org.com.weapon.Weapon;

public class GameState implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private GameLevel gameLevel;
	private Character mainCharacter;
	private Character enemy;
	private int enemiesKilledCount;
	private List<Weapon> unlockedWeapons;
	
	private int healthBoostingElixir;

	public Character getMainCharacter() {
		return mainCharacter;
	}

	public void setMainCharacter(Character mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	public Character getEnemy() {
		return enemy;
	}

	public void setEnemy(Character enemy) {
		this.enemy = enemy;
	}

	public GameLevel getGameLevel() {
		return gameLevel;
	}

	public void setGameLevel(GameLevel gameLevel) {
		this.gameLevel = gameLevel;
	}

	public int getEnemiesKilledCount() {
		return enemiesKilledCount;
	}

	public void setEnemiesKilledCount(int enemiesKilledCount) {
		this.enemiesKilledCount = enemiesKilledCount;
	}

	public List<Weapon> getUnlockedWeapons() {
		return unlockedWeapons;
	}

	public void setUnlockedWeapons(List<Weapon> unlockedWeapons) {
		this.unlockedWeapons = unlockedWeapons;
	}

	public int getHealthBoostingElixir() {
		return healthBoostingElixir;
	}

	public void setHealthBoostingElixir(int healthBoostingElixir) {
		this.healthBoostingElixir = healthBoostingElixir;
	}

}
