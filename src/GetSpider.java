

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

@WebServlet("/GetSpider")
public class GetSpider extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetSpider() {
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
	
		response.setContentType("application/json; charset=UTF-8");
		try
		{
				
					JSONObject ideal = new JSONObject();
					JSONObject unknown = new JSONObject();
					ideal.put("garbage", "0");
					ideal.put("noise", "0");
					ideal.put("queues", "0");
					ideal.put("spitting", "0");
					ideal.put("traffic", "0");
					
					unknown.put("garbage", "0");
					unknown.put("noise", "0");
					unknown.put("queues", "0");
					unknown.put("spitting", "0");
					unknown.put("traffic", "0");
					
				    dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					String query = "select incident_category, count(*) as count from incident where incident_locality!='Unknown' group by incident_category";
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
						{
							ideal.put("garbage", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
						{
							ideal.put("noise", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
						{
							ideal.put("queues", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
						{
							ideal.put("spitting", rs.getString("count"));
						}
						else 
						{	
							ideal.put("traffic", rs.getString("count"));
						}
					}
					rs.close();
					
					query = "select incident_category, count(*) as count from incident where incident_locality='Unknown' group by incident_category";
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{

						if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
						{
							unknown.put("garbage", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
						{
							unknown.put("noise", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
						{
							unknown.put("queues", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
						{
							unknown.put("spitting", rs.getString("count"));
						}
						else 
						{	
							unknown.put("traffic", rs.getString("count"));
						}
					}
					rs.close();
					
					if(con!=null)
						con.close();
					
					//System.out.println(ideal);
					//System.out.println(unknown);
					
					JSONArray spider=new JSONArray();
					spider.put(ideal);
					spider.put(unknown);
					//System.out.println(spider);
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("spider",spider);
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
		response.setContentType("application/json; charset=UTF-8");
		try
			{
				
					JSONObject ideal = new JSONObject();
					JSONObject unknown = new JSONObject();
					ideal.put("garbage", "0");
					ideal.put("noise", "0");
					ideal.put("queues", "0");
					ideal.put("spitting", "0");
					ideal.put("traffic", "0");
					
					unknown.put("garbage", "0");
					unknown.put("noise", "0");
					unknown.put("queues", "0");
					unknown.put("spitting", "0");
					unknown.put("traffic", "0");
					
				    dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					String query = "select incident_category, count(*) as count from incident where incident_locality!='Unknown' group by incident_category";
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
						{
							ideal.put("garbage", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
						{
							ideal.put("noise", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
						{
							ideal.put("queues", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
						{
							ideal.put("spitting", rs.getString("count"));
						}
						else 
						{	
							ideal.put("traffic", rs.getString("count"));
						}
					}
					rs.close();
					
					query = "select incident_category, count(*) as count from incident where incident_locality='Unknown' group by incident_category";
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{

						if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
						{
							unknown.put("garbage", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
						{
							unknown.put("noise", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
						{
							unknown.put("queues", rs.getString("count"));
						}
						else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
						{
							unknown.put("spitting", rs.getString("count"));
						}
						else 
						{	
							unknown.put("traffic", rs.getString("count"));
						}
					}
					rs.close();
					
					if(con!=null)
						con.close();
					
					//System.out.println(ideal);
					//System.out.println(unknown);
					
					JSONArray spider=new JSONArray();
					spider.put(ideal);
					spider.put(unknown);
					//System.out.println(spider);
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("spider",spider);
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
