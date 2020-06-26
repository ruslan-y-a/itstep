<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="category" value="${entity}"/>
		<c:set var="title" value="Edit category ${category.id}"/>
		<c:set var="h1" value="New category"/>
	<!--  	<c:set var="selection" value="1"/>-->			   	   
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add category"/>
		<c:set var="h1" value="Add category"/>
		<jsp:useBean id="category" class="entities.Category"/>
	</c:otherwise>
</c:choose>  

          
<u:backoffice-page h1="${h1}" title="${title}">                 			
		    <c:url var="saveUrl" value="save.html"/>
            <form id="form1" class="backoffice-edit-form" action="${saveUrl}" method="post">           
      	        
	    <h2 class="backoffice-form__label">Select Parent category</h2> 	        	     	             
    	<select class="backoffice-form__text-input" id="parentid2" name="parentid">
			<option value="0"></option>
			<c:forEach var="categoryparent" items="${list}">
			   
			     <c:if test="${categoryparent.parentid == 0 }">	      			   	             
				   <c:choose>
					   <c:when test="${categoryparent.id == category.parentid}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${categoryparent.id}" ${selected}>${categoryparent.name}</option>
			     </c:if>	   			  
			</c:forEach>
		</select>		
        
        <h2>webpages</h2>
        
       <fieldset id="backoffice-edit-form-item">
          <label class="backoffice-form__label" for="webpages">webpages </label>               
           <select class="backoffice-form__text-input" id="webpages" name="webpages">
              <option value="0"></option>
			  <c:forEach var="iwebpages" items="${webpages}">			   
			     
				   <c:choose>
					   <c:when test="${category.webpages!=null && iwebpages.id == category.webpages.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${iwebpages.id}" ${selected}>${iwebpages.title}</option>			        
			  </c:forEach>
			  
		  </select>
        </fieldset>    
      	  <h2 class="backoffice-form__label">${title}</h2> 	
       				        
	         <fieldset id="backoffice-edit-form-item"> 
	          <label class="backoffice-form__label" for="childname"> Category name </label>            
	          <input class="backoffice-form__text-input" type="text" id="childname" name="name" value="${category.name}"> 	  
	         </fieldset>                
	          <input class="backoffice-form__text-input" type="hidden" id="childid" name="id" value="${category.id}">  
	     
	       
	         <c:if test="${category.parentid==0 || category.parentid==category.id}">  <!--  && category.parentid!=category.id -->			        			  
				 <fieldset id="backoffice-edit-form-item"> 
	                <label class="backoffice-form__label" for="childname"> No child categories </label>            
	                <input type="checkbox" name="nochild" value="true">	  
	             </fieldset>   
		     </c:if>		
	      	            	     
	              	      	      	      		                                			
			<button class="form__button" type="submit">Save</button>
		</form>
		
			
		
</u:backoffice-page>
