<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SubmitIncident</title>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="js/exif.js"></script>
<script type="text/javascript" src="js/jquery-1.7.min.js"></script>
<script src="https://maps.googleapis.com/maps/api/js?v=3.exp&signed_in=true&libraries=places"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrap-multiselect.js"></script>
<link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css"/>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
	
<style>
				* {
					    margin: 0;
					}
					html, body {
					    height: 100%;
					}
					.wrapper {
					    min-height: 100%;
					    height: auto !important;
					    height: 100%;
					    margin: 0 auto -30px; /* the bottom margin is the negative value of the footer's height */
					}
					.footer, .push {
					    height: 75px; /* .push must be the same height as .footer */
						font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    					font-size: 14px;
					}
</style>

<script type="text/javascript">
				var catobj;
				xmlhttp=new XMLHttpRequest();
				var url = "service/GetCategories";

				xmlhttp.onreadystatechange=function() {
		    	if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
						//alert(xmlhttp.responseText);
		    			var json = JSON.parse(xmlhttp.responseText);
		    			catobj=json.catobj;
						addCat();
					}
				}
				
				xmlhttp.open("POST", url, true);
				xmlhttp.responseType = 'JSON';
				xmlhttp.send();
</script>

<script>
			function addCat()
		 	{
				$.each(catobj, function(key, value) {   
				     $('#multipleSelectCat')
				     	 .append($("<option></option>")		
				         .attr("value",value)
				         .text(value));
				});
								
				$(document).ready(function() {
			 	    $('#multipleSelectCat').multiselect({
			 	        buttonWidth: '200px',
			 	        dropRight: true
			 	    });
			 	});
				
				initialize();
			}	
</script>

<script type="text/javascript">
canvasCtx = null;
imageFile = null;
url = null;
flag=0;
latitude=null;
longitude=null;
loc=null;
loc_post=null;
cat_post=null;

function initialize() 
{
	canvasCtx = document.getElementById("panel").getContext("2d");
	document.getElementById("pic").onchange = function(event) {		
		flag=1;
		this.imageFile = event.target.files[0];
		
		var reader = new FileReader();
		reader.onload =  function(event) {
			var img = new Image();
			img.onload = function() {
				drawImage(img);
			}
			img.src = event.target.result;
		}
		reader.readAsDataURL(this.imageFile);
		EXIF.getData(event.target.files[0], function() {
	        	latitude = EXIF.getTag(this, "GPSLatitude"),
	            longitude = EXIF.getTag(this, "GPSLongitude");
	        	
	        	var latRef = EXIF.getTag(this, "GPSLatitudeRef") || "N";  
                var lonRef = EXIF.getTag(this, "GPSLongitudeRef") || "W";  
		        //alert(EXIF.pretty(this));
	        	if(typeof latitude !=="undefined" && typeof longitude !=="undefined")
				{
		        	latitude = (latitude[0] + latitude[1]/60 + latitude[2]/3600) * (latRef == "N" ? 1 : -1);  
	        		longitude = (longitude[0] + longitude[1]/60 + longitude[2]/3600) * (lonRef == "W" ? -1 : 1); 
				}
	        	//console.log(latitude);
		        //console.log(longitude);
	    });
}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = 300//img.width;
		this.canvasCtx.canvas.height = 300//img.height;
		this.canvasCtx.drawImage(img,0,0,300,300);
		url = canvasCtx.canvas.toDataURL("image/png",0.7);
		flag=1;
		setURL(url,flag);
	}
}
  
function PostImageToFacebook(token)
{
	var location=loc_post;
	var category=cat_post;
	var authToken=token;	
	var imageData  = pic;
	
	try{
	    	blob = dataURItoBlob(imageData);
	}
	catch(e){
			console.log(e);
	}
	
	var fd = new FormData();
	fd.append("access_token",authToken);
	fd.append("source", blob);
	fd.append("caption","A concerned citizen identified a "+category+" problem in "+location+". Vote for it on http://www.debugcity.com, if this is an annoyance for you too.");
	
	try{
		$.ajax({
		    url:"https://graph.facebook.com/938425389552084/photos?access_token=" + authToken,
		    type:"POST",
		    data:fd,
		    processData:false,
		    contentType:false,
		    cache:false,
		    success:function(data){
		        console.log("success " + data);
		    },
		    error:function(shr,status,data){
		        console.log("error " + data + " Status " + shr.status);
		    },
		    
		    //complete:function(){
		    //console.log("Posted to facebook");
		    //}
		});		
	}
	catch(e){
			console.log(e);
	}
}

function dataURItoBlob(dataURI) 
{
	var byteString = atob(dataURI.split(',')[1]);
	var ab = new ArrayBuffer(byteString.length);
	var ia = new Uint8Array(ab);
	
	for (var i = 0; i < byteString.length; i++) 
	{
	    ia[i] = byteString.charCodeAt(i);
	}
	
	return new Blob([ab], { type: 'image/png' });
}

