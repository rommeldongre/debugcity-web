<%--
	@author Shivam Gupta
 --%>
 
<%@ page import="java.io.*,java.sql.*" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<%
					String ver=request.getParameter("ver");
					
					Class.forName("com.mysql.jdbc.Driver");
					Connection con =DriverManager.getConnection("jdbc:mysql://localhost:3306/debugcity","root","root");
					Statement st=con.createStatement();
					ResultSet rs = st.executeQuery("select * from category where cat_name='"+ver+"'");  
					if(rs.next())
					{   %>
					    <font color=red>
					   
					    <input type="hidden" id="actual" name="actual" value="taken">
					     Cat_name already taken
					    </font>
					
					<% }else {%>
					    <font color=green>
					    <input type="hidden" id="actual" name="actual" value="available">
					    <input type="submit" value="Insert">
					    Available
					    </font>
					<% }%>
                   
     <%              
rs.close();
st.close();
con.close();
%>
