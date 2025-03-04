package element;

public enum CustomerInformationEnum {
	NAME("Name"),
	TELEPHONE("Telephone"),
	POSTCODE("Postcode"),
	ADDRESS1("Address 1"),
	ADDRESS2("Address 2"),
	NOTES("Notes"); //TODO makes notes TextArea instead of TextField
	
	private final String name; // todo implement regex to convert (name,address1) to (Name,Address 1)
	
	CustomerInformationEnum(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
