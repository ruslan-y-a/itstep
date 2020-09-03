package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.SaleDao;
import org.itstep.daos.OrdersDao;
import org.itstep.daos.CurrencyDao;
import org.itstep.daos.ClientDao;
import org.itstep.entities.Orders;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Sale;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class SaleServiceImpl implements SaleService {
	@Autowired		
	private SaleDao saleDao;
	@Autowired		
	private OrdersDao ordersDao;
	@Autowired		
	private CurrencyDao currencyDao;
	@Autowired		
	private ClientDao clientDao; 

	public void setSaleDao(SaleDao saleDao) {this.saleDao = saleDao;}
	public void setOrdersDao(OrdersDao ordersDao) {this.ordersDao= ordersDao;}
	public void setCurrencyDao(CurrencyDao currencyDao) {this.currencyDao = currencyDao;}
	public void setClientDao(ClientDao clientDao) {this.clientDao=clientDao;}

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
