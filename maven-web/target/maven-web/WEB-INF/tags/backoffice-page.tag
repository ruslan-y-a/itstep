<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<%@ attribute name="css" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="js" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="h1" required="false" rtexprvalue="true" type="java.lang.String" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<!DOCTYPE html>
<html>				
    <%-- <u:head title="${title}" css="${css}" js="${js}" keywords="${keywords}" image="${image}" canonical="${canonical}" 
        description="${description}" robots="${robots}">${head}</u:head>--%>
     
     	<head>
	    <meta charset="UTF-8">			
		<c:set var="mytitle" value="iShop backoffice - ${title}"/>  
		<title>${mytitle}</title>	        
        <c:url var="mainCssUrl" value="/backoffice1.css"/>
		<link rel="stylesheet" href="${mainCssUrl}" type="text/css">
			
			 <c:url var="addCssUrl" value="${css}"/>	
		<c:if test="${not empty css}"><link rel="stylesheet" href="${addCssUrl}" type="text/css"></c:if>		
			 <c:url var="addJSUrl" value="${js}"/>	
		 <c:if test="${not empty js}"><script type="text/javascript" src="${addJSUrl}"></script></c:if>		 
		 
		 <c:url var="iconUrl" value="/favicon.ico"/>
		 <link rel="icon" href= "${iconUrl}" type="image/x-icon">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">         		 		  					
		<meta name="robots" content="noindex,nofollow">				 														 
				 
</head>     
<body>
 <div class="wrapper">	   
	   <div class="header">
                               
   		  <div class="site-title fleft">iShop - online shoes store</div>				
		  <c:if test="${not empty myheader}">${myheader}</c:if>	
		  
		  		<c:if test="${not empty sessionUser}">
					<ul class="main-menu">
						<!-- TODO: add main menu -->
						<c:url var="logoutUrl" value="/logout"/>
						<li class="main-menu__item"><a class="main-menu__item_link" href="${logoutUrl}">Logout</a></li>
						<li class="end"></li>
					</ul>
				 </c:if>
				   <c:url var="backofficeUrl" value="/backoffice"/>
				   <c:url var="MainUrl" value="/index"/>
				 <ul class="main-menu">
				   <li class="main-menu__item"><a class="main-menu__item_link" href="${backofficeUrl}">Office</a></li>
				   <li class="main-menu__item"><a class="main-menu__item_link" href="${MainUrl}">Main</a></li>		
				 </ul>
				   <c:set var="user" value="${sessionUser}"/>  
			  	
				   <c:url var="itemsUrl" value="/items/list"/>
				   <c:url var="baseitemsUrl" value="/baseitem/list"/>				   		   
				   <c:url var="imgUrl" value="/img/list"/>
				   <c:url var="ordersUrl" value="/orders/list"/>
				   <c:url var="saleUrl" value="/sale/list"/>				   
				   	<c:url var="usersUrl" value="/users/list"/>
				   <c:url var="clientUrl" value="/client/list"/>
				   <c:url var="categoryUrl" value="/category/list"/>
				   <c:url var="classificationUrl" value="/classification/list"/>
				   <c:url var="colorUrl" value="/color/list"/>
				   <c:url var="sizeUrl" value="/size/list"/>
				   <c:url var="tagcloudUrl" value="/tagcloud/list"/>
				   <c:url var="currencyUrl" value="/currency/list"/>
				   <c:url var="countryUrl" value="/country/list"/>
				   <c:url var="webpagesUrl" value="/webpages/list"/>							   				
		  	
		   <div class="end"></div>
        </div>
<!--************************** -->  
<div class="side">   
   <div class="categories" >
       <sec:authorize access="hasRole('ADMIN')">       
        <ul id="admin" class="side-content">
          <li class="main-menu__item"><a class="main-menu__item_link" href="${usersUrl}">users</a></li>   
	    </ul>
	   </sec:authorize>
	    <sec:authorize access="hasAnyRole('ADMIN','MANAGER')"> 	    
          <ul id="manager" class="side-content">
            <li class="main-menu__item"><a class="main-menu__item_link" href="${clientUrl}">client</a></li>                                      
            <li class="main-menu__item"><a class="main-menu__item_link" href="${tagcloudUrl}">tagcloud</a></li>        
            <li class="main-menu__item"><a class="main-menu__item_link" href="${currencyUrl}">currency</a></li>
             <li class="main-menu__item"><a class="main-menu__item_link" href="${countryUrl}">country</a></li>        
            <li class="main-menu__item"><a class="main-menu__item_link" href="${webpagesUrl}">webpages</a></li>                                        
	     </ul>	
	    </sec:authorize>   
	    <sec:authorize access="hasAnyRole('PRODUCT','MANAGER','ADMIN')"> 	    
	      <ul id="product" class="side-content">
			<li class="main-menu__item"><a class="main-menu__item_link" href="${itemsUrl}">Items</a></li>
			<li class="main-menu__item"><a class="main-menu__item_link" href="${baseitemsUrl}">baseitems</a></li>
			<li class="main-menu__item"><a class="main-menu__item_link" href="${imgUrl}">img</a></li>		
			<li class="main-menu__item"><a class="main-menu__item_link" href="${colorUrl}">color</a></li>
            <li class="main-menu__item"><a class="main-menu__item_link" href="${sizeUrl}">size</a></li>  
            <li class="main-menu__item"><a class="main-menu__item_link" href="${categoryUrl}">Category</a></li>
            <li class="main-menu__item"><a class="main-menu__item_link" href="${classificationUrl}">Classification</a></li>            
		   </ul>		
        </sec:authorize>    			
	      <ul id="cashier" class="side-content">
	     <sec:authorize access="hasAnyRole('COURIER','MANAGER','ADMIN')"> 	
			  <li class="main-menu__item"><a class="main-menu__item_link" href="${ordersUrl}">orders</a></li>
         </sec:authorize>    					    	
         <sec:authorize access="hasAnyRole('CASHIER','MANAGER','ADMIN')">          		
			  <li class="main-menu__item"><a class="main-menu__item_link" href="${saleUrl}">sale</a></li>
         </sec:authorize>    					    								
	      </ul>			
	    
  </div>    
</div>
 <%------------------------------------------------------------------------------------ --%>     
  <div class="main">
			<div class="main-content">				     
			      <div class="fleft"><h1 class="page-title">
			        <c:if test="${not empty h1}">${h1}</c:if>	
			      </h1>
			      
			  <%--     <sec:authentication property="principal.name" />
			       <sec:authorize access="isAuthenticated()">
			      <span>access : ${access}</span>
			    </sec:authorize> 
			    <span>property : ${property}</span>		  --%>
			      
			      </div> 
			    <div class="pusher end"></div>
					   	        			
				<jsp:doBody/>
				
			</div> 
  </div>  
      
      
         <div class="pusher end"></div>
</div>      
      <div class="footer">
		 <span>&copy; &laquo;iShop&raquo; backoffice</span>		 				 		 
      </div>     
</body>
</html>