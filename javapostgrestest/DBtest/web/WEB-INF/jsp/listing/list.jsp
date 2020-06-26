<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="tabs.Entity"%>
<%
List<Entity> products = (List<Entity>)request.getAttribute("client");
int size = products.size();
String[] menu=products.get(0).getFieldsList();
response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Entity list</title>
</head>
<body>
 <h1 style=\"color: green\">Products list</h1>
 <c:if test="${empty products}">
  <p>Products list is empty</p>
 </c:if>
  <table border="1">
   <tr>   
    <c:forEach var="string" items="${menu}">
     <th>${string}</th>
    </c:forEach>     
   </tr> 
<%--    <%  for (String string : menu) { 	
	 <th><%=	string %></th>
	<%		} %>--%>		
	<c:forEach var="product" items="${client}">			
	    <tr>
	      <c:forEach var="entry" items="${product.name}">			
		   <td>${entry}</td>
		  </c:forEach>        	
        </tr>
    </c:forEach>    
  <%--  <c:forEach var="product" items="${client}">--%>
    <% for(Entity product : products) { %>
			<tr>			
	<%		for (Map.Entry<String,Object> entry: product.getEntityValues().entrySet()) { %>				 				   
			 <td><%= entry.getValue() %></td>
	<%         }	%>				
			</tr>
	<%	} %>
   <%-- </c:forEach>--%>
  </table>
  <p>Total ${products.size()} items</p>
</body>
</html>