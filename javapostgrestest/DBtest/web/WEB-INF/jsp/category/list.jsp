<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="entities.Entity"%>

<c:set var="h1" value="categories list"/>
<c:set var="title" value="categories list"/>

<u:backoffice-page h1="${h1}" title="${title}">

 <c:if test="${empty list}">
  <p>list is empty</p>
 </c:if>
 
  <c:url var="iedit" value="/img/edit.png"/>
 
  <c:url var="deleteUrl" value="delete.html"/>
  
<div class="fledt">  
 <form action="${deleteUrl}" method="post" > 
    <table id="table1" border="1">
      <tr>
        <th></th>
        <th>Name</th>
        <th>Edit</th>    
      </tr>
	  <c:forEach var="parents" items="${list}">     	
	    <c:if test="${parents.parentid == 0 || parents.parentid == parents.id}">
	      <tr>	     	      	    
            <td class="table__cell"><input type="checkbox" name="id" value="${parents.id}"></td>
            <td>${parents.name}</td>
             <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${parents.id}"/>	
			</c:url>	
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>
		  </tr>					                   
        </c:if>   	       	   	    		      	    	       
      </c:forEach>    
  
            <c:url var="addpUrl" value="edit.html">
            </c:url>
 
 </table>
  <p><button class="form__button"><a href="${addpUrl}">Add/Edit Parent categories</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete category</button></p>  
 </form> 
</div> 
 <br>
 <div class="end"></div>
 <div class="fright">
 
   <h2>Child categories</h2>
   <br/>
 
 <form action="${deleteUrl}" method="post" >  
  <c:set var="size" value="${0}"/>
   <table id="table2" border="1">
   <tr>       
     <th></th>
     <th>Parent</th>
     <th>Name</th>     
     <th></th>     
   </tr> 

	<c:forEach var="category" items="${list}">			
	   <c:if test="${category.parentid>0 }">
	    <tr class="V${category.parentid}">	      		
		    <td><input type="checkbox" name="id" value="${category.id}"></td>		   
		    
		    <td >${category.parentname}</td>		   
		    <td >${category.name}</td>
		   		   		   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${category.id}"/>
			</c:url>
			<td><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
      </c:if>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} categories</p>
  
            <c:url var="addUrl" value="edit.html">
			</c:url>
  
  <p><button class="form__button"><a href="${addUrl}">Add categories</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete categories</button></p>
  
 </form>  
</div> 
<!-- //////////SCRIPT////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ -->	
<c:url var="javascript" value="/script/category.js">
			</c:url>
	    <script type="text/javascript" src="${javascript}"></script>       
<!-- //////////SCRIPT////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ -->	
</u:backoffice-page>