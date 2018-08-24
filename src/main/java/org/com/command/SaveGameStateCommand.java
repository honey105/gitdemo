package org.com.command;

import org.com.game.controller.GameController;

public class SaveGameStateCommand implements Command{

	private GameController gameController;
	private CommandResult result;

	public SaveGameStateCommand(GameController gc) {
		this.gameController = gc;
	}
	
	@Override
	public void execute() {
		result = gameController.saveGameState();
	}

	@Override
	public CommandResult getCommandResult() {
		return result;
	}

}
