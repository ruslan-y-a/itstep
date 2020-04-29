package commands;

import service.LogicException;

public interface Command {
	public String help();
	void exec(String args[]) throws LogicException;
}
