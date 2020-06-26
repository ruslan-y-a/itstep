package factories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

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
import daos.UserDbDaoImpl;
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
//		actions1.put("backoffice", mainUserActionFactory);
		actions1.put("login", () -> getLoginAction());
		actions1.put("logout", () -> getLogoutAction());
		actions2.put("list", new ListActionCommand());
		actions2.put("edit", new EditActionCommand());
		actions2.put("delete", new DeleteActionCommand());
		actions2.put("save", new SaveActionCommand());	
			
	}

	public Action getAction(String url) throws LogicException {
		ActionFactory factory=null;
    	//System.out.println("---------------ACTION)" + url);
		if (url.equals("/")) {factory = () -> getMainAction();return factory.getInstance();}
		String Arr[]=url.substring(1).split("/");		
		if (Arr.length==1) {
		factory = actions1.get(Arr[0]);
		} else if (Arr.length==2) {		
			ActionCommand AC = actions2.get(Arr[1]);
			AC.setUri1(Arr[0]);
			factory = AC;		
		}	
		if(factory != null) {
			return factory.getInstance();
		}
		return null;
	}

	private Action mainAction = null;
	public Action getMainAction() {
		if(mainAction == null) {
			mainAction = new MainAction();
		}
		return mainAction;
	}

	private Action mainUserAction = null;
	public Action getMainUserAction() {
		if(mainUserAction == null) {
			mainUserAction = new MainUserAction();
		}
		return mainUserAction;
	}
	
	private Action loginAction = null;
	public Action getLoginAction() throws LogicException {
		if(loginAction == null) {
			LoginAction loginActionImpl = new LoginAction();
			loginAction = loginActionImpl;
			loginActionImpl.setUserService(getUserService());
		}
		return loginAction;
	}

	private Action logoutAction = null;
	public Action getLogoutAction() {
		if(logoutAction == null) {
			logoutAction = new LogoutAction();
		}
		return logoutAction;
	}
