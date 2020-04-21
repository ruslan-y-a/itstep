package service;

import java.util.List;
import postgres.DaoException;
import postgres.DbDao;
import tabs.Entity;

public class DBServiceImpl implements DBService {
  private DbDao dbDao;
  
  public DBServiceImpl(DbDao dbDao) {
	  this.dbDao=dbDao;
  }
  
  public DbDao getDbDao() {
	return dbDao;}
  public void setDbDao(DbDao dbDao) {
	this.dbDao = dbDao;}
  
  /*===============================================================*/
  /*===============================================================*/
  public List<Entity> readAll(String dbName) throws LogicException {
		try {
			List<Entity> entities = dbDao.read(dbName);
			for(Entity entity : entities) {
				
			}
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  
	public Entity read(String dbName,Long id) throws LogicException {
		try {
		   return dbDao.read(dbName,id);
		}
		catch (DaoException e) {
			  throw new LogicException(e.getExceptionText(),e);
			}		
	}
	
  public void save(Entity entity) throws LogicException {
	  try {
	  if(entity.DBgetId() == null) {
	  Long id = dbDao.create(entity);
	  entity.DBsetId(id);
	    } else {
		  dbDao.update(entity);
		  }		   
	  } catch(DaoException e) {
	  throw new LogicException(e);
	   }
   }
  
  public boolean delete(String dbName,Long id) throws LogicException {
		try {
			boolean res=dbDao.delete(dbName,id);
			 return res;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  
///////////////////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////		
   public boolean hasDbDao() {
		return dbDao!=null;
	}
	//////////////////////////////////////////////////////////////
/////////////////////////////READ/////////////////////////////////
	//////////////////////////////////////////////////////////////

	
	
  
}
 
