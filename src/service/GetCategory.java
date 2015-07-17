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

import org.json.JSONException;
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
		
		String vector = ""; 
			
		try
		{
				dbconn=new DataBaseConn();
				con = dbconn.setConnection ();
				//System.out.println("connected");
				stmt=(Statement) con.createStatement();
				//String query = "select cat_name, count(*) as count from category group by cat_name";
				String query = "select * from category";
				rs=dbconn.getResult(query, con);
				int count=1;
				String category=null;
				
				while(rs.next())
				{
					category=rs.getString("cat_name");
					//count=rs.getInt("count");
					
					vector=vector+category+":"+count+", ";
				}
				
				if(vector.length() > 0)
					vector = vector.substring(0,vector.length()-2);
				
				rs.close();
				if(con!=null)
					con.close();
	
				JSONObject ResponseObj=new JSONObject();
				ResponseObj.put("categorylist", vector);
				response.setContentType("text/json");				
				response.setContentType("application/json; charset=UTF-8");
				PrintWriter printout = response.getWriter();
				printout.print(ResponseObj.toString());
			}
			
		catch(Exception e)
		{
			JSONObject ResponseObj=new JSONObject();
			try{
				ResponseObj.put("categorylist", e.getMessage());
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			response.setContentType("text/json");				
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printout = response.getWriter();
			printout.print(ResponseObj.toString());
		}
			
	}

}
