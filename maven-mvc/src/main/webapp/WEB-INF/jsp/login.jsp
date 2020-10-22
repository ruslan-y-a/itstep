<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:set var="canonical" value="/login.html"/>  
<c:set var="description" value="iShop - Login"/>  
<c:set var="h1" value="iShop - Login"/>
<u:page title="Login!" description="${description}" canonical="${canonical}" h1="${h1}" simple="true">
    
    <div class="end"></div>
	<c:if test="${not empty param.message}">
		<div class="message">${param.message}</div>
	</c:if>
	<c:url var="loginUrl" value="/login.html"/>
	<form action="${loginUrl}" method="post" class="form">
		<label class="form__label" for="login">Name:</label>
		<input class="form__text-input" type="text" id="login" name="login">
		<label class="form__label" for="password">Password:</label>
		<input class="form__text-input" type="password" id="password" name="password">
		<button class="form__button" type="submit">Login</button>
		<input type="hidden" name="preious_uri" value="${param.preious_uri}">
	</form>
	 <c:url var="registerUrl" value="/register.html"/>
	<div><a href="${registerUrl}">Register</a></div>
</u:page>
