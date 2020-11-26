window.addEventListener('load', function() {

			var request = new XMLHttpRequest();
			request.onload = function() {
				var categories = JSON.parse(this.responseText);
				var mainUL = document.getElementById('side-categories');		
				catList(mainUL,categories,0);
			};
			request.open('GET', categUrl);
			request.send();
	
	
}, false);	

function catList(mainUL,categories, listid) {
	categories.forEach((category) => {
	  //let children=catChildList(categories,category.id);	
	  if ( category.id==category.parentid || category.parentid==0) {
		  var li=document.createElement('li'); 		  
		  li.innerHTML="<a href='" + mainUrl +"catalog/list.html?category=" + category.id + "'>"+category.name+"</a>";
		  mainUL.append(li);
		  
	//	  ul=document.createElement('ul');
	//	  li.append(ul);
	//	  catList(ul, children,category.id);
	 //  } else if (listid==category.id || category.id==category.parentid){		   
	//	  var li=document.createElement('li'); 
	//	   li.className="categories__item";
	//	   li.innerHTML="<a href='" + mainUrl + "catalog/list.html?category=" + category.id + "'>"+category.name+"</a>";
	//	   mainUL.append(li);
	//------------------------------------------------	  
		  var sublist=new Array();
		  //sublist=childrenCategory(category.id);
		  
		  var request = new XMLHttpRequest();
		  request.onload = function() {
				var sublist = JSON.parse(this.responseText);
		  
		       if (sublist!=null && sublist.length>0) {
			     li.className="categories__item submenu";
		         ul=document.createElement('ul');
			     li.append(ul);  		
  			   //  li.addEventListener("mouseover", getChildList(ul, sublist), false);
			     getChildList(ul, sublist);
		        }	else {
			     li.className="categories__item";
		        }
			};
		  request.open('GET', categUrl + "?parent="+category.id);
		  request.send();  
	   } 	  
	});
}
function getChildList(mainUL,sublist) {
	
	 sublist.forEach((category) => {
		  var li=document.createElement('li'); 		  
		  li.innerHTML="<a href='" + mainUrl +"catalog/list.html?category=" + category.id + "'>"+category.name+"</a>";
		  mainUL.append(li);
		  let subsublist=new Array();
		  var request = new XMLHttpRequest();
		  request.onload = function() {
		  var subsublist = JSON.parse(this.responseText);
		  
		     //subsublist=childrenCategory(category.id);
		     if (subsublist!=null && subsublist.length>0) {
			    li.className="categories__item submenu";
			    ul=document.createElement('ul');
			    li.append(ul);  		
			    li.addEventListener("mouseover", getChildList (ul,category.id), false);
		     } else {
		    	  li.className="categories__item"; 
		     }	
		     
		   };
		 request.open('GET', categUrl + "?parent="+category.id);
		 request.send();     
		     
	});		
}

function catChildList(categories,id) {
	let mas=new Array();
	categories.forEach((category) => {
	  if (category.parentid==id) {
		  mas.push(category);
	   } 		  
	});
	return mas;
}
function childrenCategory(parent){
	var request = new XMLHttpRequest();
	request.onload = function() {
		var categories = JSON.parse(this.responseText);
	     return categories;
	};
	request.open('GET', categUrl + "?parent="+parent);
	request.send();
}
