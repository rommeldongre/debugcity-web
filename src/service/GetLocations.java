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

		try
		{
				
					int token=Integer.parseInt(getlocreq.getToken());
					int max=0;
					int count = 0;
					String locality[]=new String[10];
					int returntoken=0;
					
					for(int i=0;i<10;i++)
					{
						locality[i]="";
					}
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					//String query = "select * from incident where incident_locality='"+location+"' or incident_lat='"+location+"' or incident_long='"+location+"' or incident_category='"+location+"' or incident_picture='"+location+"' or incident_submitter='"+location+"' or incident_owner='"+location+"' or incident_state='"+location+"' or incident_date_created='"+location+"'or incident_date_closed='"+location+"' or incident_notes='"+location+"'";
					String query=null;
					
					int min=0;
					
					query="select * from incident";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						min=rs.getInt("incident_id");
						rs.close();
						break;
					}
					
					if(token==0)
					{
						token=min;
					}
					
					query="select * from incident";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						max=(rs.getInt("incident_id"));
					}
					rs.close();
					
					String check[]=new String[max]; 
					int i=0;
					
					query="select distinct incident_locality from incident where incident_id between "+min+" and "+(token-1)+"";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						check[i]=(rs.getString("incident_locality"));
						i++;
					}
					rs.close();
					
					int flag;
					
					query="select distinct incident_locality from incident where incident_id between "+token+" and "+(token+9)+"";
					
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						flag=0;
						
						for(int j=0;j<i;j++)
						{
							if(rs.getString("incident_locality").equals(check[j]))
							{
								flag=1;
								break;
							}
						}
						
						if(flag==0)
						{
							locality[count]=rs.getString("incident_locality");
							//noDuplicate.add(locality[count]);
							count++;
						}
					}
					
					
					String[] locfinal = new String[count];
					
					for(i=0;i<count;i++)
					{
						locfinal[i] = locality[i];
					}
					
					returntoken=token+10;
					
					if((returntoken)>max)
					{
						returntoken=0;
					}
					
					//String[] localityArray = new String[noDuplicate.size()];
					//noDuplicate.toArray(localityArray);
					
				/*	String locality_str="";
					for(int i=0;i<count;i++)
					{
						locality_str=locality_str+","+locality[count];
					}
				*/	
					rs.close();
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("count", count);
					ResponseObj.put("locality", locfinal);
					ResponseObj.put("returnCode", 0);
					ResponseObj.put("returnToken", returntoken);
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
					ResponseObj.put("count", 0);
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
		GetLocationsReq getlocreq = objectmapper.readValue(request.getInputStream(), GetLocationsReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
			//int bugId = 0; 

			try
			{
				
					int token=Integer.parseInt(getlocreq.getToken());
					int max=0;
					int count = 0;
					String locality[]=new String[10];
					int returntoken=0;
					
					for(int i=0;i<10;i++)
					{
						locality[i]="";
					}
					dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					//System.out.println("connected");
					stmt=(Statement) con.createStatement();
					//String query = "select * from incident where incident_locality='"+location+"' or incident_lat='"+location+"' or incident_long='"+location+"' or incident_category='"+location+"' or incident_picture='"+location+"' or incident_submitter='"+location+"' or incident_owner='"+location+"' or incident_state='"+location+"' or incident_date_created='"+location+"'or incident_date_closed='"+location+"' or incident_notes='"+location+"'";
					String query=null;
					
					if(token==0)
					{
						query="select * from incident";
						rs=dbconn.getResult(query, con);
						while(rs.next())
						{
							token=rs.getInt("incident_id");
							rs.close();
							break;
						}
						
					}
					
					query="select * from incident";
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						max=(rs.getInt("incident_id"));
					}
					rs.close();
					
					query="select distinct incident_locality from incident where incident_id between "+token+" and "+(token+9)+"";
					
					rs=dbconn.getResult(query, con);
					while(rs.next())
					{
						locality[count]=rs.getString("incident_locality");
						//noDuplicate.add(locality[count]);
						count++;
						if(count==10)
						{
							//returntoken=token+10;
							break;
						}	
					}
					
					
					String[] locfinal = new String[count];
					
					for(int i=0;i<count;i++)
					{
						locfinal[i] = locality[i];
					}
					
					returntoken=token+10;
					
					if((returntoken)>max)
					{
						returntoken=0;
					}
					
					//String[] localityArray = new String[noDuplicate.size()];
					//noDuplicate.toArray(localityArray);
					
				/*	String locality_str="";
					for(int i=0;i<count;i++)
					{
						locality_str=locality_str+","+locality[count];
					}
				*/	
					rs.close();
					if(con!=null)
						con.close();
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("count", count);
					ResponseObj.put("locality", locfinal);
					ResponseObj.put("returnCode", 0);
					ResponseObj.put("returnToken", returntoken);
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
					ResponseObj.put("count", 0);
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
