package org.com.command;

import java.io.Serializable;

public class CommandResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommandStatus commandStatus;
	private String message;

	public CommandResult(CommandStatus commandStatus, String message) {
		super();
		this.commandStatus = commandStatus;
		this.message = message;
	}

	public CommandStatus getCommandStatus() {
		return commandStatus;
	}

	public String getMessage() {
		return message;
	}


}
