package Model;


public class Client {
	private String name;
	private String email;
	private Report report2Send;
	
	
	public Client(String name, String email, Report report2Send) {
		this.name = name;
		this.email = email;
		this.report2Send = report2Send;
	}
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Report getReport2Send() {
		return report2Send;
	}
	public void setReport2Send(Report report2Send) {
		this.report2Send = report2Send;
	}
	
	
 }
