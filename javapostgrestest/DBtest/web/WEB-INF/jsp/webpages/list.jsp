<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="entities.Entity"%>


<c:set var="h1" value="webpages sets list"/>
<c:set var="title" value="webpages sets list"/>

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
     <th>url</th>
     <th>title</th>
     <th>description</th>
     <th>keywords</th>
     <th>h1</th> 
     <th>text</th> 
     <th>robots</th> 
     <th></th>     
   </tr> 

	<c:forEach var="webpages" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${webpages.id}"></td>
		   <td>${webpages.url}</td>
		   <td>${webpages.title}</td>
		   <td>${webpages.description}</td>
		   <td>${webpages.keywords}</td>
		   <td>${webpages.h1}</td>
		   <td>${webpages.text}</td>	
		   <td>${webpages.robots}</td>				   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${webpages.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} webpages</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add webpages</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete webpages</button></p>
  
 </form> 

</u:backoffice-page>