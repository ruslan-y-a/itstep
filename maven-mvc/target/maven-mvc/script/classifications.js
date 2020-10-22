window.addEventListener('load', function() {

			var request = new XMLHttpRequest();
			request.onload = function() {
				var classifications = JSON.parse(this.responseText);
				var mForm = document.getElementById('form-classification');		
				classifList(mForm,classifications,0);
			};
			request.open('GET', classifUrl);
			request.send();
	
	
}, false);	

function classifList(mForm,classifications, listid) {
	classifications.forEach((classification) => {	
	  if (classification.parentid==0) {
		  
		  var div=document.createElement('div'); 
		  mForm.appendChild(div);
		  var select=document.createElement('select');
		  //select.name='classification';
		  select.name=classification.name; 
		  div.appendChild(select);
		  
		  select.id='select'+classification.id;
		  select.setAttribute('multiple','multiple');		  		
		//  select.className="sform__text-input";		  
		   var opt;
		   opt=document.createElement('option');
		   select.appendChild(opt);
		  
	   } else if (classification.id==classification.parentid) {
		   var div=document.createElement('div'); 
			  mForm.appendChild(div);
			  var select=document.createElement('select');
			//  select.name='classification';
			  div.appendChild(select);
			  select.setAttribute('multiple','multiple');
		      select.name=classification.name;
		   select.id='select'+classification.id;		   
		   var opt=document.createElement('option');
		   select.appendChild(opt);
		 var opt=document.createElement('option');
		   opt.appendChild(document.createTextNode(classification.name));
		   opt.value=classification.id;
		   select.appendChild(opt);
	   } else {
		   var select=document.getElementById('select'+classification.parentid);   
		   var opt=document.createElement('option');
		   opt.appendChild(document.createTextNode(classification.name));
		   opt.value=classification.id;
		   select.appendChild(opt);
	   }
	   
	   
	});
}