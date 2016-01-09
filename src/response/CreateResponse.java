package response;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import pojo.SystemUser;

public class CreateResponse {

	/**
	 * Make a function for creating a response
	 * */
	public JSONObject createResonseForUser(SystemUser myObject,String[] keys)
	{
	
		System.out.println(myObject.getUser_full_name());
		
		return null;	
	}
}
