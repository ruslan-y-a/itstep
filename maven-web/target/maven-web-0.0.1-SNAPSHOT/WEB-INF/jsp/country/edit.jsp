<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="country" value="${entity}"/>
		<c:set var="title" value="Edit User ${country.id}"/>
		<c:set var="h1" value="New country"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add country"/>
		<c:set var="h1" value="Add country"/>
		<jsp:useBean id="country" class="org.itstep.entities.Country"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${country.id}">
			</c:if>							
			<br>
			<label class="form__label" for="name">Name</label>			
			<input class="form__text-input" type="text" id="name" name="name" value="${country.name}">
			<br>
			
			<label class="form__label" for="currency">currency:</label>
		    <select class="form__text-input" id="currency" name="currency">
			<c:forEach var="items" items="${currency}">
				<c:choose>
					<c:when test="${items.id == country.currency.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${items.id}" ${selected}>${items.name}</option>
			</c:forEach>
		    </select>					
			
		
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
