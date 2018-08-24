package org.com.command;

import org.com.game.controller.GameController;

public class StartFirstLevelCommand implements Command {

	private GameController gameController;
	private CommandResult result;

	public StartFirstLevelCommand(GameController gc) {
		this.gameController = gc;
	}

	public void execute() {
		this.gameController.startFirstLevel();
	}

	@Override
	public CommandResult getCommandResult() {
		return result;
	}

}
