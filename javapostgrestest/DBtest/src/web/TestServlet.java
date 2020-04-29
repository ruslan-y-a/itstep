package web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7722079853885256436L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    String[] slink = new String[17]; 
	    slink[0] ="<p><a href=\"/test/users/list.html\">users list</a></p>";
	    slink[1]="<p><a href=\"/test/client/list.html\">Clients list</a></p>";
	    slink[2]="<p><a href=\"/test/category/list.html\">category list</a></p>";
	    slink[3]="<p><a href=\"/test/classification/list.html\">classification list</a></p>";
	    slink[4]="<p><a href=\"/test/country/list.html\">country list</a></p>";
	    slink[5]="<p><a href=\"/test/currency/list.html\">currency list</a></p>";
	    slink[6]="<p><a href=\"/test/img/list.html\">img list</a></p>";
	    slink[7]="<p><a href=\"/test/itemcatgory/list.html\">itemcatgory list</a></p>";
	    slink[8]="<p><a href=\"/test/items/list.html\">items list</a></p>";
	    slink[9]="<p><a href=\"/test/baseitem/list.html\">baseitem list</a></p>";
	    slink[10]="<p><a href=\"/test/orders/list.html\">orders list</a></p>";
	    slink[11]="<p><a href=\"/test/sale/list.html\">sale list</a></p>";
	    slink[12]="<p><a href=\"/test/tagcloud/list.html\">tagcloud list</a></p>";
	    slink[13]="<p><a href=\"/test/tagurl/list.html\">tagurl list</a></p>";
	    slink[14]="<p><a href=\"/test/webpages/list.html\">webpages list</a></p>";
	    slink[15]="<p><a href=\"/test/size/list.html\">size list</a></p>";
	    slink[16]="<p><a href=\"/test/color/list.html\">color list</a></p>";
	    
	    resp.setCharacterEncoding("UTF-8");
		PrintWriter pw = resp.getWriter();
	    pw.println("<!DOCTYPE html>");
		pw.println("<html>");
		pw.println("<head>");
		pw.println("<meta charset=\"UTF-8\">");
		pw.println("<title>Products list</title>"); 
		pw.println("</head>");
		pw.println("<body>");
		pw.println("<h1 style=\"color: red\">menu</h1>");
		for(String ss: slink) {pw.println(ss);}
		pw.println("<h2 style=\"color: blue\"> BASE menu</h2>");		
		pw.println("<p><a href=\"/test/client/listing.html\">Client list</a></p>");
		pw.println("</body>");
		pw.println("</html>");
		
	}
}
