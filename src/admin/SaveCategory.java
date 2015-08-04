package admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

import java.sql.*;
/**
 * Servlet implementation class SaveCategory
 */
@WebServlet("/SaveCategory")

public class SaveCategory extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCategory() {
        super();
        // TODO Auto-generated constructor stub
    }
    DataBaseConn dbconn;
   	Statement stmt;
   	String query;
   	Connection con;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String cat_name=request.getParameter("cat_name"); 
		String cat_desc=request.getParameter("cat_desc");
		String cat_parent=request.getParameter("cat_parent");
		String cat_child=request.getParameter("cat_child");
		try 
		{ 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "insert into category values (?,?,?,?)"; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, cat_name); 
				prep.setString(2, cat_desc);
				prep.setString(3, cat_parent);
				prep.setString(4, cat_child);
				prep.executeUpdate(); 
				prep.close(); 
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Inserted Successfully !");
				rd.forward(request, response);
			    if(con!=null)
			    	con.close();
		 }
		 catch(Exception e)
		 {
		   	    RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/Error.jsp?");
			    rd.forward(request, response);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{ 
		String cat_name=request.getParameter("cat_name"); 
		String cat_desc=request.getParameter("cat_desc");
		String cat_parent=request.getParameter("cat_parent");
		String cat_child=request.getParameter("cat_child");
		try 
		{ 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "insert into category values (?,?,?,?)"; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, cat_name); 
				prep.setString(2, cat_desc);
				prep.setString(3, cat_parent);
				prep.setString(4, cat_child);
				prep.executeUpdate(); 
				prep.close(); 
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Inserted Successfully !");
				rd.forward(request, response);
			    if(con!=null)
			    	con.close();
		 }
		 catch(Exception e)
		 {
		   	    RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/Error.jsp?");
			    rd.forward(request, response);
		}
	}
}