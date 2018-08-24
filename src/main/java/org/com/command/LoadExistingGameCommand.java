package org.com.command;

import org.com.game.controller.GameController;

public class LoadExistingGameCommand implements Command{

	private GameController gameController;
	private CommandResult result;

	public LoadExistingGameCommand(GameController gc) {
		this.gameController = gc;
	}

	
	public void execute() {
		result = gameController.loadExistingGame();
	}


	@Override
	public CommandResult getCommandResult() {
		return result;
	}

}
