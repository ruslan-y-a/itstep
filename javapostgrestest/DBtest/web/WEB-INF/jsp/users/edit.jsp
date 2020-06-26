<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="users" value="${entity}"/>
		<c:set var="title" value="Edit User ${users.id}"/>
		<c:set var="h1" value="New User"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add User"/>
		<c:set var="h1" value="Add User"/>
		<jsp:useBean id="users" class="entities.User"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${users.id}">
			</c:if>							
			<br>
			<label for="name">Name</label>			
			<input type="text" id="name" name="name" value="${users.name}">
			<br>
			<label for="login">login</label>			
			<input type="text" id="login" name="login" value="${users.login}">  
			<br>			
			<label for="password">Password</label>			
			<input type="password" id="password" name="password"  value="${users.password}">
			<br>			
			<label for="email">Email</label>			
			<input type="email" id="email" name="email" value="${users.email}">
			<br>						
			
			<label for="role">Role:</label>			
			<select id="role" name="role">				    													      
		        <option value="1">CLIENT</option>
		        <option value="2">PRODUCT MANAGER</option>	
				<option value="0">ADMIN</option>							
				<option value="2">PRODUCT MANAGER</option>				
				<option value="3">MANAGER</option>				
				<option value="4">CASHIER</option>
			</select>
			<br>
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
