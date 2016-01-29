<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>getIncident</title>

    <script type="text/javascript">
        function getIncident()
        {
            xmlhttp=new XMLHttpRequest();
            var url = "service/getIncident";

            var data= new Object();

            data["incident_id"]= document.getElementById('incident_id').value;

            xmlhttp.onreadystatechange=function() {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    var json = JSON.parse(xmlhttp.responseText);

                    /*document.getElementById("count").value=json.count;
                    if(json.count!=0)
                        document.getElementById("categories").value=json.categories;
                    else
                        document.getElementById("categories").value="";
                    document.getElementById("returncode").value=json.returnCode;

                    if(json.returnCode!=0)
                    {
                        document.getElementById("errorstring").value=json.errorString;
                    }
                    else
                        document.getElementById("errorstring").value="";*/

                    console.log(json);
                }
            };

            xmlhttp.open("POST", url, true);
            xmlhttp.responseType = 'JSON';
            xmlhttp.send(JSON.stringify(data));
        }
    </script>

</head>
<body>
<h1>getIncident</h1>



        <input type="text" placeholder="Incidnet Id" id="incident_id">
        <input type="Button" name="submit" id="submit" onclick="getIncident()" value="getIncident">



</body>
</html>
