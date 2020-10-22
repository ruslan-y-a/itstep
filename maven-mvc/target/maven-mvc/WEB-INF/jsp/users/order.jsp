<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.List"%>

<c:set var="canonical" value="/users/order"/>  <%-- req.getContextPath() --%>
<c:set var="description" value="users order"/>  
<c:set var="h1" value="users order"/>

<u:page title="Thank you!" description="${description}" canonical="${canonical}" h1="${h1}">
                
  <div class="end"></div>
 <h2>User : <span>${sessionUser.name}</span></h2> 
 
     <h2>Select Action</h2>
   
   <c:url var="uncheckoutUrl" value="/users/uncheckout.html"/>
   <c:url var="cartUrl" value="/users/cart.html"/>
   <p><button class="form__button"><a href="${cartUrl}">To Cart</a></button></p>
 <form action="${uncheckoutUrl}" method="post">    

        <h2>Orders Info</h2>
     <c:forEach var="corder" items="${list}">
             <c:set var="phone" value="${corder.client.phoneno}"/>
             <c:set var="address" value="${corder.client.address}"/>
             <c:set var="delivery" value="${corder.delivery}"/>
             <c:set var="clientid" value="${corder.client.id}"/>
        	      <div class="item">
        	         
        	         <h2> ${corder.baseitem.item.name}<span> : </span><span>${corder.baseitem.item.articul}</span> : <span>${corder.number}</span></h2>
        	         <input type="checkbox" name="ordersid" value="${corder.id}">
        	         <c:url var="imgUrl" value="${corder.baseitem.item.get1ImgUrl()}"/>        	
				      <div class="banner">
				       <div style="margin:15px;"><img class="banner__photo" src="${imgUrl}"></div>				  
						<div>Model <strong id="model">${corder.baseitem.item.model}</strong></div>
					    <div>Color <strong id="color">${corder.baseitem.color.name}</strong></div>						   
					    <div>Size <strong id="size">${corder.baseitem.size.name}</strong></div>
					    <div id="category">${corder.baseitem.item.category.name}</div>
					    <div>
		                     <c:forEach var="classif" items="${corder.baseitem.item.classification}">  		                                        		   		       		           
                              ${classif.name}</br>                                        
                            </c:forEach>  		   		   
		                </div>
		                 <div>Sum: <span>${corder.sum}</span></div>
		                 <div>Currency: <span>${corder.currency.name}</span></div>
		                 <div>Quantity: <span>${corder.quantity}</span></div>
		                 		                 
		              </div>  		               									                                   				                    			                                 	                        	                        						                     				
			       </div>	
			         <c:url var="deleteUrl" value="/users/deactiveorder.html">
				       <c:param name="id" value="${corder.id}"/>				     
			        </c:url>
			       <div><p><a class="form__button_danger" href="${deleteUrl}">delete order</a></p></div>			       
                                      
                    <div class="end"></div>        
  
     </c:forEach> 
                       
                    <h2>Client Info</h2>
               
                       <fieldset id="backoffice-edit-form-item">
		 	            <label for="phoneno">phoneno</label>			
			             <input type="tel" id="phoneno" name="phoneno" value="${phone}">  
		            </fieldset>	
		             <fieldset id="backoffice-edit-form-item">
		 	              <label for="address">address</label>			
			              <input type="text" id="address" name="address" value="${address}">
		             </fieldset> 
		                
		                 <div>
						    <label for="delivery">Delivery: </label>			
			                <select id="delivery" name="delivery">
		           <c:choose>
					   <c:when test="${corder.delivery == Delivery.POST}">						  
						   <option value="0" selected>POST</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="0">POST</option>
					   </c:otherwise>
				   </c:choose>		
				   <c:choose>
					   <c:when test="${corder.delivery == Delivery.COURIER}">						  
						   <option value="1" selected>COURIER</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="1">COURIER</option>
					   </c:otherwise>
				   </c:choose>								    													      
                   <c:choose>
					   <c:when test="${corder.delivery == Delivery.PICKUP}">						  
						   <option value="2" selected>PICKUP</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="2">PICKUP</option>
					   </c:otherwise>
				   </c:choose>			
			             </select>
		                </div>
		 	  		 	                             
              <input type="hidden" name="clientid" value="${clientid}">   
             <p><button class="form__button_danger" type="submit">Move to cart</button></p>               
 </form>
 
 
</u:page>