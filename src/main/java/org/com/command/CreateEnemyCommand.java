package org.com.command;

import org.com.game.controller.GameController;

public class CreateEnemyCommand implements Command {

	private GameController gameController;

	private CommandResult result;
	
	public CreateEnemyCommand(GameController gc) {
		this.gameController = gc;
	}

	public void execute() {

		//System.out.println("-- Executing Create Enemy Command --");
		result = gameController.createEnemy();

	}

	@Override
	public CommandResult getCommandResult() {
		// TODO Auto-generated method stub
		return result;
	}

}
