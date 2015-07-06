package service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class SubUserReq
 */
@WebServlet("/SubUserReq")
public class SubUserReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubUserReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String fullname;
	private String mobile;
	private String location;
	private String auth;
	
	 public String getFullname()
	 {
	     return fullname;
	 }
	 public void setFullname(String fullname)
	 {
	     this.fullname = fullname;
	 }

	 public String getMobile()
	 {
	     return mobile;
	 }
	 public void setMobile(String mobile)
	 {
	     this.mobile = mobile;
	 }

	 
	 public String getLocation()
	 {
	     return location;
	 }
	 public void setLocation(String location)
	 {
	     this.location = location;
	 }
	 

	 public String getAuth() {
		// TODO Auto-generated method stub
		 return auth;
	 }
	 public void setAuth(String auth)
	 {
	     this.auth = auth;
	 }
	 
}
