<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="classification" value="${entity}"/>
		<c:set var="title" value="Edit classification ${classification.id}"/>
		<c:set var="h1" value="New classification"/>
	<!-- 	<c:set var="selection" value="1"/>		
	    <c:if test="${classification.parentid>0}"><c:set var="selection" value="0"/></c:if>		 -->	   
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add classification"/>
		<c:set var="h1" value="Add classification"/>
		<jsp:useBean id="classification" class="org.itstep.entities.Classification"/>
	</c:otherwise>
</c:choose>  

          
<u:backoffice-page h1="${h1}" title="${title}">                 			
		    <c:url var="saveUrl" value="save.html"/>
            <form id="form1" class="backoffice-edit-form" action="${saveUrl}" method="post">        
               
    <h2 class="backoffice-form__label">Select Parent classification</h2> 	        	     	             
    	<select class="backoffice-form__text-input" id="parentid2" name="parentid">
			<option value="0"></option>
			<c:forEach var="classificationparent" items="${list}">			 
			     <c:if test="${classificationparent.parentid == 0 }">			 
				   <c:choose>
					   <c:when test="${classificationparent.id == classification.parentid}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${classificationparent.id}" ${selected}>${classificationparent.name}</option>
			     </c:if>	   			     
			</c:forEach>
		</select>		             
           
      <!--    <label class="backoffice-form__label" for="childname">categoryid:<span>${classification.categoryid}</span> </label>-->  
          <input class="backoffice-form__text-input" type="hidden" id="categoryid" name="categoryid" value="${classification.categoryid}">
            
      	  <h2 class="backoffice-form__label">${title}</h2> 	
       				        
	         <fieldset id="backoffice-edit-form-item"> 
	          <label class="backoffice-form__label" for="childname"> classification name </label>            
	          <input class="backoffice-form__text-input" type="text" id="childname" name="name" value="${classification.name}"> 	  
	         </fieldset>                
	          <input class="backoffice-form__text-input" type="hidden" id="childid" name="id" value="${classification.id}">  
	        
	         <c:if test="${classification.parentid==0 || classification.parentid==classification.id}">   
	          <fieldset id="backoffice-edit-form-item"> 
	           <label class="backoffice-form__label" for="childname"> No child classification </label>            
	            <input type="checkbox" name="nochild" value="true">	  
	         </fieldset>     	    
	         </c:if>     	    
	              	      	      	      		                                			                      			
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
