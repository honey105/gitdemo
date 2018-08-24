package org.com;

import java.util.ArrayList;
import java.util.List;

import org.com.character.Character;
import org.com.character.CharacterFactory;
import org.com.character.CharacterStatus;
import org.com.character.CharacterType;
import org.com.command.AttackCommand;
import org.com.command.Command;
import org.com.command.CommandResult;
import org.com.command.CommandStatus;
import org.com.command.CreateCharacterCommand;
import org.com.command.CreateEnemyCommand;
import org.com.command.GameCommandExecuter;
import org.com.command.LaunchNewGameCommand;
import org.com.command.LoadExistingGameCommand;
import org.com.command.ReplaceWeaponCommand;
import org.com.command.SaveGameStateCommand;
import org.com.game.controller.GameController;
import org.com.game.controller.MenuController;
import org.com.game.state.GameLevel;
import org.com.game.state.GameState;
import org.com.weapon.Weapon;
import org.com.weapon.WeaponFactory;
import org.com.weapon.WeaponType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {

	private GameController gameController;

	private Command attackCmd;
	private Command createEnemyCmd;
	private Command launchNewGameCmd;
	private Command loadExistingGameCmd;
	private Command replaceWeaponCmd;
	private Command saveGameStateCmd;
	private GameState gs;
	private GameCommandExecuter commandExecuter;

	@Before
	public void initilize() {
		gs = new GameState();
		gs.setGameLevel(GameLevel.First);
		Character mainCharacter = CharacterFactory.createCharacter(CharacterType.Warrior);
		mainCharacter.setWeapon(WeaponFactory.createWeapon(WeaponType.Sword));
		mainCharacter.setName("Ashok");
		Character enemy = CharacterFactory.createCharacter(CharacterType.Enemy);
		enemy.setWeapon(WeaponFactory.createWeapon(WeaponType.Sword));
		enemy.setAttackingMaxPower(4);
		enemy.setAttackingMinPower(2);
		enemy.setName("Goblin");
		gs.setMainCharacter(mainCharacter);
		gs.setEnemy(enemy);

		gameController = new GameController();
		gameController.setGameState(gs);

		attackCmd = new AttackCommand(gameController);
		createEnemyCmd = new CreateEnemyCommand(gameController);
		launchNewGameCmd = new LaunchNewGameCommand(gameController);
		loadExistingGameCmd = new LoadExistingGameCommand(gameController);
		replaceWeaponCmd = new ReplaceWeaponCommand(gameController);
		saveGameStateCmd = new SaveGameStateCommand(gameController);

		commandExecuter = new GameCommandExecuter();

	}

	@Test
	public void shouldLaunchNewGame() {
		commandExecuter.executeCommand(launchNewGameCmd);
		CommandResult result = launchNewGameCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}

	@Test
	public void ShouldCreateNewCharacter() {
		String characterName = "Ashok";
		CommandResult result = gameController.createCharacter("Ashok");
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
		Assert.assertEquals("Character name does not match", characterName, gameController.getMainCharacterName());
	}

	@Test
	public void shouldCreateEnemyForFirstGameLevel() {
		gs.setEnemy(null);
		gs.setGameLevel(GameLevel.First);
		commandExecuter.executeCommand(createEnemyCmd);
		CommandResult result = createEnemyCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}

	@Test
	public void shouldCreateEnemyForSecondGameLevel() {
		gs.setEnemy(null);
		gs.setGameLevel(GameLevel.Second);
		commandExecuter.executeCommand(createEnemyCmd);
		CommandResult result = createEnemyCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}

	@Test
	public void shouldNotCreateEnemyIfAlreadyExist() {

		commandExecuter.executeCommand(createEnemyCmd);
		CommandResult result = createEnemyCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
		Assert.assertNull(result.getMessage());
	}

	@Test
	public void shouldGiveEnemyInformation() {
		String enemyInfo = gameController.getEnemyInformation();
		Assert.assertNotNull(enemyInfo);
	}

	@Test
	public void shouldAttack() {
		commandExecuter.executeCommand(attackCmd);
		CommandResult result = attackCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}

	@Test
	public void shouldAttackAndKillEnemy() {
		gs.getEnemy().setCharacterStatus(CharacterStatus.dead);
		gs.setUnlockedWeapons(new ArrayList<Weapon>());
		commandExecuter.executeCommand(attackCmd);
		CommandResult result = attackCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());

	}

	@Test
	public void shouldGetMainCharacterInformation() {
		String info = gameController.getMainCharacterInformation();
		Assert.assertTrue(info.contains("Ashok"));
	}

	@Test
	public void shouldReturnExploreAreaDialogs() {
		String dialogs = gameController.createExploreAreaDialogs();
		Assert.assertNotNull(dialogs);
	}

	@Test
	public void shouldSaveGameState() throws Exception {
		commandExecuter.executeCommand(saveGameStateCmd);
		CommandResult result = saveGameStateCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}

	@Test
	public void shouldLoadExistingGameState() {
		commandExecuter.executeCommand(loadExistingGameCmd);
		CommandResult result = loadExistingGameCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}
	
	
	@Test
	public void shouldReplaceWeapon()
	{
		List<Weapon> weaponList = new ArrayList<>();
		weaponList.add(WeaponFactory.createWeapon(WeaponType.BowAndArrow));
		gs.setUnlockedWeapons(weaponList);
		
		commandExecuter.executeCommand(replaceWeaponCmd);
		CommandResult result = replaceWeaponCmd.getCommandResult();
		Assert.assertSame(CommandStatus.Success, result.getCommandStatus());
	}
	
	
	@Test
	public void shouldSearchHealthBoosting()
	{
		gs.getMainCharacter().setHealth(50);
		gs.setHealthBoostingElixir(20);
		String message = gameController.searchHealthBoostingElixir();
		Assert.assertNotNull(message);
	}

	@Test
	public void shouldGetCurrentGameState()
	{
		String message = gameController.getCurrentStateInformation();
		Assert.assertNotNull(message);
	}
	
	@Test
	public void shouldRenderGameMenus()
	{
		MenuController.renderMenuItems(MenuController.mainMenuItems);
		MenuController.renderMenuItems(MenuController.gameMenuItems);
		MenuController.renderMenuItems(MenuController.createCharacterMenuItems);
		MenuController.renderMenuItems(MenuController.fightMenuItems);
		MenuController.renderMenuItems(MenuController.missionMenuItems);
		
	}
	
}
