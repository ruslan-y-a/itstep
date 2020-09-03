package factories;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import org.itstep.factories.Factory.ActionCommand;

import pool.ConnectionPool;
import pool.ConnectionPoolException;

import de_services.BaseService;
import de_services.BaseitemService;
import de_services.BaseitemServiceImpl;
import de_services.CategoryService;
import de_services.CategoryServiceImpl;
import de_services.ClassificationService;
import de_services.ClassificationServiceImpl;
import de_services.ClientService;
import de_services.ClientServiceImpl;
import de_services.ColorServiceImpl;
import de_services.CountrySevice;
import de_services.CountrySeviceImpl;
import de_services.CurrencyService;
import de_services.CurrencyServiceImpl;
import de_services.ImgService;
import de_services.ImgServiceImpl;
import de_services.ItemsService;
import de_services.ItemsServiceImpl;
import de_services.OrdersService;
import de_services.OrdersServiceImpl;
import de_services.SaleService;
import de_services.SaleServiceImpl;
import de_services.SizeServiceImpl;
import de_services.TagcloudService;
import de_services.TagcloudServiceImpl;
import de_services.UserService;
import de_services.UserServiceImpl;
import de_services.ColorService;
import de_services.SizeService;
import de_services.WebpagesService;
import de_services.WebpagesServiceImpl;
import help.Reflection;
/*import main.java.org.itstep.util.ioc.BaseDbDaoImpl;
import main.java.org.itstep.util.ioc.Factory;
import main.java.org.itstep.util.ioc.Factory.DependencyInjector;*/
import daos.BaseitemDao;
import daos.BaseitemDaoImpl;
import daos.CategoryDao;
import daos.ItemsDao;
import daos.CategoryDaoImpl;
import daos.ClassificationDao;
import daos.ClassificationDaoImpl;
import daos.ClientDao;
import daos.ClientDaoImpl;
import daos.CountryDao;
import daos.CountryDaoImpl;
import daos.CurrencyDao;
import daos.CurrencyDaoImpl;
import daos.Dao;
//import daos.Dao;
import daos.DaoImpl;
import daos.ImgDao;
import daos.ImgDaoImpl;
import daos.ItemsDaoImpl;
import daos.OrdersDao;
import daos.OrdersDaoImpl;
import daos.SaleDao;
import daos.SaleDaoImpl;
import daos.TagcloudDao;
import daos.TagcloudDaoImpl;
import daos.UserDao;
import daos.UserDaoImpl;
import daos.ColorDao;
import daos.SizeDao;
import daos.ColorDaoImpl;
import daos.SizeDaoImpl;
import daos.WebpagesDao;
import daos.WebpagesDaoImpl;
import web.action.Action;
import web.action.BaseAction;
import web.action.DeleteAction;
import web.action.EditAction;
import web.action.ListAction;
import web.action.LoginAction;
import web.action.LogoutAction;
import web.action.MainAction;
import web.action.MainUserAction;
import web.action.catalog.CatalogDispatherAction;
import web.action.catalog.CatalogListAction;
import web.action.catalog.ProductListAction;
import web.action.catalog.SearchAction;
import web.action.save.BaseitemSaveAction;
import web.action.save.CategorySaveAction;
import web.action.save.ClassificationSaveAction;
import web.action.save.ClientSaveAction;
import web.action.save.ColorSaveAction;
import web.action.save.CountrySaveAction;
import web.action.save.CurrencySaveAction;
import web.action.save.ImgSaveAction;
import web.action.save.ItemsSaveAction;
import web.action.save.OrdersSaveAction;
import web.action.save.SaleSaveAction;
import web.action.save.SizeSaveAction;
import web.action.save.TagcloudSaveAction;
import web.action.save.UserSaveAction;
import web.action.save.WebpagesSaveAction;
import web.action.user.CartAction;
import web.action.user.CheckoutAction;
import web.action.user.DeactiveOrderAction;
import web.action.user.NewOrderAction;
import web.action.user.RegisterAction;
import web.action.user.UnCheckoutAction;
import web.action.user.UserOrderAction;
import web.action.user.UserPageAction;
import web.action.user.UserRegisterAction;
import service.LogicException;

