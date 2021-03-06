package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Client;

public interface ClientDao extends Dao<Client> {
	Long create(Client client) throws DaoException;
	Client read(Long id) throws DaoException;
	void update(Client client) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Client> read() throws DaoException;
	Long findByUserId(Long id) throws DaoException;
	Client readByUserId(Long id) throws DaoException;
}