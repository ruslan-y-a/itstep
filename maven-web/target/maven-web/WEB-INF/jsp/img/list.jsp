<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>


<c:set var="h1" value="Imgs list"/>
<c:set var="title" value="Imgs list"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p> list is empty</p>
 </c:if>
 <c:url var="iedit" value="/images/edit.png"/>
 <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
 
  <c:set var="size" value="${0}"/>
  <table border="1">
   <tr>    
     <th></th>      
     <th>Name</th>
     <th>alt</th>
     <th>url</th>     
     <th></th>     
   </tr> 

	<c:forEach var="img" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${img.id}"></td>
		   <td>${img.title}</td>
		   <td>${img.alt}</td>
		     
		     <c:url var="imgUrl" value="${img.url}"/>
		   
		   <td><img style="width:60px;" src="${imgUrl}"/></td>			   		   		    
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${img.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} img</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add img</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete img</button></p>
  
 </form> 

</u:backoffice-page>