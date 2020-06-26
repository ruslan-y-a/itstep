<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
   <jsp:useBean id="now" class="java.util.Date"/> 

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="client" value="${entity}"/>
		<c:set var="title" value="Edit client ${client.id}"/>
		<c:set var="h1" value="New client"/>
		<c:set var="selection" value="1"/>			   	   
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add client"/>
		<c:set var="h1" value="Add client"/>
		<jsp:useBean id="client" class="entities.Client"/>
	</c:otherwise>
</c:choose>  

          
<u:backoffice-page h1="${h1}" title="${title}">                 			
		   <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${client.id}">
			</c:if>							
			<br>
									  						
		   <fieldset id="backoffice-edit-form-item">
             <label class="backoffice-form__label" for="country">Country </label>               
           <select class="backoffice-form__text-input" id="country" name="country">
              <option value="0"></option>
			  <c:forEach var="icountry" items="${country}">			   			     
				   <c:choose>
					   <c:when test="${icountry.id == client.country.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${icountry.id}" ${selected}>${icountry.name}</option>			        
			  </c:forEach>			  
		  </select>
          </fieldset>    
          
          <fieldset id="backoffice-edit-form-item">
		 	<label for="address">address</label>			
			<input type="text" id="address" name="address" value="${client.address}">  
		  </fieldset>  		
		 
 <c:choose>
	<c:when test="${empty client.creationdate}">		
	      <c:set var="tnow" value="${now}"/>	  			   	   
	</c:when>
	<c:otherwise>
		 <c:set var="tnow" value="${client.creationdate}"/>
	</c:otherwise>
</c:choose>  
	<!-- 	  <fmt:formatDate type="time" value="${tnow}" pattern="dd.MM.yyyy HH:mm:ss"/>  -->
		  
		  <fieldset id="backoffice-edit-form-item">
		 	<label for="creationdate">creationdate <span>${client.creationdate}</span></label>			
			<input type="date" id="creationdate" name="creationdate" value="${tnow}">   <!-- type="date" -->
		  </fieldset> 	
		  
		  <fieldset id="backoffice-edit-form-item">
		 	<label for="bonuspoints">bonuspoints</label>			
			<input type="number" id="bonuspoints" name="bonuspoints" value="${client.bonuspoints}">  
		  </fieldset> 	
		  <fieldset id="backoffice-edit-form-item">
		 	<label for="phoneno">phoneno</label>			
			<input type="tel" id="phoneno" name="phoneno" value="${client.phoneno}">  
		  </fieldset>	
		  	
			 <fieldset id="backoffice-edit-form-item">
             <label class="backoffice-form__label" for="user">User </label>               
           <select class="backoffice-form__text-input" id="user" name="user">                      
              <c:forEach var="allusers" items="${users}"> 			  		
                 <c:if test="${allusers.role=='CLIENT'}">	   			    				  
				   <c:choose>
					   <c:when test="${allusers.id == client.user.id}"> 
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${allusers.id}" ${selected}>${allusers.name}</option>	
				 </c:if>   		        
			  </c:forEach>			  
		  </select>
          </fieldset>    			

          <fieldset id="backoffice-edit-form-item">
             <h3>recentitems </h3>                                      
             <c:forEach var="allitem" items="${items}">	            
                 <c:set var="ichecked" value="1"/>  
			    <c:forEach var="iitem" items="${client.recentitems}">			   			     				   
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
			    	  
			    <input id="recentitems" type="checkbox" name="recentitems" value="${allitem.id}" ${checked}>
			    <label class="backoffice-form__label">${allitem.name}</label> </br>
			 </c:forEach>	 			  		  
          </fieldset>    			

	              	      	      	      		                                			
			<button class="form__button" type="submit">Save</button>
		</form>
		
			
		
</u:backoffice-page>
