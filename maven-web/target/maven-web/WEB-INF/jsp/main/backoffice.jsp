<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%-- 
 String path=req.getContextPath();
--%> 

<c:set var="description" value="iShop - online shoes store. Bue men's and wmen's shoes, boots, slippers, flip flops here."/>  
<c:set var="h1" value="iShop - online shoes store"/>

<u:backoffice-page title="online shoes store. Buy here!" h1="${h1}">

<p><a href="/test/items/list.html">items list</a></p>
<p><a href="/test/category/list.html">categories list</a></p>
<p><a href="/test/users/list.html">users list</a></p>
<p><a href="/test/baseitem/list.html">baseitem list</a></p>

</u:backoffice-page>