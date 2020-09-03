<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<!--  %@ page import ="org.itstep.tabs.Entity"%> -->

<c:set var="h1" value="classification list"/>
<c:set var="title" value="classification list"/>

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
        <th>Parent</th>        
        <th>Category</th>
        <th>Edit</th>    
        <th></th>
      </tr>
	  <c:forEach var="parents" items="${list}">     	
	    <c:if test="${parents.parentid == 0 || parents.parentid == parents.id}">
	      <tr>	     	      	    
            <td class="table__cell"><input type="checkbox" name="id" value="${parents.id}"></td>
            <td>${parents.name}</td>
            <td>${parents.parentid}</td>
            <td>
              <c:if test="${parents.categoryid>0}">
		   	      Category id: ${parents.categoryid}
		   	   </c:if>               
             </td>
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
 <form action="${deleteUrl}" method="post" >
  
 
  <c:set var="size" value="${0}"/>
   <table id="table2" border="1">
   <tr>       
     <th></th>
     <th>Name</th>
     <th>Parent</th>   
     <th>Category</th>       
     <th></th>     
   </tr> 

	<c:forEach var="classification" items="${list}">			
	   <c:if test="${classification.parentid>0}">
	    <tr class="V${classification.parentid}">	      		
		    <td><input type="checkbox" name="id" value="${classification.id}"></td>		   
		   	   		     
		      <td >${classification.name}</td>
		       <td >${classification.parentname}</td>	
		   	   <td>
                <c:if test="${classification.categoryid>0}">
		   	      Category id: ${classification.categoryid}
		   	   </c:if>               
             </td>	
		   	   			   	   		    	
		    <c:url var="editUrl" value="edit.html">
				<c:param name="id" value="${classification.id}"/>
			</c:url>
			<td ><a href="${editUrl}"><img style="width:20px;" src="${iedit}"/></a></td>		    	
        </tr>
        <c:set var="size" value="${size+1}"/>
      </c:if>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} classification</p>
  
            <c:url var="addUrl" value="edit.html">
			</c:url>
  
  <p><button class="form__button"><a href="${addUrl}">Add classification</a></button></p>
  <p><button class="form__button_danger" type="submit">Delete classification</button></p>
  
 </form>  
</div> 
<!-- //////////SCRIPT////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ -->	
<c:url var="javascript" value="/script/category.js">
			</c:url>
	    <script type="text/javascript" src="${javascript}"></script>       
<!-- //////////SCRIPT////////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ -->	
</u:backoffice-page>