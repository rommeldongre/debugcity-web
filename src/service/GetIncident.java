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
 * Servlet implementation class GetIncident
 */
@WebServlet(description = "Get the incidents", urlPatterns = { "/GetIncident" })
public class GetIncident extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetIncident() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

		try {

            ObjectMapper objectmapper = new ObjectMapper();
			Incident incident = objectmapper.readValue(request.getInputStream(), Incident.class);

			System.out.println(incident.getIncident_id());

			IncidentDb incident_details = new IncidentDb();
			JSONObject ResponseObj = incident_details.getIncident(incident);

			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printout = response.getWriter();
			printout.print(ResponseObj.toString());
//			printout.print("anway".toString());

		}

		catch (Exception e) {

			int returnCode = 400;
			JSONObject ResponseObj = new JSONObject();
			try {

				ResponseObj.put("returnCode", returnCode);
				ResponseObj.put("errorString", e.getMessage());
				ResponseObj.put("returnToken", "");

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			response.setContentType("text/json");
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter printout = response.getWriter();
			printout.print(ResponseObj.toString());
		}
		
	}
}
