package org.itstep.de_services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.itstep.daos.ClassificationDao;
//import entities.Category;
import org.itstep.entities.Classification;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ClassificationServiceImpl implements ClassificationService {
	@Autowired	
	private ClassificationDao classificationDao;
	public void setClassificationDao(ClassificationDao classificationDao) {this.classificationDao = classificationDao;}

	@Override
	public List<Classification> findAll() throws LogicException {
		try {			
			return  classificationDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Classification classification) throws LogicException {
		try {
			Long id=classification.getId();
			if(id == null) {
				id = classificationDao.create(classification);
				classification.setId(id);
			} else {
				classificationDao.update(classification);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
			ComplexDelete(id); //	classificationDao.delete(id);
	}
	
	@Override
	public Classification read(Long id) throws LogicException {
		try {					
			return classificationDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {	
			for(Long id : ids) {
				ComplexDelete(id); // classificationDao.delete(id);
			}	
	}
	
//////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////
private boolean ComplexDelete(Long id) throws LogicException {
   try {	
	Classification classification= classificationDao.read(id);	  
	if (classification==null) {return false;}
    Long pid = classification.getParentid();		    
    if (pid==null || pid==0) {		  
       for (Classification entry:classificationDao.read().stream().filter((x) ->x.getParentid()==id).collect(Collectors.toList())) {				   
  	   ///  System.out.println("============DELETE)" + entry.getId());
    	   classificationDao.delete(entry.getId());		
       } 			   
     } 
    classificationDao.delete(id);

  } catch (DaoException e) {
     e.printStackTrace(); throw new LogicException(e);} 

    return true; 	  			  	  	  	  	 	 
    }
/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////

@Override
public List<Classification> readByCategory(List<Long> id) throws LogicException {
	try {					
		List<Classification> nl = new ArrayList<>();		
		for(Long i:id) {
			Classification cl = classificationDao.readByCategory(i);
			nl.add(cl);
		}				
		return nl;
	} catch(DaoException e) {
		throw new LogicException(e);
	}
}

 }
