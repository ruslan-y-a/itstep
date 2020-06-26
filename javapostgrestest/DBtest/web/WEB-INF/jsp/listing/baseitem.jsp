<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="tabs.Items"%>
<%@ page import ="tabs.Entity"%>
<%@ page import ="tabs.Color"%>
<%@ page import ="tabs.Currency"%>
<%@ page import ="tabs.Size"%>
<%@ page import ="tabs.Size"%>
<%@ page import ="service.DBService"%>
<%@ page import ="service.DBServiceImpl"%>

<%
//List<Entity> products = (List<Entity>)request.getAttribute("client");
//String[] menu=products.get(0).getFieldsList();
//DBService service =(DBService) request.getAttribute("service");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Entity list</title>
</head>
<body>
 <h1 style=\"color: green\">Users list</h1>
 <c:if test="${empty client}">
  <p>Users list is empty</p>
 </c:if>
  <table border="1">
   <tr>   
    <c:forEach var="string" items="${client.get(0).getFieldsList()}">
      <th>${string}</th>
    </c:forEach>     
   </tr> 
	
	<c:set var="size" value="${0}"/>
	<%
	Items items; Color icolor; Size isize; Currency icurrency;	 
	 %>
	 
	<c:forEach var="baseitem" items="${client}">		
	<%--
	items=service.read("items",baseitem.getItemid());
	icolor=service.read("color",baseitem.getColorid());
	isize=service.read("size",baseitem.getSizeid());
	icurrency=service.read("currency",baseitem.getCurrency());

	--%>	
	    <tr>	      		
		   <td>${baseitem.DBgetId()}</td>
		   <td>${service.read("items",baseitem.getItemid()).name}</td>      
		   <td>${service.read("color",baseitem.getColorid()).name}</td> 
		   <td>${service.read("size",baseitem.getSizeid()).name}</td>      		     
		   <td>${baseitem.name}</td>
		   <td>${baseitem.quantity}</td>
		   <td>${baseitem.baseprice}</td>	 	    	
		   <td>${service.read("currency",baseitem.getCurrency()).name}</td>    
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} users</p>
</body>
</html>