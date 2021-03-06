<%--
	@author Shivam Gupta
 --%>
 
 
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dispaly Incident</title>
<script type="text/javascript" src="../js/jquery-1.7.min.js"></script>
<script type="text/javascript">
canvasCtx = null;
imageFile = null;
url = null;
flag=0;

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
	}

	drawImage = function(img) {
		this.canvasCtx.canvas.width = img.width;
		this.canvasCtx.canvas.height = img.height;
		this.canvasCtx.drawImage(img,0,0);
		url = canvasCtx.canvas.toDataURL("image/png");
		//console.log(url);
		flag=1;
		setURL(url,flag);
	}
}

function setURL(url,flag)
{
	flag=flag;
	document.getElementById("hidden").value= url;
	
}
</script>
</head>
<body>
	<table border='2'>
		<tr>
				<th>incident_id</th>
				<th>incident_lat</th>
				<th>incident_long</th>
				<th>incident_category</th>
				<th>incident_picture</th>
				<th>incident_locality</th>
				<th>incident_submitter</th>
				<th>incident_owner</th>
				<th>incident_state</th>
				<th>incident_date_created</th>
				<th>incident_date_closed</th>
				<th>incident_severity</th>
				<th>incident_notes</th>
				<th>incident_votes</th>
				<th colspan='2'>Action</th>
		</tr>
		<%Iterator itr;%>
		<%List data=(List) request.getAttribute("UserData");
		for(itr = data.iterator(); itr.hasNext();){
		%>
		
		<tr>
			<% String s= (String)itr.next(); %>
			<td align="center"><%=s%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><img src="<%=itr.next()%>" height="40" width="100"></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			<td align="center"><%=itr.next()%></td>
			
			<td align="center"><a href="../adminjsp/UpdateIncident.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Edit</a></td>
			<td align="center"><a href="../adminjsp/DeleteIncident.jsp?val=<%=s%>" style='text-decoration:none; color:blue;'>Delete</a></td>
		<%}%>
		</tr>
	</table>
   <form name="saveincident" method="post" action="../admin/SaveIncident" enctype="multipart/form-data" >
  <table>
   
   <!-- 	<tr>
  				<td>incident_id</td>
  				<td><input type="text" name="incident_id" id="incident_id" ></td>
      		</tr>
  -->		 	<tr>
				<td>incident_lat</td>
				<td><input type="text" name="incident_lat"></td>
			</tr>
			<tr>
				<td>incident_long</td>
				<td><input type="text" name="incident_long"></td>
			</tr>
			<tr>
				<td>incident_category</td>
				<td><input type="text" name="incident_category"></td>
			</tr>
			<tr>
				<td>incident_picture</td>
				<td><input type="file" id="pic" name="incident_picture"  ><canvas id="panel"></canvas></td>
			</tr>
			<tr>
				<td>incident_locality</td>
				<td><input type="text" name="incident_locality"></td>
			</tr>
			<tr>
				<td>incident_submitter</td>
				<td><input type="text" name="incident_submitter"></td>
			</tr>
			<tr>
				<td>incident_owner</td>
				<td><input type="text" name="incident_owner"></td>
			</tr>
			<tr>
				<td>incident_state</td>
				<td><input type="text" name="incident_state"></td>
			</tr>
			<tr>
				<td>incident_date_created</td>
				<td><input type="text" placeholder="yyyy-mm-dd hh:mm:ss" name="incident_date_created"></td>
			</tr>
			<tr>
				<td>incident_date_closed</td>
				<td><input type="text" placeholder="yyyy-mm-dd hh:mm:ss" name="incident_date_closed"></td>
			</tr>
			<tr>
				<td>incident_severity</td>
				<td><input type="text" placeholder="enter the number" name="incident_severity"></td>
			</tr>
			<tr>
				<td>incident_notes</td>
				<td><input type="text" name="incident_notes"></td>
			</tr>
			<tr>
				<td>incident_votes</td>
				<td><input type="text" placeholder="enter the number" name="incident_votes"></td>
			</tr>			
   		<tr><td colspan="2"><input type="submit" value="Insert"></td></tr>
		<input type="hidden" id="hidden" name="hidden">
   </table>
   </form>
</body>
</html>
