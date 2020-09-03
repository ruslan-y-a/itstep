<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="org.itstep.help.Params"%>

<c:choose>
	<c:when test="${not empty entity}">		
		<c:set var="webpages" value="${entity}"/>
		<c:set var="title" value="Edit webpages ${webpages.id}"/>
		<c:set var="h1" value="New webpages"/>
	</c:when>
	<c:otherwise>
		<c:set var="title" value="Add webpages"/>
		<c:set var="h1" value="Add webpages"/>
		<jsp:useBean id="webpages" class="org.itstep.entities.Webpages"/>
	</c:otherwise>
</c:choose>   

<u:backoffice-page h1="${h1}" title="${title}">				
								 				
		    <c:url var="saveUrl" value="save.html"/>
            <form action="${saveUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${webpages.id}">
			</c:if>							
			<br>
			<label for="url">url</label>			
		<!-- <input type="url" id="url" name="url" value="${webpages.url}">  -->
			 <textarea style="width:350px; height:50px;" class="form__text-input text-input-height" 
			 id="url" name="url">${webpages.url}</textarea>
			<br>
			<label for="title">title</label>			
		<!-- 	<input type="text" id="title" name="title" value="${webpages.title}">  -->
			<textarea style="width:350px; height:50px;" class="form__text-input text-input-height" 
			 id="title" name="title">${webpages.title}</textarea> 
			<br>			
			<label for="description">description</label>			
	<!--	<input type="text" id="description" name="description"  value="${webpages.description}"> -->
			<textarea style="width:350px; height:75px;" class="form__text-input text-input-height" 
			 id="description" name="description">${webpages.description}</textarea>
			<br>			
			<label for="keywords">keywords</label>			
<!--    	<input type="text" id="keywords" name="keywords" value="${webpages.keywords}"> -->
			<textarea style="width:350px; height:75px;" class="form__text-input text-input-height" 
			 id="keywords" name="keywords">${webpages.keywords}</textarea>
			<br>	
			<label for="h1">h1</label>			
<!--	    <input type="text" id="h1" name="h1" value="${webpages.h1}"> -->
			<textarea style="width:350px; height:75px;" class="form__text-input text-input-height" 
			 id="h1" name="h1">${webpages.h1}</textarea>
			<br>		
			<label for="text">text</label>			
<!--		<input type="text" id="text" name="text" value="${webpages.text}"> -->
			<textarea style="width:400px; height:250px;" class="form__text-input text-input-height" 
			id="text" name="text">${webpages.text}</textarea>
			<br>							
			
			<label for="entity">entity</label>			
	<!--	    <input type="text" id="entity" name="entity" value="${webpages.entity}"> -->		  		
			<select id="entity" name="entity">				    													      
		        <option value="${Params.WP_CATEGORY}">category</option>
		        <option value="${Params.WP_ITEMS}">items</option>	
				<option value="${Params.WP_TAGCLOUD}">tagcloud</option>											
			</select>
		     
			<br>
			<label for="entityid">entityid</label>			
    	    <input type="text" id="entityid" name="entityid" value="${webpages.entityid}"> 
			<br>	
				
			
			<label for="robots">robots:</label>			
			<select id="robots" name="robots">				    													      
		        <option value="0">index,follow</option>
		        <option value="1">noindex,nofollow</option>	
				<option value="2">noindex,follow</option>											
			</select>
			<br>
			<button class="form__button" type="submit">Save</button>
		</form>
		
</u:backoffice-page>
