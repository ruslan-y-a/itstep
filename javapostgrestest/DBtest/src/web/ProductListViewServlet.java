package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tabs.Client;
//import tabs.Entity;

public class ProductListViewServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1508386093542791516L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Client> products = (List<Client>)req.getAttribute("client");
		int size = products.size();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset=\"UTF-8\">");
		pw.println("<title>Products list</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 style=\"color: green\">Products list</h1>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		pw.println("<th>Country</th>");
		pw.println("<th>Address</th>");
		pw.println("<th>Date</th>");
		pw.println("<th>Phone</th>");
		pw.println("<th>UserID</th>");
		pw.println("</tr>");
		for(Client product : products) {
			pw.println("<tr>");
			pw.printf("<td>%d</td>", product.getCountryid());
			pw.printf("<td>%s</td>", product.getAddress());
			pw.printf("<td>%1$td.%1$tm.%1$tY, %1$tA</td>", product.getCreationdate());
			pw.printf("<td>+%s</td>", product.getPhoneno());		
			pw.printf("<td>%d</td>", product.getUserid());
			pw.println("</tr>");
		}
		pw.println("</table>");
		pw.printf("<p>Total %d clients</p>", size);		
		int i1=req.getRequestURI().indexOf("WEB-INF/")+8;
		int i2=req.getRequestURI().indexOf("/list.html");
		String ss=req.getRequestURI().substring(i1, i2);
		pw.print("<p>"+ ss+"</p>");	
		pw.println("</body>");
		pw.println("</html>");
	}
}
