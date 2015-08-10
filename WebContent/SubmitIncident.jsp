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
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css"/>
<script type="text/javascript">

canvasCtx = null;
imageFile = null;
url = null;
flag=0;
latitude=null;
longitude=null;

window.onload = function () {
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
	var cat=document.getElementById("cat").value;
	
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
				if(json.bugId!=null)
				{	
					document.getElementById("bugId").value=json.bugId;
				}
				else
					document.getElementById("bugId").value="error";
				
				document.getElementById("returnCode").value=json.returnCode;
				if(json.returnCode!=0)
				{
					document.getElementById("errorString").value=json.errorString;		
				}
				else
					document.getElementById("errorString").value="bug successfully submitted.";
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
		                    	alert("Zip Code: " + results[0].address_components[j].short_name);
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
		alert("Invalid picture.");
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
  <form class="form-horizontal" role="form">
    <div class="form-group">
      <label class="control-label col-sm-2" for="cat">Cat <span style="color:red;font-family:'Glyphicons Halflings';font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">
        <input input type="text" id="cat" name="cat" class="form-control" placeholder="Enter category">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="pic">Pic <span style="color:red;font-family:'Glyphicons Halflings';font-weight: normal;font-size: 14px;">*</span></label>
      <div class="col-sm-10">          
        <input type="file" class="form-control" id="pic"><canvas id="panel"></canvas>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox"> Share on Facebook</label>
        </div>
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
      		<input class="btn btn-default" type="Button" name="submit" id="submit"
				onclick="getLocation()" value="Get IncidentId">
	  </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="bugId">Incident id&nbsp;&nbsp;&nbsp;</label>
      <div class="col-sm-10">
        <input input type="text" id="bugId" name="bugId" readonly="readonly" class="form-control" placeholder="Incident id">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="returnCode">Return code&nbsp;&nbsp;&nbsp;</label>
      <div class="col-sm-10">
        <input input type="text" id="returnCode" name="returnCode" readonly="readonly" class="form-control" placeholder="Return code">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="errorString">Error string&nbsp;&nbsp;&nbsp;</label>
      <div class="col-sm-10">
        <input input type="text" id="errorString" name="errorString" readonly="readonly" class="form-control" placeholder="Error string">
      </div>
    </div>
  </form>
</div>
</div>
<div class="footer" style="background-color:#222;margin-top:20px;">	
	<center><font style="color:#9d9d9d;"><br>© Grey Labs LLP. All Rights Reserved.</center>	
</div>
</body>
</html>

</body>
</html>
