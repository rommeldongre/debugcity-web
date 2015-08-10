package service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class GetLocationVectorReq
 */
@WebServlet("/GetLocationVectorReq")
public class GetLocationVectorReq extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocationVectorReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String location;

	public String getLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	public void setLocation(String location)
	{
	    this.location = location;
	}	
}
