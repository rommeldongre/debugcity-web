package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

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

@WebServlet("/SubmitBug")
public class SubmitBug extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitBug() {
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
		SubmitBugReq subbugreq = objectmapper.readValue(request.getInputStream(), SubmitBugReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			int bugId = 0; 

			try
			{
				
					String lat=subbugreq.getLat();
					String lng=subbugreq.getLng();
					String cat=subbugreq.getCat();
					
					String pic=null;
					if(!subbugreq.getPic().equals(""))
						pic=subbugreq.getPic();
					
					String locality=subbugreq.getLocality();
					
					String submitter=null;
					if(!subbugreq.getSubmitter().equals(""))		
						submitter=subbugreq.getSubmitter();
					
					String owner=null;
					if(!subbugreq.getOwner().equals(""))
						owner=subbugreq.getOwner();
					
					String state=null;
					if(!subbugreq.getOwner().equals(""))
						state=subbugreq.getState();
					
					Timestamp datecreated=new Timestamp(new Date().getTime());
					Timestamp dateclosed=null;
					
					int severity=0;
					if(!subbugreq.getSeverity().equals(""))
						severity=Integer.parseInt(subbugreq.getSeverity());
					
					String notes=null;
					if(!subbugreq.getNotes().equals(""))
						notes=subbugreq.getNotes();
					
					int votes=0;
					if(!subbugreq.getVotes().equals(""))
						votes=Integer.parseInt(subbugreq.getVotes());
					
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.print("connected");
					String sql = "insert into incident (incident_lat,incident_long,incident_category,incident_picture,incident_locality,"
							+ "incident_submitter,incident_owner,incident_state,incident_date_created"
							+ ",incident_date_closed,incident_severity,incident_notes,incident_votes) values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
					PreparedStatement prep= con.prepareStatement(sql); 
					prep.setString(1, lat);
					prep.setString(2, lng);
					prep.setString(3, cat);
					//if(inputstream!=null)
					{
						prep.setString(4, pic);
					}
					prep.setString(5, locality);
					prep.setString(6, submitter);
					prep.setString(7, owner);
					prep.setString(8, state);
					prep.setTimestamp(9, datecreated);
					prep.setTimestamp(10, dateclosed);
					prep.setInt(11, severity);
					prep.setString(12, notes);
					prep.setInt(13, votes);
					prep.executeUpdate(); 
					prep.close(); 
					if(con!=null)
				    	con.close();
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					String query = "select * from incident";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						bugId = rs.getInt("incident_id");
					}
					rs.close();
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("bugId", bugId);
					ResponseObj.put("returnCode", "0");
					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
				//e.printStackTrace();
				String bId = null;
				int returnCode = 1048;
				JSONObject ResponseObj=new JSONObject();
				try {
					ResponseObj.put("bugId", bId);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		SubmitBugReq subbugreq = objectmapper.readValue(request.getInputStream(), SubmitBugReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			int bugId = 0; 

			try
			{
				
					String lat=subbugreq.getLat();
					String lng=subbugreq.getLng();
					String cat=subbugreq.getCat();
					
					String pic=null;
					if(!subbugreq.getPic().equals(""))
						pic=subbugreq.getPic();
					
					String locality=subbugreq.getLocality();
					
					String submitter=null;
					if(!subbugreq.getSubmitter().equals(""))		
						submitter=subbugreq.getSubmitter();
					
					String owner=null;
					if(!subbugreq.getOwner().equals(""))
						owner=subbugreq.getOwner();
					
					String state=null;
					if(!subbugreq.getOwner().equals(""))
						state=subbugreq.getState();
					
					Timestamp datecreated=new Timestamp(new Date().getTime());
					Timestamp dateclosed=null;
					
					int severity=0;
					if(!subbugreq.getSeverity().equals(""))
						severity=Integer.parseInt(subbugreq.getSeverity());
					
					String notes=null;
					if(!subbugreq.getNotes().equals(""))
						notes=subbugreq.getNotes();
					
					int votes=0;
					if(!subbugreq.getVotes().equals(""))
						votes=Integer.parseInt(subbugreq.getVotes());
					
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.print("connected");
					String sql = "insert into incident (incident_lat,incident_long,incident_category,incident_picture,incident_locality,"
							+ "incident_submitter,incident_owner,incident_state,incident_date_created"
							+ ",incident_date_closed,incident_severity,incident_notes,incident_votes) values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
					PreparedStatement prep= con.prepareStatement(sql); 
					prep.setString(1, lat);
					prep.setString(2, lng);
					prep.setString(3, cat);
					//if(inputstream!=null)
					{
						prep.setString(4, pic);
					}
					prep.setString(5, locality);
					prep.setString(6, submitter);
					prep.setString(7, owner);
					prep.setString(8, state);
					prep.setTimestamp(9, datecreated);
					prep.setTimestamp(10, dateclosed);
					prep.setInt(11, severity);
					prep.setString(12, notes);
					prep.setInt(13, votes);
					prep.executeUpdate(); 
					prep.close(); 
					if(con!=null)
				    	con.close();
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					String query = "select * from incident";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						bugId = rs.getInt("incident_id");
					}
					rs.close();
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("bugId", bugId);
					ResponseObj.put("returnCode", "0");
					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
				//e.printStackTrace();
				String bId = null;
				int returnCode = 1048;
				JSONObject ResponseObj=new JSONObject();
				try {
					ResponseObj.put("bugId", bId);
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
