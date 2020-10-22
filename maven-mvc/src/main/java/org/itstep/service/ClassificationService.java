package org.itstep.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.repository.ClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class ClassificationService {
	@Autowired private ClassificationRepository repo;		

	public List<Classification> findAll() {
	  
		List<Classification> list =(List<Classification>) repo.findAll();
/*		list.forEach(x -> {
  			try {
			    x.setParentname(findById(x.getParentid()).getName());}
			    catch (Exception e) {}	
		});  */
		for (Classification c: list) {
			Long ip = c.getParentid();
			if (ip!=null && ip>0) { String sc=findById(ip).getName();c.setParentname(sc);}			
		}
		 return list;				
	}	
	
    public void save(Classification country) {repo.save(country);}        
 
    public Classification findById(Long id) {
    	
    	Classification cat = repo.findById(id).orElse(null);
    	Long ip = cat.getParentid();
		if (ip!=null && ip>0) { String sc=repo.findById(ip).orElse(null).getName();cat.setParentname(sc);}	
    	/*	try {
    		cat.setParentname(findById(cat.getParentid()).getName());}
		    catch (Exception e) {}   */
    	return cat;    	    	
    }            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    
    public List<Classification> search(String keyword) {return (List<Classification>) repo.search(keyword);}	

//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////
    public boolean ComplexDelete(Long id) throws LogicException {
	
      Classification classification= findById(id);	  
     if (classification==null) {return false;}
       Long pid = classification.getParentid();		    
     if (pid==null || pid==0) {		  
        for (Classification entry:findAll().stream().filter((x) ->x.getParentid()==id).collect(Collectors.toList())) {				   
    	   delete(entry.getId());		
        } 			   
     } 
     delete(id);
     return true; 	  			  	  	  	  	 	 
}
/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////

    public List<Classification> readByCategory(List<Long> id) {
					
      List<Classification> nl = new ArrayList<>();		
      for(Long i:id) {
         Classification cl = repo.readByCategory(i);
         nl.add(cl);
      }				
       return nl;
    }
    
}
