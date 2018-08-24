package org.com.game.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.com.character.Character;
import org.com.character.CharacterFactory;
import org.com.character.CharacterType;
import org.com.command.CommandResult;
import org.com.command.CommandStatus;
import org.com.game.state.GameLevel;
import org.com.game.state.GameState;
import org.com.weapon.Weapon;
import org.com.weapon.WeaponFactory;
import org.com.weapon.WeaponType;

/**
 * This class is the heart of shooting game. It actually update the Game state
 * depending upon the action. It is central controller and hold the business
 * logics.
 * 
 * @author ashokkumar01
 *
 */
public class GameController {

	private GameState gameState;

	private final String gameStateFileName = "gameState.txt";

	private boolean newGameLoaded = false;

	public static final Random RANDOM = new Random();

	public GameController() {
	}

	public CommandResult launchNewGame() {
		this.gameState = new GameState();
		this.gameState.setGameLevel(GameLevel.First);
		this.gameState.setUnlockedWeapons(new ArrayList<>());
		this.newGameLoaded = true;
		return new CommandResult(CommandStatus.Success, "New Game Launched Successfully");
	}

	/**
	 * This method create a new character and set the default weapon as 'sword'
	 * to player.
	 * 
	 * @param characterName
	 * @return
	 */
	public CommandResult createCharacter(String characterName) {
		Character warrior = CharacterFactory.createCharacter(CharacterType.Warrior);
		warrior.setName(characterName);
		Weapon sword = WeaponFactory.createWeapon(WeaponType.Sword);
		warrior.setWeapon(sword);
		gameState.setMainCharacter(warrior);
		StringBuilder messageBuilder = new StringBuilder(getMainCharacterInformation());
		return new CommandResult(CommandStatus.Success, messageBuilder.toString());

	}

	/**
	 * This method creates enemy and give min/max power depending upon the game
	 * level.
	 * 
	 * @return
	 */
	public CommandResult createEnemy() {

		String message = null;
		if ((newGameLoaded || this.gameState.getEnemy() == null || this.gameState.getEnemy().getHealth() <= 0)) {
			Character enemy = CharacterFactory.createCharacter(CharacterType.Enemy);
			Weapon enemyWeapon = null;

			int enemyMinAttackingPower = 0;
			int enemyMaxAttackingPower = 0;

			if (gameState.getGameLevel() == GameLevel.First) {
				enemyMinAttackingPower = RANDOM.nextInt(1) + 1;
				enemyMaxAttackingPower = RANDOM.nextInt(3) + 3;
				enemyWeapon = WeaponFactory.createWeapon(WeaponType.Knife);
			} else if (gameState.getGameLevel() == GameLevel.Second) {
				enemyMinAttackingPower = RANDOM.nextInt(1) + 1;
				enemyMaxAttackingPower = RANDOM.nextInt(4) + 3;
				enemyWeapon = WeaponFactory.createWeapon(WeaponType.Sword);
			}

			enemy.setAttackingMinPower(enemyMinAttackingPower);
			enemy.setAttackingMaxPower(enemyMaxAttackingPower);
			enemy.setWeapon(enemyWeapon);
			enemy.setName("Goblin");
			gameState.setEnemy(enemy);
			message = "Enemy Created Successfully..";
		}
		return new CommandResult(CommandStatus.Success, message);
	}

	/**
	 * This method return enemy information.
	 * 
	 * @return
	 */
	public String getEnemyInformation() {
		StringBuilder strBuilder = new StringBuilder(" Goblin Attacking Max Power : ")
				.append(gameState.getEnemy().getAttackingMaxPower()).append("\n Weapon : ")
				.append(gameState.getEnemy().getWeapon().getWeaponName());

		return strBuilder.toString();
	}

	/**
	 * This method replace main character weapon to more powerful weapon which
	 * player unlocked during play.
	 * 
	 * @return
	 */
	public CommandResult replaceWeapon() {
		Weapon unlockedWeapon = getUnlockedWeapons().get(0);
		gameState.getMainCharacter().setWeapon(unlockedWeapon);
		gameState.getUnlockedWeapons().clear();

		StringBuilder strBuilder = new StringBuilder("Your Obtained new weapon, You now have ")
				.append(this.gameState.getMainCharacter().getWeapon().getWeaponName());
		return new CommandResult(CommandStatus.Success, strBuilder.toString());
	}

