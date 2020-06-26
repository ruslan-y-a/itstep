<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import ="entities.Entity"%>


<c:set var="h1" value="baseitems list"/>
<c:set var="title" value="baseitems list"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p>Items list is empty</p>
 </c:if>
 <c:url var="iedit" value="/img/edit.png"/>
 <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
 
  <c:set var="size" value="${0}"/>
  <table border="1">
   <tr>       
    <th></th>
    <th>Item Articul</th>
    <th>name</th>
    <th>color</th>
    <th>size</th>  
    <th>quantity</th>     
    <th>baseprice</th>   
        <th>currency</th>   
     <th></th>     
   </tr> 

	<c:forEach var="baseitem" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${baseitem.id}"></td>
	       <td>${baseitem.item.name}</td>		  
	       <td>${baseitem.name}</td>	 
           <td>${baseitem.color.name}</td>
		   <td>${baseitem.size.name}</td>
		   <td>${baseitem.quantity}</td>		   		 
		   <td>${baseitem.baseprice}</td>
		   <td>${baseitem.currency.name}</td>
		   		   		   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${baseitem.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} baseitems</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add baseitem</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete baseitem</button></p>
  
 </form> 

</u:backoffice-page>