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

/**
 * Servlet implementation class FilterCategory
 */
@WebServlet("/FilterCategory")
public class FilterCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterCategory() {
        super();
        // TODO Auto-generated constructor stub
    }

    DataBaseConn dbconn;
   	Statement stmt;
   	String query;
   	Connection con;
   	ResultSet rs;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ObjectMapper objectmapper = new ObjectMapper();
		FilterCategoryReq filtercatreq = objectmapper.readValue(request.getInputStream(), FilterCategoryReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try{
			
		String category=filtercatreq.getCategory();
		String query=null;
		
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		//System.out.println("connected");
		stmt=(Statement) con.createStatement();
		if(!category.equals(""))
			query = "select * from incident where incident_category='"+category+"'";
		else
			query = "select * from incident";
		
		rs=dbconn.getResult(query, con);
		int count=0;
		while(rs.next())
		{
			count++;
		}
		rs.close();
		
		rs=dbconn.getResult(query, con);
		float[][] arr1=new float[count][5];
		String[][] arr2=new String[count][9];
		count=0;
		while(rs.next())
		{
			arr1[count][0]=rs.getInt("incident_id");
			arr1[count][1]=Float.parseFloat(rs.getString("incident_lat"));
			arr1[count][2]=Float.parseFloat(rs.getString("incident_long"));
			arr1[count][3]=rs.getInt("incident_severity");
			arr1[count][4]=rs.getInt("incident_votes");
			
			
			
			arr2[count][0]=rs.getString("incident_category");
			arr2[count][1]=rs.getString("incident_picture");
			arr2[count][2]=rs.getString("incident_locality");
			arr2[count][3]=rs.getString("incident_submitter");
			arr2[count][4]=rs.getString("incident_owner");
			arr2[count][5]=rs.getString("incident_state");
			arr2[count][6]=rs.getString("incident_date_created");
			arr2[count][7]=rs.getString("incident_date_closed");
			arr2[count][8]=rs.getString("incident_notes");
			
			count++;		
		}
		if(con!=null)
			con.close();
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
		} catch (JSONException e) {
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ObjectMapper objectmapper = new ObjectMapper();
		FilterCategoryReq filtercatreq = objectmapper.readValue(request.getInputStream(), FilterCategoryReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try{
			
		String category=filtercatreq.getCategory();
		String query=null;
		
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		//System.out.println("connected");
		stmt=(Statement) con.createStatement();
		if(!category.equals(""))
			query = "select * from incident where incident_category='"+category+"'";
		else
			query = "select * from incident";
		
		rs=dbconn.getResult(query, con);
		int count=0;
		while(rs.next())
		{
			count++;
		}
		rs.close();
		
		rs=dbconn.getResult(query, con);
		float[][] arr1=new float[count][5];
		String[][] arr2=new String[count][9];
		count=0;
		while(rs.next())
		{
			arr1[count][0]=rs.getInt("incident_id");
			arr1[count][1]=Float.parseFloat(rs.getString("incident_lat"));
			arr1[count][2]=Float.parseFloat(rs.getString("incident_long"));
			arr1[count][3]=rs.getInt("incident_severity");
			arr1[count][4]=rs.getInt("incident_votes");
			
			
			
			arr2[count][0]=rs.getString("incident_category");
			arr2[count][1]=rs.getString("incident_picture");
			arr2[count][2]=rs.getString("incident_locality");
			arr2[count][3]=rs.getString("incident_submitter");
			arr2[count][4]=rs.getString("incident_owner");
			arr2[count][5]=rs.getString("incident_state");
			arr2[count][6]=rs.getString("incident_date_created");
			arr2[count][7]=rs.getString("incident_date_closed");
			arr2[count][8]=rs.getString("incident_notes");
			
			count++;		
		}
		if(con!=null)
			con.close();
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
		} catch (JSONException e) {
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
