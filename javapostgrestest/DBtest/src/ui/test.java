package ui;

import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;

import service.DBService;
import service.LogicException;
import tabs.Baseitem;
import tabs.Category;
import tabs.Classification;
import tabs.Client;
import tabs.Country;
import tabs.Currency;
import tabs.Entity;
import tabs.Img;
import tabs.Itemcatgory;
import tabs.Items;
import tabs.Orders;
import tabs.Sale;
import tabs.Tagcloud;
import tabs.Tagurl;
import tabs.User;
import tabs.Variant;
import tabs.Webpages;

public class test {

	public static void main(String[] args) throws LogicException {
		try(Factory factory = new Factory()) {
			DBService service = factory.getDBService();
			List<Entity> products;
		//	products= service.read("client");printClient(products);
		//	products= service.read("users");printUser(products);
		//	products= service.read("baseitem");printBaseitem(products);
		//	products= service.read("category");printCategory(products);
		//	products= service.read("classification");printClassification(products);
		//	products= service.read("country");printCountry(products);
		//	products= service.read("currency");printCurrency(products);
		//	products= service.read("img");printImg(products);
		//	products= service.read("itemcatgory");printItemcatgory(products);
		//	products= service.read("items");printItems(products);
		//	products= service.read("orders");printOrders(products);
		//	products= service.read("sale");printSale(products);
		//	products= service.read("tagcloud");printTagcloud(products);
		//	products= service.read("tagurl");printTagurl(products);
		//	products= service.read("webpages");printWebpages(products);
		//	products= service.read("size");printVariant(products);																																						
			products= service.read("color");printVariant(products);
		} catch(LogicException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	static void printClient(List<Entity> products ) {
		Client client; 
		for(Entity product : products) {
			 client=(Client)product; 
			System.out.printf("<tr>");
			System.out.printf("<td>%d</td>\n", client.DBgetId());
			System.out.printf("<td>%d</td>\n", client.getCountryid());
			System.out.printf("<td>%s</td>\n", client.getAddress());
			System.out.printf("<td>%1$td.%1$tm.%1$tY, %1$tA</td>\n", client.getCreationdate());
			System.out.printf("<td>+%s</td>\n", client.getPhoneno());		
			System.out.printf("<td>%d</td>\n", client.getUserid());
			System.out.printf("</tr>");
		}
	}
	//////////////////////////////////////////////////////////	
	static void printUser(List<Entity> products ) {
		User user; 
		for(Entity product : products) {
			user=(User)product; 
			System.out.printf("<tr>");
			System.out.printf("<td>%d</td>\n", user.DBgetId());
			System.out.printf("<td>%s</td>\n", user.getName());
			System.out.printf("<td>%s</td>\n", user.getPassword());
			System.out.printf("<td>%s</td>\n", user.getEmail());
			System.out.printf("<td>+%s</td>\n", user.getRoleid());					
			System.out.printf("</tr>");
		}
	}
//////////////////////////////////////////////////////////
    static void printVariant(List<Entity> products ) {
     Variant variant; 
     for(Entity product : products) {
    	 variant=(Variant)product; 
      System.out.printf("<tr>");
      System.out.printf("<td>%d</td>\n", variant.DBgetId());
      System.out.printf("<td>%s</td>\n", variant.getName());  
      System.out.printf("</tr>");
     }
   }
//////////////////////////////////////////////////////////
    static void printBaseitem(List<Entity> products ) {
    	Baseitem baseitem; 
       for(Entity product : products) {
    	   baseitem=(Baseitem)product; 
         System.out.printf("<tr>");
        System.out.printf("<td>%d</td>\n", baseitem.DBgetId());
         System.out.printf("<td>%d</td>\n", baseitem.getItemid());
        System.out.printf("<td>%d</td>\n", baseitem.getColorID());
        System.out.printf("<td>%d</td>\n", baseitem.getSizeID());
        System.out.printf("<td>%s</td>\n", baseitem.getName());		
        System.out.printf("<td>%d</td>\n", baseitem.getQuantity());
        System.out.printf("<td>%d</td>\n", baseitem.getBaseprice());
        System.out.printf("<td>%d</td>\n", baseitem.getCurrency());    
        System.out.printf("</tr>");
       }
    }
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printCategory(List<Entity> products ) {
	Category category; 
   for(Entity product : products) {
	category=(Category)product; 
    System.out.printf("<tr>");
    System.out.printf("<td>%d</td>\n", category.DBgetId());
    System.out.printf("<td>%d</td>\n", category.getParentid());
    System.out.printf("<td>%s</td>\n", category.getName());   
    System.out.printf("</tr>");
    }
  }
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printClassification(List<Entity> products ) {
	Classification classification; 
for(Entity product : products) {
	classification=(Classification)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", classification.DBgetId());
System.out.printf("<td>%d</td>\n", classification.getParentid());
System.out.printf("<td>%d</td>\n", classification.getCategoryid());   
System.out.printf("<td>%s</td>\n", classification.getName());   
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printCountry(List<Entity> products ) {
	Country country; 
for(Entity product : products) {
	country=(Country)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", country.DBgetId());
System.out.printf("<td>%s</td>\n", country.getName());   
System.out.printf("<td>%d</td>\n", country.getCurrency());  
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printCurrency(List<Entity> products ) {
	Currency currency; 
for(Entity product : products) {
	currency=(Currency)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", currency.DBgetId());
System.out.printf("<td>%s</td>\n", currency.getName());   
System.out.printf("<td>%f</td>\n", currency.getRate());  
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printImg(List<Entity> products ) {
	Img img; 
for(Entity product : products) {
	img=(Img)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", img.DBgetId());
System.out.printf("<td>%s</td>\n", img.getItems());   
System.out.printf("<td>%s</td>\n", img.getTitle());  
System.out.printf("<td>%s</td>\n", img.getAlt());  
System.out.printf("<td>%s</td>\n", img.getUrl());  
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printItemcatgory(List<Entity> products ) {
	Itemcatgory itemcatgory; 
for(Entity product : products) {
	itemcatgory=(Itemcatgory)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", itemcatgory.DBgetId());
System.out.printf("<td>%s</td>\n", itemcatgory.getItems());   
System.out.printf("<td>%s</td>\n", itemcatgory.getClassification());   
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printItems(List<Entity> products ) {
	Items item; 
for(Entity product : products) {
	item=(Items)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", item.DBgetId());
System.out.printf("<td>%s</td>\n", item.getArticul());   
System.out.printf("<td>%s</td>\n", item.getModel());   
System.out.printf("<td>%s</td>\n", item.getBaseprice());   
System.out.printf("<td>%s</td>\n", item.getDiscount());   
System.out.printf("<td>%s</td>\n", item.getTitle());   
System.out.printf("<td>%s</td>\n", item.getText());   
System.out.printf("<td>%s</td>\n", item.getName());   
System.out.printf("<td>%s</td>\n", item.getDescription());   
System.out.printf("<td>%s</td>\n", item.getTagurl());   
System.out.printf("<td>%s</td>\n", item.getKeywords());   
System.out.printf("<td>%s</td>\n", item.getMainimgurl());   
System.out.printf("<td>%s</td>\n", item.getUrl());   
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printOrders(List<Entity> products ) {
	Orders item; 
for(Entity product : products) {
  item=(Orders)product; 
   System.out.printf("<tr>");
   System.out.printf("<td>%d</td>\n", item.DBgetId());
   System.out.printf("<td>%s</td>\n", item.getNumber());   
   System.out.printf("<td>%s</td>\n", item.getDatetime());   
   System.out.printf("<td>%s</td>\n", item.getDateexpired());   
   System.out.printf("<td>%s</td>\n", item.getCustomerid());   
   System.out.printf("<td>%s</td>\n", item.getQuantity());   
   System.out.printf("<td>%s</td>\n", item.getSum());   
   System.out.printf("<td>%s</td>\n", item.getCurrencyid());   
   System.out.printf("<td>%s</td>\n", item.getOrdertype());   
   System.out.printf("<td>%s</td>\n", item.isActive());   
   System.out.printf("<td>%s</td>\n", item.getStatus());    
   System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printSale(List<Entity> products ) {
	Sale sale; 
for(Entity product : products) {
	sale=(Sale)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", sale.DBgetId());
System.out.printf("<td>%s</td>\n", sale.getDatetime());   
System.out.printf("<td>%s</td>\n", sale.getOrderid());
System.out.printf("<td>%s</td>\n", sale.isReturn());
System.out.printf("<td>%s</td>\n", sale.getCurrencyid());
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printTagcloud(List<Entity> products ) {
	Tagcloud tagcloud; 
for(Entity product : products) {
	tagcloud=(Tagcloud)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", tagcloud.DBgetId());
System.out.println("<td>" +tagcloud.getClassification()+ "</td>\n");   
System.out.printf("<td>%s</td>\n", tagcloud.getTagurl());
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printTagurl(List<Entity> products ) {
	Tagurl tagurl; 
for(Entity product : products) {
	tagurl=(Tagurl)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", tagurl.DBgetId()); 
System.out.printf("<td>%s</td>\n", tagurl.getTagname());
System.out.printf("<td>%s</td>\n", tagurl.getUrl());
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////
static void printWebpages(List<Entity> products ) {
	Webpages webpages; 
for(Entity product : products) {
	webpages=(Webpages)product; 
System.out.printf("<tr>");
System.out.printf("<td>%d</td>\n", webpages.DBgetId()); 
System.out.printf("<td>%s</td>\n", webpages.getUrl());
System.out.printf("<td>%s</td>\n", webpages.getTitle());
System.out.printf("<td>%s</td>\n", webpages.getDescription());
System.out.printf("<td>%s</td>\n", webpages.getKeywords());
System.out.printf("<td>%s</td>\n", webpages.getH1());
System.out.printf("<td>%s</td>\n", webpages.getText());
System.out.printf("<td>%s</td>\n", webpages.getCategoryid());
System.out.printf("<td>%s</td>\n", webpages.getTagurl());
System.out.printf("<td>%s</td>\n", webpages.getRobots());
System.out.printf("</tr>");
}
}
//////////////////////////////////////////////////////////
}
