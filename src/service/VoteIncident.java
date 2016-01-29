package service;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import pojo.Incident;
import pojo.SystemUser;
import util.DataBaseConn;
import util.IncidentDb;

/**
 * Servlet implementation class VoteIncident
 */
@WebServlet("/VoteIncident")
public class VoteIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VoteIncident() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		JSONObject ResponseObj = new JSONObject();
		PrintWriter printout = response.getWriter();
		response.setContentType("application/json; charset=UTF-8");

		try {

			ObjectMapper objectmapper = new ObjectMapper();
			Incident incident = objectmapper.readValue(request.getInputStream(), Incident.class);

			IncidentDb incident_details = new IncidentDb();
			int check = incident_details.voteIncident(incident);

			ResponseObj.put("returnCode", 200);
			ResponseObj.put("returnToken", "");
			response.setContentType("application/json; charset=UTF-8");
			printout.print(ResponseObj.toString());

		}

		catch (Exception e) {

			int returnCode = 400;
			try {

				ResponseObj.put("returnCode", returnCode);
				ResponseObj.put("errorString", e.getMessage());
				response.setContentType("application/json; charset=UTF-8");
				ResponseObj.put("returnToken", "");
				printout.print(ResponseObj.toString());
				
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			printout.print(ResponseObj.toString());
		}
	}
}
