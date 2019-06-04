package Model;

public class AvisST {
	private String title;
	private String nameOf;
	private String address;
	private String phoneNum;
	private String reference;
	private String typeOfMarket;
	private String description;
	private String deadline;
	
	public AvisST(String title, String nameOf, String address, String phoneNum, String reference, String typeOfMarket,
			String description, String deadline) {
		super();
		this.title = title;
		this.nameOf = nameOf;
		this.address = address;
		this.phoneNum = phoneNum;
		this.reference = reference;
		this.typeOfMarket = typeOfMarket;
		this.description = description;
		this.deadline = deadline;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNameOf() {
		return nameOf;
	}
	public void setNameOf(String nameOf) {
		this.nameOf = nameOf;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getTypeOfMarket() {
		return typeOfMarket;
	}
	public void setTypeOfMarket(String typeOfMarket) {
		this.typeOfMarket = typeOfMarket;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public void printInfo() {
		System.out.println("-------------------------------------------------------");
		System.out.println("| Title: " + this.title);
		System.out.println("| Reference: " + this.reference);
		System.out.println("| Name: " + this.nameOf);
		System.out.println("| Address: " + this.address);
		System.out.println("| Phone: " + this.phoneNum);
		System.out.println("| Type of market: " + this.typeOfMarket);
		System.out.println("| Description: " + this.description);
		System.out.println("| Deadline: " + this.deadline);
		System.out.println("-------------------------------------------------------");
		System.out.println("");
	}
	
	

}
