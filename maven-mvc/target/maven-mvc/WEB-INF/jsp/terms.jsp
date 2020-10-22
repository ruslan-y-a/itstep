<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.List"%>

<c:set var="canonical" value="/terms"/>  
<c:set var="description" value="iShop - terms"/>  
<c:set var="h1" value="iShop - terms"/>
<u:page title="terms!" description="${description}" canonical="${canonical}" h1="${h1}" simple="true">
    <h3>terms</h3>
    <div class="end"></div>
	
	<table border="1">
	  
	 <c:forEach var="elist" items="${currency2}">
	  <tr>	      	      
	      <c:forEach var="entry" items=">${elist}">	
	         <th>${entry}</th>	        	        
         </c:forEach>
      </tr>
     </c:forEach>
	</table>
	
	    <div class="end"></div>
	    
	    
</u:page>