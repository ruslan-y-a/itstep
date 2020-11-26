<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.HashMap"%>

<c:set var="canonical" value="/about"/>  
<c:set var="description" value="iShop - about"/>  
<c:set var="h1" value="iShop - about"/>
<u:page title="about!" description="${description}" canonical="${canonical}" h1="${h1}" simple="true">
    <h2>about</h2>
    <div class="end"></div>
	 <h2>${currencytitle}</h2>
	<table border="1">
     <tr>           
       <th>Currency</th>
       <th>Rate</th>         
    </tr> 
	 <c:forEach var="entry" items="${currency}">
	  <tr>
	    <th>${entry.key}</th>
	    <th>${entry.value}</th>   
      </tr>
     </c:forEach>
	</table>
	
	<!-- 
	<c:forEach var="entry" items="${currency}">	  
     Key: <c:out value="${entry.key}"/>
     Value: <c:out value="${entry.value}"/>      
     </c:forEach>
	 -->
	
</u:page>