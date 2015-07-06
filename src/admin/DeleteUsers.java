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
 * Servlet implementation class DeleteUsers
 */
@WebServlet("/DeleteUsers")
public class DeleteUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUsers() {
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 String user_id=request.getParameter("user_id"); 
		 try 
		 { 
			 dbconn=new DataBaseConn();
			 con = dbconn.setConnection ();	 
     		 String sql = "delete from users where user_id=?"; 
			 PreparedStatement prep= con.prepareStatement(sql); 
	    	 prep.setString(1, user_id);
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
