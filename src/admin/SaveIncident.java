package admin;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import util.DataBaseConn;

import com.mysql.jdbc.Statement;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Servlet implementation class SaveIncident
 */
@WebServlet("/SaveIncident")
@MultipartConfig(maxFileSize = 16777215)
public class SaveIncident extends HttpServlet 
{

private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveIncident() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    DataBaseConn dbconn;
   	Statement stmt;
   	String query;
   	Connection con;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{ 
		try
		{
			//int incident_id=Integer.parseInt(request.getParameter("incident_id")); 
			String incident_lat=request.getParameter("incident_lat"); 
			String incident_long=request.getParameter("incident_long"); 
			String incident_category=request.getParameter("incident_category"); 
			String incident_locality=request.getParameter("incident_locality");  
			String incident_submitter=request.getParameter("incident_submitter"); 
			String incident_owner=request.getParameter("incident_owner"); 
			String incident_state=request.getParameter("incident_state"); 
			String incident_date_created=request.getParameter("incident_date_created"); 
			String incident_date_closed=request.getParameter("incident_date_closed"); 
			String str_incident_severity=request.getParameter("incident_severity");
			String str_incident_votes=request.getParameter("incident_votes");
			int incident_severity,incident_votes;
			if((str_incident_severity).equals(""))
				incident_severity=0;	
			else
				incident_severity=Integer.parseInt(str_incident_severity); 	
			String incident_notes=request.getParameter("incident_notes"); 
			if((str_incident_votes).equals(""))
				incident_votes=0;
			else
				incident_votes=Integer.parseInt(str_incident_votes); 
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-dd-MM hh:mm:ss");
			Date date_created = null,date_closed = null;
			Timestamp timestamp_created,timestamp_closed;
			if((incident_date_created).equals(""))
			{
					Date date=new Date();
					timestamp_created=new Timestamp(date.getTime());			
			}
			else
			{
				
					date_created = sdf.parse(incident_date_created);
					timestamp_created=new Timestamp(date_created.getTime());
			}
			if((incident_date_closed).equals(""))
			{
				timestamp_closed=null;
			}
			else
			{
				date_closed = sdf.parse(incident_date_closed);
				timestamp_closed=new Timestamp(date_closed.getTime());
			}
					String pic=request.getParameter("hidden");
					dbconn=new DataBaseConn();
					con = dbconn.setConnection();	 
					String sql = "insert into incident (incident_lat,incident_long,incident_category,incident_picture,incident_locality,"
							+ "incident_submitter,incident_owner,incident_state,incident_date_created"
							+ ",incident_date_closed,incident_severity,incident_notes,incident_votes) values (?,?,?,?,?,?,?,?,?,?,?,?,?)"; 
					PreparedStatement prep= con.prepareStatement(sql); 
					
					prep.setString(1, incident_lat);
					prep.setString(2, incident_long);
					prep.setString(3, incident_category);
					prep.setString(4, pic);
					prep.setString(5, incident_locality);
					prep.setString(6, incident_submitter);
					prep.setString(7, incident_owner);
					prep.setString(8, incident_state);
					prep.setTimestamp(9, timestamp_created);
					prep.setTimestamp(10, timestamp_closed);
					prep.setInt(11, incident_severity);
					prep.setString(12, incident_notes);
					prep.setInt(13, incident_votes);
					prep.executeUpdate(); 
					prep.close(); 
					RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/admin.jsp?msg=Inserted Successfully !");
					rd.forward(request, response);
			
			if(con!=null)
			con.close();
		}
		catch(Exception e)
		{
			RequestDispatcher rd=request.getRequestDispatcher("../adminjsp/Error.jsp?");
			rd.forward(request, response);
		}
	}
}