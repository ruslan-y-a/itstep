<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import ="org.itstep.entities.Entity"%>


<c:set var="h1" value="Tagcloud list"/>
<c:set var="title" value="Tagcloud list"/>

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
     <th>webpages</th>
     <th>classification</th>   
     <th></th>
   </tr> 

	<c:forEach var="tagcloud" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${tagcloud.id}"></td>		  
		   <td>${tagcloud.webpages.title}</td>
		   	<!-- 
		   <td>
		     <ul>
		     <c:forEach var="classif" items="${classification}">		
		         <c:forEach var="iclassif" items="${tagcloud.classification}">		
		            <c:if test="${classif.id==iclassif.id}">
		              <li>${classif.name}</li>
		            </c:if>
  		         </c:forEach> 		           		         
		      </c:forEach>
		      </ul>			
		   </td>  -->
		   <td>
		     <ul>
		        <c:forEach var="classif" items="${tagcloud.classification}">				         
		              <li><span>${classif.parentname}</span> : <span>${classif.name}</span></li>		             		           		         
		        </c:forEach>
		      </ul>			
		   </td>	
		   		 	   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${tagcloud.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} Tagclouds</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add Tagcloud</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete Tagcloud</button></p>
  
 </form> 

</u:backoffice-page>