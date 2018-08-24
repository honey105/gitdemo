package org.com.command;

import org.com.game.controller.GameController;

public class AttackCommand implements Command{

	private GameController gameController;
	private CommandResult commandResult;

	public AttackCommand(GameController gc) {
		this.gameController = gc;
	}

	
	@Override
	public void execute() {
		commandResult = gameController.attack();
	}

	@Override
	public CommandResult getCommandResult() {
		return commandResult;
	}

}
