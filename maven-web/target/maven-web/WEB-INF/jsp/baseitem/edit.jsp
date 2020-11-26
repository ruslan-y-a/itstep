<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="baseitem" value="${entity}"/>
		<c:set var="title" value="Edit baseitem ${baseitem.id}"/>
		<c:set var="h1" value="New baseitem"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add baseitem"/>
		<c:set var="h1" value="Add baseitem"/>
		<jsp:useBean id="baseitem" class="org.itstep.entities.Baseitem"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post" class="form">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${baseitem.id}">
			</c:if>				
			
			<br>			
			<label for="name">name</label>			
			<input type="text" id="name" name="name"  value="${baseitem.name}">			
			
			<label class="form__label" for="items">item:</label>
		    <select class="form__text-input" id="items" name="items">
			<c:forEach var="item" items="${items}">
				<c:choose>
					<c:when test="${item.id == baseitem.item.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${item.id}" ${selected}>${item.name}</option>
			</c:forEach>
		    </select>							
			 
			<label class="form__label" for="color">color:</label>
		    <select class="form__text-input" id="color" name="color">
			<c:forEach var="icolor" items="${color}">
				<c:choose>
					<c:when test="${icolor.id == baseitem.color.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${icolor.id}" ${selected}>${icolor.name}</option>
			</c:forEach>
		    </select> 
			 
			<label class="form__label" for="sizeid">size:</label>
		    <select class="form__text-input" id="sizeid" name="sizeid">
			<c:forEach var="dbsize" items="${size}">
				<c:choose>
					<c:when test="${dbsize.id == baseitem.size.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${dbsize.id}" ${selected}>${dbsize.name}</option>
			</c:forEach>
		    </select>  
		    
		    <label class="form__label" for="currency">currency:</label>
		    <select class="form__text-input" id="currency" name="currency">
			<c:forEach var="dbcurrency" items="${currency}">
				<c:choose>
					<c:when test="${dbcurrency.id == baseitem.currency.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${dbcurrency.id}" ${selected}>${dbcurrency.name}</option>
			</c:forEach>
		    </select>  
			 
			<br>			
			<label for="quantity">quantity</label>			
			<input type="number" id="quantity" name="quantity"  value="${baseitem.quantity}">
			<br>			
			<label for="baseprice">baseprice</label>			
			<input type="number" id="baseprice" name="baseprice" value="${baseitem.baseprice}">
			<br>						
			
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
