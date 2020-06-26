<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import ="entities.Entity"%>


<c:set var="h1" value="clients list"/>
<c:set var="title" value="clients list"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p> list is empty</p>
 </c:if>
 <c:url var="iedit" value="/img/edit.png"/>
 <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
 
  <c:set var="size" value="${0}"/>
  <table border="1">
   <tr>          
     <th></th>
     <th>country</th>
     <th>address</th>
     <th>creationdate</th>
     <th>user</th>
     <th>bonuspoints</th>  
     <th>phoneno</th>
     <th>recentitems</th>    
     <th></th>
   </tr> 

	<c:forEach var="client" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${client.id}"></td>
		   <td>${client.country.name}</td>
		   <td>${client.address}</td>
		   <td>${client.creationdate}</td>
		   <td>${client.user.name}</td>
		   <td>${client.bonuspoints}</td>		
		   <td>${client.phoneno}</td>	
		   <td>
		     <ul>
		     <c:forEach var="items" items="${client.recentitems}">		
		       <li>${items.name}</li>
		      </c:forEach>
		      </ul>			
		   </td>	
		   		  	   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${client.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} clients</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add client</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete client</button></p>
  
 </form> 

</u:backoffice-page>