<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>

<c:set var="h1" value="Orders list"/>
<c:set var="title" value="Orders list"/>
 <c:url var="iedit" value="/images/edit.png"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p>Orders list is empty</p>
 </c:if>

  <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
  <table border="1">
   <tr>   
      <!--  <th></th>  -->
     <th>number;</th>
   <th>datetime;</th>
   <th>dateexpired;</th>
   <th>baseitem;</th>
   <th>client;</th>	
   <th>quantity; </th>  
   <th>sum;</th>
    <td>bonuspoints</td>
  <th>currency;</th>
  <th>delivery;</th>
   <th>active;</th>   
     <th></th>
   </tr> 
	
	<c:set var="size" value="${0}"/>	
	 
	<c:forEach var="orders" items="${list}">	 
	    <tr>	      	
	     <c:if test="${orders.active}">
<!--  	        <td><input type="checkbox" name="id" value="${orders.id}"></td>  -->	       	       		   
		    <td>${orders.number}</td>
		    <td><fmt:formatDate value="${orders.datetime}" pattern="yyyy-MM-dd, HH:mm:ss"/></td>	
		    <td><fmt:formatDate value="${orders.dateexpired}" pattern="yyyy-MM-dd"/></td>	
		   <td><span>${orders.baseitem.item.name}</span> : <span>${orders.baseitem.color.name}</span> : <span>${orders.baseitem.size.name}</span></td>		        
		   <td>${orders.client.user.name}</td>
		   <td>${orders.quantity}</td>
		   <td>${orders.sum}</td>
		   <td>${orders.bonuspoints}</td>
		   <td>${orders.currency.name}</td>
		   <td>${orders.delivery}</td>		   		   		   		   		  
		   <td>${orders.status}</td>
		   		   		    
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${orders.id}"/>
			</c:url>
			
			 <td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>
		   </c:if>    
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} orders</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add orders</a></button></p>
  
   <h2>Not Active Orders</h2>     
   <table>		
	    <c:forEach var="orders" items="${list}">	      	
	     <c:if test="${orders.active==false}">
	      <tr> 
	       <td><input type="checkbox" name="id" value="${orders.id}"></td>
	       <td>${orders.number}</td>	       	       		   
		    <td><fmt:formatDate value="${orders.datetime}" pattern="yyyy-MM-dd, HH:mm:ss"/></td>	
		   <td><span>${orders.baseitem.item.name}</span> : <span>${orders.baseitem.color.name}</span> : <span>${orders.baseitem.size.name}</span></td>		
		   <td>${orders.client.user.name}</td>
		   <td>${orders.quantity}</td>
		   <td>${orders.sum}</td>		   
		   <td>${orders.currency.name}</td>
		   <td>${orders.delivery}</td>
		   <td>${orders.status}</td>
		  </tr> 		   		   		   		   
         </c:if>         
       </c:forEach>
    </table>  	
  <p><button class="form__button_danger" type="submit">Delete orders</button></p>
  
 </form> 
  
</u:backoffice-page>