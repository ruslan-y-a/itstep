package org.itstep.de_services;

import java.util.List;
import org.itstep.daos.BaseitemDao;
import org.itstep.daos.ColorDao;
import org.itstep.daos.CurrencyDao;
import org.itstep.daos.ItemsDao;
import org.itstep.daos.SizeDao;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Color;
import org.itstep.entities.Currency;
import org.itstep.entities.Items;
import org.itstep.entities.Size;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("prototype")
public class BaseitemServiceImpl implements BaseitemService {
	@Autowired	
	private BaseitemDao baseitemDao;
	@Autowired
	private ItemsDao itemsDao;
	@Autowired
	private ColorDao colorDao;
	@Autowired
	private SizeDao sizeDao;
	@Autowired
	private CurrencyDao currencyDao;

	public void setBaseitemDao(BaseitemDao baseitemDao) {this.baseitemDao= baseitemDao;}
	public void setItemsDao(ItemsDao itemsDao) {this.itemsDao = itemsDao;}
	public void setColorDao(ColorDao variantDao) {this.colorDao =variantDao; }
	public void setSizeDao(SizeDao variantDao) {this.sizeDao =variantDao; }
	public void setCurrencyDao(CurrencyDao currencyDao) {this.currencyDao= currencyDao;}
	
	@Override
	public List<Baseitem> findAll() throws LogicException {
		try {
			List<Baseitem> list = baseitemDao.read();
			list.forEach((x) -> {
				
				try {
					Items items = itemsDao.read(x.getItem().getId());
					x.setItem(items);				
					Color color=colorDao.read(x.getColor().getId());
					x.setColor(color);
					Size size=sizeDao.read(x.getSize().getId());
					x.setSize(size);
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
	public Long save(Baseitem baseitem) throws LogicException {
		try {
			Long id=baseitem.getId();
			if(id == null) {
				id = baseitemDao.create(baseitem);
				baseitem.setId(id);				
			} else {
				baseitemDao.update(baseitem);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			baseitemDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Baseitem read(Long id) throws LogicException {
		try {
			Baseitem baseitem=baseitemDao.read(id);
			Items items = itemsDao.read(baseitem.getItem().getId());
			baseitem.setItem(items);
		//	variantDao.setName("color");
			Color color=colorDao.read(baseitem.getColor().getId());
			baseitem.setColor(color);
		//	variantDao.setName("size");
			Size size=sizeDao.read(baseitem.getSize().getId());
			baseitem.setSize(size);
			Currency currency=currencyDao.read(baseitem.getCurrency().getId());
			baseitem.setCurrency(currency);		
			
			return baseitem;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				baseitemDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	
	@Override
	public List<Baseitem> readItemRow(Long itemid) throws LogicException {
		try {
			List<Baseitem> list = baseitemDao.readItemRow(itemid);			
			if (list==null) return null;
			list.forEach((x) -> {
				
				try {
					Items items = itemsDao.read(x.getItem().getId());
					x.setItem(items);					
					Color color=colorDao.read(x.getColor().getId());
					x.setColor(color);
					Size size=sizeDao.read(x.getSize().getId());
					x.setSize(size);
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
	
}
