<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %> 

<c:set var="h1" value="Items list"/>
<c:set var="title" value="Items list"/>
 <c:url var="iedit" value="/img/edit.png"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p>Items list is empty</p>
 </c:if>

  <c:url var="deleteUrl" value="delete.html"/>
 <form action="${deleteUrl}" method="post">
  <table border="1">
   <tr>   
      <th></th>
     <th>articul;</th>
   <th>model;</th>
   <th>category;</th>
   <th>baseprice;</th>
   <th>discount;</th>	
   <th>name; </th>  
   <th>classification;</th>
  <th>active;</th>
  <th>webpages;</th>
   <th>img;</th>
     <th></th>
   </tr> 
	
	<c:set var="size" value="${0}"/>	
	 
	<c:forEach var="item" items="${list}">
	 
	    <tr>	      	
	       <td><input type="checkbox" name="id" value="${item.id}"></td>	       	       		   
		   <td>${item.articul}</td>
		   <td>${item.model}</td>
		   <td>${item.category.name}</td>
		   <td>${item.baseprice}</td>
		   <td>${item.discount}</td>
		   <td>${item.name}</td>
		   
		   <td>
		      <ul>
		         <c:forEach var="classif" items="${item.classification}">  		                                        		   		       		           
                        <li>${classif.name}</li>                                        
                 </c:forEach>  		   		   
		     </ul>
		   </td>		  
		   
		   <td>${item.active}</td>
		   <td>${item.webpages.h1}</td>
		   <td>		      
		        <ul class="noli">		        
		          <c:forEach var="imgs" items="${item.img}">
		             <c:url var="imgUrl" value="${imgs.url}"/>   	                                 		   		       		            
                      <li><img style="width:100px;" src="${imgUrl}"/></li>                                                           
                  </c:forEach>  	                 	  
		       </ul>
		   </td>	 
		   								   				
		    
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${item.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>
		       
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} items</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add Item</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete item</button></p>
  
 </form> 

</u:backoffice-page>