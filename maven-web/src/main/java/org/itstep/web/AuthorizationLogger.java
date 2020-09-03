package org.itstep.web;

//import java.text.SimpleDateFormat;
//import java.util.Date;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.itstep.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AuthorizationLogger implements HttpSessionListener, HttpSessionAttributeListener {
	private static final Logger logger = LogManager.getLogger();
	//private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd, HH:mm:ss");

	@Override
	public void sessionDestroyed(HttpSessionEvent e) {
		User user = (User)e.getSession().getAttribute("sessionUser");
		if(user != null) {
			logger.info("User \"{}\" ({}) logout", user.getLogin(), user.getRole());
		}
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent e) {
		if("sessionUser".equals(e.getName())) {
			User user = (User)e.getValue();
			logger.info("User \"{}\" ({}) login", user.getLogin(), user.getRole());
		}
	}
}
