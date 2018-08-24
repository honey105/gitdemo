package org.com.game.client;

import java.util.Scanner;

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
import org.com.weapon.Weapon;

/**
 * This is the Game Client and it is responsible for taking input from user and
 * send commands to Game Controller to perform actions depending upon the user
 * selection. This is also responsible for displaying messages to user.
 * 
 * This is the main class to start the game.
 * 
 * @author ashokkumar01
 *
 */
public class GameClient {

	private GameController gameController;
	private GameCommandExecuter commandExecuter;
	public static final Scanner USERINPUT = new Scanner(System.in);
	public static final Scanner ENTERSCANNER = new Scanner(System.in);

	public static final String DASHED_LINE = "\n----------------------------------------------------------------------------------------------------------------------";

	public static void main(String args[]) {

		System.out.println(DASHED_LINE);
		System.out.println(
				"\t\t\tHello Agent, Welcome to Bureau of Crime Investigation.\nYou have been chosen for a covert mission. Should you choose to accept it? \nYour mission is to visit Crime City, a small city situated next to Gringo river, exterminate the enemies and save civilian lives.\nYou will be guided by our Mission Master, who will also provide you with a Map of the city. \nDo keep in mind, if you are caught, the Bureau will deny any association with you whatsoever.");
		System.out.println(DASHED_LINE);
		System.out.println("\n\t\t\t We wish you luck for your mission.");
		System.out.println(DASHED_LINE);
		GameClient gameClient = new GameClient();
		gameClient.initilizeGame();
	}

	public void initilizeGame() {
		gameController = new GameController();
		commandExecuter = new GameCommandExecuter();
		displayMainMenu();
	}

	private void displayMainMenu() {
		do {
			MenuController.renderMenuItems(MenuController.mainMenuItems);
			String userAction = USERINPUT.nextLine();

			switch (userAction) {

			case "1": {
				Command launchNewGame = new LaunchNewGameCommand(gameController);
				commandExecuter.executeCommand(launchNewGame);
				printSingleCharacterWithDelay("Launching.....................", 100);
				System.out.println("\n" + launchNewGame.getCommandResult().getMessage());
				System.out.println(DASHED_LINE);
				displayCreateCharacterMenu();
				break;

			}
			case "2": {
				Command loadExistingGameCmd = new LoadExistingGameCommand(gameController);
				commandExecuter.executeCommand(loadExistingGameCmd);

				CommandResult commandResult = loadExistingGameCmd.getCommandResult();

				if (CommandStatus.Success == commandResult.getCommandStatus()) {
					printSingleCharacterWithDelay("......................", 100);
					System.out.println("\n" + commandResult.getMessage());
					System.out.println(gameController.getCurrentStateInformation());
					System.out.println(DASHED_LINE);
					displayMissionMenuItems();
				} else if (CommandStatus.Failure == commandResult.getCommandStatus()) {
					System.out.println(commandResult.getMessage());
				} else if (CommandStatus.Error == commandResult.getCommandStatus()) {
					System.out.println(commandResult.getMessage());
				}
				break;
			}

			case "3": {
				System.out.println("Bye Bye.. See you next time.... :)");
				System.exit(0);
			}
			default: {
				System.out.println("Please choose valid option..");
			}
			}
		} while (true);
	}

	private void displayCreateCharacterMenu() {
		boolean goBack = false;
		do {

			MenuController.renderMenuItems(MenuController.createCharacterMenuItems);
			String userAction = USERINPUT.nextLine();

			switch (userAction) {

			case "1": {
				Command createCharacterCmd = new CreateCharacterCommand(gameController);
				commandExecuter.executeCommand(createCharacterCmd);
				CommandResult result = createCharacterCmd.getCommandResult();

				if (CommandStatus.Success == result.getCommandStatus()) {
					System.out.println(result.getMessage());
					System.out.println(DASHED_LINE);
					System.out.println("Press Enter to continue....");
					ENTERSCANNER.nextLine();
					displayGameMenu();
				} else {
					System.out.println(result.getMessage());
				}

				break;
			}
			case "2": {
				goBack = true;
				break;
			}

			default: {
				System.out.println("Please choose valid option..");
			}

			}

		} while (goBack == false);

	}

	private void displayGameMenu() {

		boolean goToMainMenu = false;

		do {

			MenuController.renderMenuItems(MenuController.gameMenuItems);
			String userAction = USERINPUT.nextLine();

			switch (userAction) {

			case "1": {
				System.out.println("************** Meeting Mission Master ******************");
				String dialogs = gameController.createExploreAreaDialogs();
				System.out.println(dialogs);
				System.out.println(DASHED_LINE);
				System.out.println("Press Enter to continue...");
				ENTERSCANNER.nextLine();
				break;
			}

			case "2": {
				displayMissionMenuItems();
				break;
			}

			case "3": {
				// Save Game state
				printSingleCharacterWithDelay("Saving Game state........", 30);
				Command saveGameStateCmd = new SaveGameStateCommand(gameController);
				commandExecuter.executeCommand(saveGameStateCmd);
				System.out.println(saveGameStateCmd.getCommandResult().getMessage());
				System.out.println(DASHED_LINE);
				break;
			}

			case "4": {

				goToMainMenu = true;
				break;
			}
			default: {
				System.out.println("Please choose valid option..");
			}
			}

		} while (goToMainMenu == false);

	}

