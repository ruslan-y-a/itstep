<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import ="org.itstep.entities.Entity"%>


<c:set var="h1" value="users list"/>
<c:set var="title" value="users list"/>

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
     <th>Name</th>
     <th>login</th>
     <th>password</th>
     <th>email</th>
     <th>role</th>
     <th></th>     
   </tr> 

	<c:forEach var="user" items="${list}">			
	    <tr>	      		
		   <td><input type="checkbox" name="id" value="${user.id}"></td>
		   <td>${user.name}</td>
		   <td>${user.login}</td>
		   <td>${user.password}</td>
		   <td>${user.email}</td>
		   <td>${user.role}</td>		   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${user.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
    </c:forEach>    
 
  </table>
  <p>Total ${size} users</p>
  <c:url var="editUrl" value="edit.html"/>
  <p><button class="form__button"><a href="${editUrl}">Add User</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete User</button></p>
  
 </form> 

</u:backoffice-page>