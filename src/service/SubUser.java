package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

@WebServlet("/SubBug")
public class SubUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubUser() {
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
	
		ObjectMapper objectmapper = new ObjectMapper();
		
		try
		{
			SubUserReq subuserreq = objectmapper.readValue(request.getInputStream(), SubUserReq.class);
			//System.out.println(subuserreq);
			int userId = 0; 

			try
			{
				
					String fullname=subuserreq.getFullname();
					String mobile=subuserreq.getMobile();
					String location=subuserreq.getLocation();
					String auth=subuserreq.getAuth();
					
					//System.out.println(objectmapper.writeValueAsString(subuserreq));
					
						dbconn=new DataBaseConn();
						con = dbconn.setConnection ();
						//System.out.print("connected");
						String sql = "insert into users (user_full_name, user_mobile, user_location, user_auth) values (?,?,?,?)"; 
						PreparedStatement prep= con.prepareStatement(sql); 
						prep.setString(1, fullname); 
						prep.setString(2, mobile); 
						prep.setString(3, location); 
						prep.setString(4, auth);
						prep.executeUpdate(); 
						prep.close(); 
						if(con!=null)
					    	con.close();
						
						//System.out.print(fullname+""+mobile+""+location);
						
						dbconn=new DataBaseConn();
						con = dbconn.setConnection ();
						//System.out.println("connected");
						stmt=(Statement) con.createStatement();
						String query = "select * from users";
						rs=dbconn.getResult(query, con);
						while(rs.next())
						{
							userId = rs.getInt("user_id");
						}
						rs.close();
						if(con!=null)
							con.close();
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			JSONObject ResponseObj=new JSONObject();
			ResponseObj.put("userId", userId);
			response.setContentType("text/json");				
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printout = response.getWriter();
			printout.print(ResponseObj.toString());
		}
		catch (JsonGenerationException e)
	    {
	        e.printStackTrace();
	    }
		catch (JsonMappingException e)
	    {
	        e.printStackTrace();
	    }
		catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}

}
