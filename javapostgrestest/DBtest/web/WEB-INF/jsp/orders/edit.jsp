<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>
<%@ page import="entities.Entity"%>
<%@ page import="entities.Items"%>
<%@ page import="entities.Delivery"%>
<%@ page import="entities.Orderstatus"%>


<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="orders" value="${entity}"/>
		<c:set var="title" value="Edit orders ${orders.id}"/>
		<c:set var="h1" value="Edit orders"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add orders"/>
		<c:set var="h1" value="New orders"/>
		<jsp:useBean id="orders" class="entities.Orders"/>
	</c:otherwise>
</c:choose>       

<u:backoffice-page h1="${h1}" title="${title}" js="/script/orderedit.js">	

     	 <c:url var="searchClientUrl" value="/client/list.json"/>
     	 <c:url var="searchItemsUrl" value="/baseitem/list.json"/>
		<script type="text/javascript">
			var clientUrl = '${searchClientUrl}';
			var itemsUrl = '${searchItemsUrl}';
		</script>

       <c:url var="saveUrl" value="save.html"/>
       <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${orders.id}">
			</c:if>				
        
          <c:if test="${orders.number>0}">
       	   <label for="number">order number: <span>${orders.number}</span></label>	
		  	<input type="hidden" id="number" name="number" value="${orders.number}">
			<br>
		  </c:if>	
		  			
			<label for="datetime">datetime:</label>	
			<input type="datetime" id="datetime" name="datetime" value="${orders.datetime}" disabled>
			<br>		
			
			<label for="dateexpired">dateexpired:</label>	
			<input type="date" id="dateexpired" name="dateexpired" value="${orders.dateexpired}">
			<br>
			
		 <fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="baseitem">Baseitem </label>               
           <select class="backoffice-form__text-input" id="baseitem" name="baseitem">
             <option value="0"></option>
             <c:forEach var="ibaseitem" items="${baseitem}">                                	  			   			     				
				    <c:choose>
					   <c:when test="${ibaseitem.id==orders.baseitem.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${ibaseitem.id}" ${selected}><span>${ibaseitem.item.name}</span> : <span>${ibaseitem.color.name}</span> :
				   <span>${ibaseitem.size.name}</span></option>				 
			  </c:forEach>			 			 
		   </select>
          </fieldset>   
                    	
		 <fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="client">Client </label>               
           <select class="backoffice-form__text-input" id="client" name="client">
             <option value="0"></option>
             <c:forEach var="iclient" items="${client}">	  			  			   			     
				    <c:choose>
					   <c:when test="${iclient.id == orders.client.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${iclient.id}" ${selected}><span>${iclient.user.name}</span> : <span>${iclient.phoneno}</span>: <span id=clientbonus>${iclient.bonuspoints}</span></option>					   					  	       
			  </c:forEach>			 			 
		   </select>
          </fieldset>                   
			
		<fieldset id="backoffice-edit-form-item">         
          <label class="backoffice-form__label" for="currency">Currency </label>               
           <select class="backoffice-form__text-input" id="currency" name="currency">
             <option value="0"></option>
             <c:forEach var="icurrency" items="${currency}">	  			  			   			     
				    <c:choose>
					   <c:when test="${icurrency.id == orders.currency.id}">
						   <c:set var="selected" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected"/>
					   </c:otherwise>
				   </c:choose>			
				   <option value="${icurrency.id}" ${selected}>${icurrency.name}</option>					   					  	       
			  </c:forEach>			 			 
		   </select>
          </fieldset>  		
			<br>		
			<label for="quantity">quantity:</label>	
			<input type="number" id="quantity" name="quantity" value="${orders.quantity}">
			
			<c:set var="totalprice" value="${orders.baseitem.baseprice}"/>
			<c:if test="${totalprice==0}">
			  <c:set var="totalprice" value="${orders.baseitem.item.baseprice}"/></c:if>
			
			<label for="price">Price:</label>	
			<input type="number" id="price" name="price" value="${totalprice}">
			<br/>
			<label for="discount">Discount:</label>	
			<input type="number" id="discount" name="discount" value="${orders.baseitem.item.discount}">
			<br/>
			<label for="maxdiscount">Maximum Discount:</label>	
			<input type="number" id="maxdiscount" name="maxdiscount" value="60">
			<br/>						  					
			<label for="maxbonuspoints">Client bonuspoints:</label>	
			<input type="text" id="maxbonuspoints" name="maxbonuspoints" value="${orders.client.bonuspoints}" disabled>			
			<p>Order bonuspoints: <span id="bonuspoints1"></span></p>	
			<input id="bonuspoints" type="hidden" name="bonuspoints">																		
			<div><strong>TOTAL SUM, <span>${orders.currency.name}</span>: <span id="totalsum"></span></strong>
			<input type="hidden" id="totalsum1" name="totalsum">
			</div>
													
			<br/>			
           <label for="delivery">Delivery: </label>			
			<select id="delivery" name="delivery">
		           <c:choose>
					   <c:when test="${orders.delivery == Delivery.POST}">						  
						   <option value="0" selected>POST</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="0">POST</option>
					   </c:otherwise>
				   </c:choose>		
				   <c:choose>
					   <c:when test="${orders.delivery == Delivery.COURIER}">						  
						   <option value="1" selected>COURIER</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="1">COURIER</option>
					   </c:otherwise>
				   </c:choose>								    													      
                   <c:choose>
					   <c:when test="${orders.delivery == Delivery.PICKUP}">						  
						   <option value="2" selected>PICKUP</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="2">PICKUP</option>
					   </c:otherwise>
				   </c:choose>			
			</select>
			<br>

            <label for="status">Orderstatus:</label>			
			<select id="status" name="status">				    													      
		         <c:choose>
					   <c:when test="${orders.status == Orderstatus.BASKET}">						  
						   <option value="0" selected>BASKET</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="0">BASKET</option>
					   </c:otherwise>
				   </c:choose>	
		        	<c:choose>
					   <c:when test="${orders.status == Orderstatus.ORDER}">						  
						   <option value="1" selected>ORDER</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="1">ORDER</option>
					   </c:otherwise>
				   </c:choose>			        	     														
			</select>
			<br>       
			
			<label for="active">Active status:</label>			
			<select id="active" name="active">				    													      		         					   
				<option value="true" selected>Active</option>
 			    <option value="false">Not Active</option>						        	     														
			</select>
			<br>       
			
			<button class="form__button" type="submit">Save</button>						
		</form>

</u:backoffice-page>