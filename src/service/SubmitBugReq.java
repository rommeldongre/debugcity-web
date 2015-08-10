package service;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class SubBugReq
 */
@WebServlet("/SubmitBugReq")
public class SubmitBugReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitBugReq() {
        super();
        // TODO Auto-generated constructor stub
    }

	private String lat;
	private String lng;
	private String cat;
	private String pic;
	private String locality;
	private String submitter;
	private String owner;
	private String state;
	private String severity;
	private String notes;
	private String votes;
	
	 public String getLat()
	 {
	     return lat;
	 }
	 public void setLat(String lat)
	 {
	     this.lat = lat;
	 }

	 public String getLng()
	 {
	     return lng;
	 }
	 public void setLng(String lng)
	 {
	     this.lng = lng;
	 }

	 
	 public String getCat()
	 {
	     return cat;
	 }
	 public void setCat(String cat)
	 {
	     this.cat = cat;
	 }

	 
	 public String getPic()
	 {
	     return pic;
	 }
	 public void setPic(String pic)
	 {
	     this.pic = pic;
	 }

	 
	 public String getLocality()
	 {
	     return locality;
	 }
	 public void setLocality(String locality)
	 {
	     this.locality = locality;
	 }

	 
	 public String getSubmitter()
	 {
	     return submitter;
	 }
	 public void setSubmitter(String submitter)
	 {
	     this.submitter = submitter;
	 }

	 
	 public String getOwner()
	 {
	     return owner;
	 }
	 public void setOwner(String owner)
	 {
	     this.owner = owner;
	 }

	 
	 public String getState()
	 {
	     return state;
	 }
	 public void setState(String state)
	 {
	     this.state = state;
	 }

	
	 /*
	 public String getDateclosed()
	 {
	     return dateclosed;
	 }
	 public void setDateclosed(String dateclosed)
	 {
	     this.dateclosed = dateclosed;
	 }*/

	 
	 public String getSeverity()
	 {
	     return severity;
	 }
	 public void setSeverity(String severity)
	 {
	     this.severity = severity;
	 }

	 
	 public String getNotes()
	 {
	     return notes;
	 }
	 public void setNotes(String notes)
	 {
	     this.notes = notes;
	 }

	 
	 public String getVotes()
	 {
	     return votes;
	 }
	 public void setVotes(String votes)
	 {
	     this.votes = votes;
	 }
	 
	/* 
	public String getDatecreated() {
		// TODO Auto-generated method stub
		return datecreated;
	}
	public void setDatecreated(String datecreated)
	{
	    this.datecreated = datecreated;
	}*/
	
}
