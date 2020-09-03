<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>



<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="items" value="${entity}"/>
		<c:set var="title" value="Edit Item ${items.id}"/>
		<c:set var="h1" value="Edit Item"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add Item"/>
		<c:set var="h1" value="New Item"/>
		<jsp:useBean id="items" class="org.itstep.entities.Items"/>
	</c:otherwise>
</c:choose>       

<u:backoffice-page h1="${h1}" title="${title}">	

       <c:url var="saveUrl" value="save.html"/>
       <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${items.id}">
			</c:if>				


        <fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="category">Category </label>               
           <select class="backoffice-form__text-input" id="category" name="category">
             <option value="0"></option>
             <c:forEach var="allitem" items="${category}">	  			  			   			     
				    <c:choose>
					   <c:when test="${allitem.id == items.category.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${allitem.id}" ${selected}>${allitem.name}</option>					   					  	       
			  </c:forEach>		 			 
		  </select>
          </fieldset>    
				
		<fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="webpages">webpages </label>               
           <select class="backoffice-form__text-input" id="webpages" name="webpages">
             <option value="0"></option>
             <c:forEach var="allitem" items="${webpages}">	  			  			   			     
				    <c:choose>
					   <c:when test="${allitem.id == items.webpages.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${allitem.id}" ${selected}>${allitem.title}</option>					   					  	       
			  </c:forEach>		 			 
		  </select>
          </fieldset>  
			
			<br>
			<label for="articul">articul: </label>
			<input type="text" id="articul" name="articul" value="${items.articul}">	
			<br>
			<label for="model">model:</label>
			<input type="text" id="model" name="model" value="${items.model}">
			<br>			
			<label for="baseprice">baseprice:</label>	
			<input type="number" id="baseprice" name="baseprice" value="${items.baseprice}">
			<br>
			<label for="discount">discount:</label>	
			<input type="number" id="discount" name="discount" value="${items.discount}">
			<br>
			<label for="name">name:</label>
			<input type="text" id="name" name="name" value="${items.name}">
			<br>			
			<label for="active">active:</label>
		     	<c:if test="${items.active==true}">
			         <c:set var="achecked" value="checked"/>
			   </c:if>
			<input id="active" type="checkbox" name="active" value="${items.active}" ${achecked}>				
			<br>						
						
		<fieldset id="backoffice-edit-form-item">
             <h3>Classification </h3>                                      
             <c:forEach var="allitem" items="${classification}">	            
               <c:if test="${allitem.parentid>0}">  
                 <c:set var="ichecked" value="1"/>  
			       <c:forEach var="iitem" items="${items.classification}">			   			     				   
					   <c:if test="${iitem.id == allitem.id}">					     
						   <c:set var="ichecked" value="0"/>									  			 
					   </c:if>					   							  	        
			       </c:forEach>
			          <c:choose>
					   <c:when test="${ichecked == 0}">					     
						   <c:set var="checked" value="checked"/>									  			 
					   </c:when>
					   <c:otherwise>
						  <c:remove var="checked"/>
					   </c:otherwise>
					  </c:choose> 
			    	  
			    <input id="classification" type="checkbox" name="classification" value="${allitem.id}" ${checked}>
			    <label class="backoffice-form__label">${allitem.name}</label> </br>
			  </c:if>    
			 </c:forEach>	 			  		  
          </fieldset>    	
			
		<fieldset id="backoffice-edit-form-item">
             <h3>img </h3>                                      
             <c:forEach var="allitem" items="${img}">	            
                 <c:set var="ichecked" value="1"/>  
			    <c:forEach var="iitem" items="${items.img}">			   			     				   
					   <c:if test="${iitem.id == allitem.id}">					     
						   <c:set var="ichecked" value="0"/>									  			 
					   </c:if>					   							  	        
			   </c:forEach>
			          <c:choose>
					   <c:when test="${ichecked == 0}">					     
						   <c:set var="checked" value="checked"/>									  			 
					   </c:when>
					   <c:otherwise>
						  <c:remove var="checked"/>
					   </c:otherwise>
					  </c:choose> 
			    	  
			    	  <c:url var="imgUrl" value="${allitem.url}"/>
			    <input id="img" type="checkbox" name="img" value="${allitem.id}" ${checked}>
			    <label class="backoffice-form__label"><img style="width:100px;" src="${imgUrl}"/></label> </br>
			 </c:forEach>	 			  		  
          </fieldset>    		
          		
			
			<button class="form__button" type="submit">Save</button>			
		<!--  	<p><input  type="checkbox" name="addparams" value="true" checked>wanna edit params?</p> -->
		</form>

</u:backoffice-page>