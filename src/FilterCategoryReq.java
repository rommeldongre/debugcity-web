import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class FilterCategoryReq
 */
@WebServlet("/FilterCategoryReq")
public class FilterCategoryReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FilterCategoryReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String category;

	public String getCategory() {
		// TODO Auto-generated method stub
		return category;
	}

	public void setCategory(String category)
	{
	    this.category = category;
	}
	 	
}
