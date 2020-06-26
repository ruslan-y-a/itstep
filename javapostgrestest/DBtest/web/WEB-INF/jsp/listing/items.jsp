<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="tabs.Items"%>
<%@ page import ="tabs.Entity"%>
<%@ page import ="tabs.Color"%>
<%@ page import ="tabs.Currency"%>
<%@ page import ="tabs.Size"%>
<%@ page import ="tabs.Classification"%>
<%@ page import ="service.DBService"%>
<%@ page import ="service.DBServiceImpl"%>

<%
//List<Entity> products = (List<Entity>)request.getAttribute("client");
//String[] menu=products.get(0).getFieldsList();
//DBService service =(DBService) request.getAttribute("service");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Entity list</title>
</head>
<body>
 <h1 style=\"color: green\">Items list</h1>
 <c:if test="${empty client}">
  <p>Items list is empty</p>
 </c:if>
  <table border="1">
   <tr>   
    <c:forEach var="string" items="${client.get(0).getFieldsList()}">
      <th>${string}</th>
    </c:forEach>     
     <th>Categories</th>
   </tr> 
	
	<c:set var="size" value="${0}"/>	
	
		<%--
	 <td>	
	    <c:forEach var="classid" items="${service.readID("itemcatgory","items",${item.DBgetId()})}">
	       <c:set var="mainclass" value="${service.readID("classification",""parentid"",${item.DBgetId()})}"/>		   
		   <ul>
           <li>              
              ${service.read("classification",${mainclass}}
              :
              ${service.read("classification",${classid}}
           </li>
           </ul>
        </c:forEach> 
      </td>  
	--%>	
	 
	 
	<c:forEach var="item" items="${client}">
	     <c:set var="classid" value="${item.DBgetId()}"/>		
	    <tr>	      		
		   <td>${classid}</td>		       		    
		   <td>${item.articul}</td>
		   <td>${item.model}</td>
		   <td>${item.baseprice}</td>
		   <td>${item.discount}</td>
		   <td>${item.title}</td>
		   <td>${item.text}</td>
		   <td>${item.name}</td>
		   <td>${item.description}</td>
		   <c:set var="tagurlid" value="${item.tagurl}"/>	
		   <td>${service.read("tagurl",tagurlid).tagname}</td>
		   <td>${item.keywords}</td>
		   <td>${item.mainimgurl}</td>
		   <td>${item.url}</td>	 
		   	
		    <td>	
		     
	         <c:forEach var="classif" items="${service.itemCategories(classid)}">	      
	          <c:set var="childclass" value="${classif.DBgetId()}"/>	     
	          <c:set var="mainclass" value="${classif.parentid}"/>	     		   
		      <ul>
               <li>              
                  ${service.read("classification",mainclass).name}
                   :
                  ${service.read("classification",childclass).name}
               </li>
              </ul>
             </c:forEach> 
            </td>  	    	
		       
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} users</p>
</body>
</html>