public class Factory  implements AutoCloseable {

	private static interface ActionFactory {
		Action getInstance() throws LogicException;
	}
	
	private Map<String, BaseAction>  asave = new HashMap<>();
	private Map<String, BaseService<?>> bservices = new HashMap<>();
	private Map<String, ActionCommand> actions2 = new HashMap<>();
	private Map<String, ActionFactory> actions1 = new HashMap<>();
	{
		ActionFactory mainUserActionFactory = () -> getMainUserAction();
		ActionFactory mainActionFactory = () -> getMainAction();
		actions1.put("/", mainActionFactory); //mainActionFactory
		actions1.put("index", null); //mainActionFactory
		actions1.put("user", mainUserActionFactory);
		actions1.put("register", () -> getRegisterAction());
		actions1.put("registeruser", () -> getUserRegisterAction());
		actions1.put("login", () -> getLoginAction());
		actions1.put("logout", () -> getLogoutAction());
		actions2.put("list", new ListActionCommand());
		actions2.put("edit", new EditActionCommand());
		actions2.put("delete", new DeleteActionCommand());
		actions2.put("save", new SaveActionCommand());	
			
	}
	private ActionCommand getSpecialCommand(String str1, String str2, String url) {
		if (str1.equals("users")) {
			if (str2.equals("cart")) {
            ActionCommand AC = new CartActionCommand(); AC.setUri1("/users/cart"); 
			return AC;} 
		    if (str2.equals("page")) {
			ActionCommand AC = new UserPageActionCommand(); AC.setUri1("/users/page"); 
			return AC;}
		    if (str2.equals("checkout")) {
			ActionCommand AC = new CheckoutActionCommand(); AC.setUri1("/users/checkout"); 
			return AC;}
		    if (str2.equals("order")) {
			ActionCommand AC = new UserOrderActionCommand(); AC.setUri1("/users/order"); 
			return AC;}
		    if (str2.equals("uncheckout")) {
			ActionCommand AC = new UnCheckoutActionCommand(); AC.setUri1("/users/uncheckout"); 
			return AC;}
		    if (str2.equals("deactiveorder")) {
			ActionCommand AC = new DeactiveOrderActionCommand(); AC.setUri1("/users/deactiveorder"); 
			return AC;}
		}
		if (str1.equals("product") && str2.equals("order")) {
			ActionCommand AC = new UserNewOrderActionCommand(); AC.setUri1("/product/order"); 
			return AC;} 
		if (str1.equals("product") && str2.equals("list")) {
			ActionCommand AC = new ProductListActionCommand(); AC.setUri1("/product/list"); 
			return AC;} 
		if (str1.equals("catalog") && str2.equals("list")) {
			ActionCommand AC = new CatalogListActionCommand(); AC.setUri1(url); 
			return AC;} 
		
		return null;
	}

	public Action getAction(String url, String search) throws LogicException {
		ActionFactory factory=null;    
		if (url.equals("/")) {factory = () -> getMainAction();return factory.getInstance();}	
		if (url.contains("catalog") && (url.contains("category") || (search!=null && search.contains("category")))) {factory = () -> getCatalogDispatherAction(url);return factory.getInstance();}
	//	System.out.println("---------------ACTION  NON)" + url);
		String Arr[]=url.substring(1).split("/");		
		if (Arr.length==1) {
		//	System.out.println("---------------ACTION Arr[0])" + Arr[0]);
		factory = actions1.get(Arr[0]);
		} else if (Arr.length>=2) {					
			ActionCommand AC;
		try {
			AC=getSpecialCommand(Arr[0],Arr[1],url);
			if (AC==null) {
			AC= actions2.get(Arr[1]);
			AC.setUri1(Arr[0]); }			
			factory = AC;
		   } catch (NullPointerException e) {return null;}	
		}	
		if(factory != null) {
			return factory.getInstance();
		}
		return null;
	}

