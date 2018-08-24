package org.com.command;

import org.com.game.controller.GameController;

public class LaunchNewGameCommand implements Command{

	private GameController gameController;
	private CommandResult result;

	public LaunchNewGameCommand(GameController gc) {
		this.gameController = gc;
	}

	
	public void execute() {
		result = gameController.launchNewGame();
	}


	@Override
	public CommandResult getCommandResult() {
		return result;
	}

}
