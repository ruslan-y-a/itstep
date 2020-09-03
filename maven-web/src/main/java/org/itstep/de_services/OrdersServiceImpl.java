package org.itstep.de_services;

import java.util.Date;
import java.util.List;

import org.itstep.daos.ClientDao;
import org.itstep.daos.OrdersDao;
import org.itstep.daos.UserDao;
import org.itstep.daos.BaseitemDao;
import org.itstep.daos.CurrencyDao;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Client;
import org.itstep.entities.Currency;
import org.itstep.entities.Delivery;
import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.entities.User;
import org.itstep.help.DateHelp;
import org.itstep.help.Params;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class OrdersServiceImpl implements OrdersService {
	@Autowired		
	private ClientDao clientDao;
	@Autowired		
	private OrdersDao ordersDao;
	@Autowired		
	private BaseitemDao baseitemDao;
	@Autowired		
	private CurrencyDao currencyDao;
	@Autowired	
	private UserDao userDao;

	public void setOrdersDao(OrdersDao ordersDao) {this.ordersDao= ordersDao;}
	public void setClientDao(ClientDao clientDao) {this.clientDao = clientDao;}
	public void setBaseitemDao( BaseitemDao baseitemDao) {this.baseitemDao= baseitemDao;}
	public void setCurrencyDao(CurrencyDao currencyDao) {		this.currencyDao= currencyDao;}
	public void setUserDao(UserDao userDao) {this.userDao= userDao;}

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
        	synchronized (OrdersServiceImpl.class){
        	  maxNum = findAll().stream().map((x) -> x.getNumber()).max(Integer::compareTo).get(); ++maxNum;}
        	}
           return maxNum;
        }
	
	@Override
	public List<Orders> search(Orderstatus status, Long lclient) throws LogicException {
		try {
		   List<Orders> list = ordersDao.search(status, lclient);
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
		}
	    catch(DaoException e) {throw new LogicException(e);}
		
	 }
