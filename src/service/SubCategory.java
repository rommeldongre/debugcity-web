package service;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import util.DataBaseConn;

import com.mysql.jdbc.Statement;

@WebServlet("/SubCategory")
public class SubCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubCategory() {
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
		String catname = null;
		try
		{
			SubCategoryReq subcatreq = objectmapper.readValue(request.getInputStream(), SubCategoryReq.class);
			//System.out.println(subuserreq);
			try
			{
				
						   catname=subcatreq.getCatname();
					String catdesc=subcatreq.getCatdesc();
					String catparent=subcatreq.getCatparent();
					String catchild=subcatreq.getCatchild();
					
					//System.out.println(objectmapper.writeValueAsString(subuserreq));
					
				
						dbconn=new DataBaseConn();
						con = dbconn.setConnection ();
						//System.out.print("connected");
						String sql = "insert into category (cat_name, cat_desc, cat_parent, cat_child) values (?,?,?,?)"; 
						PreparedStatement prep= con.prepareStatement(sql); 
						prep.setString(1, catname); 
						prep.setString(2, catdesc); 
						prep.setString(3, catparent); 
						prep.setString(4, catchild);
						prep.executeUpdate(); 
						prep.close(); 
						if(con!=null)
					    	con.close();
						
						//System.out.print(fullname+""+mobile+""+location);
						
						/*dbconn=new DataBaseConn();
						con = dbconn.setConnection ();
						//System.out.println("connected");
						stmt=(Statement) con.createStatement();
						String query = "select * from category";
						rs=dbconn.getResult(query, con);
						while(rs.next())
						{
							catname = rs.getString("cat_name");
						}
						rs.close();
						if(con!=null)
							con.close();
						*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			JSONObject ResponseObj=new JSONObject();
			//System.out.println(catname);
			ResponseObj.put("catname", catname);
			response.setContentType("text/json");				
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printout = response.getWriter();
			printout.print(ResponseObj.toString());
		}
		catch (JsonGenerationException e)
	    {
	        e.printStackTrace();
	    }
		catch (JsonMappingException e)
	    {
	        e.printStackTrace();
	    }
		catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}

}
