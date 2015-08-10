import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
				    dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					
					int counter=0;
					
					query = "select distinct incident_locality from incident where incident_locality!='unknown'";
					
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						counter++;
					}
					rs.close();
					
					rs=dbconn.getResult(query, con);
					
					String uniloc[]=new String[counter];
					counter=0;
					
					JSONObject obj = new JSONObject();
					
					while(rs.next())
					{
						uniloc[counter]=rs.getString("incident_locality");
						obj.put(uniloc[counter], uniloc[counter]);
						counter++;
					}
					rs.close();
					
					JSONObject spiallloc = new JSONObject();
					
					for(int i=0;i<uniloc.length;i++)
					{
						JSONObject spiuniloc = new JSONObject();
						spiuniloc.put("garbage", 0);
						spiuniloc.put("noise", 0);
						spiuniloc.put("queues", 0);
						spiuniloc.put("spitting", 0);
						spiuniloc.put("traffic", 0);
						query = "select incident_category, count(*) as count from incident where incident_locality='"+ uniloc[i] +"' group by incident_category";
						rs=dbconn.getResult(query, con);
						
						while(rs.next())
						{
							if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
							{
								spiuniloc.put("garbage", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
							{
								spiuniloc.put("noise", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
							{
								spiuniloc.put("queues", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
							{
								spiuniloc.put("spitting", rs.getInt("count"));
							}
							else 
							{	
								spiuniloc.put("traffic", rs.getInt("count"));
							}
						}
						spiallloc.put(uniloc[i], spiuniloc);
					}

					rs.close();
					
					
					query = "select incident_locality, count(*) as count from incident where incident_category='garbage' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					
					int[] topscore=new int[5];
					int c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[0]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='queues' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[1]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='noise' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[2]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='spitting' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[3]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='traffic' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[4]=rs.getInt("count");
						c++;
					}

					
					if(con!=null)
						con.close();
					
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("spiallloc",spiallloc);	
					ResponseObj.put("obj",obj);	
					ResponseObj.put("topscore",topscore);	
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
				    dbconn=new DataBaseConn();
					con = dbconn.setConnection ();
					stmt=(Statement) con.createStatement();
					
					int counter=0;
					
					query = "select distinct incident_locality from incident where incident_locality!='unknown'";
					
					rs=dbconn.getResult(query, con);
					
					while(rs.next())
					{
						counter++;
					}
					rs.close();
					
					rs=dbconn.getResult(query, con);
					
					String uniloc[]=new String[counter];
					counter=0;
					
					JSONObject obj = new JSONObject();
					
					while(rs.next())
					{
						uniloc[counter]=rs.getString("incident_locality");
						obj.put(uniloc[counter], uniloc[counter]);
						counter++;
					}
					rs.close();
					
					JSONObject spiallloc = new JSONObject();
					
					for(int i=0;i<uniloc.length;i++)
					{
						JSONObject spiuniloc = new JSONObject();
						spiuniloc.put("garbage", 0);
						spiuniloc.put("noise", 0);
						spiuniloc.put("queues", 0);
						spiuniloc.put("spitting", 0);
						spiuniloc.put("traffic", 0);
						query = "select incident_category, count(*) as count from incident where incident_locality='"+ uniloc[i] +"' group by incident_category";
						rs=dbconn.getResult(query, con);
						
						while(rs.next())
						{
							if(rs.getString("incident_category").equalsIgnoreCase("garbage"))
							{
								spiuniloc.put("garbage", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("noise"))
							{
								spiuniloc.put("noise", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("queues"))
							{
								spiuniloc.put("queues", rs.getInt("count"));
							}
							else if(rs.getString("incident_category").equalsIgnoreCase("spitting"))
							{
								spiuniloc.put("spitting", rs.getInt("count"));
							}
							else 
							{	
								spiuniloc.put("traffic", rs.getInt("count"));
							}
						}
						spiallloc.put(uniloc[i], spiuniloc);
					}

					rs.close();
					
					
					query = "select incident_locality, count(*) as count from incident where incident_category='garbage' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					
					int[] topscore=new int[5];
					int c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[0]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='queues' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[1]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='noise' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[2]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='spitting' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[3]=rs.getInt("count");
						c++;
					}

					rs.close();
					
					query = "select count(*) as count from incident where incident_category='traffic' group by incident_locality ORDER BY count DESC";
					rs=dbconn.getResult(query, con);
					c=0;
					while(rs.next())
					{
						if(c==1)
							break;
						topscore[4]=rs.getInt("count");
						c++;
					}

					
					if(con!=null)
						con.close();
					
					JSONObject ResponseObj=new JSONObject();
					ResponseObj.put("spiallloc",spiallloc);	
					ResponseObj.put("obj",obj);	
					ResponseObj.put("topscore",topscore);	
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