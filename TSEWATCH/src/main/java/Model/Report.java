package Model;

import java.util.ArrayList;
import java.util.Date;

public class Report {
	private String name;
	private ArrayList<Avis> listAvis2WriteIn; 
	private Date dateGenerated;
	
	public Report(String name, ArrayList<Avis> listAvis2WriteIn, Date dateGenerated) {
		super();
		this.name = name;
		this.listAvis2WriteIn = listAvis2WriteIn;
		this.dateGenerated = dateGenerated;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<Avis> getListAvis2WriteIn() {
		return listAvis2WriteIn;
	}


	public void setListAvis2WriteIn(ArrayList<Avis> listAvis2WriteIn) {
		this.listAvis2WriteIn = listAvis2WriteIn;
	}


	public Date getDateGenerated() {
		return dateGenerated;
	}


	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}
	
	
	
	
	
	
	
}
