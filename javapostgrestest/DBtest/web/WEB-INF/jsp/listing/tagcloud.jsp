<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page import ="tabs.Tagcloud"%>
<%@ page import ="tabs.Tagurl"%>
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
<title>Tagcloud list</title>
</head>
<body>
 <h1 style=\"color: green\">tagcloud list</h1>
 <c:if test="${empty client}">
  <p>tagcloud list is empty</p>
 </c:if>
  <table border="1">
   <tr>   
    <c:forEach var="string" items="${client.get(0).getFieldsList()}">
      <th>${string}</th>
    </c:forEach>        
   </tr> 
	
	<c:set var="size" value="${0}"/>	
	
	
	 
	 
	<c:forEach var="tagcloud" items="${client}">	    	
	    <tr>	      		
		   <td>${tagcloud.DBgetId()}</td>		       		    		  
		  		   	
		    <td>			     
	         <c:forEach var="classid" items="${tagcloud.classification}">	         
	          <c:set var="classif" value="${service.read(\"classification\",classid)}"/>	      
	     <%--     <c:set var="childclass" value="${classif.DBgetId()}"/>  --%>		     
	          <c:set var="mainclass" value="${classif.parentid}"/>	     		   
		      <ul>
               <li>              
                  ${service.read("classification",mainclass).name}
                   :
                  ${classif.name}
               </li>
              </ul>
             </c:forEach> 
            </td>  	
                	
            <c:set var="tagurlid" value="${tagcloud.tagurl}"/>	
		    <td>${service.read("tagurl",tagurlid).tagname}</td>    	
		       
        </tr>
        <c:set var="size" value="${size+1}"/>  
    </c:forEach>    
 
  </table>
  <p>Total ${size} users</p>
</body>
</html>