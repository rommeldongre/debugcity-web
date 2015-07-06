<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Testing JSON Service</title>

<script type="text/javascript">
function subCategory()
{
	var catname=document.getElementById("catname").value;
	var catdesc=document.getElementById("catdesc").value;
	var catparent=document.getElementById("catparent").value;
	var catchild=document.getElementById("catchild").value;
		
	xmlhttp=new XMLHttpRequest();
	var url = "../service/SubCategory";
	
	var subCategoryData=new Object();
		
	subCategoryData["catname"]=catname;
	subCategoryData["catdesc"]=catdesc;
	subCategoryData["catparent"]=catparent;
	subCategoryData["catchild"]=catchild;
	
	xmlhttp.onreadystatechange=function() {
	    if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
				var json = JSON.parse(xmlhttp.responseText);
				document.getElementById("res").value="Successfully submitted";		
	    }
	}
			
	xmlhttp.open("POST", url, true);
	xmlhttp.responseType = 'JSON';
	xmlhttp.send(JSON.stringify(subCategoryData));	
}
</script>

</head>
<body>
	<h1>Submit Category</h1>
	<table>
		<tr>
			<td>cat_name</td>
			<td><input type="text" id="catname" name="catname"></td>
		</tr>
		<tr>
			<td>cat_desc</td>
			<td><input type="text" id="catdesc" name="catdesc"></td>
		</tr>
		<tr>
			<td>cat_parent</td>
			<td><input type="text" id="catparent" name="catparent"></td>
		</tr>
		<tr>
			<td>cat_child</td>
			<td><input type="text" id="catchild" name="catchild"></td>
		</tr>
		<tr>
			<td colspan="2"><input type="Button" name="submit" id="submit" onclick="subCategory()"  value="Submit Category"></td>
		</tr>
	</table>
	<br/> 
	<input type="text" id="res" name="res" readonly="readonly">
	
</body>
</html>