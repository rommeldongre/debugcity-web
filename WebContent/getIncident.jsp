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

            data["incident_id"]= document.getElementById('getincident_id').value;

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

                    document.getElementById("incident_category").value = json['incident_category'];
                    document.getElementById("incident_date_closed").value = json['incident_date_closed'];
                    document.getElementById("incident_date_created").value = json['incident_date_created'];
                    document.getElementById("incident_id").value = json['incident_id'];
                    document.getElementById("incident_lat").value = json['incident_lat'];
                    document.getElementById("incident_long").value = json['incident_long'];
                    document.getElementById("incident_notes").value = json['incident_notes'];
                    document.getElementById("incident_owner").value = json['incident_owner'];
                    document.getElementById("incident_severity").value = json['incident_severity'];
                    document.getElementById("incident_state").value = json['incident_state'];
                    document.getElementById("incident_submitter").value = json['incident_submitter'];
                    document.getElementById("incident_votes").value = json['incident_votes'];
                    document.getElementById("returnCode").value = json['returnCode'];
                    document.getElementById("error_string").value = json['errorString'];


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



        <input type="text" placeholder="Incidnet Id" id="getincident_id">
        <input type="Button" name="submit" id="submit" onclick="getIncident()" value="getIncident">


        <br><br> <label> Incident Id </label> <input type="text" id="incident_id">
        <br><br> <label> Incident lat </label> <input type="text" id="incident_lat">
        <br><br> <label> Incident lng </label> <input type="text" id="incident_long">
        <br><br> <label> Incident notes </label> <input type="text" id="incident_notes">
        <br><br> <label> Incident owner </label> <input type="text" id="incident_owner">
        <br><br> <label> Incident severity </label> <input type="text" id="incident_severity">
        <br><br> <label> Incident state </label> <input type="text" id="incident_state">
        <br><br> <label> Incident submitter </label> <input type="text" id="incident_submitter">
        <br><br> <label> Incident votes </label> <input type="text" id="incident_votes">
        <br><br> <label> Incident category </label> <input type="text" id="incident_category">
        <br><br> <label> Incident date closed </label> <input type="text" id="incident_date_closed">
        <br><br> <label> Incident date created </label> <input type="text" id="incident_date_created">
        <br><br> <label> Return code </label> <input type="text" id="returnCode">
        <br><br> <label> Error String </label> <input type="text" id="error_string">


</body>
</html>
