<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="u" %>
<jsp:useBean id="now" class="java.util.Date"/> 
<%-- 
 String path=req.getContextPath();
--%> 

<c:set var="canonical" value="/"/>  <%-- req.getContextPath() --%>
<c:set var="description" value="iShop - Registration."/>  
<c:set var="h1" value="iShop - thank you for the Registration"/>

<u:page title="Register here!" description="${description}" canonical="${canonical}" h1="${h1}">
              
             <h2>Fill the form</h2>   
	        <c:url var="registerUrl" value="/registeruser.html"/>
            <form action="${registerUrl}" method="post">
				
			<c:if test="${not empty entity}">
			<input type="hidden" name="id" value="${users.id}">
			</c:if>							
			<br>
			<label for="name">Name</label>			
			<input type="text" id="name" name="name" value="">
			<br>
			<label for="login">login</label>			
			<input type="text" id="login" name="login" value="">  
			<br>			
			<label for="password">Password</label>			
			<input type="password" id="password" name="password"  value="123456789">
			<br>			
			<label for="email">Email</label>			
			<input type="email" id="email" name="email" value="">
			<br>
			<input type="hidden" id="role" name="role" value="1">	
			<input type="hidden" id="creationdate" name="creationdate" value="${now}">						            
		 	<label for="address">address</label>			
			<input type="text" id="address" name="address" value="">   
			<br>
		 	<label for="bonuspoints">bonuspoints: 10</label>			
			<input type="hidden" id="bonuspoints" name="bonuspoints" value="10">  
		   		<br>	  
		 	<label for="phoneno">phoneno</label>			
			<input type="tel" id="phoneno" name="phoneno" value="">  
			
			 <fieldset id="backoffice-edit-form-item">
             <label class="backoffice-form__label" for="country">Country </label>               
            <select class="backoffice-form__text-input" id="country" name="country">
              <option value="0"></option>
			  <c:forEach var="icountry" items="${countries}">			   			     					
				   <option value="${icountry.id}">${icountry.name}</option>			        
			  </c:forEach>			  
		  </select>
          </fieldset>    
			
		  	<button class="form__button" type="submit">Register</button>
		</form>	
		    
</u:page>