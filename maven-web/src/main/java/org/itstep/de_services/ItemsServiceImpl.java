package org.itstep.de_services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.itstep.entities.Baseitem;

@Component
@Service
@Scope("prototype")
public class ItemsServiceImpl implements ItemsService {
	private static final Logger logger = LogManager.getLogger();
	
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
	public Items findById(Long id) throws LogicException {
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
	public Boolean updateItemsStatus() {
		Classification cls=null;
		List<Classification> clsList;
		try {
			clsList = classificationDao.read();
		} catch (DaoException e2) {e2.printStackTrace();return false;}		
		
		try {
			cls= clsList.stream().filter(x -> x.getName().equals("In stock")).findFirst().get();
			if (cls==null) {logger.warn("Item UPDATING FAILED"); return false;}
			for(Items it: itemsDao.read()) {
/////////////////UPDATE PRICES////////////				
				List<Baseitem> bis = baseitemDao.readItemRow(it.getId());
				 if (bis==null || !(bis.size()>0)) {logger.warn("THERE ARE NO BASEITEMS FOR ITEM " + it.getArticul()); continue;} 
				 Baseitem bi=null;
				 bi=bis.stream().filter(x -> x.getQuantity()>0).findFirst().get();				 
				 List<Classification> itemClsList= it.getClassification();
				 long sid= cls.getId(); Classification classificationT=null;
				 for(Classification classification: itemClsList) {
					 if (classification.getId()==sid) {classificationT=classification;break;}
				 } 				 
				 if (bi==null && classificationT!=null) {
					 itemClsList.remove(classificationT); it.setClassification(itemClsList); 
					 itemsDao.update(it); }
				 if (bi!=null && classificationT==null) {
					 itemClsList.add(cls); it.setClassification(itemClsList); 
					 itemsDao.update(it); } 
/////////////////////////////////////
				 updatePrices(it,clsList,itemClsList);				 
			 } 						
		} catch (DaoException e) {
			logger.warn("Item UPDATING FAILED"); e.printStackTrace(); return false;
		}									 
		
		return true;
	}
///////////////////////////////////////////////////////////////	
	public Boolean updateItemStatus(Long id) {
		Classification cls=null;
		List<Classification> clsList;
		try {
			clsList = classificationDao.read();
		} catch (DaoException e2) {e2.printStackTrace();return false;}		
		
		try {
			cls= clsList.stream().filter(x -> x.getName().equals("In stock")).findFirst().get();
			if (cls==null) {logger.warn("Item UPDATING FAILED"); return false;}
			Items it= itemsDao.read(id); 
/////////////////UPDATE PRICES////////////				
				List<Baseitem> bis = baseitemDao.readItemRow(it.getId());
				 if (bis==null || !(bis.size()>0)) {logger.warn("THERE ARE NO BASEITEMS FOR ITEM " + it.getArticul()); return false;} 
				 Baseitem bi=null;
				 bi=bis.stream().filter(x -> x.getQuantity()>0).findFirst().get();				 
				 List<Classification> itemClsList= it.getClassification();
				 long sid= cls.getId(); Classification classificationT=null;
				 for(Classification classification: itemClsList) {
					 if (classification.getId()==sid) {classificationT=classification;break;}
				 } 				 
				 if (bi==null && classificationT!=null) {
					 itemClsList.remove(classificationT); it.setClassification(itemClsList); 
					 itemsDao.update(it); }
				 if (bi!=null && classificationT==null) {
					 itemClsList.add(cls); it.setClassification(itemClsList); 
					 itemsDao.update(it); } 
/////////////////////////////////////
				 updatePrices(it,clsList,itemClsList);				 
			  						
		} catch (DaoException e) {
			logger.warn("Item UPDATING FAILED"); e.printStackTrace(); return false;
		}									 
		
		return true;
	}
///////////////////////////////////////////////////////////////	
	public void updatePrices(Items item, List<Classification> clsList,List<Classification> itemClsList) throws DaoException {
		Classification prices=null; Classification discounts=null;	
		for(Classification classification:clsList) {
			if (classification.getName().equals("Prices")) {prices=classification;}
			if (classification.getName().equals("Discounts")) {discounts=classification;}
		}
		if (prices==null ||  discounts==null) {System.out.println("no prices in the classification table");return;}
		long pricesId=prices.getId(); long discountsId=discounts.getId();
		List<Classification> prc=null; List<Classification> dsc=null;
		try {
		prc= clsList.stream().filter(x -> x.getParentid()==pricesId).collect(Collectors.toList());
		dsc= clsList.stream().filter(x -> x.getParentid()==discountsId).collect(Collectors.toList());
		} catch (Exception e) {}
		if (prc==null ||  dsc==null) {System.out.println("no prices in the classification table");return;}
/////////////////////////////////////////////////////////
		int in=0;		
		int ik = (prc.get(in).getName().equals("0")?0:Integer.parseInt(prc.get(in).getName().substring(1)));
		if (item.getBaseprice()<=ik)  {
			   if (!itemClsList.contains(prc.get(in))) {itemClsList.add(prc.get(in));}				   
		} else if (itemClsList.contains(prc.get(in))) {
			itemClsList.remove(prc.get(in));
		}
		
		for(int i=1; i<prc.size()-1;i++) {
			int ik1 = (prc.get(i-1).getName().equals("0")?0:Integer.parseInt(prc.get(i-1).getName().substring(1)));
			int ik2 = (prc.get(i).getName().equals("0")?0:Integer.parseInt(prc.get(i).getName().substring(1)));
			if (item.getBaseprice()>ik1 && item.getBaseprice()<=ik2)  {
				   if (!itemClsList.contains(prc.get(i))) {itemClsList.add(prc.get(i));}				   
			} else if (itemClsList.contains(prc.get(i))) {
				itemClsList.remove(prc.get(i));
			}			
		} 
		in=prc.size()-1;
		ik = (prc.get(in).getName().equals("0")?0:Integer.parseInt(prc.get(in).getName().substring(1)));
		if (item.getBaseprice()>ik)  {
			   if (!itemClsList.contains(prc.get(in))) {itemClsList.add(prc.get(in));}				   
		} else if (itemClsList.contains(prc.get(in))) {
			itemClsList.remove(prc.get(in));
		}		
////////////////////////////////////////////////////////////////////////		
		
/////////////////////////////////////////////////////////
        in=0;		
        ik = (dsc.get(in).getName().equals("0")?0:Integer.parseInt(dsc.get(in).getName().substring(1)));
        if (item.getDiscount()<=ik)  {
           if (!itemClsList.contains(dsc.get(in))) {itemClsList.add(dsc.get(in));}				   
         } else if (itemClsList.contains(dsc.get(in))) {
              itemClsList.remove(dsc.get(in));
         }

         for(int i=1; i<dsc.size()-1;i++) {
           int ik1 = (dsc.get(i-1).getName().equals("0")?0:Integer.parseInt(dsc.get(i-1).getName().substring(1)));
           int ik2 = (dsc.get(i).getName().equals("0")?0:Integer.parseInt(dsc.get(i).getName().substring(1)));
           if (item.getDiscount()>ik1 && item.getDiscount()<=ik2)  {
              if (!itemClsList.contains(dsc.get(i))) {itemClsList.add(dsc.get(i));}				   
            } else if (itemClsList.contains(dsc.get(i))) {
               itemClsList.remove(dsc.get(i));
            }			
          } 
          in=dsc.size()-1;
          ik = (dsc.get(in).getName().equals("0")?0:Integer.parseInt(dsc.get(in).getName().substring(1)));
          if (item.getDiscount()>ik)  {
               if (!itemClsList.contains(dsc.get(in))) {itemClsList.add(dsc.get(in));}				   
           } else if (itemClsList.contains(dsc.get(in))) {
               itemClsList.remove(dsc.get(in));
           }		
////////////////////////////////////////////////////////////////////////	
          item.setClassification(itemClsList);
	}
}
