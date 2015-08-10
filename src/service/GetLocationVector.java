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

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;


@WebServlet("/GetLocationVector")
public class GetLocationVector extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocationVector() {
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
		GetLocationVectorReq getlocvecreq = objectmapper.readValue(request.getInputStream(), GetLocationVectorReq.class);
		response.setContentType("application/json; charset=UTF-8");
			try
			{
				
					String location=getlocvecreq.getLocation();
					
					JSONObject obj = new JSONObject();

				     /* obj.put("name", "foo");
				      obj.put("num", new Integer(100));
				      obj.put("balance", new Double(1000.21));
				      obj.put("is_vip", new Boolean(true));
					*/
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					String query = "select incident_category, count(*) as count from incident where incident_locality='"+location+"' group by incident_category";
					rs=dbconn.getResult(query, con);
					String count="0";
					String category=null;
					
					while(rs.next())
					{
						category=rs.getString("incident_category");
						count=rs.getString("count");
						obj.put(category, count);
					}
					
					//System.out.println(obj);
					
					rs.close();
					if(con!=null)
						con.close();
					 
					int returnCode=0;
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("locationVector", obj);
					ResponseObj.put("returnCode", returnCode);
					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
				//e.printStackTrace();
				//String bId = null;
				int returnCode = 1048;
				JSONObject ResponseObj=new JSONObject();
				try {
					ResponseObj.put("returnCode", returnCode );
					ResponseObj.put("errorString", e.getMessage());
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		ObjectMapper objectmapper = new ObjectMapper();
		GetLocationVectorReq getlocvecreq = objectmapper.readValue(request.getInputStream(), GetLocationVectorReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			//String vector = ""; 

			try
			{
				
					String location=getlocvecreq.getLocation();
					
					JSONObject obj = new JSONObject();

				     /* obj.put("name", "foo");
				      obj.put("num", new Integer(100));
				      obj.put("balance", new Double(1000.21));
				      obj.put("is_vip", new Boolean(true));
					*/
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					String query = "select incident_category, count(*) as count from incident where incident_locality='"+location+"' group by incident_category";
					rs=dbconn.getResult(query, con);
					String count="0";
					String category=null;
					
					while(rs.next())
					{
						category=rs.getString("incident_category");
						count=rs.getString("count");
						obj.put(category, count);
					}
					
					//System.out.println(obj);
					
					rs.close();
					if(con!=null)
						con.close();
					 
					int returnCode=0;
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("locationVector", obj);
					ResponseObj.put("returnCode", returnCode);
					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
				//e.printStackTrace();
				//String bId = null;
				int returnCode = 1048;
				JSONObject ResponseObj=new JSONObject();
				try {
					ResponseObj.put("returnCode", returnCode );
					ResponseObj.put("errorString", e.getMessage());
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
