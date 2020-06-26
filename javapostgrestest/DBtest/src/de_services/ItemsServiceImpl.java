package de_services;

import java.util.ArrayList;
import java.util.List;

import daos.CategoryDao;
import daos.ClassificationDao;
import daos.ItemsDao;
import daos.ImgDao;
import daos.WebpagesDao;
import entities.Category;
import entities.Classification;
import entities.Items;
import entities.Img;
import entities.Webpages;
import postgres.DaoException;
import service.LogicException;

public class ItemsServiceImpl implements ItemsService {
	private WebpagesDao webpagesDao;
	private CategoryDao categoryDao;
	private ClassificationDao classificationDao;
	private ItemsDao itemsDao;
	private ImgDao imgDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao= categoryDao;}
	public void setWebpagesDao(WebpagesDao webpagesDao) {
		this.webpagesDao = webpagesDao;}
	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao= classificationDao;}
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao= itemsDao;}
	public void setImgDao(ImgDao imgDao) {
		this.imgDao= imgDao;}
	

	@Override
	public List<Items> findAll() throws LogicException {
		try {
			List<Items> list = itemsDao.read();
			list.forEach((x) -> {				
				try {
			
					Category category =categoryDao.read(x.getCategory().getId());
					x.setCategory(category);
					Webpages webpages = webpagesDao.read(x.getWebpages().getId());
					x.setWebpages(webpages);			
					List<Classification> ilist=new ArrayList<>();
					x.getClassification().forEach((y)->{
						  try {
							ilist.add(classificationDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 
					x.setClassification(ilist); 
					
					List<Img> iImg=new ArrayList<>();
					x.getImg().forEach((y)->{
						  try {
							  iImg.add(imgDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Items items) throws LogicException {
		try {
			Long id =items.getId();
			if(id == null) {
				id = itemsDao.create(items);
				items.setId(id);
			} else {
				itemsDao.update(items);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			itemsDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Items read(Long id) throws LogicException {
		try {
			Items items=itemsDao.read(id);
			Category category =categoryDao.read(items.getCategory().getId());
			items.setCategory(category);
			Webpages webpages = webpagesDao.read(items.getWebpages().getId());
			items.setWebpages(webpages);			
			List<Classification> ilist=new ArrayList<>();
			items.getClassification().forEach((y)->{
				  try {
					ilist.add(classificationDao.read(y.getId()));
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 
			items.setClassification(ilist); 
			
			List<Img> iImg=new ArrayList<>();
			items.getImg().forEach((y)->{
				  try {
					  iImg.add(imgDao.read(y.getId()));
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 			
			items.setImg(iImg); 
						
			return items;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				itemsDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public List<Items> search(String search) throws LogicException {
		try {		
			List<Items> list=itemsDao.search(search);
			
			list.forEach((x) -> {				
				try {
			
					Category category =categoryDao.read(x.getCategory().getId());
					x.setCategory(category);
					Webpages webpages = webpagesDao.read(x.getWebpages().getId());
					x.setWebpages(webpages);			
					List<Classification> ilist=new ArrayList<>();
					x.getClassification().forEach((y)->{
						  try {
							ilist.add(classificationDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 
					x.setClassification(ilist); 
					
					List<Img> iImg=new ArrayList<>();
					x.getImg().forEach((y)->{
						  try {
							  iImg.add(imgDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
