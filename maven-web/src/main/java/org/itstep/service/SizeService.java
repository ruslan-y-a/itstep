package org.itstep.service;

import java.util.List;

import org.itstep.entities.Size;
import org.itstep.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@org.springframework.stereotype.Service
@Transactional
public class SizeService {
	@Autowired 
	private SizeRepository repo;
		
	public SizeService(SizeRepository repo) {
		this.repo = repo;
	}
	public List<Size> findAll() {return (List<Size>) repo.findAll();}	 	   
    public void save(Size size) {repo.save(size);}        
    public Size findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}   
}
