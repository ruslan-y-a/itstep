<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="img" value="${entity}"/>
		<c:set var="title" value="Edit User ${img.id}"/>
		<c:set var="h1" value="New img"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add img"/>
		<c:set var="h1" value="Add img"/>
		<jsp:useBean id="img" class="org.itstep.entities.Img"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
		<form:form method="POST" action="${saveUrl}" enctype="multipart/form-data">		    
         <!--   <form action="${saveUrl}" enctype="multipart/form-data" method="post">  --> 
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${img.id}">
			</c:if>							
			<br>
			<label for="title">title</label>			
			<input type="text" id="title" name="title" value="${img.title}">
			<br>
			<label for="alt">alt</label>			
			<input type="text" id="alt" name="alt" value="${img.alt}">  
			<br>			
			<label for="url">url</label>		
			<input type="text" id="url" name="url"  value="${img.url}">		
			<label for="file">url</label>			
		    <input type="file" id="file"  name="file">
				
			<button class="form__button" type="submit">Save</button>
		</form:form>			
		
</u:backoffice-page>