	public Action getMainAction() throws LogicException {return get(MainAction.class);}	
	public Action getCatalogDispatherAction(String url) throws LogicException {
		CatalogDispatherAction catalogDispatherActionImpl =get(CatalogDispatherAction.class);
		catalogDispatherActionImpl.setService(getCategoryService()); catalogDispatherActionImpl.setUrl(url);
		return catalogDispatherActionImpl;					
	}	
	public Action getSearchAction(String str) throws LogicException {
			SearchAction searchAction =  get(SearchAction.class); searchAction.setService(getItemsService()); 
			searchAction.setSearchStr(str);			
		return get(SearchAction.class); //  searchAction;
	}	
	public Action getRegisterAction() throws LogicException {return get(RegisterAction.class);}	
	
	public Action getUserRegisterAction() throws LogicException {
		UserRegisterAction userRegisterActionImpl = get(UserRegisterAction.class);
		userRegisterActionImpl.setService(getUserService());return userRegisterActionImpl;}
	
	public Action getMainUserAction() throws LogicException {return get(MainUserAction.class);}
			
	public Action getLoginAction() throws LogicException {return get(LoginAction.class);}		
	public Action getLogoutAction() throws LogicException {return get(LogoutAction.class);}	
//////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////	
	public BaseService<?> getService(String uri1) throws LogicException {	
		  BaseService<?> BS=bservices.get(uri1);
	      if (BS==null) {	    	 
	    	  BS=CreateBaseServise(uri1);	 
	    	  bservices.put(uri1,  BS);	    	   
	      } 	
		  return BS;
	    }
	private BaseService<?> CreateBaseServise(String surl) throws LogicException {
		 BaseService<?> BS=null;
		 if (surl.equals("baseitem")) {return getBaseitemService();}
		 if (surl.equals("category")) {return getCategoryService();}
		 if (surl.equals("classification")) {return getClassificationService();}
		 if (surl.equals("client")) {return getClientService();}
		 if (surl.equals("color")) {return getColorService();}
		 if (surl.equals("country")) {return getCountrySevice();}
		 if (surl.equals("currency")) {return getCurrencyService();}
		 if (surl.equals("img")) {return getImgService();}
		 if (surl.equals("items")) {return getItemsService();}
		 if (surl.equals("orders")) {return getOrdersService();}
		 if (surl.equals("sale")) {return getSaleService();}
		 if (surl.equals("size")) {return getSizeService();}
		 if (surl.equals("tagcloud")) {return getTagcloudService();}
		 if (surl.equals("users")) {return getUserService();}		 		
		 if (surl.equals("webpages")) {return getWebpagesService();}		
		return BS;
	}
	private BaseAction getSaveAction(String uri1) throws LogicException {	
		  BaseAction BA =asave.get(uri1);	
	      if (BA==null) { BA=CreateBaseAction(uri1); asave.put(uri1, BA);} 	
		  return BA;
	    }	
	private BaseAction CreateBaseAction(String surl) throws LogicException { 
		BaseAction BA = null;
		 if (surl.equals("baseitem")) {return new BaseitemSaveAction();}
		 if (surl.equals("category")) {return new CategorySaveAction();}		
		 if (surl.equals("classification")) {return new ClassificationSaveAction();}
		 if (surl.equals("client")) {return new ClientSaveAction();}
		 if (surl.equals("color")) {return new ColorSaveAction();}
		 if (surl.equals("country")) {return new CountrySaveAction();}
		 if (surl.equals("currency")) {return new CurrencySaveAction();}
		 if (surl.equals("img")) {return new ImgSaveAction();}
		 if (surl.equals("items")) {return new ItemsSaveAction();}
		 if (surl.equals("orders")) {
			 OrdersSaveAction osa = new OrdersSaveAction();
			 osa.setClientService(getClientService()); BA =osa;
     		 return BA;
			 }
		 if (surl.equals("sale")) {//return new SaleSaveAction(getOrdersService());
		      SaleSaveAction osa = new SaleSaveAction(getOrdersService());
		      osa.setClientService(getClientService());
		      osa.setCurrencyService(getCurrencyService()); BA =osa;
		      return BA;
		 }
		 if (surl.equals("tagcloud")) {return new TagcloudSaveAction();}
		 if (surl.equals("users")) {return new UserSaveAction();}
		 if (surl.equals("size")) {return new SizeSaveAction();}
		 if (surl.equals("webpages")) {return new WebpagesSaveAction();}
		return BA;
	}	
////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////////////////////////////////////////////////////////////////		
	private class UserPageActionCommand extends ActionCommand {				
		@Override public Action getInstance() throws LogicException {
			UserPageAction userPageAction = get(UserPageAction.class);		
			userPageAction.setService(getUserService()); return userPageAction;}}	
	
