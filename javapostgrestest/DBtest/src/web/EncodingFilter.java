package web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter {
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
/*		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		System.out.println("!!!!!!!!!!!!!!!!getQueryString: "+request.getQueryString());
		System.out.println("!!!!!!!!!!!!!!!!getRequestURL: "+request.getRequestURL());
		System.out.println("!!!!!!!!!!!!!!!!getRequestURI: "+request.getRequestURI());*/
	//	System.out.println("FILTER: "+req);
		req.setCharacterEncoding("UTF-8");
		chain.doFilter(req, resp);
	}
}
