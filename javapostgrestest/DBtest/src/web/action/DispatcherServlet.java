package web.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import factories.Factory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.LogicException;
import web.action.Action;
import web.action.Action.Result;
import web.action.Action.ResultType;
import web.action.ActionException;

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
		logger.debug(String.format("Start processing the request on URI \"%s\" from client %s", uri, req.getLocalAddr()));
		String search = req.getQueryString();
		uri = uri.substring(req.getContextPath().length());		
		if(uri.endsWith(".html")) {
			uri = uri.substring(0, uri.length() - ".html".length());			
		}
	   // System.out.println("DS========================req.getParameter(id) )" + req.getParameter("id"));
		
		try(Factory factory = new Factory()) {
			Action action;
			if (search== null || search.indexOf("search")==-1) {action = factory.getAction(uri,search);}
			else {action = factory.getSearchAction(search);}
			Result result = null;
			
			if(action != null) {
				result = action.exec(req, resp);
			}
			
			if(result == null || result.getType() == ResultType.FORWARD) {
				if(result != null) {
					uri = result.getUrl();
				}
		//	System.out.println("========================uri result)" + uri);
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
