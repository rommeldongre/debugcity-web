package service;

import java.io.Console;
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
 * Servlet implementation class GetUserInfo
 */
@WebServlet("/GetUserInfo")
public class GetUserInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GetUserInfo() {
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

			response.setContentType("application/json; charset=UTF-8");
			printout.print(ResponseObj.toString());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

}
