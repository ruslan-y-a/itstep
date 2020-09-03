<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import ="org.itstep.entities.Entity"%>


<c:set var="h1" value="size list"/>
<c:set var="title" value="size list"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p> list is empty</p>
 </c:if>
 <c:url var="iedit" value="/img/edit.png"/>
 <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
 
  <c:set var="csize" value="${0}"/>
  <table border="1">
   <tr>   
     <th></th>        
     <th>Name</th>     
     <th></th>     
   </tr> 

	<c:forEach var="size" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${size.id}"></td>
		   <td>${size.name}</td>		     		   		    
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${size.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="csize" value="${csize+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${csize} size</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add size</a></button></p>
  <p><button type="submit" class="form__button_danger">Delete size</button></p>
  
 </form> 

</u:backoffice-page>