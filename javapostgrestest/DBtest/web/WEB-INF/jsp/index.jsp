<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>

<%-- 
 String path=req.getContextPath();
--%> 

<c:set var="canonical" value="/"/>  <%-- req.getContextPath() --%>
<c:set var="description" value="iShop - online shoes store. Bue men's and wmen's shoes, boots, slippers, flip flops here."/>  
<c:set var="h1" value="iShop - online shoes store"/>

<u:page title="online shoes store. Buy here!" description="${description}" canonical="${canonical}" cart="0" h1="${h1}" >
<div class="end"></div>
                    <div class="banner">
						<h3 class="banner__title">Women's shoes</h3>
						<a href="#"><img class="banner__photo" src="img/womansshoes.png"></a>						
						<div class="banner__description">Women's shoes of natural leather</div>
						<a class="product__button-add" href="#">Look</a>						
					</div>
					<div class="banner">
						<h3 class="banner__title">Men's shoes</h3>
						<a href="#"><img class="banner__photo" src="img/mansshoes.png"></a>									
						<div class="banner__description">Natural Men's shoes</div>			
						<a class="product__button-add" href="#">Look</a>
					</div>
					<div class="banner">
						<h3 class="banner__title">Summer shoes</h3>
						<a href="#"><img class="banner__photo" src="img/summershoes.png"></a>						
						<div class="banner__description">Summer shoes collection</div>
						<a class="product__button-add" href="#">Look</a>
					</div>
					<div class="banner">
						<h3 class="banner__title">Winter shoes</h3>
						<a href="#"><img class="banner__photo" src="img/wintershoes.png"></a>						
						<div class="banner__description">Winter shoes collection</div>
						<a class="product__button-add" href="#">Look</a>
					</div>

</u:page>