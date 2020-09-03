<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<%@ attribute name="css" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="js" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="keywords" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="image" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="robots" required="false" rtexprvalue="true" type="java.lang.String" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<%-- <%@ attribute name="head" required="false" rtexprvalue="true" type="java.lang.String" %> --%>
<%@ attribute name="myheader" required="false" rtexprvalue="true" type="java.lang.String" %> 
<%@ attribute name="sidemenu" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="sidemenu2" required="false" rtexprvalue="true" type="java.lang.String" %> 
<%@ attribute name="h1" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="canonical" required="false" rtexprvalue="true" type="java.lang.String" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<%--  <%@ attribute name="cart" required="true" rtexprvalue="true" type="java.lang.Integer" %> --%>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="description" required="true" rtexprvalue="true" type="java.lang.String" %>
<%-- /////////////////////////////////////////////////////////////////////////////////////////--%>
<%@ attribute name="simple" required="false" rtexprvalue="true" type="java.lang.Boolean" %>

<!DOCTYPE html>
<html>				
    <%-- <u:head title="${title}" css="${css}" js="${js}" keywords="${keywords}" image="${image}" canonical="${canonical}" 
        description="${description}" robots="${robots}">${head}</u:head>--%>
     
     	<head>
	    <meta charset="UTF-8">			
		<c:set var="mytitle" value="iShop - ${title}"/>  
		<title>${mytitle}</title>
	<%--	<title>iShop - <c:if test="${not empty title}">:: ${title}</c:if></title>
		<link rel="stylesheet" href="main.css" type="text/css">         --%>
		
        <c:url var="mainCssUrl" value="/main.css"/>
		<link rel="stylesheet" href="${mainCssUrl}" type="text/css">
		
		 <c:url var="addCssUrl" value="${css}"/>
		<c:if test="${not empty css}"><link rel="stylesheet" href="${addCssUrl}" type="text/css"></c:if>				
		<c:url var="mainJSUrl" value="/script/main.js"/>							
		<link type="text/javascript" src="${mainJSUrl}">		
		
		 <c:url var="addJSUrl" value="${js}"/>
		 <c:if test="${not empty js}"><script type="text/javascript" src="${addJSUrl}"></script></c:if>		 
		 
		  <c:url var="iconUrl" value="/favicon.ico"/>
		 
		 <link rel="icon" href= "${iconUrl}" type="/image/x-icon">
		 <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
        		 		 
    	<c:if test="${not empty canonical}"><link rel="canonical" href="${canonical}" /></c:if>
		<meta name="description" content="${description}">
		<c:if test="${empty robots}"><c:set var="robots" value="index,follow"/></c:if>					
		<meta name="robots" content="${robots}">		
		<meta name="keywords" content="${keywords}"> 		
		<meta property="og:description"	content="${description}">
		<meta property="og:url" content="${canonical}" />		
		
		<c:url var="addImgUrl" value="${image}"/>
		<c:if test="${not empty image}"><meta property="og:image" content="${addImgUrl}"/></c:if>	
			 
		 <meta property="og:title" content="${mytitle}" />
		 <meta property="og:type" content="website" />
		 <meta property="og:site_name" content="iShop - online shoes store" /> 
		 
		 <c:url var="searchJsonUrl" value="/items/search.json"/>
		<script type="text/javascript">
			var searchUrl = '${searchJsonUrl}';
		</script>
		<c:url var="searchJsUrl" value="/script/dynamic-search.js"/>
		<script type="text/javascript" src="${searchJsUrl}"></script>
		 
	</head>     
     
	<body>
	  <div class="wrapper">
	   
