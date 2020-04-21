package tabs;

//import java.util.HashMap;
//import java.util.List;

public class Mapper {
  //private HashMap<String,Entity> DBTabs;
 // private List<String> tabsList;
  /*
  public Mapper() {
	  DBTabs = new HashMap<String,Entity>();	  
	  DBTabs.put("users", getUser());
  }
  
    */
  public Entity getEntity(String name) {
	if ("users"==name)  {return new User();}
	if ("client"==name)  {return new Client();}
	return null;    
  }
  
}
