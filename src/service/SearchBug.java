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

@WebServlet("/SearchBug")
public class SearchBug extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchBug() {
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
		SearchBugReq searchbugreq = objectmapper.readValue(request.getInputStream(), SearchBugReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			try
			{
					int token=Integer.parseInt(searchbugreq.getToken());
					int id=Integer.parseInt(searchbugreq.getId());
					String locality=searchbugreq.getLocality();
					String lat=searchbugreq.getLat();
					String lng=searchbugreq.getLng();
					String cat=searchbugreq.getCat();
					int max=0,flag=0;

					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					
					
				if(id==0)
				{
							query="select * from incident";
						
							if(!lat.equals(""))
							{
								query=query+" where incident_lat='"+lat+"' ";
								flag=1;
							}
							
							if(!lng.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_long='"+lng+"' ";
									flag=1;
								}
								else
									query=query+" and incident_long='"+lng+"' ";
							}
									
							if(!cat.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_category='"+cat+"' ";
									flag=1;
								}
								else
									query=query+" and incident_category='"+cat+"' ";
							}
									
							if(!locality.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_locality='"+locality+"' ";
									flag=1;
								}
								else
									query=query+" and incident_locality='"+locality+"' ";
							}
							
					String mquery = "select * from incident";
					rs=dbconn.getResult(mquery, con);
					while(rs.next())
					{
						max=(rs.getInt("incident_id"));
					}
					rs.close();
				
					query=query+" and incident_id between "+(token)+" and "+max+"";
				
