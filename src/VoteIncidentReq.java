import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class VoteIncidentReq
 */
@WebServlet("/VoteIncidentReq")
public class VoteIncidentReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VoteIncidentReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String id;

	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(String id)
	{
	    this.id = id;
	}
}
