package org.itstep.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.itstep.entities.User;
import org.itstep.repository.UserRepository;

@Component
@org.springframework.stereotype.Service
@Transactional
public class UserService {
 	@Autowired private UserRepository repo;
	
	public UserService(UserRepository repo) {
		this.repo = repo;
	}
	public List<User> findAll() {return (List<User>) repo.findAll();}	 	   
    public void save(User user) {repo.save(user);}        
    public User findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}   
    public User authenticate(String login, String password) {    	    		
			User user= new User();
			user=repo.authenticate(login, password);			
			if (user!=null) {user=findById(user.getId());}
			return user;		    	
    }
 //   public List<User> search(String keyword) {return repo.search(keyword);}	*/
}
