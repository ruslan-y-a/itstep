package org.itstep.web.action;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;

import org.itstep.de_services.BaseService;
import org.itstep.de_services.BaseitemService;
import org.itstep.de_services.CategoryService;
import org.itstep.de_services.ClassificationService;
import org.itstep.de_services.ClientService;
import org.itstep.de_services.ColorService;
import org.itstep.de_services.CountrySevice;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.ImgService;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.OrdersService;
import org.itstep.de_services.SaleService;
import org.itstep.de_services.SizeService;
import org.itstep.de_services.TagcloudService;
import org.itstep.de_services.UserService;
import org.itstep.de_services.WebpagesService;
import org.itstep.service.LogicException;
import org.itstep.web.UploadFileAction;
import org.itstep.web.action.catalog.CatalogDispatherAction;
import org.itstep.web.action.catalog.CatalogListAction;
import org.itstep.web.action.catalog.ProductListAction;
import org.itstep.web.action.catalog.SearchAction;
import org.itstep.web.action.save.BaseitemSaveAction;
import org.itstep.web.action.save.CategorySaveAction;
import org.itstep.web.action.save.ClassificationSaveAction;
import org.itstep.web.action.save.ClientSaveAction;
import org.itstep.web.action.save.ColorSaveAction;
import org.itstep.web.action.save.CountrySaveAction;
import org.itstep.web.action.save.CurrencySaveAction;
import org.itstep.web.action.save.ImgSaveAction;
import org.itstep.web.action.save.ItemsSaveAction;
import org.itstep.web.action.save.OrdersSaveAction;
import org.itstep.web.action.save.SaleSaveAction;
import org.itstep.web.action.save.SizeSaveAction;
import org.itstep.web.action.save.TagcloudSaveAction;
import org.itstep.web.action.save.UserSaveAction;
import org.itstep.web.action.save.WebpagesSaveAction;
import org.itstep.web.action.user.CartAction;
import org.itstep.web.action.user.CheckoutAction;
import org.itstep.web.action.user.DeactiveOrderAction;
import org.itstep.web.action.user.NewOrderAction;
import org.itstep.web.action.user.RegisterAction;
import org.itstep.web.action.user.UnCheckoutAction;
import org.itstep.web.action.user.UserOrderAction;
import org.itstep.web.action.user.UserPageAction;
import org.itstep.web.action.user.UserRegisterAction;
import org.springframework.context.ApplicationContext;

public class ActionFactory {
//	private static Map<String, Class<? extends Action>> actions = new HashMap<>();
	private static ApplicationContext context;
	public static void init(ServletContext context) {
	   ActionFactory.context = (ApplicationContext)context.getAttribute("spring-context");}
	
	private static Map<String, Class<? extends BaseAction>>  asave = new HashMap<>();
	private static Map<String, Class<?>> bservices = new HashMap<>();
	//private Map<String, BaseAction> actions2 = new HashMap<>();
	private static Map<String, Class<?>> actions1 = new HashMap<>();
	
	private static Class<?> mapServiceClass(String surl) throws LogicException {
		Class<?> BS=null;
		 if (surl.equals("baseitem")) {return BaseitemService.class;}
		 if (surl.equals("category")) {return CategoryService.class;}
		 if (surl.equals("classification")) {return ClassificationService.class;}
		 if (surl.equals("client")) {return ClientService.class;}
		 if (surl.equals("color")) {return ColorService.class;}
		 if (surl.equals("country")) {return CountrySevice.class;}
		 if (surl.equals("currency")) {return CurrencyService.class;}
		 if (surl.equals("img")) {return ImgService.class;}
		 if (surl.equals("items")) {return ItemsService.class;}
		 if (surl.equals("orders")) {return OrdersService.class;}
		 if (surl.equals("sale")) {return SaleService.class;}
		 if (surl.equals("size")) {return SizeService.class;}
		 if (surl.equals("tagcloud")) {return TagcloudService.class;}
		 if (surl.equals("users")) {return UserService.class;}		 		
		 if (surl.equals("webpages")) {return WebpagesService.class;}		
		return BS;
	}
	
	public static Class<?> getServiceClass(String uri1) throws LogicException {	
		  Class<?> BS=bservices.get(uri1);
	      if (BS==null) {	    	 
	    	  BS=mapServiceClass(uri1);	 
	    	  bservices.put(uri1,  BS);	    	   
	      } 	
		  return BS;
	    }
	
	private static Class<? extends BaseAction> mapBaseAction(String surl) throws LogicException { 		
		 if (surl.equals("baseitem")) {return BaseitemSaveAction.class;}
		 if (surl.equals("category")) {return CategorySaveAction.class;}		
		 if (surl.equals("classification")) {return ClassificationSaveAction.class;}
		 if (surl.equals("client")) {return ClientSaveAction.class;}
		 if (surl.equals("color")) {return ColorSaveAction.class;}
		 if (surl.equals("country")) {return CountrySaveAction.class;}
		 if (surl.equals("currency")) {return CurrencySaveAction.class;}
		 if (surl.equals("img")) {return ImgSaveAction.class;}
		 if (surl.equals("items")) {return ItemsSaveAction.class;}
		 if (surl.equals("orders")) {return OrdersSaveAction.class;}
		 if (surl.equals("sale")) {return SaleSaveAction.class;}		 
		 if (surl.equals("tagcloud")) {return TagcloudSaveAction.class;}
		 if (surl.equals("users")) {return UserSaveAction.class;}
		 if (surl.equals("size")) {return SizeSaveAction.class;}
		 if (surl.equals("webpages")) {return WebpagesSaveAction.class;}
		return null;
	}	
	
