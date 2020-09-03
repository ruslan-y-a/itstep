<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="title" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="css" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="js" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="keywords" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="image" required="false" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="robots" required="false" rtexprvalue="true" type="java.lang.String" %>

<%@ attribute name="canonical" required="true" rtexprvalue="true" type="java.lang.String" %>
<%@ attribute name="description" required="true" rtexprvalue="true" type="java.lang.String" %>

	<head>
		<meta charset="UTF-8">			
		<c:set var="mytitle" value="${title}"/>  
		<title>iShop - online shoes store <c:if test="${not empty title}">:: ${title}</c:if></title>
		<link rel="stylesheet" href="main.css" type="text/css">        
        <c:url var="mainCssUrl" value="/main.css"/>
		<link rel="stylesheet" href="${mainCssUrl}" type="text/css">
		<c:if test="${not empty css}"><link rel="stylesheet" href="${css}" type="text/css"></c:if>		
		<c:url var="mainJSUrl" value="/script/main.js"/>						
		<link type="text/javascript" src="${mainJSUrl}">
		 <c:if test="${not empty js}"><script type="text/javascript" src="${js}"></script></c:if>		 
		 <link rel="icon" href= "favicon.ico" type="image/x-icon">
		 <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"> 
        
        <jsp:doBody/>
		 		 
    	<link rel="canonical" href="${canonical}" />
		<meta name="description" content="${description}">
		<c:if test="${empty robots}"><c:set var="robots" value="index,follow"/></c:if>					
		<meta name="robots" content="${robots}">		
		<meta name="keywords" content="${keywords}"> 		
		<meta property="og:description"	content="${description}">
		<meta property="og:url" content="${canonical}" />		
		
		<c:if test="${not empty image}"><meta property="og:image" content="${image}"/></c:if>	
			 
		 <meta property="og:title" content="${mytitle}" />
		 <meta property="og:type" content="website" />
		 <meta property="og:site_name" content="iShop - online shoes store" /> 
		 
	</head>


