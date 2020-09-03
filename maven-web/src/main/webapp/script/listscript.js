/**
 * sort
 * itemsonpage
 * currency
 * page 
 */
window.addEventListener('load', function() {
 
	  var PS = document.getElementById("sort");	 
	  var PIOP = document.getElementById("itemsonpage");	
	  var PIC = document.getElementById("currency");
	  var PP = document.getElementById("page");
	 
	  PS.addEventListener("change", function () { window.location.href=PS.value;});
	  PIOP.addEventListener("change", function () { window.location.href=PIOP.value;});
	  PIC.addEventListener("change", function () { window.location.href=PIC.value;});
	  PP.addEventListener("change", function () { window.location.href=PP.value;});	 	  
	  
});