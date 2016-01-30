<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Get user info</title>

    <script type="text/javascript">

        function getUserInfo()
        {
            xmlhttp=new XMLHttpRequest();
            var url = "service/GetUserInfo";

            var data= new Object();

            data["user_id"]= document.getElementById('getuser_id').value;
            data["user_auth"]= document.getElementById('getuser_auth').value;

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

                    document.getElementById("returnCode").value= json['returnCode'];
                    document.getElementById("user_auth").value= json['user_auth'];
                    document.getElementById("user_full_name").value= json['user_full_name'];
                    document.getElementById("user_id").value= json['user_id'];
                    document.getElementById("user_location").value= json['user_location'];
                    document.getElementById("user_mobile").value= json['user_mobile'];
                    document.getElementById("error_string").value= json['user_mobile'];


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
<h1>Get User Info</h1>



        <input type="text" placeholder="user_id Id" id="getuser_id">
        <input type="text" placeholder="user_auth Id" id="getuser_auth">


        <input type="Button" name="submit" id="submit" onclick="getUserInfo()" value="getUserInfo">

        <br><br> <label> user id </label> <input type="text" id="user_id">
        <br><br> <label> user_auth </label> <input type="text" id="user_auth">
        <br><br> <label> Fullname </label> <input type="text" id="user_full_name">
        <br><br> <label> Location </label> <input type="text" id="user_location">
        <br><br> <label> Mobile </label> <input type="text" id="user_mobile">
        <br><br> <label> Return code </label> <input type="text" id="returnCode">
        <br><br> <label> Error String </label> <input type="text" id="error_string">




</body>
</html>