					if(flag==0)
						query="select * from incident where incident_id between "+token+" and "+max+" ";
			}		
			else
					query="select * from incident where incident_id='"+id+"'";
				
					rs=dbconn.getResult(query, con);
					int rid = 0;
					String rlat = null;
					String rlng=null;
					String rcat=null;
					String rpic=null;
					String rlocality=null;
					String rsubmitter=null;
					String rowner=null;
					String rstate=null;
					String rdatecreated=null;
					String rdateclosed=null;
					int rseverity=0;
					String rnotes=null;
					int rvotes=0;
					while(rs.next())
					{
						rid=rs.getInt("incident_id");	
						rlat=rs.getString("incident_lat");	
						rlng=rs.getString("incident_long");	
						rcat=rs.getString("incident_category");	
						rpic=rs.getString("incident_picture");	
						rlocality=rs.getString("incident_locality");	
						rsubmitter=rs.getString("incident_submitter");	
						rowner=rs.getString("incident_owner");	
						rstate=rs.getString("incident_state");	
						rdatecreated=rs.getString("incident_date_created");	
						rdateclosed=rs.getString("incident_date_closed");	
						rseverity=rs.getInt("incident_severity");	
						rnotes=rs.getString("incident_notes");	
						rvotes=rs.getInt("incident_votes");	
						token=rid+1;
						break;
					}
					
					int returntoken=0;
					if(token > max)
						returntoken=0;
					else
						returntoken=token;
					
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("returnCode", 0);
					ResponseObj.put("returnToken", returntoken);
					ResponseObj.put("id", rid);
					ResponseObj.put("lat", rlat);
					ResponseObj.put("lng", rlng);
					ResponseObj.put("cat", rcat);
					ResponseObj.put("pic", rpic);
					ResponseObj.put("locality", rlocality);
					ResponseObj.put("submitter", rsubmitter);
					ResponseObj.put("owner", rowner);
					ResponseObj.put("state", rstate);
					ResponseObj.put("datecreated", rdatecreated);
					ResponseObj.put("dateclosed", rdateclosed);
					ResponseObj.put("severity", rseverity);
					ResponseObj.put("notes", rnotes);
					ResponseObj.put("votes", rvotes);
					
					ResponseObj.put("slat", lat);
					ResponseObj.put("slng", lng);
					ResponseObj.put("scat", cat);
					ResponseObj.put("slocality", locality);
					

					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		
		ObjectMapper objectmapper = new ObjectMapper();
		SearchBugReq searchbugreq = objectmapper.readValue(request.getInputStream(), SearchBugReq.class);
		response.setContentType("application/json; charset=UTF-8");
		

			try
			{
					int token=Integer.parseInt(searchbugreq.getToken());
					int id=Integer.parseInt(searchbugreq.getId());
					String locality=searchbugreq.getLocality();
					String lat=searchbugreq.getLat();
					String lng=searchbugreq.getLng();
					String cat=searchbugreq.getCat();
					int max=0,flag=0;

					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					
					
				if(id==0)
				{
							query="select * from incident";
						
							if(!lat.equals(""))
							{
								query=query+" where incident_lat='"+lat+"' ";
								flag=1;
							}
							
							if(!lng.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_long='"+lng+"' ";
									flag=1;
								}
								else
									query=query+" and incident_long='"+lng+"' ";
							}
									
							if(!cat.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_category='"+cat+"' ";
									flag=1;
								}
								else
									query=query+" and incident_category='"+cat+"' ";
							}
									
							if(!locality.equals(""))
							{
								if(flag==0)
								{
									query=query+" where incident_locality='"+locality+"' ";
									flag=1;
								}
								else
									query=query+" and incident_locality='"+locality+"' ";
							}
							
					String mquery = "select * from incident";
					rs=dbconn.getResult(mquery, con);
					while(rs.next())
					{
						max=(rs.getInt("incident_id"));
					}
					rs.close();
				
					query=query+" and incident_id between "+(token)+" and "+max+"";
				
					if(flag==0)
						query="select * from incident where incident_id between "+token+" and "+max+" ";
			}		
			else
					query="select * from incident where incident_id='"+id+"'";
				
					rs=dbconn.getResult(query, con);
					int rid = 0;
					String rlat = null;
					String rlng=null;
					String rcat=null;
					String rpic=null;
					String rlocality=null;
					String rsubmitter=null;
					String rowner=null;
					String rstate=null;
					String rdatecreated=null;
					String rdateclosed=null;
					int rseverity=0;
					String rnotes=null;
					int rvotes=0;
					while(rs.next())
					{
						rid=rs.getInt("incident_id");	
						rlat=rs.getString("incident_lat");	
						rlng=rs.getString("incident_long");	
						rcat=rs.getString("incident_category");	
						rpic=rs.getString("incident_picture");	
						rlocality=rs.getString("incident_locality");	
						rsubmitter=rs.getString("incident_submitter");	
						rowner=rs.getString("incident_owner");	
						rstate=rs.getString("incident_state");	
						rdatecreated=rs.getString("incident_date_created");	
						rdateclosed=rs.getString("incident_date_closed");	
						rseverity=rs.getInt("incident_severity");	
						rnotes=rs.getString("incident_notes");	
						rvotes=rs.getInt("incident_votes");	
						token=rid+1;
						break;
					}
					
					int returntoken=0;
					if(token > max)
						returntoken=0;
					else
						returntoken=token;
					
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("returnCode", 0);
					ResponseObj.put("returnToken", returntoken);
					ResponseObj.put("id", rid);
					ResponseObj.put("lat", rlat);
					ResponseObj.put("lng", rlng);
					ResponseObj.put("cat", rcat);
					ResponseObj.put("pic", rpic);
					ResponseObj.put("locality", rlocality);
					ResponseObj.put("submitter", rsubmitter);
					ResponseObj.put("owner", rowner);
					ResponseObj.put("state", rstate);
					ResponseObj.put("datecreated", rdatecreated);
					ResponseObj.put("dateclosed", rdateclosed);
					ResponseObj.put("severity", rseverity);
					ResponseObj.put("notes", rnotes);
					ResponseObj.put("votes", rvotes);
					
					ResponseObj.put("slat", lat);
					ResponseObj.put("slng", lng);
					ResponseObj.put("scat", cat);
					ResponseObj.put("slocality", locality);
					

					response.setContentType("text/json");				
					response.setContentType("application/json; charset=UTF-8");
					PrintWriter printout = response.getWriter();
					printout.print(ResponseObj.toString());
			}
			
			catch(Exception e)
			{
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