	private void unlockedNewWeapon() {
		getUnlockedWeapons().clear();
		getUnlockedWeapons().add(WeaponFactory.createWeapon(WeaponType.BowAndArrow));
	}

	/**
	 * This method invoked when player and enemy attack on each other. Actual
	 * damage effect will be calculated by another class named as AttackHandler.
	 * 
	 * @return
	 */
	public CommandResult attack() {

		String playerMessage = playerAttacked();
		StringBuilder messageBuilder = new StringBuilder(playerMessage).append("\n");

		if (!this.gameState.getEnemy().isDead()) {
			String enemyMessage = enemyAttacked();
			messageBuilder.append(enemyMessage).append("\n");
		}
		if (this.gameState.getEnemy().isDead() && !this.gameState.getMainCharacter().isDead()) {
			// Enemy is dead and player is live
			unlockedNewWeapon();
			increaseEnemyCount();
			addExperience();
			addHealthBoostingLiquid();

			if (gameState.getMainCharacter().getExperience() > 4) {
				gameState.setGameLevel(GameLevel.Second);
			}

		}

		return new CommandResult(CommandStatus.Success, messageBuilder.toString());
	}

	private String playerAttacked() {

		boolean hit = RANDOM.nextBoolean();
		String message = null;

		if (hit) {
			message = AttackHandler.attack(gameState.getMainCharacter(), gameState.getEnemy());
		} else {
			message = "Ops.... You missed the attack";
		}
		return message;
	}

	private String enemyAttacked() {
		boolean hit = RANDOM.nextBoolean();
		String message = null;

		if (hit) {
			message = AttackHandler.attack(gameState.getEnemy(), gameState.getMainCharacter());
		} else {
			message = "Enemy missed the attack";
		}

		return message;
	}

	/**
	 * This method returns player information.
	 * 
	 * @return
	 */
	public String getMainCharacterInformation() {
		StringBuilder strBuilder = new StringBuilder();
		Character mainCharater = gameState.getMainCharacter();
		strBuilder.append("\nHello ").append(mainCharater.getName()).append(",\nYour stats :- ").append("\n Weapon : ")
				.append(mainCharater.getWeapon().getWeaponName()).append("\n Health points : ")
				.append(mainCharater.getHealth()).append("\n Max Combat power : ")
				.append(mainCharater.getAttackingMaxPower()).append("\n Enemies Terminated : ")
				.append(getKilledEnemiesCount()).append("\n Experience Level : ").append(getGameLevel());

		return strBuilder.toString();

	}

	public int getKilledEnemiesCount() {
		return this.gameState.getEnemiesKilledCount();
	}

	public List<Weapon> getUnlockedWeapons() {
		return gameState.getUnlockedWeapons();
	}

	public String createExploreAreaDialogs() {

		Character character = gameState.getMainCharacter();
		StringBuilder dialogBuilder = new StringBuilder("Mission Master: Hello ").append(character.getName())
				.append("\nI am your Mission Master. \nHere is your map of Crime City Harbour.")
				.append("\nOn your [ East ], you will find the Weapon Factory where you can upgrade your weapons on collecting enough points after killing the enemies.")
				.append("\nOn your [ West ], you will see the Elixir station, where you can rest and rejuvenate your health points.")
				.append("\nKeep in mind that you may or may not get a pod to rest in depending on their availability.")
				.append("\nStraight ahead of you is a wide street that leads inside the Crime City. Go and get them.... \nALL the best for your mission");

		return dialogBuilder.toString();

	}

	public String searchHealthBoostingElixir() {
		StringBuilder messageBuilder = new StringBuilder();

		if (this.gameState.getHealthBoostingElixir() > 0) {

			if (this.gameState.getMainCharacter().getHealth() == 100) {
				messageBuilder.append("\nYou do not need the Elixir now, as your health is 100.");
			} else if (this.gameState.getMainCharacter().getHealth() < 100) {
				int difference = 100 - this.gameState.getMainCharacter().getHealth();

				if (difference >= this.gameState.getHealthBoostingElixir()) {
					this.gameState.getMainCharacter().setHealth(
							this.gameState.getMainCharacter().getHealth() + this.gameState.getHealthBoostingElixir());
				} else {
					this.gameState.getMainCharacter().setHealth(100);
				}

				messageBuilder.append(
						"\nYou have the Elixir and your health is : " + this.gameState.getMainCharacter().getHealth());
				this.gameState.setHealthBoostingElixir(0);

			}
		} else {
			messageBuilder.append("\nSorry... No Elixir available right now...");
		}

		return messageBuilder.toString();
	}

