package de_services;

import java.util.List;

import daos.ClientDao;
import daos.OrdersDao;
import daos.UserDao;
import daos.BaseitemDao;
import daos.CurrencyDao;
import entities.Baseitem;
import entities.Client;
import entities.Currency;
import entities.Orders;
import entities.User;
import postgres.DaoException;
import service.LogicException;

public class OrdersServiceImpl implements OrdersService {
	private ClientDao clientDao;
	private OrdersDao ordersDao;
	private BaseitemDao baseitemDao;
	private CurrencyDao currencyDao;		
	private UserDao userDao;

	public void setOrdersDao(OrdersDao ordersDao) {
		this.ordersDao= ordersDao;}
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;}
	public void setBaseitemDao( BaseitemDao baseitemDao) {
		this.baseitemDao= baseitemDao;}
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao= currencyDao;}
	public void setUserDao(UserDao userDao) {
		this.userDao= userDao;}


	@Override
	public List<Orders> findAll() throws LogicException {
		try {
			List<Orders> list = ordersDao.read();
			list.forEach((x) -> {
			
			  try {	 
				Baseitem baseitem=baseitemDao.read(x.getBaseitem().getId());
				x.setBaseitem(baseitem);	
				Client client=clientDao.read(x.getClient().getId());	
				x.setClient(client);	
				User user = userDao.read(client.getUser().getId());
				client.setUser(user);
				Currency currency=currencyDao.read(x.getCurrency().getId());		
				x.setCurrency(currency); 
								
			  } catch (DaoException e) {
				 e.printStackTrace();  
			  }		
			  						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Orders orders) throws LogicException {
		try {
			Long id =orders.getId();
			if(id == null) {
				id = ordersDao.create(orders);
				orders.setId(id);
			} else {
				ordersDao.update(orders);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			ordersDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Orders read(Long id) throws LogicException {
		try {
			Orders orders=ordersDao.read(id);			
			Baseitem baseitem=baseitemDao.read(orders.getBaseitem().getId());
			orders.setBaseitem(baseitem);
			Client client=clientDao.read(orders.getClient().getId());
			orders.setClient(client);
			Currency currency=currencyDao.read(orders.getCurrency().getId());
			orders.setCurrency(currency); 
			
			return orders;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				ordersDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	public Integer getMaxNumber() throws LogicException {
		Integer maxNum = ordersDao.getMaxOrder(); 
        if (maxNum==null || maxNum==0) {
        	maxNum = findAll().stream().map((x) -> x.getNumber()).max(Integer::compareTo).get();}
           return maxNum;
        }
		
}
