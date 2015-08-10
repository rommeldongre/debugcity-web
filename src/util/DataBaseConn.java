package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
/**
 * Servlet implementation class DatabaseConnection
 */
@WebServlet("/DataBaseConn")
public class DataBaseConn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	Connection con;
	ResultSet rs;
	Statement stmt;
    public DataBaseConn() {
      
    }

    public Connection setConnection()
    {
    	try
    	{
    		Class.forName("com.mysql.jdbc.Driver");
    		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/debugcity","root","root");
    	}
    	
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return con;
    }
    
    public ResultSet getResult(String sql,Connection con)
    {
    	this.con=con;
    	try
    	{
    		stmt=con.createStatement();
    		rs=stmt.executeQuery(sql);
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return rs;
    }
}
