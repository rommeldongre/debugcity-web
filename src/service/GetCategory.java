package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

@WebServlet("/GetCategory")
public class GetCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    DataBaseConn dbconn;
   	Statement stmt;
   	String query;
   	Connection con;
   	ResultSet rs;
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setContentType("application/json; charset=UTF-8");
		
			int count=0;
			
			try
			{
				
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					
					query="select distinct incident_category from incident";
					
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						count++;	
					}					
					
					rs.close();
					
					String[] category = new String[count];
					
					count=0;
					
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						category[count]=rs.getString("incident_category");
						count++;
					}
					
					if(con!=null)
						con.close();
					//System.out.println(category);
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("category", category);
					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
	}

}
