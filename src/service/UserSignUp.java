package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import pojo.SystemUser;
import response.CreateResponse;
import util.SystemUserDb;

/**
 * Servlet implementation class UserSignUp
 */
// @WebServlet("/UserSignUp")
@WebServlet(description = "User Signup", urlPatterns = { "/UserSignUp" })
public class UserSignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserSignUp() {
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
//		response.getWriter().append("Served at: ").append(request.getContextPath());
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
			SystemUser myUser = objectmapper.readValue(request.getInputStream(), SystemUser.class);

			SystemUserDb insertUser = new SystemUserDb();
			
			int a = insertUser.addUser(myUser);
			ResponseObj.put("returnCode", a);
			ResponseObj.put("returnToken", "");

			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			int returnCode = 1048;

			try {

				ResponseObj.put("returnCode", returnCode);
				ResponseObj.put("errorString", e.getMessage());
				ResponseObj.put("returnToken", "");

			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		response.setContentType("text/json");
		response.setContentType("application/json; charset=UTF-8");
		printout.print(ResponseObj.toString());
		
	}

}
