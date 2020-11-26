<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="currency" value="${entity}"/>
		<c:set var="title" value="Edit currency ${currency.id}"/>
		<c:set var="h1" value="New currency"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add currency"/>
		<c:set var="h1" value="Add currency"/>
		<jsp:useBean id="currency" class="org.itstep.entities.Currency"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${currency.id}">
			</c:if>							
			<br>
			<label for="name">Name</label>			
			<input type="text" id="name" name="name" value="${currency.name}">
			<br>
			<label for="rate">rate</label>			
			<input type="text" id="rate" name="rate" value="${currency.rate}">  
			<br>						
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
