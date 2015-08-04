package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class DisplayUsers
 */
@WebServlet("/DisplayUsers")
public class DisplayUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayUsers() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    DataBaseConn dbconn;
	ResultSet rs;
	Statement stmt;
	String query;
	Connection con;
	
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		List lst=new ArrayList();
		
		try
		{ 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection (); 
				stmt=(Statement) con.createStatement();
				query = "select * from users";
				rs=dbconn.getResult(query, con);
				while(rs.next())
				{
				
					lst.add(rs.getString("user_id"));
					lst.add(rs.getString("user_full_name"));
					lst.add(rs.getString("user_mobile"));
					lst.add(rs.getString("user_location"));
					lst.add(rs.getString("user_auth"));
				
				}				
				rs.close();
				request.setAttribute("UserData", lst);
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/DisplayUsers.jsp");
		    	rd.forward(request, response);
				lst.clear();
				out.close();
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
