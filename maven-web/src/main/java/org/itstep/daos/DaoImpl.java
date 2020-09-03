package org.itstep.daos;

import java.sql.Connection;

import org.itstep.entities.Entity;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class DaoImpl<T extends Entity> implements Dao<T> {
	@Autowired
	protected Connection c;
	protected final Connection getConnection() {return c;}
	public final void setConnection(Connection c) {this.c = c;}
}
