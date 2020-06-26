<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="color" value="${entity}"/>
		<c:set var="title" value="Edit User ${color.id}"/>
		<c:set var="h1" value="New color"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add color"/>
		<c:set var="h1" value="Add color"/>
		<jsp:useBean id="color" class="entities.Color"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${color.id}">
			</c:if>							
			<br>
			<label for="name">Name</label>			
			<input type="text" id="name" name="name" value="${color.name}">
			<br>
		
			<button type="submit" class="form__button">Save</button>
		</form>
		
</u:backoffice-page>
