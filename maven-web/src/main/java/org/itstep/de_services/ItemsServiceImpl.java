package org.itstep.de_services;

import java.util.ArrayList;
import java.util.List;

import org.itstep.daos.BaseitemDao;
import org.itstep.daos.CategoryDao;
import org.itstep.daos.ClassificationDao;
import org.itstep.daos.ItemsDao;
import org.itstep.daos.ImgDao;
import org.itstep.daos.WebpagesDao;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Items;
import org.itstep.entities.ItemsSort;
import org.itstep.entities.Img;
import org.itstep.entities.Webpages;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ItemsServiceImpl implements ItemsService {
	@Autowired		
	private WebpagesDao webpagesDao;
	@Autowired		
	private CategoryDao categoryDao;
	@Autowired		
	private ClassificationDao classificationDao;
	@Autowired		
	private ItemsDao itemsDao;
	@Autowired		
	private ImgDao imgDao;
	@Autowired		
	private BaseitemDao baseitemDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {this.categoryDao= categoryDao;}
	public void setBaseitemDao(BaseitemDao baseitemDao) {this.baseitemDao= baseitemDao;}
	public void setWebpagesDao(WebpagesDao webpagesDao) {this.webpagesDao = webpagesDao;}
	public void setClassificationDao(ClassificationDao classificationDao) {this.classificationDao= classificationDao;}
	public void setItemsDao(ItemsDao itemsDao) {this.itemsDao= itemsDao;}
	public void setImgDao(ImgDao imgDao) {this.imgDao= imgDao;}	

	@Override
	public List<Items> findAll() throws LogicException {
		try {
			List<Items> list = itemsDao.read();
			list.forEach((x) -> {				
				try {
			
					Category category =categoryDao.read(x.getCategory().getId());
					x.setCategory(category);						
					try {Webpages webpages = webpagesDao.read(x.getWebpages().getId());
					x.setWebpages(webpages); } catch (NullPointerException e) {}
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
								Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}							                               
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {x.setMyimg(iImg.get(0).getUrl()); }
					
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
			
			if (items.getWebpages()!=null && items.getWebpages().getId()!=null) {
				Webpages webpages =webpagesDao.read(items.getWebpages().getId());
				if (items.getWebpages().getEntity()!=null && !items.getWebpages().getEntity().isBlank()) {
					webpages.setEntity(items.getWebpages().getEntity());}
				if (items.getWebpages().getEntityid()!=null) {
					webpages.setEntityid(items.getWebpages().getEntityid());}	
				webpagesDao.update(webpages);
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
			try {Webpages webpages = webpagesDao.read(items.getWebpages().getId());
			items.setWebpages(webpages); } catch (NullPointerException e) {}			
			List<Classification> ilist=new ArrayList<>();
			items.getClassification().forEach((y)->{
				  try {
					Classification classification =classificationDao.read(y.getId()); 
				//	System.out.println(classification);
					Long parentid = classification.getParentid();
					if (parentid>0) {
						if (parentid==classification.getId()) {classification.setParentname(classification.getName());}
						else {
							classification.setParentname(classificationDao.read(parentid).getName());
						}
					}					
					ilist.add(classification);
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 
			items.setClassification(ilist); 
			
			List<Img> iImg=new ArrayList<>();
			if (items.getImg()!=null || iImg.size()>0) {items.getImg().forEach((y)->{
				  try {		
					Img tImg=imgDao.read(y.getId());   
					if (y!=null && tImg!=null) {iImg.add(tImg);
				//	System.out.println("===============READ ITEM ID============y)"+items.getId() + "img)"+tImg.getUrl());
					}					 
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 			
			items.setImg(iImg);} 
			if (iImg!=null && iImg.size()>0) {
				if (iImg.get(0)!=null) {items.setMyimg(iImg.get(0).getUrl()); }}
						
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
								Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}							    
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////
	@Override
	public List<Items> search(String search, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.search(search, itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
/////////////////////////////////////////////////////////////////////
	@Override
	public List<Items> search(Integer scategory, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.search(scategory, itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	///////////////////////////////////////////////////////////////
	@Override
	public List<Items> readPage(ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.readPage(itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	///////////////////////////////////////////////////////////////
	
	@Override
	public List<Items> search(List<Integer> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.search(sclassification, itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	///////////////////////////////////////////////////////////////
	@Override
	public List<Items> search(List<Integer> sclassification, ItemsSort itemsSort, Integer limit, Integer page, Boolean instock) throws LogicException {
		try {		
			List<Items> list=itemsDao.search(sclassification, itemsSort, limit, page);
			List<Items> list2 = new ArrayList<>();
			list.forEach((x) -> {				
				try {
					boolean bIn = baseitemDao.getInStock(x.getId());				
					if (instock != bIn)  // {list.remove(x);}
					 {
					  list2.add(x);
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					  }); 			
					  x.setImg(iImg); 
					  if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
				  }	
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list2;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////
	@Override
	public List<Items> searchCategories(List<Long> categories, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.searchCategories(categories, itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException {
		try {		
			List<Items> list=itemsDao.searchListsCategories(sclassification, itemsSort, limit, page);
			
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 			
					x.setImg(iImg); 
					if (iImg!=null && iImg.size()>0) {
						if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}
					
				} catch (DaoException e) {
					e.printStackTrace();}
				  				
			});
			
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	@Override
	public List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page, boolean instock) throws LogicException {
		try {		
			List<Items> list=itemsDao.searchListsCategories(sclassification, itemsSort, limit, page);
			List<Items> list2 = new ArrayList<>();
			list.forEach((x) -> {				
				try {			
				  boolean bIn = baseitemDao.getInStock(x.getId());				
				  if (instock == bIn) //{list.remove(x);}
				   {
					  list2.add(x);
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
							  Img tImg=imgDao.read(y.getId());   
								if (y!=null && tImg!=null) {iImg.add(tImg);}
						  } catch (DaoException e) {					
							e.printStackTrace();}
					  }); 			
					  x.setImg(iImg); 
					  if (iImg!=null && iImg.size()>0) {
					  if (iImg.get(0)!=null) {x.setMyimg(iImg.get(0).getUrl()); }}				
					}	 
				} catch (DaoException e) {
					e.printStackTrace();}				  				
			    });
		
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
