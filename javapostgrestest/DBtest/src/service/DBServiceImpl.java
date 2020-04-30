package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import csvLoader.CsvLoader;
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
 // @SuppressWarnings("null")
public void createCsvLoad(String dbName) throws LogicException, DaoException {
		try {
			CsvLoader csvLoader = new CsvLoader(dbName);
			Map<String,ArrayList<String>> mData = csvLoader.Load();
			ArrayList<String> fileds=new ArrayList<String>();
	//		mData.keySet().forEach((x)->fileds.add(x));			
			mData.keySet().forEach((x)->{
				  if (!x.equals("id")) {fileds.add(x);}
				  });
			int rnum =mData.get(fileds.get(0)).size();
			long idc=-1;
			ArrayList<String> values=new ArrayList<String>();						
			for (int i=0;i<rnum;i++) {
				values.clear();
				
				for (Map.Entry<String,ArrayList<String>> entry: mData.entrySet()) {	
				  if (!entry.getKey().equals("id")) {
					 values.add(entry.getValue().get(i));
				  } else {
					  idc=Long.parseLong(entry.getValue().get(i));}
				}
		/*		for(ArrayList<String> als:mData.values()) {
					values.add(als.get(i));
				 }*/				
				if (idc==-1) {				
				   try {dbDao.create(dbName, fileds, values);
				       } catch (DaoException e) {				
						e.printStackTrace();}
				} else {
					try {dbDao.update(dbName,idc, fileds, values);
				       } catch (DaoException e) {				
						e.printStackTrace();}					
				}
				
			}						
		} catch(ClassNotFoundException | IOException e) {
			throw new LogicException(e);
		}
	}
  
  /*===============================================================*/
  /*===============================================================*/
  public List<Entity> read(String dbName) throws LogicException {
		try {
			List<Entity> entities = dbDao.read(dbName);
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  public List<Entity> categoryListing(Long idCategotes[]) throws LogicException {
		try {
			Long[] ids = dbDao.readIDOr("itemcatgory","classification",idCategotes);
		//	Long[] idsT = dbDao.readIDToArray("tagcloud","classification",idCategotes);	
		//	List<Entity> tags = dbDao.read("tagurl","id", idsT);
			List<Entity> entities = dbDao.read("items","id", ids);
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
    public List<Entity> tagList(Long idCategotes[]) throws LogicException {
		try {			
			Long[] idsT = dbDao.readIDOr("tagcloud","classification",idCategotes);	
			List<Entity> tags = dbDao.read("tagurl","id", idsT);			
			return tags;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
    public List<Entity> imgList(Long idItem) throws LogicException {
		try {						
			List<Entity> imgs = dbDao.read("img","id", idItem);			
			return imgs;
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
 
