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
              
               <c:url var="uCart" value="/users/cart.html"/>
               <c:url var="oUrder" value="/users/order.html"/>
 
              
             <h2>User Info</h2>   
             <div>name: <span>${user.name}</span></div>
             <div>login: <span>${user.login}</span></div>
             <div>pass: <span>${user.password}</span></div>
             <div>email: <span>${user.email}</span></div>
             <div>bonus: <span>${client.bonuspoints}</span></div>
	         <div>address: <span>${client.address}</span></div>
	         <div>phoneno: <span>${client.phoneno}</span></div>
	         
	           <button class="form__button"><a href="${uCart}">Cart</a></button>
               <button class="form__button"><a href="${oUrder}">Orders</a></button> 
	         <h2>Orders</h2>
	            <table border="1">
                <tr>   
                <th>number;</th>
                <th>quantity; </th>  
                <th>sum;</th>
                <th>currency;</th>
                <td>status</td>
                <td>active</td>
                </tr>
	            <c:forEach var="order" items="${orders}">			   			     					
				   <tr>
				   <th>${order.number}</th>
                   <th>${order.quantity}</th>  
                   <th>${order.sum}</th>
                   <th>${order.currency}</th>
                   <td>${order.status}</td>
                   <td>${order.active}</td>
				   </tr>	        
			   </c:forEach>	
		    </table>
</u:page>