package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.service.LogicException;

public interface OrdersService extends BaseService<Orders> {
	List<Orders> findAll() throws LogicException;
	Long save(Orders orders) throws LogicException;
	void delete(Long id) throws LogicException;
	Orders read(Long id) throws LogicException;
	List<Orders> search(Orderstatus status, Long client) throws LogicException;
	List<Orders> orderdItems(Long lclient) throws LogicException;
	void orderListUpdate(List<Orders> list, boolean updatePrice) throws LogicException;
	void newOrder(Long iid, Long uid, Boolean useBonus, Integer quantity, Long currency, Delivery delivery) throws LogicException;
	List<Orders> allOrderedItems(Long lclient) throws LogicException;
	public Integer getMaxNumber() throws LogicException;
}
