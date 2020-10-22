<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import="org.itstep.entities.Orderstatus"%>


<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="sale" value="${entity}"/>
		<c:set var="title" value="Edit sale ${sale.id}"/>
		<c:set var="h1" value="Edit sale"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add sale"/>
		<c:set var="h1" value="New sale"/>
		<jsp:useBean id="sale" class="org.itstep.entities.Sale"/>
	</c:otherwise>
</c:choose>       

<u:backoffice-page h1="${h1}" title="${title}">	

       <c:url var="saveUrl" value="save.html"/>
       <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${sale.id}">
			</c:if>		
			
			<input type="hidden" name="orderid" value="${sale.order.id}">						       	   
			
			<label for="datetime">datetime:</label>	
		<!-- 	<input type="text" id="datetime" name="datetime" value="${sale.datetime}"> -->
			<input type="text" id="datetime" name = "datetime" value = "<fmt:formatDate value="${sale.datetime}" pattern="yyyy-MM-dd, HH:mm:ss"/>"/>
			<br>				
			
		 <fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="orders">Orders </label>               
           <select class="backoffice-form__text-input" id="orders" name="orders">
             <option value="0"></option>
             <c:forEach var="iorders" items="${orders}">	
               	<c:if test="${iorders.status==Orderstatus.ORDER && iorders.active==true}">		  			   			     				 
				    <c:choose>
					   <c:when test="${iorders.id == sale.order.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${iorders.id}" ${selected}><span>${iorders.number}</span>-				      
				       <span><fmt:formatDate value="${iorders.datetime}" pattern="yyyy-MM-dd"/></span>-				       
				       sum: <span>${iorders.sum}</span>-				 
				       currency: <span>${iorders.currency.id}</span></option>				       
				 </c:if>	      				      
			  </c:forEach>			 			 
		   </select>
          </fieldset>    
			
		<h3>Not Active orders</h3>	
		 <fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="orders1">Orders </label>               
           <select class="backoffice-form__text-input" id="orders1" name="orders1">
             <option value="0"></option>
             <c:forEach var="iorders1" items="${orders}">	
               	<c:if test="${iorders1.status==Orderstatus.SOLD}">		  			   			     				 
				    <c:choose>
					   <c:when test="${iorders1.id == sale.order.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${iorders1.id}" ${selected}><span>${iorders1.number}</span>-				      
				       <span><fmt:formatDate value="${iorders1.datetime}" pattern="yyyy-MM-dd"/></span>-				       
				       sum: <span>${iorders1.sum}</span>-				 
				       currency: <span>${iorders1.currency.id}</span></option>				       
				 </c:if>	      				      
			  </c:forEach>			 			 
		   </select>
          </fieldset>   	
			
		  <label for="returned">returned status:</label>			
			<select id="returned" name="returned">				    													      		         					   
				<option value="false" selected>False</option>
 			    <option value="true">True</option>						        	     														
			</select>
			<br>    
			
			<label class="form__label" for="currency">currency:</label>
		    <select class="form__text-input" id="currency" name="currency">
			<c:forEach var="icurrency" items="${currency}">
				<c:choose>
					<c:when test="${icurrency.id == sale.currency.id}">
						<c:set var="selected" value="selected"/>
					</c:when>
					<c:otherwise>
						<c:remove var="selected"/>
					</c:otherwise>
				</c:choose>
				<option value="${icurrency.id}" ${selected}>${icurrency.name}</option>
			</c:forEach>
		    </select>										
			
			<button class="form__button" type="submit">Save</button>						
		</form>

</u:backoffice-page>