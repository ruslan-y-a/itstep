<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="inshop" required="false" rtexprvalue="true" type="java.lang.Integer" %>

<div class="header">
                <c:url var="ibag" value="/images/bag.png"/>
                <c:url var="iletter" value="/images/letter.png"/>
                <c:url var="inote" value="/images/note.png"/>
                <c:url var="ilock" value="/images/lock.png"/>
                <c:url var="iarrow" value="/images/arrow.png"/>
                <c:url var="ishoesm" value="/images/shoesm.png"/>
                
				<div class="site-title fleft">iShop - online shoes store</div>
				
				<jsp:doBody/>
				
				<ul class="main-menu">
					<li class="main-menu__item"><a class="main-menu__item_link" href="/">
					<img src="${iarrow}">Main</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="products">
					<img src="${ishoesm}">Catalog</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="basket">
					 <img src="${ibag}">cart (${inshop})</a></li>				
					<li class="main-menu__item"><a class="main-menu__item_link" href="login">
					<img src="${ilock}">login</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="contact">
					 <img src="${iletter}">Contacts</a></li>	
					<li class="main-menu__item"><a class="main-menu__item_link" href="about">
					<img src="${inote}">About</a></li>
					<li class="main-menu__item"><a class="main-menu__item_link" href="terms">
					<img src="${inote}">Terms</a></li>					
					<!--  <li class="end"></li>  -->
				</ul>				
				<div class="end"></div>
</div>