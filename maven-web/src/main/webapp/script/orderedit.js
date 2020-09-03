function calcSum() {
				   var FDC=0; var OBP=0; 
				   var PriceV = document.getElementById("price").value *1;
				   var FPrice=PriceV; 
				   var DCV = document.getElementById("discount").value * 1;
				   var TDC = document.getElementById("maxdiscount").value *1;
				   var BPV = document.getElementById("bonuspoints").value *1;
				   var MBPV = document.getElementById("maxbonuspoints").value *1;
				   var QT = document.getElementById("quantity").value *1;
				   var KrateO = document.getElementById("currency");
				   var KrateV = KrateO.value*1;
				   var KrateOp = KrateO.options;
				   var KrateOpN = KrateOp[KrateV];
				   var KrateT = KrateOpN.text;
				   var  Krate=KrateT.substring(3);
				   Krate=Krate*1;	
				   
				   FDC=TDC*PriceV*QT/100;
				   var disc=0; var sum=0;
					  if (DCV<TDC) {
						  disc=MBPV+PriceV*DCV*QT/100;
	                     if (FDC>disc) {FDC=disc; OBP=MBPV;}
	                     else {OBP=MBPV-(FDC-PriceV*DCV*QT/100);}
					  }
					  sum=(PriceV*QT-FDC)*Krate;					  
				  var TS = document.getElementById("totalsum");	 
				  var TS1 = document.getElementById("totalsum1");	
				  var FOBP = document.getElementById("bonuspoints");
				  var FOBP1 = document.getElementById("bonuspoints1");
				  FOBP.value= OBP;
				  FOBP1.innerHTML= OBP;
				  TS.innerHTML = sum;
				  TS1.value= sum;
				  BPV.value=OBP;
}
window.addEventListener('load', function() {				
	              calcSum(); 
				  var Price = document.getElementById("price");				 
				  var DC = document.getElementById("discount");
				  var TDC = document.getElementById("maxdiscount");			
				  var QT = document.getElementById("quantity");		
				  var BP = document.getElementById("bonuspoints");
				  var KrateO = document.getElementById("currency");
				  
				  Price.oninput = function () {calcSum();}
				  DC.oninput = function () {calcSum();}
				  TDC.oninput = function () {calcSum();}
				  QT.oninput = function () {calcSum();}
				  BP.oninput = function () {calcSum();}
				  KrateO.oninput = function () {calcSum();}
				  
		
				  var CSel = document.getElementById("client");
				  var BSel = document.getElementById("baseitem");
				  CSel.addEventListener("change", function () { getBonus();});
				  BSel.addEventListener("change", function () { getPrice();}); 
		/*		  Price.addEventListener("clock", function(){calcSum}, false);
				  DC.addEventListener("input", function(){calcSum}, false);
				  TDC.addEventListener("input", function(){calcSum}, false);
				  QT.addEventListener("input", function(){calcSum}, false);
				  BP.addEventListener("input", function(){calcSum}, false);*/				 				  
}, false);		

function getBonus() {
	
	var CL = document.getElementById("client").value;
	var request = new XMLHttpRequest();
	request.onload = function() {
		var client = JSON.parse(this.responseText);
		var CLBP = document.getElementById("maxbonuspoints");
		CLBP.value=client.bonuspoints;	
		 calcSum();
	};
	request.open('GET', clientUrl + '?id=' + CL);
	request.send();
}
function getPrice() {
	
	var BA = document.getElementById("baseitem").value;
	var request = new XMLHttpRequest();
	request.onload = function() {
		var products = JSON.parse(this.responseText);
		var ajaxPrice = document.getElementById("price");
		ajaxPrice.value = products.item.baseprice;	
		 calcSum();
	};
	request.open('GET', itemsUrl + '?id=' + BA);
	request.send();
}