	private void displayMissionMenuItems() {

		boolean goBack = false;

		do {

			System.out.println(DASHED_LINE);
			System.out.println("Game Level : " + gameController.getGameLevel().toString());
			String message = "You are facing North ";
			System.out.println(message);

			MenuController.renderMenuItems(MenuController.missionMenuItems);
			String userAction = USERINPUT.nextLine();

			switch (userAction) {

			case "1": {

				StringBuilder dialogBuilder = new StringBuilder("Hello ").append(gameController.getMainCharacterName())
						.append("\nWelcome to Weapon Factory.").append("\nYou can see only unlocked weapons.");
				printSingleCharacterWithDelay(dialogBuilder.toString(), 50);

				if (gameController.getUnlockedWeapons().isEmpty()) {
					printSingleCharacterWithDelay("Opps Sorry... You have not unlocked any weapon yet... ", 20);
					System.out.println("Press Enter to go Back");

					ENTERSCANNER.nextLine();
				} else {
					System.out.println(
							"You unlocked new weapons, do you want to take it? If yes, press 'Y' otherwise 'N'  ");

					for (Weapon weapon : gameController.getUnlockedWeapons()) {
						System.out.println("[ " + weapon.getWeaponName() + " ]");
					}

					String yesOrNo = USERINPUT.nextLine();

					switch (yesOrNo) {
					case "Y": {
						Command replaceWeaponCmd = new ReplaceWeaponCommand(gameController);
						commandExecuter.executeCommand(replaceWeaponCmd);

						if (CommandStatus.Success == replaceWeaponCmd.getCommandResult().getCommandStatus()) {
							System.out.println(replaceWeaponCmd.getCommandResult().getMessage());
						}
						System.out.println("Press Enter to continue....");
						ENTERSCANNER.nextLine();
						break;
					}
					case "N": {
						break;
					}

					}

				}

				break;
			}

			case "2": {

				StringBuilder dialogBuilder = new StringBuilder("Hello : ")
						.append(gameController.getMainCharacterName()).append("\nWelcome to Elixir station.");
				printSingleCharacterWithDelay(dialogBuilder.toString(), 50);
				String result = gameController.searchHealthBoostingElixir();
				System.out.println(result);
				System.out.println("Press Enter to go Back");
				ENTERSCANNER.nextLine();
				break;
			}

			case "3": {

				StringBuilder dialogBuilder = new StringBuilder("You Entered in Crime city..");
				System.out.println(dialogBuilder.toString());
				System.out.println(DASHED_LINE);

				System.out.println("You Encounter Goblin.");
				Command createEnemyCmd = new CreateEnemyCommand(gameController);
				commandExecuter.executeCommand(createEnemyCmd);
				System.out.println(gameController.getEnemyInformation());
				System.out.println(DASHED_LINE);
				displayAttackOption();
				break;

			}

			case "4": {
				goBack = true;
				displayGameMenu();
				break;

			}

			default: {
				System.out.println("Please choose valid option..");
			}

			}

		} while (goBack == false);

	}

	private void displayAttackOption() {

		boolean goBack = false;

		do {

			MenuController.renderMenuItems(MenuController.fightMenuItems);
			String userAction = USERINPUT.nextLine();

			switch (userAction) {

			case "1": {
				// Attack
				Command attackCmd = new AttackCommand(gameController);
				commandExecuter.executeCommand(attackCmd);
				System.out.println(attackCmd.getCommandResult().getMessage());

				if (gameController.isEnemyDead() && gameController.isPlayerDead()) {
					System.out.println("#####################################");
					printSingleCharacterWithDelay(".........MISSION FAILED........ ", 10);
					System.out.println("#####################################");
					goBack = true;
					displayMainMenu();

				} else if (gameController.isPlayerDead()) {
					// Captain died
					System.out.println("#####################################");

					printSingleCharacterWithDelay(".........MISSION FAILED........", 50);
					System.out.println("\nPress enter to start new game");
					ENTERSCANNER.nextLine();
					goBack = true;
					displayMainMenu();
				} else if (gameController.isEnemyDead()) {
					// Enemy killed
					System.out.println("*********************************************");
					printSingleCharacterWithDelay("Good Job, You killed Goblin and save civilians life.", 10);
					System.out.println("Congratulations : You unlocked new weapon ");
					System.out.println(gameController.getMainCharacterInformation());
					System.out.println(" Game Level  : " + gameController.getGameLevel());
					System.out.println("Press enter to go to Continue ");
					ENTERSCANNER.nextLine();

					System.out.println(DASHED_LINE);

					System.out.println("You Encounter Goblin.");
					Command createEnemyCmd = new CreateEnemyCommand(gameController);
					commandExecuter.executeCommand(createEnemyCmd);
					System.out.println(gameController.getEnemyInformation());
					System.out.println(DASHED_LINE);
					goBack = true;
					displayAttackOption();

				} else {
					System.out.println(DASHED_LINE);
					System.out.println(gameController.getMainCharacterName() + "'s Health : "
							+ gameController.getMainCharacterHealth());
					System.out
							.println(gameController.getEnemyName() + "'s Health : " + gameController.getEnemyHealth());
					System.out.println(DASHED_LINE);
				}

				break;

			}
			case "2": {
				// go Back
				goBack = true;
				break;
			}
			default: {
				System.out.println("Please choose valid option..");
			}
			}

		} while (goBack == false);

	}

	private void printSingleCharacterWithDelay(String message, long delayInMillisec) {
		System.out.println("\n");
		char charArray[] = message.toCharArray();
		try {
			for (char c : charArray) {
				Thread.sleep(delayInMillisec);
				System.out.print(c);
			}
		} catch (Exception e) {
			e.printStackTrace();
			;
		}

	}

}