package org.com.command;

import java.util.Scanner;

import org.com.game.controller.GameController;

public class CreateCharacterCommand implements Command {
	
	public static final Scanner USERINPUT = new Scanner(System.in);

	private GameController gameController;

	private CommandResult result;

	public CreateCharacterCommand(GameController gc) {
		this.gameController = gc;
	}

	public void execute() {
		System.out.println("Enter your Character name : ");
		String characterName = USERINPUT.nextLine();
		result = gameController.createCharacter(characterName.toUpperCase());

	}

	@Override
	public CommandResult getCommandResult() {
		return result;
	}

}
