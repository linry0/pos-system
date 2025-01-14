package menu;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
	private final ZonedDateTime zonedDateTime;
	private String name;
	private String telephone;
	private String postcode;
	private String address1;
	private String address2;
	private String notes;
	private List<Item> items; //TODO change to ArrayList<Item> later
	
	public Order() {
		this.zonedDateTime = ZonedDateTime.now();
		this.name = "";
		this.telephone = "";
		this.postcode = "";
		this.address1 = "";
		this.address2 = "";
		this.notes = "";
		this.items = new ArrayList<>();
	}
	
	public Order(String name, String telephone, String postcode, String address1, String address2, String notes, List<Item> items) {
		this.zonedDateTime = ZonedDateTime.now();
		this.name = name;
		this.telephone = telephone;
		this.postcode = postcode;
		this.address1 = address1;
		this.address2 = address2;
		this.notes = notes;
		this.items = items;
	}
	
	//TODO improve safety by returning copy instead of original
	public ZonedDateTime getZonedDateTime() {
		return zonedDateTime;
	}
	public String getName() {
		return name;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getPostcode() {
		return postcode;
	}
	public String getAddress1() {
		return address1;
	}
	public String getAddress2() {
		return address2;
	}
	public String getNotes() {
		return notes;
	}
	public List<Item> getItems() {
		return items;
	}
	//TODO makes notes TextArea instead of TextField
	
	public void setName(String name) {
		this.name = name;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
}
