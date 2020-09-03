<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>


<c:set var="canonical" value="/product/list.html"/>  <%-- req.getContextPath() --%>  
<c:set var="h1" value="${item.webpages.h1}"/>
<c:set var="mytitle" value="${item.webpages.title}"/>
<c:set var="mydesc" value="${item.webpages.description}"/>

<u:page title="${mytitle}" description="${mydesc}">
        
       	<c:url var="orderJsUrl" value="/script/productlist.js"/>
		<script type="text/javascript" src="${orderJsUrl}"></script>
        
        <div><span id="name">${item.name}</span></div> 	
        
        <c:if test="${empty item}">
          <p>Item is not found</p>
        </c:if> 
    
      <c:set var="totalsum" value="${item.baseprice * (100-item.discount)/100}"/>      
          <div><strong>Currency</strong>           
         <select class="backoffice-form__text-input" id="currency" name="currency">
             <c:forEach var="icurrency" items="${currencies}">
                   <c:choose>
					   <c:when test="${param.currency==icurrency.id}">
						   <c:set var="selected3" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected3"/>
					   </c:otherwise>
				   </c:choose>		
             	  	
                    <c:url var="curencyUrl" value="/product/list.html">                       		  			   			     				                                               
                       <c:param name="currency" value="${icurrency.id}"/>
                       <c:if test="${not empty param.id}"><c:param name="id" value="${item.id}"/></c:if>                       
                     </c:url>		  			   			     				    
				    <option value="${curencyUrl}" ${selected3}>${icurrency.name}</option>		                                              				   			   					  	      
			  </c:forEach>	                           	
         </select>
     </div>
                    <c:url var="orderUrl" value="/product/order.html">
			     	   <c:param name="id" value="${item.id}"/>
			         </c:url>	                   	 
		<!-- ///////////////////////////////////////////////////////////////////////////////////////// -->
		<!-- ///////////////////////////////////////////////////////////////////////////////////////// -->
		<c:if test="${empty list}"> <strong><h2>Item is not available. </h2></strong></c:if>
		         
         	         
                <form action="${orderUrl}" method="post">	 
                	 <c:url var="imgUrl1" value="${item.myimg}"/>                	 
				    <input type="hidden" id="ordercurrency" name="ordercurrency" value="${param.currency}">				 
				    				
					<div class="banner">						
					 <div style="margin:10px;">
						<img class="banner__photo" src="${imgUrl1}">							
						<div>Articul <strong id="articul">${item.articul}</strong> Model <strong id="model">${item.model}</strong></div>																				
						<div class="product__price">Price: <span id="price">${item.baseprice}</span></div>
						<div class="product__price">
						Price discount: <span id="pricediscount">${totalsum}</span>
					<!-- 	 <fmt:formatNumber value="${item.baseprice * (100-item.discount)/100}" type="number" pattern="#"/>  -->
						</div>	
											      
					  </div>     		
					</div>									 					 
					
					<div class="banner">	
					  <div style="margin:10px;">										    																																   					   
					    <div id="classif">
                           <ul class="noli">
		                     <c:forEach var="classif" items="${item.classification}">  		                                        		   		       		           
                                <li>${classif.parentname} : ${classif.name}</li>                                        
                             </c:forEach>  		   		   
		                   </ul>
                        </div>												
		                	
					  </div>  						   	                 	  					                       
					</div>
					
					<div class="end"></div>
					
		<c:if test="${not empty list}">			
					
					<h3>Order Details</h3>
					     <div><label for="quantity">quantity</label><input type="number" id="quantity" name="quantity" value="1"> 						 						   
					     <strong>sum: </strong>
					     <input type="number" id="sum" name="sum" value="${totalsum}" disabled></div>	
					     
					       <!-- <label for="sum">sum</label> <input type="text" id="sum" name="sum" value="${totalsum}"> 	-->
                        <c:if test="${client.bonuspoints>0}">
                         <div>client bonuspoints :<span id="bonuspoints">${client.bonuspoints}</span>					    					      
					      <label for="usebonuspoints">Use bonuspoints</label>
					      <input type="checkbox" id="usebonuspoints" name="usebonuspoints" value="true">						   
					      <label for="usedbonuspoints">used bonuspoints :</label>
					      <input type="number" id="usedbonuspoints" name="usedbonuspoints" value="${client.bonuspoints}" disabled>					    
					    </div>
					   </c:if> 
					 <div>
						<select id="baseitem" name="baseitem">
		                   <c:forEach var="baseitem" items="${list}">
		                     <option value="${baseitem.id}">
		                         <span>Color: </span>		                         
		                         <span id="color">${baseitem.color.name}</span>
		                         <span>Size: </span>
		                         <span id="size">${baseitem.size.name}</span>
		                         <span>Price: </span>
                                 <span id="baseprice">${baseitem.baseprice}</span>  
                             </option>  	                                 		   		       		                                                                                             
                            </c:forEach>			              			               			              
		                 </select>	
					
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
						   <option value="2" selected>PICKUP (5% discount)</option>	
					   </c:when>
					   <c:otherwise>
						   <option value="2">PICKUP</option>
					   </c:otherwise>
				   </c:choose>			
			      </select>
			      <button class="form__button" type="submit">To Cart</button>
			     </div>		
		 </c:if>		     
			    </form>
			    
	 	    
	<!-- ///////////////////////////////////////////////////////////////////////////////////////// -->
	<!-- ///////////////////////////////////////////////////////////////////////////////////////// -->		    
			        <div class="end"></div> 
			        <p></p>    				        
					<div id="description">${item.webpages.text}</div>
					<p></p>
					<c:set var="num" value="${0}"/>	
			        <div id="images">			           		       
		                  <c:forEach var="imgs" items="${item.img}">
		                     <c:set var="num" value="${num+1}"/>		                 
		                     <c:url var="imgUrl" value="${imgs.url}"/>	
		                     <c:if test="${not empty imgUrl}">          	                     		
		                      <div  id="img${num}" class="banner">                                      		   		       		                                         
                                <div class="margin"><img class="banner__photo" src="${imgUrl}"></div>
                              </div>                   
                             </c:if>                                        
                          </c:forEach>  	                 	  
		               			          
					</div>
             
</u:page>