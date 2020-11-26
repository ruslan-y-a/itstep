function calcSum() {
	  
	  var PriceE = document.getElementById("price");
	  var PriceV = PriceE.innerHTML *1;
	  var PriceDCE = document.getElementById("pricediscount");
	  var PriceDC = PriceDCE.innerHTML *1;
	  var BBP = document.getElementById("usebonuspoints");
	  var BUB = document.getElementById("usedbonuspoints");
	  var TS = document.getElementById("sum");	 
	  var QT = document.getElementById("quantity").value *1;	
	  if (QT<=0) {QT=1;}
	
	   if (BBP!= null) {
		   
		/*   if (BBP.attr("checked")='checked') {*/
		    if (BBP.checked=='checked') {  
		    	  	 	  
		  var BUBV=0; var BPV=0;
		  if (BUB!=null) {
			   BUBV = document.getElementById("usedbonuspoints").value *1;
			   BPV = document.getElementById("bonuspoints").value *1;
		     }	  
		  	  
		  var TDC = PriceV*60/100;	  
		  var FDC=0;	   
		   var FPrice=PriceV; 
		   var DCV= PriceV-PriceDC;
		    var sum=0;
		    var UBP=0;	   	   
		
		    if (DCV>=TDC || BUBV==false) {sum=PriceDC*QT;UBP=0;}
		    else {
			  if ((DCV*QT+BPV)<TDC*QT) {
				  sum=(PriceDC-BPV)*QT; UBP=BPV;
			  }	  
			  else {	 
				  sum=(PriceV-TDC)*QT; UBP=(TDC - DCV)*QT;
			  }		 
		    }  
			  	
		  TS.value = sum;
		  BUB.value=UBP;
	   }  
	  } else {
		  TS.value = PriceDC*QT;		
	  }
	  
	
}
/*function baseSum() {
	  var PriceDCE = document.getElementById("pricediscount");	
	  var BP = document.getElementById("baseprice");	
	  PriceDCE.innerHTML =BP.innerHTML;
	  calcSum();
}*/
window.addEventListener('load', function() {				
    calcSum(); 
      var QT_ = document.getElementById("quantity");
	  var UBV_ = document.getElementById("usebonuspoints");	  	  
	  var Cur = document.getElementById("currency");
	  Cur.addEventListener("change", function () { window.location.href=Cur.value;});
	  
	/*  var BI = document.getElementById("baseitem");*/
	  
	  QT_.oninput = function () {calcSum();}
	  QT_.addEventListener("change", calcSum());
/*	  BI.addEventListener("change", baseSum());*/
	  
	  if (UBV_!= null) {	  
	     UBV_.oninput = function () {calcSum();}	  
	     UBV_.addEventListener("change", calcSum());	  	 
	   }		 				  
}, false);	

