<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
  
<c:set var="h1" value="iShop  - OOps! Error Page!"/>
<c:set var="canonical" value="/"/>  <%-- req.getContextPath() --%>
<c:set var="description" value="iShop  - OOps! Error Page!"/>  


<u:page title="iShop  - OOps! Error Page!" description="${description}" canonical="${canonical}" cart="0" h1="${h1}">
	<c:choose>
		<c:when test="${not empty pageContext.exception}">
			<p>Database error</p>
		</c:when>
		<c:when test="${pageContext.errorData.statusCode == 404}">
			<p>Page not found</p>
		</c:when>
		<c:when test="${pageContext.errorData.statusCode == 400}">
			<p>Wrong request data </p>
		</c:when>
		<c:otherwise>
			<p>Unexpected Error</p>
		</c:otherwise>
	</c:choose>
	<p>Please, repeat your request</p>
	<c:url var="mainUrl" value="/index.html"/>
	<p><a href="${mainUrl}" class="form__button">Back</a></p>
</u:page>
