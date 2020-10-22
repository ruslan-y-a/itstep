 document.querySelector('#table1').addEventListener('click', function(e){         
	 var CatIDS=0;
	// var elements = document.getElementsByClassName("table__cell"); 
	 var id = e.target.value;
	 var el;
	  var pCatID;
	  let elements = document.getElementsByClassName('V' + id);
	  // pCatID=elements[0].style.display;
	  while (el=elements[CatIDS++]) {			  			
			//  el = document.getElementById(id)[0];
		    if (el.style.display=='none') {
		    	el.style.display='table-row';  
		   } else {el.style.display='none';}
	  }          	      
  });
 
document.querySelector('#table2').addEventListener('click', function(e){         
        	  var id = e.target.id; 
});
                   
