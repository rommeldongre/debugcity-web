package util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import pojo.Incident;

public class IncidentDb {

	DataBaseConn dbconn;
	Statement stmt;
	String query;
	Connection con;
	ResultSet rs;

	public JSONObject getIncident(Incident incident_object) throws SQLException, JSONException {

		dbconn = new DataBaseConn();
		con = dbconn.setConnection();
		String query = "select * from incident where incident_id=" + incident_object.getIncident_id();
		rs = dbconn.getResult(query, con);
		JSONObject ResponseObj = new JSONObject();

		if (rs.next()) {

			ResponseObj.put("returnCode", 200);
			ResponseObj.put("incident_id", rs.getString(1));
			ResponseObj.put("incident_lat", rs.getString(2));
			ResponseObj.put("incident_long", rs.getString(3));
			ResponseObj.put("incident_category", rs.getString(4));
			ResponseObj.put("incident_locality", rs.getString(5));
			ResponseObj.put("incident_submitter", rs.getString(6));
			ResponseObj.put("incident_owner", rs.getString(7));
			ResponseObj.put("incident_state", rs.getString(8));
			ResponseObj.put("incident_date_created", rs.getString(9));
			ResponseObj.put("incident_date_closed", rs.getString(10));
			ResponseObj.put("incident_severity", rs.getString(11));
			ResponseObj.put("incident_notes", rs.getString(12));
			ResponseObj.put("incident_votes", rs.getString(13));
		} else {

			ResponseObj.put("returnCode", 400);
			ResponseObj.put("errorString", "Incident is not present");
			ResponseObj.put("returnToken", "");

		}

		return ResponseObj;

	}

	public int voteIncident(Incident my_incident) throws SQLException {

		dbconn = new DataBaseConn();
		con = dbconn.setConnection();

		String sql = "update incident set incident_votes = incident_votes +1 where incident_id = ?";
		PreparedStatement prep = con.prepareStatement(sql);
		prep.setInt(1, my_incident.getIncident_id());

		int flag = prep.executeUpdate();
		prep.close();

		return flag;
	}

}
