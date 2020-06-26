<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="service.DBService"%>
<%@ page import="tabs.Entity"%>
<%@ page import="tabs.Items"%>
<%@ page import="tabs.Classification"%>



<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>New product</title>
	</head>
	<body>
		<h1 style="color: green">New product</h1>
    
    <p>articul: <span>${product.articul}</span></p>     
    <p>model: <span>${product.model}</span></p>
    <p>category <span>${product.category}</span></p>
    <p>baseprice <span>${product.baseprice}</span></p>
    <p>discount <span>${product.discount}</span></p>
    <p>title <span>${product.title}</span></p>
    <p>text <span>${product.text}</span></p>
    <p>name <span>${product.name}</span></p>
    <p>description <span>${product.description}</span></p>
    <p>keywords <span>${product.keywords}</span></p>
    <p>mainimgurl <span>${product.mainimgurl}</span></p>
    <p>url <span>${product.url}</span></p>   
    
		<form action="saveparams.html" method="post">
			<h2>Classification:</h2>				
			<c:if test="${not empty product}">
			<input type="hidden" name="itemid" value="${product.DBgetId()}">
			</c:if>		
			                  	     
             <br>
             <label for="category">Classification:</label>						
			          
			    <c:forEach var="category" items="${classification}">
			       <c:if test="${category.getParentid()==0 || category.getParentid()==category.DBgetId()}">
			         <p>${category.name}</p>
			         <c:set var="parent" value="${category.name}"/>
			       </c:if>
				   <c:if test="${category.getParentid()>0 || category.getParentid()==category.DBgetId()}">
			         <p>
			    <%-- <input type="checkbox" name="${parent}" value="${category.DBgetId()}">${category.name} --%>
			         <input type="radio" id="${category.name}" name="${parent}" value="${category.DBgetId()}">${category.name}
			         </p>
			       </c:if> 				 				 				  				     				     				  			  				  								 				     
			    </c:forEach>			          
                 	
			<button type="submit">Save</button>						
		</form>
	</body>
</html>
