package de_services;

import java.util.List;

import daos.SaleDao;
import daos.OrdersDao;
import daos.CurrencyDao;
import daos.ClientDao;
import entities.Orders;
import entities.Client;
import entities.Currency;
import entities.Sale;
import postgres.DaoException;
import service.LogicException;

public class SaleServiceImpl implements SaleService {
	private SaleDao saleDao;
	private OrdersDao ordersDao;
	private CurrencyDao currencyDao;
	private ClientDao clientDao; 

	public void setSaleDao(SaleDao saleDao) {
		this.saleDao = saleDao;}
	public void setOrdersDao(OrdersDao ordersDao) {		
		this.ordersDao= ordersDao;}
	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;}
	public void setClientDao(ClientDao clientDao) {
		this.clientDao=clientDao;}

	@Override
	public List<Sale> findAll() throws LogicException {
		try {
			List<Sale> list = saleDao.read();
			list.forEach((x) -> {
				
				try {
					Orders orders=ordersDao.read(x.getOrder().getId());
					x.setOrder(orders); 
					Client client = clientDao.read(x.getOrder().getClient().getId());
					x.getOrder().setClient(client);
					Currency currency=currencyDao.read(x.getCurrency().getId());
					x.setCurrency(currency); 		
				} catch (DaoException e) {
					e.printStackTrace();}
				
						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Sale sale) throws LogicException {
		try {
			Orders orders= sale.getOrder();
			Client client =orders.getClient();
		//	  System.out.println("====================SERV DATA" + sale.getDatetime());
			ordersDao.update(orders);
			clientDao.update(client);
	//		 System.out.println("====================SERV DATA 2" + sale.getDatetime());
			Long id =sale.getId();
			if(id == null) {
				 id = saleDao.create(sale);
				sale.setId(id);								
			} else {
				saleDao.update(sale);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			saleDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Sale read(Long id) throws LogicException {
		try {
			Sale sale=saleDao.read(id);
			
			Orders orders=ordersDao.read(sale.getOrder().getId());
			sale.setOrder(orders);
			Client client = clientDao.read(sale.getOrder().getClient().getId());
			sale.getOrder().setClient(client);
			Currency currency=currencyDao.read(sale.getCurrency().getId());
			sale.setCurrency(currency); 
			
			return sale;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				saleDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
