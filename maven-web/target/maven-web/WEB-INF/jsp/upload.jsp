<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import ="org.itstep.entities.Role"%>
<%-- 
 String path=req.getContextPath();
--%> 

<c:set var="description" value="iShop - online shoes store. upload"/>  
<c:set var="h1" value="iShop - upload"/>

<u:backoffice-page title="online shoes store. upload here!" h1="${h1}">
<c:url var="saveUrl" value="/"/>
<c:url var="uploadPath" value="/upload"/>

<p>upload</p>
<form method="POST" enctype="multipart/form-data" action="${uploadPath}">
  <input type="file" name="upfile" multiple>
  <input type="text" id="path" name="path" value="${saveUrl}">
  <input type="submit" value="Load">Load File
</form> 



</u:backoffice-page>