	private static Class<? extends BaseAction> getSaveClass(String uri1) throws LogicException {	
		  Class<? extends BaseAction> BA =asave.get(uri1);	
	      if (BA==null) { BA=mapBaseAction(uri1); asave.put(uri1, BA);} 	
		  return BA;
	    }			
	
	private static BaseAction ListActionCommand(String url) throws LogicException {						
		Class<?> BC= getServiceClass(url);
		BaseService<?> BS=(BaseService<?>) context.getBean(BC);		
		BaseAction BA=context.getBean(ListAction.class);
		BA.setService(BS);
		BA.setUrl(url);	
		return BA;
	}
	private static BaseAction EditActionCommand(String url) throws LogicException {						
		Class<?> BC= getServiceClass(url);
		BaseService<?> BS=(BaseService<?>) context.getBean(BC);		
		BaseAction BA=context.getBean(EditAction.class);
		BA.setService(BS);
		BA.setUrl(url);	
		return BA;		
	}
	private static BaseAction DeleteActionCommand(String url) throws LogicException {						
		Class<?> BC= getServiceClass(url);
		BaseService<?> BS=(BaseService<?>) context.getBean(BC);		
		BaseAction BA=context.getBean(DeleteAction.class);
		BA.setService(BS);
		BA.setUrl(url);
		return BA;		
	}
	private static BaseAction SaveActionCommand(String url) throws LogicException {						
		Class<? extends BaseAction> BCSave= getSaveClass(url);
		Class<?> BCServ= getServiceClass(url);
		BaseService<?> BS=(BaseService<?>) context.getBean(BCServ);		
		BaseAction BA= context.getBean(BCSave);
		BA.setService(BS);
		BA.setUrl(url);	
		return BA;		
	}	
	
	static {		
		actions1.put("/", MainAction.class); //mainActionFactory
		actions1.put("index", null); //mainActionFactory
		actions1.put("user", MainUserAction.class);
		actions1.put("register", RegisterAction.class);
		actions1.put("registeruser", UserRegisterAction.class);
		actions1.put("login", LoginAction.class);
		actions1.put("logout", LogoutAction.class);
	}		
/////////////////////////////////////////////////////////////////////////	
	private static BaseAction getBaseActionCommand(String url0, String url) throws LogicException {
		BaseAction BA=null;
		if (url.equals("list")) {BA=ListActionCommand(url0);} 
		else if (url.equals("edit")) {BA=EditActionCommand(url0);}
		else if (url.equals("delete")) {BA=DeleteActionCommand(url0);}
		else if (url.equals("save")) {BA=SaveActionCommand(url0);}
		return BA;
	}
						
	
	private static BaseAction getSpecialCommand(String str1, String str2, String url) {
		BaseAction BA;
		if (str1.equals("users")) {
			if (str2.equals("cart")) {
				BA=context.getBean(CartAction.class); BA.setUrl("/users/cart"); return BA;} 
		    if (str2.equals("page")) {
		    	BA=context.getBean(UserPageAction.class); BA.setUrl("/users/page"); return BA;}
		    if (str2.equals("checkout")) {
		    	BA=context.getBean(CheckoutAction.class); BA.setUrl("/users/checkout"); return BA;}
		    if (str2.equals("order")) {
		    	BA=context.getBean(UserOrderAction.class); BA.setUrl("/users/order"); return BA;}
		    if (str2.equals("uncheckout")) {
		    	BA=context.getBean(UnCheckoutAction.class); BA.setUrl("/users/uncheckout"); return BA;}
		    if (str2.equals("deactiveorder")) {
		    	BA=context.getBean(DeactiveOrderAction.class); BA.setUrl("/users/deactiveorder"); return BA;}
		}
		if (str1.equals("product") && str2.equals("order")) {
			BA=context.getBean(NewOrderAction.class); BA.setUrl("/product/order"); return BA;} 
		if (str1.equals("product") && str2.equals("list")) {
			BA=context.getBean(ProductListAction.class); BA.setUrl("/product/list"); return BA;} 
		if (str1.equals("catalog") && str2.equals("list")) {
			BA=context.getBean(CatalogListAction.class); BA.setUrl(url); return BA;} 
		
		return null;
	}
/////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////	


	public static Action getAction(String url, String search) throws LogicException {
		BaseAction BA=null;  
		Class<?> actionClass=null; // = actions.get(url);		
		
		if (url.contains("upload")) {return (Action)context.getBean(UploadFileAction.class);}
		if (url.equals("/")) {return (Action)context.getBean(MainAction.class);}	
		if (url.contains("catalog") && (url.contains("category") || (search!=null && search.contains("category")))) {
			BA =context.getBean(CatalogDispatherAction.class); BA.setUrl(url); return BA;}
	    //	System.out.println("---------------ACTION  NON)" + url);
		String Arr[]=url.substring(1).split("/");		
		if (Arr.length==1) {
			actionClass = actions1.get(Arr[0]);

		} else if (Arr.length>=2) {					
		//	System.out.println("---------------=========================ACTION Arr[0])" + Arr[0]+ ""+  Arr[1]);			
		try {
			BA=getSpecialCommand(Arr[0],Arr[1],url);
			if (BA==null) {
				
				BA=getBaseActionCommand(Arr[0], Arr[1]);				
				}						
		   } catch (NullPointerException e) {return null;}	
		}	
	//	System.out.println("---------------===================ACTION actionClass)" + BA);
		if (BA!=null) {return BA;}
		else if(actionClass != null) {
			return (Action)context.getBean(actionClass);
		} else {
			return null;
		}
				
	}

	public static Action getSearchAction(String str) throws LogicException {	
		SearchAction searchAction =context.getBean(SearchAction.class); searchAction.setUrl(str); 			
	    return searchAction;}	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	


	
}
