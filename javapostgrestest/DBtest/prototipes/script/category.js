 document.querySelector('#table1').addEventListener('click', function(e){         
        	  var id = e.target.id; 
        	  var tmp=id.substring(0,5);  
        	    if (tmp=='pedit') {        	
      			  Editp(e.target);
      	        }
        	  });
 
document.querySelector('#table2').addEventListener('click', function(e){         
        	  var id = e.target.id; 
        	  var tmp=id.substring(0,5);
        	    if (tmp=='cedit') {        	
        		  Editc(e.target);
        	     }    
        	  });      
                   
function Editp(e){        	  
        	  var pnode = e.parentNode.parentNode;                	         	        	 
        	  var id= pnode.childNodes[1].innerHTML; //pnode.childNodes[0]
        	  var name = pnode.childNodes[3].innerHTML; //getchildid(pnode,'tname').innerHTML;        	  
        	  //var childid = pnode.childNodes[5].innerHTML; //getchildid(pnode,'tpassword').innerHTML;
        	 
        	  document.getElementById('parentid').value=id;        
        	  document.getElementById('parentcategory').value=id;         	  
        	  
        	  document.getElementById('id').value = id;
        	  document.getElementById('name').value = name;    	    
        	  chForm2Visibility(); 
          }

function Editc(e){        	  
        	  var pnode = e.parentNode.parentNode;        
        	 // var id= getchildid(pnode,'tid').innerHTML; //pnode.childNodes[0]        	        	  
        	  var id= pnode.childNodes[1].innerHTML; //pnode.childNodes[0]
        	  var name = pnode.childNodes[3].innerHTML; //getchildid(pnode,'tname').innerHTML;
        	  var cparentid = pnode.childNodes[5].innerHTML; //getchildid(pnode,'tpassword').innerHTML;        	      
        	          	  
        	  document.getElementById('chid').value = id;
        	  document.getElementById('chname').value = name;
        	  document.getElementById('parentid').value = cparentid;
        	  document.getElementById('parentcategory').value=cparentid;       	  
          }
      
var chForm2Visibility = function() {
	var chElement= document.getElementById('cid');
	if (!chElement) {
		document.getElementById('table2').style.display='none';	
		document.getElementById('form2').style.display='none';	
	}
	if (!chElement) {
		document.getElementById('table2').style.display='block';	
		document.getElementById('form2').style.display='block';	
	}
}
chForm2Visibility();     

document.querySelector('#parentcategory').addEventListener('click', function(e){         
	  var val=e.target.value;
	    var obj=document.getElementById('parentid');
	    obj.value=val;
	  }); 

var checkConfirm=function(){
	var res=false;
	var obj=document.getElementById('parentid');
	if (parseInt(obj.value)>0) {res=true;}
	if (!res){window.alert('Where is a Parent Category?');}
	return res;
}

var formSumm = document.getElementById('form2');
if(formSumm) {
	formSumm.onsubmit = checkConfirm;
}