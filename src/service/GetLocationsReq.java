package service;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class GetLocationsReq
 */

@WebServlet("/GetLocationsReq")
public class GetLocationsReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetLocationsReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String token;

	public String getToken() {
		// TODO Auto-generated method stub
		return token;
	}

	public void setToken(String token)
	{
	    this.token = token;
	}	
}
