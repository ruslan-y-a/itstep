package tabs;

import java.util.ArrayList;
//import java.util.Map;
//import java.util.Set;

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
  public static Entity getEntity(String name) {
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
	if (name.equals("size"))  {return new Size();}
	if (name.equals("color"))  {return new Color();}
	if (name.equals("itemtagurl"))  {return new Itemtagurl();}
	return null;    
  }
   /*
  public static String addServlet(String str) {
	  if (str.isBlank()) {return null;} 
	  if (str.indexOf("users")>=0)       {return new String("/WEB-INF/jsp/users/edit.jsp");}  
	  if (str.indexOf("client")>=0)      {return new String("/WEB-INF/jsp/client/edit.jsp");}
	  if (str.indexOf("category")>=0)    {return new String("/WEB-INF/jsp/category/edit.jsp");}
   if (str.indexOf("classification")>=0) {return new String("/WEB-INF/jsp/classification/edit.jsp");}
	  if (str.indexOf("country")>=0)     {return new String("/WEB-INF/jsp/country/edit.jsp");}
	  if (str.indexOf("currency")>=0)    {return new String("/WEB-INF/jsp/currency/edit.jsp");}
	  if (str.indexOf("img")>=0)         {return new String("/WEB-INF/jsp/img/edit.jsp");}
	  if (str.indexOf("itemcatgory")>=0) {return new String("/WEB-INF/jsp/itemcatgory/edit.jsp");}
	  if (str.indexOf("items")>=0)       {return new String("/WEB-INF/jsp/items/edit.jsp");}
	  if (str.indexOf("baseitem")>=0)    {return new String("/WEB-INF/jsp/baseitem/edit.jsp");}
	  if (str.indexOf("orders")>=0)      {return new String("/WEB-INF/jsp/orders/edit.jsp");}
	  if (str.indexOf("sale")>=0)        {return new String("/WEB-INF/jsp/sale/edit.jsp");}
	  if (str.indexOf("tagcloud")>=0)    {return new String("/WEB-INF/jsp/tagcloud/edit.jsp");}
	  if (str.indexOf("tagurl")>=0)       {return new String("/WEB-INF/jsp/tagurl/edit.jsp");}
	  if (str.indexOf("webpages")>=0)       {return new String("/WEB-INF/jsp/webpages/edit.jsp");}
	  if (str.indexOf("size")>=0)       {return new String("/WEB-INF/jsp/size/edit.jsp");}
	  if (str.indexOf("color")>=0)       {return new String("/WEB-INF/jsp/color/edit.jsp");}	
	  if (str.indexOf("itemtagurl")>=0)       {return new String("/WEB-INF/jsp/itemtagurl/edit.jsp");}	
		return null;	  
	  }

  public static Entity saveServlet(String str) {
	  if (str.isBlank()) {return null;} 
	  if (str.indexOf("users")>=0)       {return new User();}  
	  if (str.indexOf("client")>=0)      {return new Client();}
	  if (str.indexOf("category")>=0)    {return new Category();}
   if (str.indexOf("classification")>=0) {return new Classification();}
	  if (str.indexOf("country")>=0)     {return new Country();}
	  if (str.indexOf("currency")>=0)    {return new Currency();}
	  if (str.indexOf("img")>=0)         {return new Img();}
	  if (str.indexOf("itemcatgory")>=0) {return new Itemcatgory();}
	  if (str.indexOf("items")>=0)       {return new Items();}
	  if (str.indexOf("baseitem")>=0)    {return new Baseitem();}
	  if (str.indexOf("orders")>=0)      {return new Orders();}
	  if (str.indexOf("sale")>=0)        {return new Sale();}
	  if (str.indexOf("tagcloud")>=0)    {return new Tagcloud();}
	  if (str.indexOf("tagurl")>=0)      {return new Tagurl();}
	  if (str.indexOf("webpages")>=0)    {return new Webpages();}
	  if (str.indexOf("size")>=0)        {return new Size();}
	  if (str.indexOf("color")>=0)       {return new Color();}			
	  if (str.indexOf("itemtagurl")>=0)  {return new Itemtagurl();}
		return null;	  
	  }
    */
  public static String getDBName(String str) {
	  if (str.isBlank()) {return null;} 
	  if (str.indexOf("users")>=0)       {return "users";}  
	  if (str.indexOf("client")>=0)      {return "client";}
	  if (str.indexOf("category")>=0)    {return "category";}
   if (str.indexOf("classification")>=0) {return "classification";}
	  if (str.indexOf("country")>=0)     {return "country";}
	  if (str.indexOf("currency")>=0)    {return "currency";}
	  if (str.indexOf("img")>=0)         {return "img";}
	  if (str.indexOf("itemcatgory")>=0) {return "itemcatgory";}
	  if (str.indexOf("items")>=0)       {return "items";}
	  if (str.indexOf("baseitem")>=0)    {return "baseitem";}
	  if (str.indexOf("orders")>=0)      {return "orders";}
	  if (str.indexOf("sale")>=0)        {return "sale";}
	  if (str.indexOf("tagcloud")>=0)    {return "tagcloud";}
	  if (str.indexOf("tagurl")>=0)      {return "tagurl";}
	  if (str.indexOf("webpages")>=0)    {return "webpages";}
	  if (str.indexOf("size")>=0)        {return "size";}
	  if (str.indexOf("color")>=0)       {return "color";}	
	  if (str.indexOf("itemtagurl")>=0)       {return "itemtagurl";}	
		return null;	  
	  }
  
   public static ArrayList<String> getEntityFieldsforName(String name){	    
	return getEntity(name).getFieldsArrayList();	   
   }
  /* public static ArrayList<String> getEntityFieldsforUri(String name){	    
	return saveServlet(name).getFieldsArrayList();	   
   }
 */
   

   
}
