package org.itstep.daos;

import java.util.List;

//import entities.Client;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;

public interface OrdersDao extends Dao<Orders> {
	Integer getMaxOrder();
	Long create(Orders orders) throws DaoException;
	Orders read(Long id) throws DaoException;
	void update(Orders orders) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Orders> read() throws DaoException;
	List<Orders> search(Orderstatus status, Long client) throws DaoException;
	List<Orders> orderdItems(Long client) throws DaoException;
	List<Orders> clientOrderedItems(Long client) throws DaoException;
}