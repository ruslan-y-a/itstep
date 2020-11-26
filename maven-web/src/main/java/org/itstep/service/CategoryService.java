package org.itstep.service;

//import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//import org.itstep.daos.ClassificationDao;
//import org.itstep.daos.DaoException;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Webpages;
import org.itstep.repository.CategoryRepository;
import org.itstep.repository.WebpagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class CategoryService {
   @Autowired private CategoryRepository repo;	
   @Autowired private WebpagesRepository wrepo;
   //@Autowired ClassificationDao classificationDao;
   @Autowired private ClassificationService classificationService;

	public List<Category> findAll() {
		List<Category> list =(List<Category>) repo.findAll();
	/*	list.forEach(x -> {
  			try {
			    x.setParentname(findById(x.getParentid()).getName());}
			    catch (Exception e) {}	
		});*/
		for (Category c: list) {
			Long ip = c.getParentid();
			if (ip!=null && ip>0) { String sc=findById(ip).getName();c.setParentname(sc);}			
		}
		 return list;
		}	 	   
    public void save(Category country) {
    	if (country.getWebpages()!=null && country.getWebpages().getId()!=null) {
    		Webpages wp = wrepo.findById(country.getWebpages().getId()).orElse(null);
    		if (wp!=null && country.getId()!=null) {wp.setEntityid(country.getId());wrepo.save(wp);}
    	}
    	repo.save(country);
    	}        
    public Category findById(Long id) {
    	Category cat = repo.findById(id).orElse(null);
    	/*		try {
    		cat.setParentname(findById(cat.getParentid()).getName());}
		    catch (Exception e) {}   */
    	Long ip = cat.getParentid();
		if (ip!=null && ip>0) {
			String sc=repo.findById(ip).orElse(null).getName();cat.setParentname(sc);
			}	
    	return cat;
    	
    	}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    public Category search(String keyword) {return repo.search(keyword);}
    public List<Category> searchLike(String keyword) {return repo.searchLike(keyword);}
    public List<Category> readByParent(Long parentid) {return repo.readByParent(parentid);}    
    
	///////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////
	 public boolean updateClassificationFromCategory() throws LogicException  {
		  Long id; Classification CL;
		  List<Category> categories; List<Long> classification;
		
		  categories=  findAll().stream().filter((x)-> x.getParentid()>0).collect(Collectors.toList());
		  classification= classificationService.findAll().stream().
				  filter((x)-> x.getCategoryid()>0).map((x) -> x.getCategoryid())
				  .collect(Collectors.toList());	
		  		  		  
		  
		  if (!classificationService.findById(1L).getName().equals("Category")) {
			 System.out.println("OPS! SORRY, In Classification table the first should be \"Category\""); 
			 throw new LogicException(null);
			 /**
			  * TODO  If there i
			  * */
		      }
 
		  for (Category entry:categories) {
			  id=entry.getId();
			  if (!classification.contains(id)) { 			  
				   CL = new Classification();	
				
					   CL.setCategoryid(id); //.put("parentid",categoryid);
					   CL.setName(entry.getName());
					   CL.setParentid(1L);
					   classificationService.save(CL);
					   //classificationDao.create(CL);							   	
			  }
		  }
		  return true;
	  }
	 public boolean updateClassificationFromCategory(Category category) throws LogicException  {
		  Long id = category.getId(); Classification CL;
		  List<Long> classification;
		
		  classification= classificationService.findAll().stream().
				  filter((x)-> x.getCategoryid()>0).map((x) -> x.getCategoryid())
				  .collect(Collectors.toList());			  		  		  
		  
		  if (!classificationService.findById(1L).getName().equals("Category")) {
			 System.out.println("OPS! SORRY, In Classification table the first should be \"Category\""); 
			 throw new LogicException(null);
			 /**
			  * TODO
			  * */
		      }				 		
			 
			  if (!classification.contains(id)) { 			  
				   CL = new Classification();	
				  
					   CL.setCategoryid(id); //.put("parentid",categoryid);
					   CL.setName(category.getName());
					   CL.setParentid(1L);
					   classificationService.save(CL);
					 //  classificationDao.create(CL);
				   		   		
			  }
		
		  return true;
	  }
	//////////////////////////////////////////////////////////////////
	 //////////////////////////////////////////////////////////////////
	 public boolean ComplexDelete(Long id) throws LogicException {
	
		  Category category= findById(id);	  
		  if (category==null) {return false;}
		  Long pid = category.getParentid();		  
		  if (pid==null || pid==0) {		  
			  for (Category entry:findAll().stream().filter((x) ->x.getParentid()==id).collect(Collectors.toList())) {				   
				    	delete(entry.getId());		
				  } 			   
		    } 
		   delete(id);		   	 
		  
		  return true; 	  			  	  	  	  	 	 
	  }
	 ///////////////////////////////////////////////////////////////
	 ////////////////////////////////////////////////////////////////////////
    
}
