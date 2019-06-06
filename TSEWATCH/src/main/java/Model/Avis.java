package Model;

public class Avis {
	String date;
	String titre;
	String link;
	String location = null; //not every site has the information of the location
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public Avis(String date,String titre,String link){
		this.date = date;
		this.titre = titre;
		this.link = link;
	}
	public Avis(String date,String titre,String link,String location){
		this.date = date;
		this.titre = titre;
		this.link = link;
		this.location = location;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
	public void print() {
		System.out.println(this.date);
		System.out.println("titre: "+ titre);
		System.out.println("link: "+link);
		System.out.println("location: " +location);
		System.out.println("");
	}
}
