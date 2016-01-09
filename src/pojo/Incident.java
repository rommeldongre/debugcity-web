package pojo;

import java.sql.Date;

public class Incident {

	int incident_id;
	String incident_lat;
	String incident_long;
	String incident_category;
	String incident_locality;
	String incident_submitter;
	String incident_owner;
	String incident_state;
	Date incident_date_created;
	Date incident_date_closed;
	int incident_severity;
	String incident_notes;
	String incident_votes;
	public int getIncident_id() {
		return incident_id;
	}
	public void setIncident_id(int incident_id) {
		this.incident_id = incident_id;
	}
	public String getIncident_lat() {
		return incident_lat;
	}
	public void setIncident_lat(String incident_lat) {
		this.incident_lat = incident_lat;
	}
	public String getIncident_long() {
		return incident_long;
	}
	public void setIncident_long(String incident_long) {
		this.incident_long = incident_long;
	}
	public String getIncident_category() {
		return incident_category;
	}
	public void setIncident_category(String incident_category) {
		this.incident_category = incident_category;
	}
	public String getIncident_locality() {
		return incident_locality;
	}
	public void setIncident_locality(String incident_locality) {
		this.incident_locality = incident_locality;
	}
	public String getIncident_submitter() {
		return incident_submitter;
	}
	public void setIncident_submitter(String incident_submitter) {
		this.incident_submitter = incident_submitter;
	}
	public String getIncident_owner() {
		return incident_owner;
	}
	public void setIncident_owner(String incident_owner) {
		this.incident_owner = incident_owner;
	}
	public String getIncident_state() {
		return incident_state;
	}
	public void setIncident_state(String incident_state) {
		this.incident_state = incident_state;
	}
	public Date getIncident_date_created() {
		return incident_date_created;
	}
	public void setIncident_date_created(Date incident_date_created) {
		this.incident_date_created = incident_date_created;
	}
	public Date getIncident_date_closed() {
		return incident_date_closed;
	}
	public void setIncident_date_closed(Date incident_date_closed) {
		this.incident_date_closed = incident_date_closed;
	}
	public int getIncident_severity() {
		return incident_severity;
	}
	public void setIncident_severity(int incident_severity) {
		this.incident_severity = incident_severity;
	}
	public String getIncident_notes() {
		return incident_notes;
	}
	public void setIncident_notes(String incident_notes) {
		this.incident_notes = incident_notes;
	}
	public String getIncident_votes() {
		return incident_votes;
	}
	public void setIncident_votes(String incident_votes) {
		this.incident_votes = incident_votes;
	}
		
}
