package service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import pojo.SystemUser;
import util.SystemUserDb;

/**
 * Servlet implementation class LoginService
 */
@WebServlet("/userlogin")
public class UserLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserLogin() {
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

		SystemUserDb insertUser = new SystemUserDb();
		PrintWriter printout = response.getWriter();

		try {
			ObjectMapper objectmapper = new ObjectMapper();
			SystemUser myUser = objectmapper.readValue(request.getInputStream(), SystemUser.class);

			JSONObject ResponseObj = insertUser.getUserInfo(myUser);
			System.out.println(ResponseObj);

			JSONObject myReturnObj = new JSONObject();
			String check = ResponseObj.getString("returnCode");

			
			System.out.println(check);

			if (check.equals("200")) {

				System.out.println("here");

				myReturnObj.put("returnCode", 200);
				myReturnObj.put("errorString", "");
				myReturnObj.put("returnToken", "");

			} else {

				myReturnObj.put("returnCode", 400);
				myReturnObj.put("errorString", "");
				myReturnObj.put("returnToken", "");
			}

			response.setContentType("application/json; charset=UTF-8");
			printout.print(myReturnObj.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
