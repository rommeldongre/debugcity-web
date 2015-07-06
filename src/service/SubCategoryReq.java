package service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class SubUserReq
 */
@WebServlet("/SubCategoryReq")
public class SubCategoryReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubCategoryReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String catname;
	private String catdesc;
	private String catparent;
	private String catchild;

	public String getCatname() {
		// TODO Auto-generated method stub
		return catname;
	}
	public void setCatname(String catname)
	{
	    this.catname = catname;
	}
	
	 
	public String getCatdesc() {
		// TODO Auto-generated method stub
		return catdesc;
	}
	public void setCatdesk(String catdesc)
	{
	    this.catdesc = catdesc;
	}
	 
	 
	public String getCatparent() {
		// TODO Auto-generated method stub
		return catparent;
	}
	public void setCatparent(String catparent)
	{
	    this.catparent = catparent;
	}
	 
	 
	public String getCatchild() {
		// TODO Auto-generated method stub
		return catchild;
	}
	public void setCatchild(String catchild)
	{
	    this.catchild = catchild;
	}
 
}