function setURL(url,flag)
{
	flag=flag;
	pic= url;
}

function submitBug(location)
{
	var cat=$('#multipleSelectCat').val();
	
	var subBugData=new Object();
	
	subBugData["cat"]=cat;	
	subBugData["lat"]=latitude;	
	subBugData["lng"]=longitude;	
	subBugData["locality"]=location;	
		
	if(flag==1)
		subBugData["pic"]=pic;
	else
		subBugData["pic"]="";
	
	subBugData["submitter"]="";
	subBugData["owner"]="";
	subBugData["state"]="";
	subBugData["severity"]="";
	subBugData["notes"]="";
	subBugData["votes"]="";
		
	xmlhttp=new XMLHttpRequest();
	var url = "service/SubmitBug";

	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
		   		
				if(json.returnCode!=0)
				{
				   	$("#bugExist").modal();		
				}
				else
				{	
					loc_post=loc;
					cat_post=cat;
					var token=	"CAAOl30X9gXABAMIIe5vWN1vt2u9w3co20O8dqj1aji17TiIcZC6PxmJlpZBZBtGj7Mm9gmHNqAZBVSQ3rt4XlnPQjZA3NhC1pqGGZBmsO0oefr3ldL3T85rfaxOzZAfxjPDPvJicUOXNEzY56FvNn0p9WIR61JZAbfSDJodfdY82rWr9PqOCM8q4TOP81TECNIEZD";
					
				   	if(document.getElementById("dbctfb1").checked == true && document.getElementById("dbctfb2").checked == true)
				    {
				   		PostImageToFacebook(token);
				   		loginCheck();
				   		$("#mySuccessModal2").modal();
					}
				   	
				   	else if(document.getElementById("dbctfb1").checked == true && document.getElementById("dbctfb2").checked == false)
				   	{
				   		PostImageToFacebook(token);
				   		$("#mySuccessModal1").modal();
				   	}
				   	
				   	else if(document.getElementById("dbctfb1").checked == false && document.getElementById("dbctfb2").checked == true)
					{
				   		loginCheck();
				   		$("#mySuccessModal2").modal();
				  	}
				   
				   	else
				   		$("#mySuccessModal1").modal();
				}   	
			   	$("#subform")[0].reset();   
			   	canvasCtx.clearRect(0, 0, 300, 300);
		}
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send(JSON.stringify(subBugData));
}

function getLocation()
{
	if(typeof latitude !=="undefined" && typeof longitude !=="undefined")
	{
		var latlng = new google.maps.LatLng(latitude, longitude);
		
		geocoder = new google.maps.Geocoder();
		var flag=0;
		var res=null;
		    geocoder.geocode({'latLng': latlng}, function(results, status) {
		        if (status == google.maps.GeocoderStatus.OK) {
		            if (results[0]) {
		                for (j = 0; j < results[0].address_components.length; j++) {
		                    if (results[0].address_components[j].types[0] == 'postal_code')
		                    {
		                    	//alert("Zip Code: " + results[0].address_components[j].short_name);
		                    	res=results[0].address_components[j].short_name;
		                    	flag=1;
		                    	break;
		                    }
		                }
		                if(flag==1)
		                {
		                	flag=0;
		                	submitBug(res);
		               	}
		                else
			          		getCoordinates(loc);
		            }
		        } else
		        	  getCoordinates(loc);
		    });
	}
	else
	{
		if(loc!="")
			getCoordinates(loc);
		else
			alert("Please enter location also, location information is absent in the image.");
	}
}

function getCoordinates(loc)
{
	var geocoder = new google.maps.Geocoder();
    latitude = '';
    longitude = '';
    
    geocoder.geocode( { 'address': ''+loc+', india'}, function(results, status) 
    		{
    			if (status == google.maps.GeocoderStatus.OK) 
    			{
    				if (results[0]) 
    				{
    					latitude = results[0].geometry.location.lat();
             			longitude = results[0].geometry.location.lng();	
             			//for (var p = 0; p < results[0].address_components.length; p++) {
		                  //  if (results[0].address_components[p].types[0] == 'postal_code')
		                    {
		                        redirect(loc);
		                    }
		             	//}
               		}
    			}
    			else
    				alert("Please enter correct location.");
    		});
}

function redirect(loc)
{
		submitBug(loc);
}

function CheckValidation()
{
    var isValidForm = document.forms['subform'].checkValidity();
    if (isValidForm)
    {
    	loc=document.getElementById("locality").value;
    	getLocation();
    }
    else
    {
    	alert("Please choose an image to submit the incident.");
    }
}

function loginCheck()
{
	FB.getLoginStatus(function(response) {
	    statusChangeCallback(response);
	  });
}