	private class ListActionCommand extends ActionCommand {				
		@Override
		public Action getInstance() throws LogicException {
			ListAction listActionImpl = new ListAction(); Action listAction = listActionImpl;
			listActionImpl.setService(getService(this.uri1)); return listAction;
		}		
	}
	private class EditActionCommand extends ActionCommand {		
		@Override
		public Action getInstance() throws LogicException {
			EditAction listActionImpl = new EditAction();
			Action editAction = listActionImpl;	listActionImpl.setService(getService(this.uri1));
			return editAction;
		}	
	}		
	private class DeleteActionCommand extends ActionCommand {		
		@Override
		public Action getInstance() throws LogicException {
			DeleteAction deleteActionImpl = new DeleteAction();
			Action deleteAction = deleteActionImpl;
			deleteActionImpl.setService(getService(this.uri1));	deleteActionImpl.setPath(this.uri1);
			return deleteAction;
		}		
	}			
	private class SaveActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			BaseAction saveAction = getSaveAction(this.uri1); saveAction.setService(getService(this.uri1));
			return saveAction;
		}		
	}		
	private class CartActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			CartAction saveAction = get(CartAction.class);	saveAction.setService(getOrdersService());			
			return saveAction;}}	
	private class CheckoutActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			CheckoutAction saveAction = get(CheckoutAction.class); saveAction.setService(getOrdersService());	
			return saveAction;}}	
	private class UnCheckoutActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			UnCheckoutAction saveAction = get(UnCheckoutAction.class); saveAction.setService(getOrdersService());	
			return saveAction;}}	
	private class DeactiveOrderActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			DeactiveOrderAction saveAction = get(DeactiveOrderAction.class);saveAction.setService(getOrdersService());	
			return saveAction;}}		
	private class UserOrderActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			UserOrderAction saveAction = get(UserOrderAction.class); saveAction.setService(getOrdersService());
			return saveAction;}}		
	private class UserNewOrderActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			NewOrderAction saveAction = get(NewOrderAction.class); saveAction.setService(getOrdersService());	
			return saveAction;}}	
	private class ProductListActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
			ProductListAction saveAction = get(ProductListAction.class); saveAction.setService(getItemsService());	
			return saveAction;}}  	
	private class CatalogListActionCommand extends ActionCommand {		
		@Override public Action getInstance() throws LogicException {
		CatalogListAction saveAction = get(CatalogListAction.class);  saveAction.setService(getItemsService());
		saveAction.setUrl(uri1);return saveAction;}}
