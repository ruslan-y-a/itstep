package org.itstep.security;

import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
//import org.hibernate.SessionFactory;
import org.itstep.entities.User;
import org.itstep.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UserDetailsDaoImp implements UserDetailsDao {
	@Autowired private UserRepository repo;
	
	public UserDetailsDaoImp(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public User findUserByLogin(String login) {
		User user= repo.findUserByLogin(login);		 
		return user;
	}

	@Override
	public void saveUser(User user) {
		repo.save(user);
	}
	
/*
  private SessionFactory sessionFactory;
  
  @Autowired
  public UserDetailsDaoImp(EntityManagerFactory factory) {
    if(factory.unwrap(SessionFactory.class) == null){
      throw new NullPointerException("factory is not a hibernate factory");
    }
    this.sessionFactory = factory.unwrap(SessionFactory.class);
  }
  
  @Override
  public User findUserByLogin(String login) {
	  Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	  Session session = sessionFactory.getCurrentSession();
	  CriteriaBuilder builder = session.getCriteriaBuilder();
	  CriteriaQuery<User> criteria = builder.createQuery(User.class);	    
	  Root<User> contactRoot = criteria.from(User.class);
	  Predicate  restriction =  builder.equal(contactRoot.get("login"), login);
      criteria.select(contactRoot).where(restriction);

      TypedQuery<User> query = session.createQuery(criteria);
      User user = query.getSingleResult();
      	  
	  //Criteria criteria = session.createCriteria(User.class);
      //criteria.add(Restrictions.like("login", login));
    //  return (User) criteria.uniqueResult();
	  
	  //User user = sessionFactory.getCurrentSession().get(User.class, login);
	  tx.commit();
    return user;
  }
  @Override
  public void saveUser(User user) {
	  Transaction tx = sessionFactory.getCurrentSession().beginTransaction();
	  sessionFactory.getCurrentSession().save(user);    
	  tx.commit();
  }
  */
}
