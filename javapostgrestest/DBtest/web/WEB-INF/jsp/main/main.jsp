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

<u:page title="online shoes store. Buy here!" description="${description}" canonical="${canonical}" h1="${h1}" >
<div class="end"></div>
                  <c:url var="banner1Url" value="/catalog/category/women-shoes.html"/>
                       <c:url var="banner2Url" value="/catalog/category/men-shoes.html"/>
                         <c:url var="banner3Url" value="/catalog/category/men-summer-shoes.html"/>
                           <c:url var="banner4Url" value="/catalog/category/women-winter-boots.html"/>
                                       <c:url var="banner5Url" value="/catalog/list/men-white-textile.html"/>
                           <c:url var="banner6Url" value="/catalog/list/women-faux-leather-flip-flops.html"/>             
                           
	                <div class="banner">
						<h3 class="banner__title">Women's shoes</h3>
						<a href="${banner1Url}"><img class="banner__photo" src="img/womansshoes.png"></a>						
						<div class="banner__description">Women's shoes of natural leather</div>
						<a class="product__button-add" href="${banner1Url}">Look</a>						
					</div>
					<div class="banner">
						<h3 class="banner__title">Men's shoes</h3>
						<a href="${banner2Url}"><img class="banner__photo" src="img/mansshoes.png"></a>									
						<div class="banner__description">Natural Men's shoes</div>			
						<a class="product__button-add" href="${banner2Url}">Look</a>
					</div>
					<div class="banner">
						<h3 class="banner__title">Summer shoes</h3>
						<a href="${banner3Url}"><img class="banner__photo" src="img/summershoes.png"></a>						
						<div class="banner__description">Summer shoes collection</div>
						<a class="product__button-add" href="${banner3Url}">Look</a>
					</div>
					<div class="banner">
						<h3 class="banner__title">Winter shoes</h3>
						<a href="${banner4Url}"><img class="banner__photo" src="img/wintershoes.png"></a>						
						<div class="banner__description">Winter shoes collection</div>
						<a class="product__button-add" href="${banner4Url}">Look</a>
					</div>
					
					<div class="banner">
						<h3 class="banner__title">men sandals</h3>
						<a href="${banner5Url}"><img class="banner__photo" src="img/mansshoes.png"></a>						
						<div class="banner__description">white textile sandals</div>
						<a class="product__button-add" href="${banner5Url}">Look</a>
					</div>
					<div class="banner">
						<h3 class="banner__title">women flip flops</h3>
						<a href="${banner6Url}"><img class="banner__photo" src="img/allseasonshoes.png"></a>						
						<div class="banner__description">women faux leather flip flops</div>
						<a class="product__button-add" href="${banner6Url}">Look</a>
					</div>

</u:page>