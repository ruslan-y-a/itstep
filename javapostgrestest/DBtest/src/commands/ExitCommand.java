package commands;

import ui.Factory;

public class ExitCommand implements Command {
	private Factory factory;
		
	public ExitCommand(Factory factory) {	
		this.factory=factory;
	}

	@Override
	public void exec(String[] args) {			
		factory.close();
		System.out.println("Goodbye");
		System.exit(0);
	}

	@Override
	public String help() {
		return "Exit of the program";
	}
}