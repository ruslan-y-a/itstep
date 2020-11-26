<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import ="org.itstep.entities.Role"%>
<%-- 
 String path=req.getContextPath();
--%> 

<c:set var="description" value="iShop - online shoes store. Bue men's and wmen's shoes, boots, slippers, flip flops here."/>  
<c:set var="h1" value="iShop - online shoes store"/>

<u:backoffice-page title="online shoes store. Buy here!" h1="${h1}">

<p>Welcome to the backoffice</p>
 <c:url var="saveUrl" value="/upload.html"/>
 
 <form:form method="POST" action="${saveUrl}" enctype="multipart/form-data">
     <input type="file" id="file"  name="file">				
	 <button class="form__button" type="submit">Save</button>	
 </form:form>
 
</u:backoffice-page>