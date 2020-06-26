package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tabs.Entity;

public class ListViewer extends HttpServlet  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8100204119043454296L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Entity> products = (List<Entity>)req.getAttribute("client");
		int size = products.size();
		String[] menu=products.get(0).getFieldsList();
		resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
		pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset=\"UTF-8\">");
		pw.println("<title>Entity list</title>");
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 style=\"color: green\">Products list</h1>");
		pw.println("<table border=\"1\">");
		pw.println("<tr>");
		for (String string : menu) {
			pw.println("<th>" +string+"</th>");
		}
		/*pw.println("<th>Country</th>");
		pw.println("<th>Address</th>");
		pw.println("<th>Date</th>");
		pw.println("<th>Phone</th>");
		pw.println("<th>UserID</th>");*/
		pw.println("</tr>"); 		
		for(Entity product : products) {			
			pw.println("<tr>");			
			for (Map.Entry<String,Object> entry: product.getEntityValues().entrySet()) {				 				   
				   pw.println("<td>"+entry.getValue()+"</td>");
		         }			
			/*for (Object o: product.getEntityValuesList()) {				
				pw.println("<td>"+o+"</td>");
			}*/		
			pw.println("</tr>");
		}
		pw.println("</table>");
		pw.printf("<p>Total %d clients</p>", size);
		pw.println("</body>");
		pw.println("</html>");
	}
	
}
