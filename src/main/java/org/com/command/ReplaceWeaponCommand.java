package org.com.command;

import org.com.game.controller.GameController;

public class ReplaceWeaponCommand implements Command {

	private GameController gameController;
	private CommandResult result;

	public ReplaceWeaponCommand(GameController gc) {
		this.gameController = gc;
	}

	public void execute() {

		// System.out.println("-- executing replace weapon command -- ");
		result = gameController.replaceWeapon();
		/*System.out.println("Your Obtained new weapon, You now have "
				+ gameController.getCurrentGameState().getMainCharacter().getWeapon().getWeaponName());
*/
	}

	@Override
	public CommandResult getCommandResult() {
		// TODO Auto-generated method stub
		return result;
	}

}
