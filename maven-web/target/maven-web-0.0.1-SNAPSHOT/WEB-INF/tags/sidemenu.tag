<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ attribute name="sidemenu2" required="false" rtexprvalue="true" type="java.lang.String" %>

<!-- **********SIDE CONTENT************************* -->			
<div class="side">
  <div class="side-content">
<!--************************** -->
    <c:url var="categUrl" value="/category/list.json"/>
 	<script type="text/javascript">var categUrl = '${categUrl}';</script>
 	
 	    <c:url var="mainUrl" value="/"/>
 	<script type="text/javascript">var mainUrl = '${mainUrl}';</script>
	
	<c:url var="categListUrl" value="/script/categories.js"/>
	<script type="text/javascript" src="${categListUrl}"></script>
<div>	
<ul id="side-categories" class="categories">

   
</ul> 
</div>
	<div class="end"></div>
<c:if test="${not empty sidemenu2}">	
    <c:url var="imgUrl" value="/img/point.png"/>
    <img src="${imgUrl}">
    <div class="end"></div>
    <c:url var="classifUrl" value="/classification/list.json"/>
 	<script type="text/javascript">var classifUrl = '${classifUrl}';</script>
	
	<c:url var="classifListUrl" value="/script/classifications.js"/>
	<script type="text/javascript" src="${classifListUrl}"></script>


   <div class="fleft" id="side-classification">
    <form id="form-classification" class="sform">
        
      <button class="form__button" type="submit">Select</button>
      <button id="button-cancel" class="form__button_danger" type="reset">Cancel</button>					
    </form>     
   </div>

</c:if>
<jsp:doBody/>

  </div>
</div>