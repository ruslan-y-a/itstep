<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>


<c:set var="canonical" value="/catalog/search.html"/>  <%-- req.getContextPath() --%>
<c:set var="description" value="iShop - online catalog"/>  
<c:set var="h1" value="iShop - online shoes store"/>

<u:page title="online shoes store. Buy here!" description="${description}" canonical="${canonical}" h1="${h1}" sidemenu2="sidemenu2"> <!-- sidemenu2="sidemenu2" -->
         </br>
         <c:url var="catalogUrl1" value="/catalog/search.html">
           <c:param name="sort" value="NAMEASC"/>
           <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
           <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
           <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
           <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
          </c:url>
          <c:if test="${param.sort=='NAMEASC'}"><c:set var="selected1" value="selected"/></c:if>
          <c:url var="catalogUrl2" value="/catalog/search.html">
             <c:param name="sort" value="NAMEDESC"/>
             <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
             <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
             <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
             <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
          </c:url>
            <c:if test="${param.sort=='NAMEDESC'}"><c:set var="selected2" value="selected"/></c:if>
          <c:url var="catalogUrl3" value="/catalog/search.html">
            <c:param name="sort" value="PRICEASC"/>
            <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
            <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
            <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
            <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
         </c:url>
           <c:if test="${param.sort=='PRICEASC'}"><c:set var="selected3" value="selected"/></c:if>
       <c:url var="catalogUrl4" value="/catalog/search.html">
         <c:param name="sort" value="PRICEDESC"/>
         <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
         <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
         <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
         <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
       </c:url>
        <c:if test="${param.sort=='PRICEDESC'}"><c:set var="selected4" value="selected"/></c:if>       
      
       <c:url var="catalogUrl1p" value="/catalog/search.html">
          <c:param name="itemsonpage" value="20"/>
          <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
          <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
          <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
          <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
         </c:url>
          <c:if test="${param.itemsonpage=='20'}"><c:set var="selected21" value="selected"/></c:if>
       <c:url var="catalogUrl2p" value="/catalog/search.html">
          <c:param name="itemsonpage" value="40"/>
          <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
          <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
          <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
          <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
         </c:url>
         <c:if test="${param.itemsonpage=='40'}"><c:set var="selected22" value="selected"/></c:if>
       <c:url var="catalogUrl3p" value="/catalog/search.html">
          <c:param name="itemsonpage" value="60"/>
          <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
          <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
          <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
          <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
         </c:url>
          <c:if test="${param.itemsonpage=='60'}"><c:set var="selected23" value="selected"/></c:if>        
         
		<c:url var="listscript" value="/script/listscript.js"/>
		<script type="text/javascript" src="${listscript}"></script>		 

   <p></p><p></p>
    <div><strong>Sorting:</strong>  
      <select class="backoffice-form__text-input" id="sort" name="sort">
          <option></option>                             
          <option value="${catalogUrl1}" ${selected1}>Name asc</option>
          <option value="${catalogUrl2}" ${selected2}>Name desc</option>
          <option value="${catalogUrl3}" ${selected3}>Price asc</option>                            
          <option value="${catalogUrl4}" ${selected4}>Price desc</option>	
       </select>         
             
     <strong>Items on page</strong>
       <select class="backoffice-form__text-input" id="itemsonpage" name="itemsonpage">       
          <option></option>
          <option value="${catalogUrl1p}" ${selected21}>20</option>
          <option value="${catalogUrl2p}" ${selected22}>40</option>                            
          <option value="${catalogUrl3p}" ${selected23}>60</option>	
       </select>
            
      <strong>Currency</strong>           
         <select class="backoffice-form__text-input" id="currency" name="currency">
            <option></option>
             <c:forEach var="icurrency" items="${currencies}">
                   <c:choose>
					   <c:when test="${param.currency==icurrency.id}">
						   <c:set var="selected3" value="selected"/>
					   </c:when>
					   <c:otherwise>
						   <c:remove var="selected3"/>
					   </c:otherwise>
				   </c:choose>		
             	  	
                    <c:url var="curencyUrl" value="/catalog/search.html">
                      <c:if test="${not empty param.itemsonpage}"> <c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
                      <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                      <c:param name="currency" value="${icurrency.id}"/>
                      <c:if test="${not empty param.page}"><c:param name="page" value="${param.page}"/></c:if>
                      <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
                     </c:url>		  			   			     				    
				    <option value="${curencyUrl}" ${selected3}>${icurrency.name}</option>					   					  	       
			  </c:forEach>	                           	
         </select>
     </div>
     <div class="end"></div>
           
           <c:url var="catalogPageUrlPrev" value="/catalog/search.html">
             <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
             <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
             <c:if test="${not empty param.page}"><c:param name="page" value="${param.page-1}"/></c:if>
             <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>  
             <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>           
           </c:url>
           		   
           <div id="navigation fleft">	
           
            <c:if test="${param.page>1}">
              <span id="prev"><a href="${catalogPageUrlPrev}" rel="prev">Previous</a> </span><span> </span>            
             </c:if> 

	        <c:if test="${not empty list}">		
	              
		      <c:url var="catalogPageUrlNext" value="/catalog/search.html">
                 <c:if test="${not empty param.itemsonpage}"><c:param name="itemsonpage" value="${param.itemsonpage}"/></c:if>
                 <c:if test="${not empty param.sort}"><c:param name="sort" value="${param.sort}"/></c:if>
                 <c:if test="${not empty param.page}"><c:param name="page" value="${param.page+1}"/></c:if>
                 <c:if test="${empty param.page}"><c:param name="page" value="2"/></c:if>
                 <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
                 <c:if test="${not empty param.search}"><c:param name="search" value="${param.search}"/></c:if>
              </c:url>		   		
              <span id="next"><a href="${catalogPageUrlNext}"  rel="next">Next</a> </span><span> </span>
	       </c:if>	      	          	          
         </div>
              
             <div id="itemlist">
             
              <c:forEach var="item" items="${list}">
                    <c:set var="itemart" value="${item.articul}"/>
                    <div class="item">
                        <c:url var="orderUrl" value="/product/order.html">
			     	       <c:param name="id" value="${item.id}"/>
			            </c:url>			            
                  <!--   <form action="${orderUrl}" method="post">  -->
                        <div class="banner"> 
						  <h3 style="margin-left:15px;">${item.name}</h3>					
						  <c:url var="imgUrl" value="${item.myimg}"/>
		                  <div style="margin:15px;">     	                                 		   		       		                                         
                            <img alt="${item.name}" class="product__photo" src="${imgUrl}"></div>  
						   <div><span>${itemart}</span></div>
						   <div class="product__price1">Price: <span>${item.baseprice}</span></div>
						   <div class="product__price">
						     Price discount: <fmt:formatNumber value="${item.baseprice * (100-item.discount)/100}" type="number" pattern="#"/>
						   </div>
						    <div> ${item.category.name}</div>																
						     <c:url var="productUrl" value="/product/list.html">
			     	            <c:param name="id" value="${item.id}"/>
			     	            <c:if test="${not empty param.currency}"><c:param name="currency" value="${param.currency}"/></c:if>
			                 </c:url>						     
						<div><button class="form__button"><a href="${productUrl}">Look</a></button></div>						
					 <!--	<div><button class="form__button" type="submit">To Cart</button></div>  -->	
                       </div>
				 <!--	  </form>			 -->		  	
					</div>
             </c:forEach>  
            </div>
            <div class="end"></div>
            <div class="fright">page<span id="page">${param.page}</span></div>
</u:page>