//////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Orders> orderdItems(Long lclient) throws LogicException {
		try {
			   List<Orders> list = ordersDao.orderdItems(lclient);
				list.forEach((x) -> {
					
					  try {	 
						Baseitem baseitem=baseitemDao.read(x.getBaseitem().getId(),true);
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
			}
		    catch(DaoException e) {throw new LogicException(e);}
	}	
/////////////////////////////////////////////////////////////////////////////
@Override
public List<Orders> allOrderedItems(Long lclient) throws LogicException {
   try {
    List<Orders> list = ordersDao.clientOrderedItems(lclient);
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
   }
   catch(DaoException e) {throw new LogicException(e);}
 }		
////////////////////////////////////////////////////////////////////	
	@Override
	public void orderListUpdate(List<Orders> list, boolean updatePrice) throws LogicException {		
		     //  boolean exp=false;
				list.forEach((x) -> {
					try {   
						Orders ord= ordersDao.read(x.getId());								
						Client client = clientDao.read(ord.getClient().getId());
						
						if (x.getStatus()!=null) {ord.setStatus(x.getStatus());}						
						try {client.setAddress(x.getClient().getAddress());} catch (NullPointerException e) {}						
						try {client.setPhoneno(x.getClient().getPhoneno());} catch (NullPointerException e) {}
						if (x.getDelivery()!=null) {ord.setDelivery(x.getDelivery());}						
						ord.setClient(client);		
						ord.setActive(x.getActive());	
						Integer mbpv=client.getBonuspoints();
						if (updatePrice && mbpv!= null && mbpv>0) {
							long bid=ord.getBaseitem().getId();
							Baseitem baseitem = baseitemDao.read(bid);
							long currencyid = ord.getCurrency().getId();
							Currency currency=currencyDao.read(currencyid);
							Double kurs =1d;
							if (currencyid!=1 && currency!=null) {kurs = currency.getRate();} 
							
							Integer qt=ord.getQuantity();
							
							Long price =baseitem.getBaseprice();
							Integer itemDisc = baseitem.getItem().getDiscount();
							Double fdc=Params.MAX_DISCOUNT*price*qt/100d;							 
							   Double disc=0d; Long sum=0l; int opb=0;
								  if (itemDisc<Params.MAX_DISCOUNT) {
									  disc=mbpv+price*itemDisc*qt/100d;
								//	  System.out.println("================fdc)" + fdc);
								//	  System.out.println("================disc)" + disc);
				                     if (fdc>disc) {opb=mbpv;}
				                     else {
				                    	 if (itemDisc<Params.MAX_DISCOUNT) {
				                    	 opb=(int)Math.round(fdc-price*itemDisc*qt/100);}
				                    	 else {opb=0;}
				                     }
				                     fdc=itemDisc*price*qt/100d;
								  }
							//	  System.out.println("================opb)" + opb);
								  sum=Math.round((price*qt-fdc)*kurs);
							//	  System.out.println("================sum)" + sum);
							 
								  ord.setBonuspoints(opb); 
								  ord.setSum(sum);
							
						}
						
						ordersDao.update(ord);							
						if (ord.getActive()) {clientDao.update(client);}
					    } catch (DaoException e) {
						   e.printStackTrace();	//throw new LogicException(e);		
					    }						 						
					  						
					});
				 
				  //throw new LogicException(e);
						
	   }	
////////////////////////////////////////////////////////////////////
	@Override
	public void newOrder(Long iid, Long uid, Boolean useBonus, Integer quantity, Long currencyid, Delivery delivery) throws LogicException {
		
		Orders ord= new Orders();
		try {			
			Baseitem baseitem = baseitemDao.read(iid);
			Baseitem baseitem1 = baseitemDao.read(iid,true);
			Client client = clientDao.readByUserId(uid);
			Currency currency=currencyDao.read(currencyid);
			Double kurs =1d;
			if (currencyid!=1 && currency!=null) {kurs = currency.getRate();} 
			ord.setClient(client);

			ord.setBaseitem(baseitem1);
			ord.setDatetime(new Date());
			ord.setDateexpired(DateHelp.getFuture(new Date(), 5));
			ord.setNumber(getMaxNumber());
			ord.setQuantity(quantity);
			ord.setStatus(Orderstatus.BASKET); 
			ord.setActive(true);
			ord.setDelivery(delivery);
			ord.setCurrency(currency);	
				ord.setSum((Long) Math.round(quantity * baseitem.getBaseprice()*kurs));
			//	 System.out.println("================useBonus)" + useBonus);
				if (useBonus) {					
					Integer qt=ord.getQuantity();
					Integer mbpv=client.getBonuspoints();
					Long price =baseitem.getBaseprice();
					Integer itemDisc = baseitem.getItem().getDiscount();
					Double fdc=Params.MAX_DISCOUNT*price*qt/100d;							 
					   Double disc=0d; Long sum=0l; int opb=0;
						  if (itemDisc<Params.MAX_DISCOUNT) {
							  disc=mbpv+price*itemDisc*qt/100d;
						//	  System.out.println("================fdc)" + fdc);
						//	  System.out.println("================disc)" + disc);
		                     if (fdc>disc) {opb=mbpv;}
		                     else {
		                    	 if (itemDisc<Params.MAX_DISCOUNT) {
		                    	 opb=(int)Math.round(fdc-price*itemDisc*qt/100);}
		                    	 else {opb=0;}
		                     }
		                     fdc=itemDisc*price*qt/100d;
						  }
				//		  System.out.println("================opb)" + opb);
						  sum=Math.round((price*qt-fdc)*kurs);
				//		  System.out.println("================sum)" + sum);
						
						  ord.setBonuspoints(opb); 
						  ord.setSum(sum);
				}
			
			ordersDao.create(ord);
		} catch (DaoException e) {			
			e.printStackTrace();
			throw new LogicException(e);
		}		
	   
	   
	} 
////////////////////////////////////////////////////////////////////
}
