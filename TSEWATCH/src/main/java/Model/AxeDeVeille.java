package Model;

import java.util.ArrayList;

public class AxeDeVeille {
	
	private String name;
	private ArrayList<String> keywords;
	
	
	
	
	public AxeDeVeille(String name, ArrayList<String> keywords) {
		super();
		this.name = name;
		this.keywords = keywords;
	}
	
	
	public AxeDeVeille() {
		keywords = new ArrayList<String>();
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public ArrayList<String> getKeywords() {
		return keywords;
	}


	public void setKeywords(ArrayList<String> keywords) {
		this.keywords = keywords;
	}
	
	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}
	
	//TODO
	public void save2File() {
		
	}
	
	//TODO
	public void readFromFile() {
		
	}
	
}
