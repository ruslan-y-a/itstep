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
	if (name.equals("users"))  {return new User();}
	if (name.equals("client"))  {return new Client();}
	if (name.equals("category"))  {return new Category();}
	if (name.equals("classification"))  {return new Classification();}
	if (name.equals("country"))  {return new Country();}
	if (name.equals("currency"))  {return new Currency();}
	if (name.equals("img"))  {return new Img();}
	if (name.equals("itemcatgory"))  {return new Itemcatgory();}
	if (name.equals("items"))  {return new Items();}
	if (name.equals("baseitem"))  {return new Baseitem();}
	if (name.equals("orders"))  {return new Orders();}
	if (name.equals("sale"))  {return new Sale();}
	if (name.equals("tagcloud"))  {return new Tagcloud();}
	if (name.equals("tagurl"))  {return new Tagurl();}
	if (name.equals("webpages"))  {return new Webpages();}
	if (name.equals("size"))  {return new Variant("size");}
	if (name.equals("color"))  {return new Variant("color");}
	return null;    
  }
  
}
