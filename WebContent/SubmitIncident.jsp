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
					    height: 55px; /* .push must be the same height as .footer */
						font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
    					font-size: 14px;
					}
</style>

<script type="text/javascript">
				var catobj;
				xmlhttp=new XMLHttpRequest();
				var url = "FilterCategory";

			 	var onloadData=new Object();
			 	onloadData["category"]="";
			 	onloadData["location"]="";
			 	
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
				xmlhttp.send(JSON.stringify(onloadData));
</script>

<script>
			function addCat()
		 	{
				$.each(catobj, function(key, value) {   
				     $('#multipleSelectCat')
				     	 .append($("<option></option>")		
				         .attr("value",key)
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
	        //alert(EXIF.pretty(this));
	    });
}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = img.width;
		this.canvasCtx.canvas.height = img.height;
		this.canvasCtx.drawImage(img,0,0);
		url = canvasCtx.canvas.toDataURL("image/png");
		flag=1;
		setURL(url,flag);
	}
}

function setURL(url,flag)
{
	flag=flag;
	pic= url;
}

function submitBug(loc)
{
	var cat=$('#multipleSelectCat').val();
	
	var subBugData=new Object();
	
	subBugData["cat"]=cat;	
	subBugData["lat"]=lat;	
	subBugData["lng"]=lng;	
	subBugData["locality"]=loc;	
		
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
				   	$("#myErrorModal").modal();		
				}
				else
				   	$("#mySuccessModal").modal();
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

		lat=latitude[0];
		if(latitude[1])
			lat=lat+"."+Math.round(latitude[1]);
		if(latitude[2])
			lat=lat+Math.round(latitude[2]);
		
		lng=longitude[0];
		if(longitude[1])
			lng=lng+"."+Math.round(longitude[1]);
		if(longitude[2])
			lng=lng+Math.round(longitude[2]);
		
		var latlng = new google.maps.LatLng(lat, lng);
		
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
		                	submitBug(res);
		               	}
		                else
			            {
		                	var loc="Unknown";
			          		submitBug(loc);
			            }
		            }
		        } else {
		            var loc="Unknown";
		          	submitBug(loc);
		        }
		    });
	}
	else
		$("#myInvalidModal").modal();
}

function CheckValidation()
{

    var isValidForm = document.forms['subform'].checkValidity();
    if (isValidForm)
    {
    	getLocation();
    }
    else
    {
    	//$('#modal-7 .modal-body').html(html);
    	$("#myValidModal").modal();
    }
}
</script>

</head>
<body style="background:">
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
      <label class="control-label col-sm-2" for="cat">Cat <span style="color:red;font-family:'Glyphicons Halflings';font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">
      		<select id="multipleSelectCat" style="position:relative;" name="multipleSelectCat">
			</select>	
	  </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pic">Pic <span style="color:red;font-family:'Glyphicons Halflings';font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">          
        <input type="file" class="form-control" id="pic" required="required"><canvas id="panel"></canvas>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox" checked="checked"> Post to <b>DebugCity</b> facebook page. </label>&nbsp;&nbsp;&nbsp;&nbsp;
          <label><input type="checkbox"> Post to my facebook page.</label>
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
	<center><font style="color:#9d9d9d;"><br>© Grey Labs LLP. All Rights Reserved.</center>	
</div>

<div class="modal fade" id="myInvalidModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#f0ad4e;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">No location information found. Please contact Support.</font></h4>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-warning" data-dismiss="modal">OK</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="mySuccessModal" role="dialog">
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

<div class="modal fade" id="myErrorModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#5bc0de;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">Incident already exists.</font></h4>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-info" data-dismiss="modal">OK</button>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myValidModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header" style="background-color:#5bc0de;">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><font style="color:white;">Please choose a picture to submit the incident.</font></h4>
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

</body>
</html>
