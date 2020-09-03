<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import ="org.itstep.entities.Entity"%>


<c:set var="h1" value="Sale list"/>
<c:set var="title" value="Sale list"/>
 <c:url var="iedit" value="/img/edit.png"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p>Sales list is empty</p>
 </c:if>

  <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
  <table border="1">
   <tr>   
      <th></th>
   <th>sale date</th>
   <th>order №</th>
   <th>order date</th>
   <th>order sum</th>
   <th>order client</th>
   <th>returned</th>
   <th>currency</th>
     <th></th>
   </tr> 
   
	<c:set var="size" value="${0}"/>	
	 
	<c:forEach var="sale" items="${list}">	 
	    <tr>	      	
	       <td><input type="checkbox" name="id" value="${sale.id}"></td>	       	       		   
		   <td><fmt:formatDate value="${sale.datetime}" pattern="yyyy-MM-dd, HH:mm:ss"/></td>
		   <td>${sale.order.number}</td>
		   <td><fmt:formatDate value="${sale.order.datetime}" pattern="yyyy-MM-dd, HH:mm:ss"/></td>
		   <td>${sale.order.sum}</td>
		   <td>${sale.order.client.address}</td>
		   <td>${sale.returned}</td>
		   <td>${sale.currency.name}</td>
		   		   		    
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${sale.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>
		       
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} sale</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add sales</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete sales</button></p>
  
 </form> 

</u:backoffice-page>