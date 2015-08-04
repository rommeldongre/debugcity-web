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
   	
   	public static boolean isNumeric(String str)  
   	{  
   	  try  
   	  {  
   	    double d = Double.parseDouble(str);  
   	  }  
   	  catch(NumberFormatException nfe)  
   	  {  
   	    return false;  
   	  }  
   	  return true;  
   	}
   	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ObjectMapper objectmapper = new ObjectMapper();
		FilterCategoryReq filtercatreq = objectmapper.readValue(request.getInputStream(), FilterCategoryReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try
		{
			
		String category=filtercatreq.getCategory();
		String location=filtercatreq.getLocation();
		String unilocation=location;
		
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		stmt=(Statement) con.createStatement();
		
		int i=0,flag=0,flagpart=0;
		String query=null;
		query = "select * from incident";
		if(!category.equals(""))
		{	
			category=category.substring(0,category.length()-1);
			String[] parts = category.split(" ");
			for(i=0;i<parts.length;i++)
			{
				if(flag==0)
				{	
					query = query+" where (incident_category='"+parts[i]+"'";
					flag=1;
				}
				else
					query=query+" OR incident_category='"+parts[i]+"'";
			}	
		}
		else
			query = "select * from incident";
		
		if(flag==1)
			query=query+")";
		
		if(!location.equals(""))
		{	
			location=location.substring(0,location.length()-1);
			String[] partsloc = location.split(" ");
			for(i=0;i<partsloc.length;i++)
			{
				if(flagpart==0)
				{	
					query = query+" AND (incident_locality='"+partsloc[i]+"'";
					flagpart=1;
				}
				else
					query=query+" OR incident_locality='"+partsloc[i]+"'";
			}	
		}
		if(flagpart==1)
			query=query+")";
		
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
			if(isNumeric(rs.getString("incident_lat")) && isNumeric(rs.getString("incident_long")))
			{
				if((!rs.getString("incident_lat").equals("")))
				{
					if((!rs.getString("incident_long").equals("")))
					{	
						if(Float.parseFloat(rs.getString("incident_lat")) > -85 && Float.parseFloat(rs.getString("incident_lat")) < 85 && Float.parseFloat(rs.getString("incident_long")) > -180 && Float.parseFloat(rs.getString("incident_long")) < 180)
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
					}
				}
			}	
		}
		
		rs.close();
		
		int counter=0;
		
		query = "select distinct incident_locality from incident where incident_locality!='Unknown'";
		
		int uniflagpart=0;
		
		if(!unilocation.equals(""))
		{	
			unilocation=unilocation.substring(0,unilocation.length()-1);
			String[] partsuniloc = unilocation.split(" ");
			for(i=0;i<partsuniloc.length;i++)
			{
				if(uniflagpart==0)
				{	
					query ="select distinct incident_locality from incident where incident_locality='"+partsuniloc[i]+"'";
					uniflagpart=1;
				}
				else
					query=query+" OR incident_locality='"+partsuniloc[i]+"'";
			}	
		}
		
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
		
		query="select distinct incident_category from incident";
		
		rs=dbconn.getResult(query, con);
		JSONObject catobj = new JSONObject();
		while(rs.next())
		{
			catobj.put(rs.getString("incident_category"), rs.getString("incident_category"));
		}
		rs.close();
		
		JSONObject spiallloc = new JSONObject();
		
		for(i=0;i<uniloc.length;i++)
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
		
		if(con!=null)
			con.close();
		
		String[] parts = category.split(" ");
		category="";
		for(i=0;i<parts.length;i++)
		{
			category=category+parts[i]+", ";
		}
		category=category.substring(0,category.length()-2);	
		int no=0;//arr1.length;
		
		for(i=0;i<arr1.length;i++)
		{
			if(arr1[i][0]!=0)
				no++;
		}
		
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
			ResponseObj.put("category",category);	
			ResponseObj.put("no",no);		
			ResponseObj.put("uniloc",uniloc);
			ResponseObj.put("obj",obj);	
			ResponseObj.put("catobj",catobj);	
			ResponseObj.put("spiallloc",spiallloc);	
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

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		ObjectMapper objectmapper = new ObjectMapper();
		FilterCategoryReq filtercatreq = objectmapper.readValue(request.getInputStream(), FilterCategoryReq.class);
		response.setContentType("application/json; charset=UTF-8");
		
		try{
			
		String category=filtercatreq.getCategory();
		String location=filtercatreq.getLocation();
		String unilocation=location;
		
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		stmt=(Statement) con.createStatement();
		
		int i=0,flag=0,flagpart=0;
		String query=null;
		query = "select * from incident";
		if(!category.equals(""))
		{	
			category=category.substring(0,category.length()-1);
			String[] parts = category.split(" ");
			for(i=0;i<parts.length;i++)
			{
				if(flag==0)
				{	
					query = query+" where (incident_category='"+parts[i]+"'";
					flag=1;
				}
				else
					query=query+" OR incident_category='"+parts[i]+"'";
			}	
		}
		else
			query = "select * from incident";
		
		if(flag==1)
			query=query+")";
		
		if(!location.equals(""))
		{	
			location=location.substring(0,location.length()-1);
			String[] partsloc = location.split(" ");
			for(i=0;i<partsloc.length;i++)
			{
				if(flagpart==0)
				{	
					query = query+" AND (incident_locality='"+partsloc[i]+"'";
					flagpart=1;
				}
				else
					query=query+" OR incident_locality='"+partsloc[i]+"'";
			}	
		}
		if(flagpart==1)
			query=query+")";
		
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
			if(isNumeric(rs.getString("incident_lat")) && isNumeric(rs.getString("incident_long")))
			{
				if((!rs.getString("incident_lat").equals("")))
				{
					if((!rs.getString("incident_long").equals("")))
					{	
						if(Float.parseFloat(rs.getString("incident_lat")) > -85 && Float.parseFloat(rs.getString("incident_lat")) < 85 && Float.parseFloat(rs.getString("incident_long")) > -180 && Float.parseFloat(rs.getString("incident_long")) < 180)
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
					}
				}
			}	
		}
		
		rs.close();
		
		int counter=0;
		
		query = "select distinct incident_locality from incident where incident_locality!='Unknown'";
		
		int uniflagpart=0;
		
		if(!unilocation.equals(""))
		{	
			unilocation=unilocation.substring(0,unilocation.length()-1);
			String[] partsuniloc = unilocation.split(" ");
			for(i=0;i<partsuniloc.length;i++)
			{
				if(uniflagpart==0)
				{	
					query ="select distinct incident_locality from incident where incident_locality='"+partsuniloc[i]+"'";
					uniflagpart=1;
				}
				else
					query=query+" OR incident_locality='"+partsuniloc[i]+"'";
			}	
		}
		
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
		
		query="select distinct incident_category from incident";
		
		rs=dbconn.getResult(query, con);
		JSONObject catobj = new JSONObject();
		while(rs.next())
		{
			catobj.put(rs.getString("incident_category"), rs.getString("incident_category"));
		}
		rs.close();
		
		JSONObject spiallloc = new JSONObject();
		
		for(i=0;i<uniloc.length;i++)
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
		
		if(con!=null)
			con.close();
		
		String[] parts = category.split(" ");
		category="";
		for(i=0;i<parts.length;i++)
		{
			category=category+parts[i]+", ";
		}
		category=category.substring(0,category.length()-2);	
		int no=0;//arr1.length;
		
		for(i=0;i<arr1.length;i++)
		{
			if(arr1[i][0]!=0)
				no++;
		}
		
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
			ResponseObj.put("category",category);	
			ResponseObj.put("no",no);		
			ResponseObj.put("uniloc",uniloc);
			ResponseObj.put("obj",obj);	
			ResponseObj.put("catobj",catobj);	
			ResponseObj.put("spiallloc",spiallloc);	
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