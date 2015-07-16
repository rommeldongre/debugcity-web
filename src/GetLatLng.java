

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class GetLatLng
 */
@WebServlet("/GetLatLng")
public class GetLatLng extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLatLng() {
        super();
        // TODO Auto-generated constructor stub
    }

    DataBaseConn dbconn;
   	Statement stmt;
   	String query;
   	Connection con;
   	ResultSet rs;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
      
		response.setContentType("application/json; charset=UTF-8");
		
		try{
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		//System.out.println("connected");
		stmt=(Statement) con.createStatement();
		String query = "select * from incident";
		rs=dbconn.getResult(query, con);
		int count=0;
		while(rs.next())
		{
			count++;
		}
		rs.close();
		
		query = "select * from incident";
		rs=dbconn.getResult(query, con);
		float[][] arr=new float[count][3];
		count=0;
		while(rs.next())
		{
			arr[count][0]=rs.getInt("incident_id");
			arr[count][1]=Float.parseFloat(rs.getString("incident_lat"));
			arr[count][2]=Float.parseFloat(rs.getString("incident_long"));
			count++;		
		}
		if(con!=null)
			con.close();
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("returnCode",arr);	
			//System.out.println(arr[1]);
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