//////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////	
	public BaseService<?> getService(String uri1) throws LogicException {	
		  BaseService<?> BS=bservices.get(uri1);
	      if (BS==null) {	    	 
	    	  BS=CreateBaseServise(uri1);	 
	//    	  System.out.println("---------------BS)" + BS);
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
	//	System.out.println("-------------GET SAVE ACTION:"+uri1);
	      if (BA==null) {
	    	  BA=CreateBaseAction(uri1);
	    	  asave.put(uri1, BA);	    	   
	      } 	
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
		 if (surl.equals("orders")) {return new OrdersSaveAction();}
		 if (surl.equals("sale")) {return new SaleSaveAction(getOrdersService());}
		 if (surl.equals("tagcloud")) {return new TagcloudSaveAction();}
		 if (surl.equals("users")) {return new UserSaveAction();}
		 if (surl.equals("size")) {return new SizeSaveAction();}
		 if (surl.equals("webpages")) {return new WebpagesSaveAction();}
		return BA;
	}	
////////////////////////////////////////////////////////////////////////////////////////	
/////////////////////////////////////////////////////////////////////////////////////////////////	
	private class ListActionCommand extends ActionCommand {				
		@Override
		public Action getInstance() throws LogicException {
			ListAction listActionImpl = new ListAction();
			Action listAction = listActionImpl;
		//	System.out.println("---------------LIST)" + this.uri1);
			listActionImpl.setService(getService(this.uri1));
			return listAction;
		}		
	}

	private class EditActionCommand extends ActionCommand {		
		@Override
		public Action getInstance() throws LogicException {
			EditAction listActionImpl = new EditAction();
			Action editAction = listActionImpl;
			listActionImpl.setService(getService(this.uri1));
			return editAction;
		}	
	}
		

	private class DeleteActionCommand extends ActionCommand {		
		@Override
		public Action getInstance() throws LogicException {
			DeleteAction deleteActionImpl = new DeleteAction();
			Action deleteAction = deleteActionImpl;
			deleteActionImpl.setService(getService(this.uri1));
			deleteActionImpl.setPath(this.uri1);
			return deleteAction;
		}
		
	}		
	
	private class SaveActionCommand extends ActionCommand {		
		@Override
		public Action getInstance() throws LogicException {
	//		System.out.println("-------------SAVE ACTION:");
			BaseAction saveAction = getSaveAction(this.uri1);	
			saveAction.setService(getService(this.uri1));
			return saveAction;
		}
		
	}	
	
///////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////
	private  ColorService colorService = null;  //ColorServiceImpl
	public  ColorService getColorService() throws LogicException {
		if(colorService == null) {
			ColorServiceImpl service = new ColorServiceImpl();
			colorService = service;
			service.setColorDao(getColorDao());				
		}
		return colorService;}
	
	private  SizeService sizeService = null;  //SizeServiceImpl
	public  SizeService getSizeService() throws LogicException {
		if(sizeService == null) {
			SizeServiceImpl service = new SizeServiceImpl();
			sizeService = service;
			service.setVariantDao(getSizeDao());				
		}
		return sizeService;}
	

	
	private  TagcloudService tagcloudService = null;
	public  TagcloudService getTagcloudService() throws LogicException {
		if(tagcloudService == null) {
			TagcloudServiceImpl service = new TagcloudServiceImpl();
			tagcloudService = service;
			service.setTagcloudDao(getTagcloudDao());		
			service.setClassificationDao(getClassificationDao());
			service.setWebpagesDao(getWebpagesDao());
		}
		return tagcloudService;}
	
	private  WebpagesService webpagesService = null;
	public  WebpagesService getWebpagesService() throws LogicException {
		if(webpagesService == null) {
			WebpagesServiceImpl service = new WebpagesServiceImpl();
			webpagesService = service;
			service.setWebpagesDao(getWebpagesDao());
		}
		return webpagesService;}
	
	private  SaleService saleService = null;
	public  SaleService getSaleService() throws LogicException {
		if(saleService == null) {
			SaleServiceImpl service = new SaleServiceImpl();
			saleService = service;
			service.setSaleDao(getSaleDao());	
			service.setOrdersDao(getOrdersDao());	
			service.setCurrencyDao(getCurrencyDao());	
			service.setClientDao(getClientDao());	
		}
		return saleService;}
	
	private ImgService imgService = null;
	public ImgService getImgService() throws LogicException {
		if(imgService == null) {
			ImgServiceImpl service = new ImgServiceImpl();
			imgService = service;
			service.setImgDao(getImgDao());				
		}
		return imgService;}
	
	private ItemsService itemsService = null;
	public ItemsService getItemsService() throws LogicException {		
		if(itemsService == null) {
			ItemsServiceImpl service = new ItemsServiceImpl();			
			itemsService = service;						
			service.setItemsDao(getItemsDao());					
			service.setCategoryDao(getCategoryDao());					
			service.setWebpagesDao(getWebpagesDao());				
			service.setClassificationDao(getClassificationDao());				
			service.setImgDao(getImgDao());		
		}		
		return itemsService;}
	
	private OrdersService ordersService = null;
	public OrdersService getOrdersService() throws LogicException {
		if(ordersService == null) {
			OrdersServiceImpl service = new OrdersServiceImpl();
			ordersService = service;
			service.setOrdersDao(getOrdersDao());			
			service.setClientDao(getClientDao());	
			service.setBaseitemDao(getBaseitemDao());	
			service.setCurrencyDao(getCurrencyDao());			
			service.setUserDao(getUserDao());	
		}
		return ordersService;}
	
	private CurrencyService сurrencyService = null;
	public CurrencyService getCurrencyService() throws LogicException {
		if(сurrencyService == null) {
			CurrencyServiceImpl service = new CurrencyServiceImpl();
			сurrencyService = service;
			service.setCurrencyDao(getCurrencyDao());				
		}
		return сurrencyService;}
	
	private CountrySevice countrySevice = null;
	public CountrySevice getCountrySevice() throws LogicException {
		if(countrySevice == null) {
			CountrySeviceImpl service = new CountrySeviceImpl();
			countrySevice = service;
			service.setCurrencyDao(getCurrencyDao());
			service.setCountryDao(getCountryDao());					
		}
		return countrySevice;}
	
	private ClientService clientService = null;
	public ClientService getClientService() throws LogicException {
		if(clientService == null) {
			ClientServiceImpl service = new ClientServiceImpl();
			clientService = service;
			service.setClientDao(getClientDao());
			service.setCountryDao(getCountryDao());		
			service.setUserDao(getUserDao());		
			service.setItemsDao(getItemsDao());		
		}
		return clientService;}
	
	private ClassificationService classificationService = null;
	public ClassificationService getClassificationService() throws LogicException {
		if(classificationService == null) {
			ClassificationServiceImpl service = new ClassificationServiceImpl();
			classificationService = service;
			service.setClassificationDao(getClassificationDao());			
		}
		return classificationService;}
	
	private CategoryService categoryService = null;
	public CategoryService getCategoryService() throws LogicException {
		if(categoryService == null) {
			CategoryServiceImpl service = new CategoryServiceImpl();
			categoryService = service;
			service.setCategoryDao(getCategoryDao());
			service.setWebpagesDao(getWebpagesDao());
			service.setClassificationDao(getClassificationDao());
		}
		return categoryService;}

	private BaseitemService baseitemService = null;
	public BaseitemService getBaseitemService() throws LogicException {
		if(baseitemService == null) {
			BaseitemServiceImpl service = new BaseitemServiceImpl();
			baseitemService = service;
			service.setBaseitemDao(getBaseitemDao());
			service.setItemsDao(getItemsDao());
			service.setColorDao(getColorDao());
			service.setSizeDao(getSizeDao());
			service.setCurrencyDao(getCurrencyDao());
		}
		return baseitemService;}

	private UserService userService = null;
	public UserService getUserService() throws LogicException {
		if(userService == null) {
			UserServiceImpl service = new UserServiceImpl();
			userService = service;
			//System.out.println("GET USER SERVICE:"+userService);
			service.setUserDao(getUserDao());

		}
		return userService;}
//////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////
	private WebpagesDao webpagesDao = null;
	public WebpagesDao getWebpagesDao() throws LogicException {
		if(webpagesDao == null) {
			WebpagesDaoImpl webpagesDaoImpl = new WebpagesDaoImpl();
			webpagesDao = webpagesDaoImpl;
			webpagesDaoImpl.setConnection(getConnection());
		}
		return webpagesDao;
	}

	
	private ColorDao colorDao = null;
	public ColorDao getColorDao() throws LogicException {
		if(colorDao == null) {
			ColorDaoImpl variantDaoImpl = new ColorDaoImpl();
			colorDao = variantDaoImpl;
			variantDaoImpl.setConnection(getConnection());
		}
		return colorDao;
	}
	
	private SizeDao sizeDao = null;
	public SizeDao getSizeDao() throws LogicException {
		if(sizeDao == null) {
			SizeDaoImpl variantDaoImpl = new SizeDaoImpl();
			sizeDao = variantDaoImpl;
			variantDaoImpl.setConnection(getConnection());
		}
		return sizeDao;
	}

	private CategoryDao categoryDao = null;
	public CategoryDao getCategoryDao() throws LogicException {
		if(categoryDao == null) {
			CategoryDaoImpl categoryDaoImpl = new CategoryDaoImpl();
			categoryDao = categoryDaoImpl;
			categoryDaoImpl.setConnection(getConnection());
		}
		return categoryDao;
	}

	private UserDao userDao = null;
	public UserDao getUserDao() throws LogicException {
		if(userDao == null) {
			UserDbDaoImpl userDaoImpl = new UserDbDaoImpl();
			userDao = userDaoImpl;
			userDaoImpl.setConnection(getConnection());
		}
		return userDao;
	}
	
	private TagcloudDao tagcloudDao = null;
	public TagcloudDao getTagcloudDao() throws LogicException {
		if(tagcloudDao == null) {
			TagcloudDaoImpl tagcloudDaoImpl = new TagcloudDaoImpl();
			tagcloudDao = tagcloudDaoImpl;
			tagcloudDaoImpl.setConnection(getConnection());
		}
		return tagcloudDao;
	}
	
	private SaleDao saleDao = null;
	public SaleDao getSaleDao() throws LogicException {
		if(saleDao == null) {
			SaleDaoImpl saleDaoImpl = new SaleDaoImpl();
			saleDao = saleDaoImpl;
			saleDaoImpl.setConnection(getConnection());
		}
		return saleDao;
	}
	
	private OrdersDao ordersDao = null;
	public OrdersDao getOrdersDao() throws LogicException {
		if(ordersDao == null) {
			OrdersDaoImpl ordersDaoImpl = new OrdersDaoImpl();
			ordersDao = ordersDaoImpl;
			ordersDaoImpl.setConnection(getConnection());
		}
		return ordersDao;
	}
	
	private ItemsDao itemsDao = null;
	public ItemsDao getItemsDao() throws LogicException {
		if(itemsDao == null) {
			ItemsDaoImpl itemsDaoImpl = new ItemsDaoImpl();
			itemsDao = itemsDaoImpl;			
			itemsDaoImpl.setConnection(getConnection());			
		}		
		return itemsDao;
	}
	
	private ImgDao imgDao = null;
	public ImgDao getImgDao() throws LogicException {
		if(imgDao == null) {
			ImgDaoImpl imgDaoImpl = new ImgDaoImpl();
			imgDao = imgDaoImpl;
			imgDaoImpl.setConnection(getConnection());
		}
		return imgDao;
	}
	
	private CurrencyDao currencyDao = null;
	public CurrencyDao getCurrencyDao() throws LogicException {
		if(currencyDao == null) {
			CurrencyDaoImpl currencyDaoImpl = new CurrencyDaoImpl();
			currencyDao = currencyDaoImpl;
			currencyDaoImpl.setConnection(getConnection());
		}
		return currencyDao;
	}
	
	private CountryDao countryDao = null;
	public CountryDao getCountryDao() throws LogicException {
		if(countryDao == null) {
			CountryDaoImpl countryDaoImpl = new CountryDaoImpl();
			countryDao = countryDaoImpl;
			countryDaoImpl.setConnection(getConnection());
		}
		return countryDao;
	}
	
	private ClientDao clientDao = null;
	public ClientDao getClientDao() throws LogicException {
		if(clientDao == null) {
			ClientDaoImpl clientDaoImpl = new ClientDaoImpl();
			clientDao = clientDaoImpl;
			clientDaoImpl.setConnection(getConnection());
		}
		return clientDao;
	}
	
	private ClassificationDao classificationDao = null;
	public ClassificationDao getClassificationDao() throws LogicException {
		if(classificationDao == null) {
			ClassificationDaoImpl classificationDaoImpl = new ClassificationDaoImpl();
			classificationDao = classificationDaoImpl;
			classificationDaoImpl.setConnection(getConnection());
		}
		return classificationDao;
	}
	
	private BaseitemDao baseitemDao = null;
	public BaseitemDao getBaseitemDao() throws LogicException {
		if(baseitemDao == null) {
			BaseitemDaoImpl baseitemDaoImpl = new BaseitemDaoImpl();
			baseitemDao = baseitemDaoImpl;
			baseitemDaoImpl.setConnection(getConnection());
		}
		return baseitemDao;
	}
/////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////
	private Connection connection = null;
	public Connection getConnection() throws LogicException {
		if(connection == null) {
			try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/ishop", "root", "root");
		    } catch(SQLException e) {
			  throw new LogicException(e);
			}
		 }
			return connection;
		}
	public Connection getConnection(String db,String user, String pass) throws LogicException {
		if(connection == null) {
			try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost/" + db +", " + user + ", " + pass);
		    } catch(SQLException e) {
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
/*
		public String getUri1() {
			return uri1;}
*/
		public void setUri1(String uri1) {
			this.uri1 = uri1;}				
	}
	
}
