<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="tagcloud" value="${entity}"/>
		<c:set var="title" value="Edit tagcloud ${tagcloud.id}"/>
		<c:set var="h1" value="New tagcloud"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add tagcloud"/>
		<c:set var="h1" value="Add tagcloud"/>
		<jsp:useBean id="tagcloud" class="entities.Tagcloud"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${tagcloud.id}">
			</c:if>							
			<br>
				
		    <select id="webpages" name="webpages"> <!-- class="form__text-input"  -->
			<c:forEach var="iwebpages" items="${webpages}">
				<c:choose>
					<c:when test="${iwebpages.id == tagcloud.webpages.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${iwebpages.id}" ${selected}>${iwebpages.title}</option>
			</c:forEach>
		    </select>			
		    	
		</br>
		  <fieldset id="backoffice-edit-form-item">         
            <label class="backoffice-form__label" for="classification">Classification </label>
            </br>                               
             <c:forEach var="allclass" items="${classification}">	  
			     <c:if test="${allclass.parentid>0}">
			       <c:set var="checked" value=""/> 
			       <c:forEach var="iitem" items="${tagcloud.classification}">			   			     				  		 		 				       				 
					   <c:if test="${allclass.id == iitem.id}">					      						  			
						  <c:set var="checked" value="checked"/>			  
					   </c:if>					   					  				   				    				   							  	        
			       </c:forEach>			       
			        <input id="iclassification" type="checkbox" name="classification" value="${allclass.id}" ${checked}>
			        <label class="backoffice-form__label" for="classification"><span>${allclass.parentname}</span> : <span>${allclass.name}</span></label> 
			       </br>
			    </c:if>
			 </c:forEach>	 			  		 
          </fieldset>    
				
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
