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
 * Servlet implementation class DisplayCategory
 */
@WebServlet("/DisplayCategory")
public class DisplayCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayCategory() {
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
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();	
		@SuppressWarnings("rawtypes")
		List lst=new ArrayList(); 
		try
		{ 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection (); 
				stmt=(Statement) con.createStatement();
				String query = "select * from category";
				rs=dbconn.getResult(query, con);
				while(rs.next())
				{
					lst.add(rs.getString("cat_name"));
					lst.add(rs.getString("cat_desc"));
					lst.add(rs.getString("cat_parent"));
					lst.add(rs.getString("cat_child"));				
				}
				rs.close();
				request.setAttribute("UserData", lst);
				RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/DisplayCategory.jsp");
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
