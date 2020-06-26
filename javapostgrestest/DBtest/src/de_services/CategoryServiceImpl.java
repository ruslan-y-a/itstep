package de_services;

import java.util.List;
import java.util.stream.Collectors;

import entities.Category;
import entities.Webpages;
import daos.CategoryDao;
import daos.ClassificationDao;
import daos.WebpagesDao;
import postgres.DaoException;
import service.LogicException;
import entities.Classification;
//import entities.Entity;

public class CategoryServiceImpl implements CategoryService {
	private CategoryDao categoryDao;
	private WebpagesDao webpagesDao;
	private ClassificationDao classificationDao;

	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;}
	public void setWebpagesDao(WebpagesDao webpagesDao) {		
		this.webpagesDao= webpagesDao;}
	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;}

	@Override
	public List<Category> findAll() throws LogicException {
		try {
			List<Category> list = categoryDao.read();
			list.forEach((x) -> {
				Webpages webpages=x.getWebpages();
				try {
					if (x.getParentid()>0) {x.setParentname(categoryDao.read(x.getParentid()).getName());}
					webpages = webpagesDao.read(webpages.getId());
					x.setWebpages(webpages);		
				} catch (DaoException e) {
					e.printStackTrace();}
				
						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Category category) throws LogicException {
		try {
			Long id = category.getId();
		//	System.out.println("-----------------SAVE SERVICE)" + categoryDao);	
			if(id == null) {
				id = categoryDao.create(category);
				category.setId(id);
			} else {
				categoryDao.update(category);
			}			
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {		
			ComplexDelete(id); //categoryDao.delete(id);		
	}
	
	@Override
	public Category read(Long id) throws LogicException {
		try {
			Category category=categoryDao.read(id);
			if (category.getParentid()>0) {category.setParentname(categoryDao.read(category.getParentid()).getName());}
			Webpages webpages=webpagesDao.read(category.getWebpages().getId());
			category.setWebpages(webpages);
			return category;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {		
			for(Long id : ids) {
				ComplexDelete(id); //categoryDao.delete(id);
			}	
	}
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	 public boolean updateClassificationFromCategory() throws LogicException  {
		  Long id; Classification CL;
		  List<Category> categories; List<Long> classification;
		 try { 
		  categories= categoryDao.read().stream().filter((x)-> x.getParentid()>0).collect(Collectors.toList());
		  classification= classificationDao.read().stream().
				  filter((x)-> x.getCategoryid()>0).map((x) -> x.getCategoryid())
				  .collect(Collectors.toList());	
		  		  		  
		  
		  if (!classificationDao.read(1L).getName().equals("Category")) {
			 System.out.println("OPS! SORRY, In Classification table the first should be \"Category\""); 
			 throw new LogicException(null);
			 /**
			  * TODO
			  * */
		      }
		  } catch (DaoException e) {				
			e.printStackTrace(); throw new LogicException(e);}		 
		  for (Category entry:categories) {
			  id=entry.getId();
			  if (!classification.contains(id)) { 			  
				   CL = new Classification();	
				   try {
					   CL.setCategoryid(id); //.put("parentid",categoryid);
					   CL.setName(entry.getName());
					   CL.setParentid(1L);
					   classificationDao.create(CL);
				    } catch (DaoException e) {				
					e.printStackTrace(); throw new LogicException(e);}			   		
			  }
		  }
		  return true;
	  }
	 public boolean updateClassificationFromCategory(Category category) throws LogicException  {
		  Long id = category.getId(); Classification CL;
		  List<Long> classification;
		 try { 
		  
		  classification= classificationDao.read().stream().
				  filter((x)-> x.getCategoryid()>0).map((x) -> x.getCategoryid())
				  .collect(Collectors.toList());			  		  		  
		  
		  if (!classificationDao.read(1L).getName().equals("Category")) {
			 System.out.println("OPS! SORRY, In Classification table the first should be \"Category\""); 
			 throw new LogicException(null);
			 /**
			  * TODO
			  * */
		      }
		  } catch (DaoException e) {				
			e.printStackTrace(); throw new LogicException(e);}		 
		
			 
			  if (!classification.contains(id)) { 			  
				   CL = new Classification();	
				   try {
					   CL.setCategoryid(id); //.put("parentid",categoryid);
					   CL.setName(category.getName());
					   CL.setParentid(1L);
					   classificationDao.create(CL);
				    } catch (DaoException e) {				
					e.printStackTrace(); throw new LogicException(e);}			   		
			  }
		
		  return true;
	  }
	//////////////////////////////////////////////////////////////////
	 //////////////////////////////////////////////////////////////////
	 private boolean ComplexDelete(Long id) throws LogicException {
		try {	
		  Category category= categoryDao.read(id);	  
		  if (category==null) {return false;}
		  Long pid = category.getParentid();		  
		  if (pid==null || pid==0) {		  
			  for (Category entry:categoryDao.read().stream().filter((x) ->x.getParentid()==id).collect(Collectors.toList())) {				   
				    	categoryDao.delete(entry.getId());		
				  } 			   
		    } 
		   categoryDao.delete(id);
		   
		} catch (DaoException e) {
				   e.printStackTrace(); throw new LogicException(e);} 
		  
		  return true; 	  			  	  	  	  	 	 
	  }
	 ///////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////////////

	 
}
