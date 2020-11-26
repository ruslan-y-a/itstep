package org.itstep.controller_main;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.itstep.aspect.MyAnnotation;
//import org.itstep.de_services.BaseService;
import org.itstep.de_services.BaseitemService;
import org.itstep.de_services.BaseitemServiceImpl;
import org.itstep.de_services.CategoryService;
import org.itstep.de_services.CategoryServiceImpl;
import org.itstep.de_services.ClassificationService;
import org.itstep.de_services.ClassificationServiceImpl;
import org.itstep.de_services.ClientService;
import org.itstep.de_services.ClientServiceImpl;
//import org.itstep.de_services.ColorService;
//import org.itstep.de_services.ColorServiceImpl;
import org.itstep.service.ColorService;
import org.itstep.de_services.CountryService;
import org.itstep.de_services.CountryServiceImpl;
import org.itstep.de_services.CurrencyService;
import org.itstep.de_services.CurrencyServiceImpl;
import org.itstep.de_services.ImgService;
import org.itstep.de_services.ImgServiceImpl;
import org.itstep.de_services.ItemsService;
import org.itstep.de_services.ItemsServiceImpl;
import org.itstep.de_services.OrdersService;
import org.itstep.de_services.OrdersServiceImpl;
import org.itstep.de_services.SaleService;
import org.itstep.de_services.SaleServiceImpl;
import org.itstep.service.SizeService;
//import org.itstep.de_services.SizeService;
//import org.itstep.de_services.SizeServiceImpl;
import org.itstep.de_services.TagcloudService;
import org.itstep.de_services.TagcloudServiceImpl;
import org.itstep.de_services.UserService;
import org.itstep.de_services.UserServiceImpl;
import org.itstep.de_services.WebpagesService;
import org.itstep.de_services.WebpagesServiceImpl;
import org.itstep.entities.Baseitem;
import org.itstep.entities.Category;
import org.itstep.entities.Classification;
import org.itstep.entities.Client;
import org.itstep.entities.Color;
import org.itstep.entities.Country;
import org.itstep.entities.Currency;
import org.itstep.entities.Img;
import org.itstep.entities.Items;
import org.itstep.entities.Orders;
import org.itstep.entities.Sale;
import org.itstep.entities.Size;
import org.itstep.entities.Tagcloud;
import org.itstep.entities.User;
import org.itstep.entities.Webpages;
import org.itstep.service.LogicException;

public class Mapping {
	private static Map<String, Class<?>> bservices = new HashMap<>();
	private static Map<String, Class<?>> bimpls = new HashMap<>();
	private static Map<String, Class<?>> bentities = new HashMap<>();
	
	static
	{
		bentities.put("baseitem", Baseitem.class);
		bentities.put("category", Category.class);
		bentities.put("classification", Classification.class);
		bentities.put("client", Client.class);
		bentities.put("color", Color.class);
		bentities.put("country", Country.class);
		bentities.put("currency", Currency.class);
		bentities.put("img", Img.class);
		bentities.put("items", Items.class);    	
		bentities.put("orders", Orders.class);
		bentities.put("sale", Sale.class);
		bentities.put("size", Size.class);
		bentities.put("tagcloud", Tagcloud.class);    	
		bentities.put("users", User.class);    	   
		bentities.put("webpages", Webpages.class);    	    			
	}
	
	static
	{
    	bservices.put("baseitem", BaseitemService.class);
    	bservices.put("category", CategoryService.class);
    	bservices.put("classification", ClassificationService.class);
    	bservices.put("client", ClientService.class);
    	bservices.put("color", ColorService.class);
    	bservices.put("country", CountryService.class);
    	bservices.put("currency", CurrencyService.class);
    	bservices.put("img", ImgService.class);
    	bservices.put("items", ItemsService.class);    	
    	bservices.put("orders", OrdersService.class);
    	bservices.put("sale", SaleService.class);
    	bservices.put("size", SizeService.class);
    	bservices.put("tagcloud", TagcloudService.class);    	
    	bservices.put("users", UserService.class);    	   
    	bservices.put("webpages", WebpagesService.class);    	    			
	}
	static
	{
		bimpls.put("baseitem", BaseitemServiceImpl.class);
		bimpls.put("category", CategoryServiceImpl.class);
		bimpls.put("classification", ClassificationServiceImpl.class);
		bimpls.put("client", ClientServiceImpl.class);
		bimpls.put("color", ColorService.class); //ColorServiceImpl.class);
		bimpls.put("country", CountryServiceImpl.class);
    	bimpls.put("currency", CurrencyServiceImpl.class);
    	bimpls.put("img", ImgServiceImpl.class);
    	bimpls.put("items", ItemsServiceImpl.class);    	
    	bimpls.put("orders", OrdersServiceImpl.class);
    	bimpls.put("sale", SaleServiceImpl.class);
    	bimpls.put("size", SizeService.class);  //SizeServiceImpl.class);
    	bimpls.put("tagcloud", TagcloudServiceImpl.class);    	
    	bimpls.put("users", UserServiceImpl.class);    	   
    	bimpls.put("webpages", WebpagesServiceImpl.class);    	    			
	}  
	public static Class<?> getService(String uri1) throws LogicException {return bservices.get(uri1);}
	public static Class<?> getServiceImpl(String uri1) throws LogicException {return bimpls.get(uri1);}
	public static Class<?> getEntity(String uri1) throws LogicException {return bentities.get(uri1);}
	
	@SuppressWarnings("unchecked")
	public static <T> T getProxySevice(String uri1) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Class<?> impl = bimpls.get(uri1);
		Class<?> bean = bservices.get(uri1);
		return (T)Proxy.newProxyInstance(
			bean.getClassLoader(),
			new Class<?>[] {bean},
			new InvocationHandlerImpl(impl.getConstructor().newInstance())
		);
	}

	private static class InvocationHandlerImpl implements InvocationHandler {
		private Object service;
		public InvocationHandlerImpl(Object service) {this.service = service;}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			boolean annotationPresent = service.getClass().getMethod(method.getName(), method.getParameterTypes()).isAnnotationPresent(MyAnnotation.class);
			if(annotationPresent) {System.out.println("method: " + method.getName() + ", transaction start");}
			Object result = method.invoke(service, args);
			if(annotationPresent) {System.out.println("method: " + method.getName() + ", transaction stop");}
			return result;
		}
	}
}