function statusChangeCallback(response) {
    //console.log('statusChangeCallback');
    //console.log(response);
    
    if (response.status === 'connected') {
    	//console.log('connected');
    	FB.login(function(response) {
    		 if (response.authResponse) {
    	            //console.log('Access Token: ' + response.authResponse.accessToken);
    	            PostImageToFacebook(response.authResponse.accessToken);  
    	     } else {
    	            console.log('User cancelled login or did not fully authorize.');
    	        }
    		 }, {
    	   scope: 'publish_actions', 
    	   return_scopes: true
    	 });
    } 
    else if (response.status === 'not_authorized') {
    	//console.log('Please log into this app.');
    	FB.login(function(response) {
    		 if (response.authResponse) {
    	            //console.log('Access Token: ' + response.authResponse.accessToken);
    	            PostImageToFacebook(response.authResponse.accessToken);  
    	     } else {
    	            console.log('User cancelled login or did not fully authorize.');
    	        }
    		 }, {
    		   scope: 'publish_actions', 
    		   return_scopes: true
    		 });
    } 
    else {
      	//console.log('Please log into Facebook.');
      	FB.login(function(response) {
      		 if (response.authResponse) {
                 //console.log('Access Token: ' + response.authResponse.accessToken);
                 PostImageToFacebook(response.authResponse.accessToken);   
             } else {
                 console.log('User cancelled login or did not fully authorize.');
             }
      	 }, {
      	   scope: 'publish_actions', 
      	   return_scopes: true
      	 });
    }
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

</script>

</head>
<body style="background:">
<script>
  window.fbAsyncInit = function() {
    FB.init({
      appId      : '1026803300663664',
      xfbml      : true,
      version    : 'v2.4'
    });
  };

  (function(d, s, id){
     var js, fjs = d.getElementsByTagName(s)[0];
     if (d.getElementById(id)) {return;}
     js = d.createElement(s); js.id = id;
     js.src = "//connect.facebook.net/en_US/sdk.js";
     fjs.parentNode.insertBefore(js, fjs);
   }(document, 'script', 'facebook-jssdk'));
</script>
	<div class="wrapper">
	<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header pull-left">
			<a class="navbar-brand" href="index.html"><b>DebugCityV1</b></a>
		</div>
		<div class="navbar-header pull-right">
			<ul class="nav navbar-nav pull-left">
				<li><a class="" href="admin.html">Admin&nbsp;</a></li>
			</ul>
			<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
            	<span class="icon-bar"></span>
          	</button>
         </div>
         <div class="navbar-collapse collapse">
         	
         	<div class="navbar-right">
         		<ul class="nav navbar-nav">
         			<li><a href="index.html">Home</a></li> 
         			<li><a href="spider.html">Spider Graph</a></li> 
         			<li><a href="DebugCityv1.20150723.apk">Download App</a></li> 
         		</ul>
         	</div>
        </div>
   </div>
</div>
<div class="container" style="margin-top:40px;">
  <h2>Submit Incident</h2>
  <form class="form-horizontal" role="form" name="subform" id="subform">
    <div class="form-group">
      <label class="control-label col-sm-2" for="cat">Cat <span style="color:red; font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">
      		<select id="multipleSelectCat" style="position:relative;" name="multipleSelectCat">
			</select>	
	  </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="locality">Location </label>
      <div class="col-sm-10">
        <input input type="text" id="locality" onkeypress="return isNumber(event)" name="locality" class="form-control" placeholder="Enter pin-code or zip-code of the locality">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pic">Pic <span style="color:red; font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">          
        <input type="file" class="form-control" id="pic" required="required"><br><canvas id="panel"></canvas>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox" checked="checked" id="dbctfb1">Post anonymously to DebugCity Facebook page</label>&nbsp;&nbsp;&nbsp;&nbsp;
          <label><input type="checkbox" id="dbctfb2">Post as yourself to DebugCity Facebook page</label>
        </div>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
      		<input class="btn btn-default" type="Button" name="submit" id="submit"
				onclick="CheckValidation()" value="Submit">
	  </div>
    </div>
  </form>
</div>
</div>
<div class="footer" style="background-color:#222;margin-top:20px;">	
	<center><font style="color:#9d9d9d;"><br>Designed and Developed by Grey Labs in Pune<br>© Grey Labs. All Rights Reserved.</center>	
</div>

<div class="modal fade" id="mySuccessModal1" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#5cb85c;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">Incident successfully submitted.</font></h4>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="mySuccessModal2" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#5cb85c;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">Incident successfully submitted.</font></h4>
        </div>
        <div class="modal-body">
        	<p><font style="color:black;">Please also give permissions to post as youself on Debugcity facebook page if needed. </font></p>
     	 </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">OK</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="bugExist" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#5bc0de;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">Incident already exists.</font></h4>
        </div>
        <div class="modal-body">
        	<p><font style="color:black;">To vote for this incident check all the incidents <a href="index.html" style="text-decoration:none;">here</a>. </font></p>
     	</div>
        <div class="modal-footer">
          <button type="button" class="btn btn-info" data-dismiss="modal">OK</button>
        </div>
      </div>
    </div>
  </div>
</div>

</body>
</html>