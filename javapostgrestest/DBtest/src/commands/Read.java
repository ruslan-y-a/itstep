package commands;

import service.DBService;
import service.LogicException;
import ui.Factory;

public class Read implements Command {
	private Factory factory;
	private String dbName = "items";
	
	public Read(Factory factory) {	
		this.factory=factory;}	
	public Read(Factory factory,String dbName) {	
		this.factory=factory;this.dbName = dbName;}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	@Override
	public String help() {
		return "Read tabs data";}

	@Override
	public void exec(String[] args) throws LogicException {
		DBService dBService=  factory.getDBService();
		dBService.read(dbName);
	}

}
