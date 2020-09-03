<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
