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
		
		try{
			
		String category=filtercatreq.getCategory();
		//System.out.println(category);
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		//System.out.println("connected");
		stmt=(Statement) con.createStatement();
		int i=0,flag=0;
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
					query = query+" where incident_category='"+parts[i]+"'";
					flag=1;
				}
				else
					query=query+" OR incident_category='"+parts[i]+"'";
			}	
		}
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
		query = "select distinct incident_locality from incident";
		rs=dbconn.getResult(query, con);
		
		while(rs.next())
		{
			counter++;
		}
		rs.close();
		
		query = "select distinct incident_locality from incident where incident_locality!='Unknown'";
		rs=dbconn.getResult(query, con);
		
		String uniloc[]=new String[counter];
		counter=0;
		
		while(rs.next())
		{
			uniloc[counter]=rs.getString("incident_locality");
			counter++;
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
		//System.out.println(no);
		
		
		
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
			ResponseObj.put("category",category);	
			ResponseObj.put("no",no);		
			ResponseObj.put("uniloc",uniloc);
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
		//System.out.println(category);
		dbconn=new DataBaseConn();
		con = dbconn.setConnection ();
		//System.out.println("connected");
		stmt=(Statement) con.createStatement();
		int i=0,flag=0;
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
					query = query+" where incident_category='"+parts[i]+"'";
					flag=1;
				}
				else
					query=query+" OR incident_category='"+parts[i]+"'";
			}	
		}
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
		//System.out.println(no);
		
		JSONObject ResponseObj=new JSONObject();
		try {
			ResponseObj.put("arr1",arr1);	
			ResponseObj.put("arr2",arr2);	
			ResponseObj.put("category",category);	
			ResponseObj.put("no",no);			
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