	/**
	 * This method saved the current game state when invoked.
	 * 
	 * @return
	 */
	public CommandResult saveGameState() {

		CommandResult result;

		try {

			FileOutputStream fos = new FileOutputStream(new File(gameStateFileName));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this.gameState);
			result = new CommandResult(CommandStatus.Success, "Game State saved successfully..");
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			result = new CommandResult(CommandStatus.Success, "Unable to create game state..");
			// e.printStackTrace();
		} catch (IOException ioException) {
			result = new CommandResult(CommandStatus.Success, "There is some technical issue occured...");
			// ioException.printStackTrace();
		}

		return result;
	}

	public String getCurrentStateInformation() {
		Character mainCharacter = this.gameState.getMainCharacter();
		StringBuilder messageBuilder = new StringBuilder("\t Game Level :")
				.append(this.gameState.getGameLevel().toString()).append("\n\t Player Name : ")
				.append(mainCharacter.getName()).append("\n\t Player's Weapon : ")
				.append(mainCharacter.getWeapon().getWeaponName()).append("\n\t Player Health :")
				.append(mainCharacter.getHealth()).append("\n\t Total Enemies killed yet : ")
				.append(this.gameState.getEnemiesKilledCount()).append("\n\t Total experience : ")
				.append(mainCharacter.getExperience());

		return messageBuilder.toString();
	}

	/**
	 * This method is responsible for loading existing game
	 * @return
	 */
	public CommandResult loadExistingGame() {

		CommandResult result = null;
		try {
			FileInputStream fi = new FileInputStream(new File(gameStateFileName));
			ObjectInputStream oi = new ObjectInputStream(fi);
			this.gameState = (GameState) oi.readObject();
			newGameLoaded = false;
			oi.close();
			fi.close();
			result = new CommandResult(CommandStatus.Success, "Game Loaded Successfully");
			return result;
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			result = new CommandResult(CommandStatus.Failure, "******* No Game State saved yet. *******");
		} catch (IOException ioException) {
			// ioException.printStackTrace();
			result = new CommandResult(CommandStatus.Error, "******* There is some technical issue. *******");
		} catch (ClassNotFoundException cnf) {
			// cnf.printStackTrace();
			result = new CommandResult(CommandStatus.Error, "******* There is some technical issue. *******");
		}

		return result;

	}

	public void setGameState(GameState gs) {
		this.gameState = gs;
	}

	private void increaseEnemyCount() {
		this.gameState.setEnemiesKilledCount(this.gameState.getEnemiesKilledCount() + 1);
	}

	private void addExperience() {
		this.gameState.getMainCharacter().setExperience(this.gameState.getMainCharacter().getExperience() + 1);
	}

	private void addHealthBoostingLiquid() {
		this.gameState.setHealthBoostingElixir(RANDOM.nextInt(9) + 20);
	}

	public int getMainCharacterHealth() {
		return this.gameState.getMainCharacter().getHealth();
	}

	public int getMainCharacterExperience() {
		return this.gameState.getMainCharacter().getExperience();
	}

	public int getEnemyHealth() {
		return this.gameState.getEnemy().getHealth();
	}

	public String getMainCharacterName() {

		return this.gameState.getMainCharacter().getName();
	}

	public String getEnemyName() {
		return this.gameState.getEnemy().getName();
	}

	public String getGameLevel() {
		return this.gameState.getGameLevel().getLevelName();
	}

	public boolean isPlayerDead() {
		return this.gameState.getMainCharacter().isDead();
	}

	public boolean isEnemyDead() {
		return this.gameState.getEnemy().isDead();
	}

	public void startFirstLevel() {
		System.out.println("Start First Level..");
	}
}
