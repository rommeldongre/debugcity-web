package util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import pojo.SystemUser;

public class SystemUserDb {

	SystemUser user = new SystemUser();
	private SecureRandom random = new SecureRandom();

	DataBaseConn dbconn;
	Statement stmt;
	String query;
	Connection con;
	ResultSet rs;

	public int addUser(SystemUser myUser) {

		try {
			// String auth_key = new BigInteger(130, random).toString(32);
			dbconn = new DataBaseConn();
			con = dbconn.setConnection();

			String sql = "insert into users (user_full_name, user_mobile, user_location, user_auth) values (?,?,?,?)";
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, myUser.getUser_full_name());
			prep.setString(2, myUser.getUser_mobile());
			prep.setString(3, myUser.getUser_location());
			prep.setString(4, myUser.getUser_auth());

			int flag = prep.executeUpdate();
			prep.close();

			return flag;
//			return 200;
		} catch (SQLException e) {

			System.out.println(e.getMessage());
			return 1048;
		}
	}

	public JSONObject getUserInfo(SystemUser myUser) throws SQLException, JSONException {
		dbconn = new DataBaseConn();
		con = dbconn.setConnection();
		String query = "select * from users where user_id=" + myUser.getUser_id() + " and user_auth = "
				+ myUser.getUser_auth();
		rs = dbconn.getResult(query, con);

		JSONObject ResponseObj = new JSONObject();

		if ( rs.next() ) {
			
			ResponseObj.put("returnCode", 200);
			ResponseObj.put("user_id", rs.getString(1));
			ResponseObj.put("user_full_name", rs.getString(2));
			ResponseObj.put("user_mobile", rs.getString(3));
			ResponseObj.put("user_location", rs.getString(4));
			ResponseObj.put("user_auth", rs.getString(5));
		} else {
			
			ResponseObj.put("returnCode", 400);
			ResponseObj.put("errorString", "User is not present");
			ResponseObj.put("returnToken", "");
		}

		return ResponseObj;
//		return ResponseObj;
	}

}
