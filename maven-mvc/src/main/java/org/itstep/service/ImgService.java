package org.itstep.service;

import java.util.List;

//import org.itstep.daos.DaoException;
import org.itstep.entities.Img;
import org.itstep.repository.ImgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@org.springframework.stereotype.Service
@Transactional
public class ImgService {
	@Autowired private ImgRepository repo;
	public ImgService(ImgRepository repo) {
		this.repo = repo;
	}
	public List<Img> findAll() {return (List<Img>) repo.findAll();}	 	   
    public void save(Img img) {
    	repo.save(img);}      
    public Long saveGetId(Img img) {
    	repo.save(img);  
    	Long id=null;
    	if (img!=null && img.getId()!=null) {}
    	else {img = repo.findByUrl(img.getUrl());}
    	id=img.getId();
    	return id;
    	}      
    public Img findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}       
}
