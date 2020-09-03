package org.itstep.web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.itstep.factories.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.itstep.service.LogicException;
import org.itstep.web.action.Action;
import org.itstep.web.action.Action.Result;
import org.itstep.web.action.Action.ResultType;
import org.itstep.web.action.ActionException;

public class DispatcherServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final Logger logger = LogManager.getLogger();
	private static final long serialVersionUID = 9007851103631631999L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		String uri = req.getRequestURI();		
		logger.info("Start processing request on URI \"{}\" from client {}", uri, req.getLocalAddr());
		String search = req.getQueryString();
		uri = uri.substring(req.getContextPath().length());		
		if(uri.endsWith(".html")) {
			uri = uri.substring(0, uri.length() - ".html".length());			
		}
	//	System.out.println("========================uri )" + uri);
		try {
			Action action;
			Result result = null;
			if (search== null || search.indexOf("search")==-1) {action = ActionFactory.getAction(uri,search);}
			else {action = ActionFactory.getSearchAction(search);}
			
			if(action != null) {
				result = action.exec(req, resp);
			}
			
			if(result == null || result.getType() == ResultType.FORWARD) {
				if(result != null) {
					uri = result.getUrl();
				}
			//System.out.println("========================uri result)" + uri);
				req.getRequestDispatcher("/WEB-INF/jsp" + uri + ".jsp").forward(req, resp);
			} else {
				uri = req.getContextPath() + result.getUrl() + ".html";
				String params = result.getParameters();
				if(!params.isEmpty()) {
			//		System.out.println("========================params )" + params);
					uri += "?" + params;
				}
				resp.sendRedirect(uri);
			}
		} catch(ActionException e) {
			resp.sendError(e.getCode());
		} catch(LogicException e) {
			logger.error(String.format("Error & Logic Exception in processing the request on URI \"%s\" from client %s", uri, req.getLocalAddr()));
			throw new ServletException(e);
		} catch (Exception e1) {
			logger.error(String.format("Error in processing the request on URI \"%s\" from client %s", uri, req.getLocalAddr()));
			e1.printStackTrace();
			throw new ServletException(e1);
		}
	}
}
