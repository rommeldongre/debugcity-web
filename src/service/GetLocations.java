package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;
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

@WebServlet("/GetLocations")
public class GetLocations extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocations() {
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
		GetLocationsReq getlocreq = objectmapper.readValue(request.getInputStream(), GetLocationsReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			//int bugId = 0; 

			try
			{
				
					String location=getlocreq.getToken();
					int count = 0;
					String locality[]=new String[10];
					HashSet<String> noDuplicate = new HashSet<String>();
					
					for(int i=0;i<10;i++)
					{
						locality[i]="";
					}
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					String query = "select * from incident where incident_locality='"+location+"' or incident_lat='"+location+"' or incident_long='"+location+"' or incident_category='"+location+"' or incident_picture='"+location+"' or incident_submitter='"+location+"' or incident_owner='"+location+"' or incident_state='"+location+"' or incident_date_created='"+location+"'or incident_date_closed='"+location+"' or incident_notes='"+location+"'";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						locality[count]=rs.getString("incident_locality");
						noDuplicate.add(locality[count]);
						count++;
					}
					
					String[] localityArray = new String[noDuplicate.size()];
					noDuplicate.toArray(localityArray);
					
					rs.close();
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("count", count);
					ResponseObj.put("locality", localityArray);
					ResponseObj.put("returnCode", 0);
					ResponseObj.put("returnToken", "");
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
					ResponseObj.put("returnToken", "");
					}
				catch (JSONException e1) {
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