<%--  <u:header inshop="${cart}"> <c:if test="${not empty myheader}">${myheader}</c:if></u:header>--%>	   
	   <div class="header">
                <c:url var="ibag" value="/img/bag.png"/>
                <c:url var="iletter" value="/img/letter.png"/>
                <c:url var="inote" value="/img/note.png"/>
                <c:url var="ilock" value="/img/lock.png"/>
                <c:url var="iarrow" value="/img/arrow.png"/>
                <c:url var="ishoesm" value="/img/shoesm.png"/>
                
				<div class="site-title fleft">iShop - online shoes store</div>
				
		
				  <c:if test="${not empty myheader}">${myheader}</c:if>	
	
				<ul class="main-menu">
				
				  <c:url var="mainUrl" value="/index.html"/>
				   <c:url var="catalogUrl" value="/catalog/list.html"/>
				   <c:url var="contactUrl" value="/contact.html"/>
				   <c:url var="aboutUrl" value="/about.html"/>
				   <c:url var="termsUrl" value="/terms.html"/>
				   <c:url var="loginUrl" value="/login.html">
				     <c:param name="preious_uri" value="${pageContext.request.requestURI}"/>
				   </c:url>
			   				   				  
				
					<li class="main-menu__item"><a class="main-menu__item_link" href="${mainUrl}">
					<img src="${iarrow}">Main</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="${catalogUrl}">
					<img src="${ishoesm}">Catalog</a></li>												
					<li class="main-menu__item"><a class="main-menu__item_link" href="${contactUrl}">
					 <img src="${iletter}">Contacts</a></li>	
					<li class="main-menu__item"><a class="main-menu__item_link" href="${aboutUrl}">
					<img src="${inote}">About</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="${termsUrl}">
					<img src="${inote}">Terms</a></li>					
					
				  <c:choose>	 
				    <c:when test="${not empty sessionUser}">				 	  					
						<c:url var="logoutUrl" value="/logout.html"/>												 											
						<c:url var="basketUrl" value="/users/cart.html"/>	
						<c:url var="pageUrl" value="/users/page.html"/>				       			            
			            
			            <c:url var="useCartUrl" value="/users/cart.json"/>
		                 <script type="text/javascript">
			               var cartUrl = '${useCartUrl}' + '?' +'${sessionUser.id}'; 
		                 </script>
		                   <c:url var="userCartJsUrl" value="/script/user-cart.js"/>
		                 <script type="text/javascript" src="${userCartJsUrl}"></script>
						<li class="main-menu__item"><a class="main-menu__item_link" href="${pageUrl}">Profile</a></li>
						<li class="main-menu__item"><a class="main-menu__item_link" href="${logoutUrl}">Logout</a></li>						
						<li class="main-menu__item"><a class="main-menu__item_link" href="${basketUrl}">
					    <img src="${ibag}"><span id="pagecart">cart</span></a></li>						    					
				    </c:when>
				    <c:otherwise>					   
				      <li class="main-menu__item"><a class="main-menu__item_link" href="${loginUrl}">
					  <img src="${ilock}">login</a></li>				   
				    </c:otherwise>	
			      </c:choose>  					     
				    <c:if test="${empty simple or not simple}">
				     <li class="fright">
				      <form action="#">
				       <input id="search-text" type="text" name="search" list="search-text-datalist" autocomplete="off">
				       <datalist id="search-text-datalist"></datalist>
				       <button class="main-menu__item" type="submit"><span class="main-menu__item_link">Find</span></button>
				      </form> 
				    </li>
				   </c:if> 
				</ul>				
				<div class="end"></div>
</div>
	   	   
	   <u:sidemenu sidemenu2="${sidemenu2}"> </u:sidemenu>
  	 <!--  <c:if test="${not empty sidemenu}"><u:sidemenu2> </u:sidemenu2></c:if>  --> 		 		 
		 
  	     <div class="main">
			<div class="main-content">				     
			  <div class="fleft"><h1 class="page-title">
			     <c:if test="${not empty h1}">${h1}</c:if>	
			    </h1></div> 
			<%--  <div class="banner2"><img src="img/summershoes.png"></div> --%>
					   	        			
				<jsp:doBody/>
				
			</div> 
  	     </div>  
  	     	      
  	  <div class="pusher end"></div>
	  </div>
     <%-- <u:footer>  </u:footer>--%>
      <div class="footer">
		 <span>&copy; &laquo;iShop&raquo; - online shoes store</span>		 				 
		 <div class="fright"><span><a href="sitemap.xml">Sitemap</a></span></div>
      </div>
     
	</body>
</html>