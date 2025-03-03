package menu;

import element.CustomerInformationField;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Order implements Cloneable{
	private final ZonedDateTime zonedDateTime;
	private HashMap<CustomerInformationField, String> fields;
	private List<Item> items;
	
	public Order() {
		this.zonedDateTime = ZonedDateTime.now();
		this.fields = new HashMap<>();
		for (CustomerInformationField customerInformationField : CustomerInformationField.values()) {
			fields.put(customerInformationField, "");
		}
		this.items = new ArrayList<>();
	}
	
	public Order(HashMap<CustomerInformationField, String> fields, List<Item> items) {
		this();
		
		for (CustomerInformationField customerInformationField : fields.keySet()) {
			this.fields.put(customerInformationField, fields.get(customerInformationField));
		}
		
		this.items = items;
	}
	
	public String getField(CustomerInformationField customerInformationField) { //TODO improve safety by returning copy instead of original
		return fields.get(customerInformationField);
	}
	public List<Item> getItems() {
		return items;
	}
	
	public void setField(CustomerInformationField customerInformationField, String string) {
		fields.put(customerInformationField, string);
	}
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}
	public void removeItem(Item item) {
		items.remove(item);
	}
	
	@Override
	public Order clone() {
		try {
			Order order = (Order) super.clone();
			// keep the zonedDateTime constant throughout clones
			
			// copy over customer fields
			for (CustomerInformationField customerInformationField : CustomerInformationField.values()) {
				order.setField(customerInformationField, getField(customerInformationField));
			}
			
			// copy over items
			order.items = new ArrayList<>();
			for (Item item : items) {
				order.items.add(item.clone());
			}
			
			return order;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError("Cloning failed", e);
		}
	}
	
	@Override
	public String toString() {
		return String.format("%02d:%02d:%02d", zonedDateTime.getHour(), zonedDateTime.getMinute(), zonedDateTime.getSecond());
	}
}