///////////////////////////////////////////////////////////////////////
	public ColorService getColorService() throws LogicException {return get(ColorService.class);}		
	public SizeService getSizeService() throws LogicException {return get(SizeService.class);}		
	public TagcloudService getTagcloudService() throws LogicException {return get(TagcloudService.class);}	
	public WebpagesService getWebpagesService() throws LogicException {return get(WebpagesService.class);}	
	public SaleService getSaleService() throws LogicException {return get(SaleService.class);}	
	public ImgService getImgService() throws LogicException {return get(ImgService.class);}	
	public ItemsService getItemsService() throws LogicException { return get(ItemsService.class);}	
	public OrdersService getOrdersService() throws LogicException {return get(OrdersService.class);}	
	public CurrencyService getCurrencyService() throws LogicException {return get(CurrencyService.class);}		
	public CountrySevice getCountrySevice() throws LogicException {return get(CountrySevice.class);}	
	public ClientService getClientService() throws LogicException {return get(ClientService.class);}	
	public ClassificationService getClassificationService() throws LogicException {return  get(ClassificationService.class);}	
	public CategoryService getCategoryService() throws LogicException {return get(CategoryService.class);}
	public BaseitemService getBaseitemService() throws LogicException {return get(BaseitemService.class);}
	public UserService getUserService() throws LogicException {return get(UserService.class);}
	public WebpagesDao getWebpagesDao() throws LogicException {return get(WebpagesDao.class);}	
	public ColorDao getColorDao() throws LogicException {return get(ColorDao.class);}	
	public SizeDao getSizeDao() throws LogicException {return get(SizeDao.class);}
	public CategoryDao getCategoryDao() throws LogicException {return get(CategoryDao.class);}
	public UserDao getUserDao() throws LogicException {return get(UserDao.class);}	
	public TagcloudDao getTagcloudDao() throws LogicException {return get(TagcloudDao.class);}	
	public SaleDao getSaleDao() throws LogicException {return get(SaleDao.class);}
	public OrdersDao getOrdersDao() throws LogicException {return get(OrdersDao.class);}
	public ItemsDao getItemsDao() throws LogicException {return get(ItemsDao.class);}
	public ImgDao getImgDao() throws LogicException {return get(ImgDao.class);}
	public CurrencyDao getCurrencyDao() throws LogicException {return get(CurrencyDao.class);}	
	public CountryDao getCountryDao() throws LogicException {return get(CountryDao.class);}
	public ClientDao getClientDao() throws LogicException {return get(ClientDao.class);}	
	public ClassificationDao getClassificationDao() throws LogicException {return get(ClassificationDao.class);}	
	public BaseitemDao getBaseitemDao() throws LogicException {return get(BaseitemDao.class);}
/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	private Connection connection = null;
	public Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
				connection = ConnectionPool.getInstance().getConnection();
			} catch(ConnectionPoolException e) {
				throw new LogicException(e);
			}
		}
		return connection;
	}

	@Override
	public void close() {
		try { connection.close();System.out.println("The Connection is closed");} catch(Exception e) {}
	}

	private abstract class ActionCommand implements ActionFactory {
		String uri1;
		public void setUri1(String uri1) {this.uri1 = uri1;}				
	}
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////	
	private Map<Class<?>, Object> cache = new HashMap<>();
//	private static Map<Class<?>, CustomFactory<?>> factories = new HashMap<>();	
	
	 @SuppressWarnings("unchecked")
	  public <T> T get(Class<T> i) throws LogicException {
		//	System.out.println("==================="+i);
			Object o = cache.get(i);
			if(o == null) {
				Class<?> c=null;
				if(i.isInterface()) {
					try {c = Class.forName(i.getName() + "Impl");} catch (ClassNotFoundException e) {}	
				} else {
					c = i;
				}
				if(c != null) {
					try {																				
						  o = c.getConstructor().newInstance();
						  cache.put(i, o);
						//  System.out.println("==========Dao========="+o);
						    if (Dao.class.isInstance(o)) {
					//	    	 System.out.println("==========Dao2========="+o);						    					    	
						    	 Method setConnection = DaoImpl.class.getDeclaredMethod("setConnection", Connection.class);
						    	 setConnection.setAccessible(true);	
						    	 setConnection.invoke(o,getConnection());
						    } else if (Action.class.isInstance(o) || BaseService.class.isInstance(o)) {					    
						    	 for (Field field : o.getClass().getDeclaredFields()) {
						    		 Object obj=null;
						    	   if(field.getType().isInterface()) {
						    		 field.setAccessible(true);						 
						    		 try {					    								    			 
						    			 obj = get(field.getType());
						    		 } catch (Exception e) 
						    		 {e.printStackTrace(); System.out.println("==========ERROR CREATION=========" + field);}
						    		 Method setter = Reflection.getSetter(o,field.getName(),field.getType());
						    		 if (setter!=null) {setter.invoke(o,obj);}
						    	   }	 
						    	 } 
						   }
						} catch(InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
						   throw new ImplementationInstantiationIocContainerException(e);
						}
				 } else {System.out.println("==========Dao FAIL=========)" + i);}
			}
			return (T)o;
		}  
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////	

	private static interface DependencyInjector {
		void inject(Object obj, Factory factory) throws LogicException;
	}

	public static interface CustomFactory<T> {
		T newInstance() throws LogicException;
	}
////////////////////////////////////////////////////////
////////////////////////////////////////////////////////	
}
