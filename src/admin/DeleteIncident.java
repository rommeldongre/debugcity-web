package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class DeleteIncident
 */
@WebServlet("/DeleteIncident")
@MultipartConfig(maxFileSize = 16777215)
public class DeleteIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteIncident() {
        super();
        // TODO Auto-generated constructor stub
    }
    DataBaseConn dbconn;
   	ResultSet rs;
   	Statement stmt;
   	String query;
   	Connection con;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String incident_id=request.getParameter("incident_id"); 
		try 
		{ 
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();	 
			String sql = "delete from incident where incident_id=?"; 
			PreparedStatement prep= con.prepareStatement(sql);
			prep.setString(1, incident_id);
			prep.executeUpdate(); 
			prep.close(); 
			RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Deleted Successfully !");
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
		String incident_id=request.getParameter("incident_id"); 
		try 
		{ 
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();	 
			String sql = "delete from incident where incident_id=?"; 
			PreparedStatement prep= con.prepareStatement(sql);
			prep.setString(1, incident_id);
			prep.executeUpdate(); 
			prep.close(); 
			RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Deleted Successfully !");
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


