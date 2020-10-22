package org.itstep.daos;

import java.sql.Connection;

import org.itstep.config.ConnectionThreadHolder;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class DaoImpl<T> implements Dao<T> {
	//@Autowired
	protected Connection c = ConnectionThreadHolder.getConnection();
	protected final Connection getConnection() {return c;}
	public final void setConnection(Connection c) {this.c = c;}
}
