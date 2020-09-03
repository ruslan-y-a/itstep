package daos;

import java.sql.Connection;

import entities.Entity;

public abstract class DaoImpl<T extends Entity> implements Dao<T> {
	protected Connection c;
	protected final Connection getConnection() {return c;}
	public final void setConnection(Connection c) {this.c = c;}
}
