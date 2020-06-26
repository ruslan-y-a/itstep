<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="tabs.Entity"%>
<%
List<Entity> products = (List<Entity>)request.getAttribute("client");
int size = products.size();
String[] menu=products.get(0).getFieldsList();
//response.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Entity list</title>
</head>
<body>
 <h1 style=\"color: green\">Users list</h1>
 <c:if test="${empty products}">
  <p>Users list is empty</p>
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
	<c:forEach var="user" items="${client}">			
	    <tr>	      		
		   <td>${user.DBgetId()}</td>
		   <td>${user.name}</td>
		   <td>${user.password}</td>
		   <td>${user.email}</td>
		   <td>${user.roleid}</td>		    	
        </tr>
    </c:forEach>    
 
  </table>
  <p>Total ${products.size()} users</p>
</body>
</html>