<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="service.DBService"%>
<%@ page import="tabs.Entity"%>
<%@ page import="tabs.Classification"%>

<%--
DBService service =(DBService) request.getAttribute("service");
Map<String,Map<Long,String>> mapIdField = service.mapClassify();
--%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>New product</title>
	</head>
	<body>
		<h1 style="color: green">New product</h1>
	<%-- 
		<c:forEach var="c1" items="${mapIdField}">				  				  
				     <p>${c1.getKey()}</p>				  
				</c:forEach>
				--%>
		<form action="save.html" method="post">
				
			<label for="category">Classification:</label>						
			<select id="category" name="category">		

				<c:forEach var="category" items="${service.read(\"category\",\"parentid\",\">0\")}">	
				      <c:set var="parent" value="${service.read(\"category\",category.getParentid())}"/>  				  
				     <option value="${category.DBgetId()}">${parent.getName()} - ${category.getName()}</option>				  			  				  								 
				</c:forEach>
					
			</select>
			<br>			
			<br>
			<label for="articul">articul: </label>
			<input type="text" id="articul" name="articul">	
			<br>
			<label for="model">model:</label>
			<input type="text" id="model" name="model">
			<br>			
			<label for="baseprice">baseprice:</label>	
			<input type="number" id="baseprice" name="baseprice">
			<br>
			<label for="discount">discount:</label>	
			<input type="number" id="discount" name="discount">
			<br>
			<label for="title">title:</label>	
			<input type="text" id="title" name="title">
			<br>
			<label for="text">text:</label>
			<input type="text" id="text" name="text">
			<br>
			<label for="name">name:</label>
			<input type="text" id="name" name="name">
			<br>
			<label for="description">description:</label>
			<input type="text" id="description" name="description">			
			<br>
			<label for="keywords">keywords:</label>
			<input type="text" id="keywords" name="keywords">			
			<br>
			<label for="mainimgurl">mainimgurl:</label>
			<input type="text" id="mainimgurl" name="mainimgurl">			
			<br>
			<label for="url">url:</label>
			<input type="text" id="url" name="url">			
			<br>
			<button type="submit">Save</button>			
			<p><input type="checkbox" name="addparams" value="true" checked>wanna add params?</p>
		</form>
	</body>
</html>
