package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;
/**
 * Servlet implementation class UpdateUsers
 */
@WebServlet("/UpdateUsers")
public class UpdateUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUsers() {
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
		try
		{
				String user_id=request.getParameter("user_id"); 
				String user_full_name=request.getParameter("user_full_name"); 
				String user_mobile=request.getParameter("user_mobile"); 
				String user_location=request.getParameter("user_location"); 
				String user_auth=request.getParameter("user_auth"); 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "update users set user_full_name=?, user_mobile=?, user_location=?, user_auth=? where user_id=?" ; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, user_full_name);
				prep.setString(2, user_mobile);
				prep.setString(3, user_location);
				prep.setString(4, user_auth);
				prep.setString(5, user_id);
				prep.executeUpdate(); 
				prep.close();
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Updated Successfully !");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
				String user_id=request.getParameter("user_id"); 
				String user_full_name=request.getParameter("user_full_name"); 
				String user_mobile=request.getParameter("user_mobile"); 
				String user_location=request.getParameter("user_location"); 
				String user_auth=request.getParameter("user_auth"); 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "update users set user_full_name=?, user_mobile=?, user_location=?, user_auth=? where user_id=?" ; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, user_full_name);
				prep.setString(2, user_mobile);
				prep.setString(3, user_location);
				prep.setString(4, user_auth);
				prep.setString(5, user_id);
				prep.executeUpdate(); 
				prep.close();
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Updated Successfully !");
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
