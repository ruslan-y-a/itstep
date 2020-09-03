window.addEventListener('load', function() {
	var searchElement = document.getElementById('pagecart');						
			var request = new XMLHttpRequest();
			request.onload = function() {
				var products = JSON.parse(this.responseText);		
				var il=products.length;
				searchElement.innerText = 'cart (' + il+ ')'; 				
			};
			request.open('GET', cartUrl);
			request.send();		
	
}, false);