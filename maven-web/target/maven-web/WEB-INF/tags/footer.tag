<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="footer">
		 &copy; &laquo;iShop&raquo; - online shoes store
		 
		 <jsp:doBody/>
		  <c:url var="sitemap" value="/sitemap.xml"/>
		 <div class="fright"><a href="${sitemap}">Sitemap</a></div>
</div>