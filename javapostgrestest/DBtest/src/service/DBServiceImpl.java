package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import csvLoader.CsvLoader;
import postgres.DaoException;
import postgres.DbDao;
import tabs.Category;
//import tabs.Category;
import tabs.Classification;
import tabs.Entity;
import tabs.Itemcatgory;
import tabs.Mapper;
//import tabs.Mapper;

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
public void createCsvLoad(String dbName) throws LogicException {
		try {
			CsvLoader csvLoader = new CsvLoader(dbName);
			Map<String,ArrayList<String>> mData = csvLoader.Load();
			dbName=Mapper.getDBName(dbName);
			ArrayList<String> fileds=new ArrayList<String>();
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
				if (idc==-1) {				
				   try {dbDao.create(dbName, fileds, values);
				       } catch (DaoException e) {				
						e.printStackTrace();
						throw new LogicException(e);
				       }
				} else {
					try {dbDao.update(dbName,idc, fileds, values);
				       } catch (DaoException e) {				
						e.printStackTrace();
						throw new LogicException(e);
				       }					
				}
				
			}						
		} catch(ClassNotFoundException | IOException e) {
			throw new LogicException(e);
		}
	}
   public void create(String dbName, Map<String,String> mass) throws LogicException {
	   long idc=-1;   
	   ArrayList<String> fileds = new ArrayList<String>();
	   ArrayList<String> values = new ArrayList<String>(); 
	   for (Map.Entry<String,String> entry: mass.entrySet()) {	
			  if (entry.getKey().equals("id")) {				 
				  if (entry.getValue()!=null) {idc=Long.parseLong(entry.getValue());continue;}
		      } else {
			  fileds.add(entry.getKey()); values.add(entry.getValue());
			  //System.out.println(entry.getKey() + " " +entry.getValue());
			  } 			  
	      }
	   
	       if (idc==-1) {
	          try {
		      dbDao.create(dbName, fileds, values);
	          } catch (DaoException e) {				
			    e.printStackTrace(); throw new LogicException(e);
	          }
			} else {
				try {
					dbDao.update(dbName,idc, fileds, values);
			       } catch (DaoException e) {				
					e.printStackTrace(); throw new LogicException(e);
			       }					
			}												
}
   public void create(String dbName, ArrayList<String> fileds,ArrayList<String> values) throws LogicException {
	   long idc=-1;   	  
	   for (int i=0; i<fileds.size();i++) {	
			  if (fileds.get(i).equals("id")) {				 
				  idc=Long.parseLong(values.get(i));}			  
	   }	   
	       if (idc==-1) {
	          try {
		      dbDao.create(dbName, fileds, values);
	          } catch (DaoException e) {				
			    e.printStackTrace(); throw new LogicException(e);
	          }
			} else {
				try {
					dbDao.update(dbName,idc, fileds, values);
			       } catch (DaoException e) {				
					e.printStackTrace(); throw new LogicException(e);
			       }					
			}												
}
 
   public void create(String dbName, ArrayList<Entity> values) throws LogicException {	      	  
	   for (int i=0; i<values.size();i++) {	
			  if (values.get(i).DBgetId()==null) {				 
				  try {
				     dbDao.create(values.get(i));
			          } catch (DaoException e) {				
					    e.printStackTrace(); throw new LogicException(e);
			          }
			  } else {	 				  
					try {
						dbDao.update(values.get(i));
				       } catch (DaoException e) {				
						e.printStackTrace(); throw new LogicException(e);
				       }					  
			    }			  			  
	   }	   
	    	    												
}
  /*===============================================================*/
  /*===============================================================*/
   //V//
  public List<Entity> read(String dbName) throws LogicException {
		try {
			List<Entity> entities = dbDao.read(dbName);
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  //V//
  public List<Entity> read(String dbName, String field, Object fValue) throws LogicException {
		try {
			List<Entity> entities = dbDao.read(dbName,field,fValue);
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  //V//
  public List<Entity> read(String dbName, String field, String expr) throws LogicException {
		try {
			List<Entity> entities = dbDao.read(dbName,field,expr);
			return entities;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
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
  public List<Classification> itemCategories(Long itemId) throws LogicException {
		try {
			List<Entity> itemClass = dbDao.read("itemcatgory","items",itemId);
			List<Classification> categs=new ArrayList<Classification>();// = dbDao.read("itemcatgory","id", idCateg);
			Itemcatgory ic=null;
			for (Entity lid: itemClass) {
				ic=(Itemcatgory) lid;
				categs.add((Classification)dbDao.read("classification", ic.getClassification()));}
			return categs;
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
    public Map<Long,Object> mapIdField(String name, String tarfield, String field, Object fValue) throws LogicException {
		try {						
			Map<Long,Object> mymap = dbDao.readField(name,tarfield, field,fValue);			
			return mymap;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}    
    public Map<Long,Object> mapIdField(String name, String tarfield, String field, String expr) throws LogicException {
		try {						
			Map<Long,Object> mymap = dbDao.readField(name,tarfield, field,expr);			
			return mymap;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}   
    
    public Map<Long,Entity> mapEntities(String name) throws LogicException {
    	try {    		
    		Map<Long,Entity> expMap=new LinkedHashMap<Long,Entity>();    
			List<Entity> list = dbDao.read(name);
			for (Entity entity: list) {			 
				expMap.put(entity.DBgetId(), entity);}			
			return expMap;
		} catch (DaoException e) {			
			e.printStackTrace();
			throw new LogicException(e);
		} 
		//return null;    	
    }
    
//////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////// 
 //V//   
    @Override
	public Entity read(String dbName,Long id) throws LogicException {
		try {
		   return dbDao.read(dbName,id);}
		catch (DaoException e) {
			  throw new LogicException(e.getExceptionText(),e);}		
	}
  //V//	
	 @Override
	public Long[] readID(String name, String field, Object fValue) throws LogicException {
		try {
		   return dbDao.readID( name, field, fValue);}
		catch (DaoException e) {
			  throw new LogicException(e.getExceptionText(),e);}		
	}
///////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////
//V//	
	 @Override
  public Long save(Entity entity) throws LogicException {
	  try {
	  if(entity.DBgetId() == null) {	  
	    return dbDao.create(entity);
	  } else { 
		  dbDao.update(entity);
		  }	
	    return entity.DBgetId();
	  } catch(DaoException e) {
	  throw new LogicException(e);
	   }
   }
//V//	
  @Override
  public void update(Entity entity) throws LogicException {
	  try {	  
		  dbDao.update(entity);				    
	  } catch(DaoException e) {
	  throw new LogicException(e);
	   }
   }
  //V// 
  @Override
  public Long save(String name, Long id, ArrayList<String> fields, ArrayList<String> values) throws LogicException {
	  try {
	  if(id == null || id == 0) {		  	
	     return dbDao.create(name,fields,values);
	  } else {
		  dbDao.update(name,id, fields,  values);
		  }	
	    return id;
	  } catch(DaoException e) {
	  throw new LogicException(e);
	   }
   }
///////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////
///////////////////////////////////////////////////////// 
  @Override
  public boolean delete(Entity entity) throws LogicException {
		try {
			String dbName = new String(entity.getDBName());
			Long id = entity.DBgetId();
			boolean res=delete(dbName,id);
			 return res;
		} catch(Exception e) {
			throw new LogicException(e);
		} 
	}
  //V//
  @Override
  public boolean delete(String dbName,Long id) throws LogicException {
		try {
			boolean res;		
			if (!ComplexDelete(dbName,id)) {
			     res=dbDao.delete(dbName,id);}
			else {res=true;}
			 return res;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  //V//
  @Override
  public void delete(String dbName,List<Long> ids) throws LogicException {
	  try {
			for(Long id : ids) {
				if (!ComplexDelete(dbName,id)) {
			 	   if (dbDao.delete(dbName,id)) {System.out.println("deleted id " + id);};
			 	} 
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
  @Override
  public boolean delete(String name,  String field, Object fValue) throws LogicException {
	  
	  try {
		  boolean res=false; Long id;
		  List<Entity> list=read(name,  field, fValue);
		  for (Entity entity: list) 
		  {
			  id= entity.DBgetId();
			  if (!ComplexDelete(name,id)) {res=delete(name,id);}
			  else {res=true;}
		  }			
			return res;
		} catch(Exception e) {
			throw new LogicException(e);
		}
  }
  private boolean ComplexDelete(String name, Long id) throws LogicException {
	  if (!(name.equals("category") || name.equals("classification"))) { return false;}
	  Entity entity=read(name,id);	  
	  Long pid=null;
	  if (name.equals("category")) {pid = ((Category)entity).getParentid();}
	  if (name.equals("classification")) {pid = ((Classification)entity).getParentid();}
	  if (pid==null || pid==0 || entity.DBgetId()==id) {		  
		  for (Entity entry:read(name,"parentid",id)) {
			    try {
				   dbDao.delete(name,entry.DBgetId());
			     } catch (DaoException e) {
				   e.printStackTrace(); throw new LogicException(e);}
			  } 
		  try { dbDao.delete(name,id); } catch (DaoException e) { e.printStackTrace(); throw new LogicException(e);}
	  
	  } else {return false;}
	  return true; 	  			  	  	  	  	 	 
  }
///////////////////////////////////////////////////////////////////////////	
///////////////////////////////////////////////////////////////////////////		
   public boolean hasDbDao() {
		return dbDao!=null;
	}
	//////////////////////////////////////////////////////////////
/////////////////////////////OTHER/////////////////////////////////
	//////////////////////////////////////////////////////////////
 //V//  
   @Override
  public void updateClassification(Long idItem, Long idCategory) throws LogicException  {
	  try {
		  Classification classification = (Classification)dbDao.read("classification", "categoryid", idCategory).get(0); 
		  Long idNewParent = classification.getParentid();		
		  Long idOldParent = null;
		  Long iClass=null;
		  Itemcatgory itemcatgory;
		for (Entity entity: dbDao.read("itemcatgory", "items", idItem)) {
			itemcatgory=(Itemcatgory)entity;
			iClass=itemcatgory.getClassification();
			idOldParent=((Classification)dbDao.read("classification",iClass)).getParentid();
		  if (idNewParent==idOldParent) {
			  dbDao.delete("itemcatgory", entity.DBgetId());}
		   
		   if (itemcatgory.getItems()==idItem && itemcatgory.getClassification()==classification.DBgetId()) {
			  dbDao.delete("itemcatgory", entity.DBgetId());}
	//	   System.out.println("item idcategory | " + idItem + " | " + entity.DBgetId());
		 }
		  dbDao.delete("itemcatgory", "items",idItem);
		  itemcatgory = new Itemcatgory(); 
		  itemcatgory.put("classification",classification.DBgetId());
		  itemcatgory.put("items", idItem);
		  dbDao.create(itemcatgory);
		
	} catch (DaoException e) {		
		e.printStackTrace(); throw new LogicException(e); 
	}
  }
/////////////////////////////////
//V//   
  @Override
  public boolean updateClassificationFromCategory() throws LogicException  {
	  List<Entity> categories= read("category","parentid",">0");
	 // List<Entity> classifications = read("classification","categoryid",">0");
	  Long categoryid=null;
	  try {categoryid=read("classification","name","Category").get(0).DBgetId();} catch (IndexOutOfBoundsException | NullPointerException e) {
		  throw new LogicException(e);}
	  if (categoryid==null) {System.out.println("Category in classification table was not found");return false;}
	  
	  Long id;
	  Classification classification;
	  Entity entity=null;
	  for (Entity entry:categories) {
		  id=entry.DBgetId();
		  entity = read("classification",id);
		  if (entity==null) {
			   classification = new Classification();	
			   try {
				   classification.put("parentid",categoryid);
				   classification.put("name", ((Category)entry).getName());
				   classification.put("categoryid",id); 
				   classification.setDBName("classification");
				   save(classification);
			    } catch (DaoException e) {				
				e.printStackTrace(); throw new LogicException(e);}			   		
		  }
	  }
	  return true;
  }
  
}
 
