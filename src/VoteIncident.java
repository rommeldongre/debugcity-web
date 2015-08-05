

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import util.DataBaseConn;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
			
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();
			query = "UPDATE incident SET incident_votes =  (incident_votes + 1) WHERE incident_id='"+id+"'";
			stmt=(PreparedStatement) con.prepareStatement(query);
			stmt.executeUpdate();
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
			
			dbconn=new DataBaseConn();
			con = dbconn.setConnection ();
			query = "UPDATE incident SET incident_votes =  (incident_votes + 1) WHERE incident_id='"+id+"'";
			stmt=(PreparedStatement) con.prepareStatement(query);
			stmt.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
