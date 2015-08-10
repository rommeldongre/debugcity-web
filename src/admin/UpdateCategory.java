package admin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.DataBaseConn;
import com.mysql.jdbc.Statement;
/**
 * Servlet implementation class UpdateCategory
 */
@WebServlet("/UpdateCategory")
public class UpdateCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateCategory() {
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
		try
		{
			String cat_name=request.getParameter("cat_name"); 
				String cat_desc=request.getParameter("cat_desc"); 
				String cat_parent=request.getParameter("cat_parent"); 
				String cat_child=request.getParameter("cat_child"); 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "update category set cat_desc=?, cat_parent=?, cat_child=? where cat_name=?" ; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, cat_desc);
				prep.setString(2, cat_parent);
				prep.setString(3, cat_child);
				prep.setString(4, cat_name);
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
			String cat_name=request.getParameter("cat_name"); 
				String cat_desc=request.getParameter("cat_desc"); 
				String cat_parent=request.getParameter("cat_parent"); 
				String cat_child=request.getParameter("cat_child"); 
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();	 
				String sql = "update category set cat_desc=?, cat_parent=?, cat_child=? where cat_name=?" ; 
				PreparedStatement prep= con.prepareStatement(sql); 
				prep.setString(1, cat_desc);
				prep.setString(2, cat_parent);
				prep.setString(3, cat_child);
				prep.setString(4, cat_name);
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
