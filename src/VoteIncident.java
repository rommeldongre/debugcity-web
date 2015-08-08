

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

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class VoteIncident
 */
@WebServlet("/VoteIncident")
public class VoteIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteIncident() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    DataBaseConn dbconn;
   	PreparedStatement stmt;
   	String query;
   	Connection con;
   	ResultSet rs;
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper objectmapper = new ObjectMapper();
		VoteIncidentReq vir= objectmapper.readValue(request.getInputStream(), VoteIncidentReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try
		{
			
			String id=vir.getId();
			String user=vir.getUser();
				
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();
			
			int count=0;
			
			query="select * from votes where (vote_user='"+user+"' && vote_incident_id='"+id+"')";
			rs=dbconn.getResult(query, con);
			while(rs.next())
			{
				count++;
			}
			rs.close();
			
			if(count==0)
			{
				query = "insert into votes (vote_user, vote_incident_id) values (?,?)"; 
				PreparedStatement prep= (PreparedStatement) con.prepareStatement(query); 
				prep.setString(1, user); 
				prep.setString(2, id);
				prep.executeUpdate(); 
				prep.close(); 
				
				query = "UPDATE incident SET incident_votes =  (incident_votes + 1) WHERE incident_id='"+id+"'";
				stmt=(PreparedStatement) con.prepareStatement(query);
				stmt.executeUpdate();
				
				count=1;
			}
			else
				count=0;
			
			if(con!=null)
				con.close();

			JSONObject ResponseObj=new JSONObject();
			try {
				ResponseObj.put("votres", count);	
			} 
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ObjectMapper objectmapper = new ObjectMapper();
		VoteIncidentReq vir= objectmapper.readValue(request.getInputStream(), VoteIncidentReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try
		{
			
			String id=vir.getId();
			String user=vir.getUser();
				
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();
			
			int count=0;
			
			query="select * from votes where (vote_user='"+user+"' && vote_incident_id='"+id+"')";
			rs=dbconn.getResult(query, con);
			while(rs.next())
			{
				count++;
			}
			rs.close();
			
			if(count==0)
			{
				query = "insert into votes (vote_user, vote_incident_id) values (?,?)"; 
				PreparedStatement prep= (PreparedStatement) con.prepareStatement(query); 
				prep.setString(1, user); 
				prep.setString(2, id);
				prep.executeUpdate(); 
				prep.close(); 
				
				query = "UPDATE incident SET incident_votes =  (incident_votes + 1) WHERE incident_id='"+id+"'";
				stmt=(PreparedStatement) con.prepareStatement(query);
				stmt.executeUpdate();
				
				count=1;
			}
			else
				count=0;
			
			if(con!=null)
				con.close();

			JSONObject ResponseObj=new JSONObject();
			try {
				ResponseObj.put("votres", count);	
			} 
			catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
